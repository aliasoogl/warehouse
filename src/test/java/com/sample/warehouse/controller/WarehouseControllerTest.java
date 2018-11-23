package com.sample.warehouse.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sample.warehouse.data.DataGenerator;
import com.sample.warehouse.domain.Deal;
import com.sample.warehouse.repo.WarehouseRepository;
import com.sample.warehouse.response.UploadedFileMetaInfoResponse;
import com.sample.warehouse.service.WarehouseService;

@RunWith(SpringRunner.class)
@WebMvcTest(WarehouseController.class)
public class WarehouseControllerTest extends DataGenerator {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@MockBean(name = "warehouseService")
	private WarehouseService warehouseService;

	@MockBean
	private WarehouseRepository warehouseRepo;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void shouldContainUploadResource() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/upload")).andExpect(status().isOk());
	}

	@Test
	public void shouldUploadAndAnalyzeFile() throws Exception {
		String fileName = getRandomFileName();

		Deal deal = new Deal();
		deal.setId(1);
		deal.setFileName(fileName);
		deal.setProcessDuration(10000L);
		deal.setInvalidRecordSize(10L);
		deal.setValidRecordSize(10L);

		Mockito.when(warehouseRepo.save(Mockito.any(Deal.class))).thenReturn(deal);
		Mockito.when(warehouseRepo.existsByFileName(fileName)).thenReturn(Boolean.TRUE);
		Mockito.when(warehouseRepo.findByFileName(fileName)).thenReturn(deal);

		UploadedFileMetaInfoResponse response = new UploadedFileMetaInfoResponse();
		response.setIsValid(Boolean.TRUE);
		response.setInvalidImportedRecords(deal.getInvalidRecordSize());
		response.setValidImportedRecords(deal.getValidRecordSize());
		response.setProcessDuration(deal.getProcessDuration());

		Mockito.when(warehouseService.analyze(fileName)).thenReturn(response);
		MockMultipartFile uploadedFile = new MockMultipartFile("file", fileName,
				"text/plain", getRawData(100).getBytes());

		mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(uploadedFile))
				.andExpect(MockMvcResultMatchers.view().name("metaResult"))
				.andExpect(MockMvcResultMatchers.model().attribute("meta", response));

	}

	@Test
	public void shouldAnalyzeByFileName() throws Exception {
		String fileName = getRandomFileName();

		Deal deal = new Deal();
		deal.setId(1);
		deal.setFileName(fileName);
		deal.setProcessDuration(10000L);
		deal.setInvalidRecordSize(10L);
		deal.setValidRecordSize(10L);

		UploadedFileMetaInfoResponse response = new UploadedFileMetaInfoResponse();
		response.setIsValid(Boolean.TRUE);
		response.setInvalidImportedRecords(deal.getInvalidRecordSize());
		response.setValidImportedRecords(deal.getValidRecordSize());
		response.setProcessDuration(deal.getProcessDuration());

		Mockito.when(warehouseService.analyze(fileName)).thenReturn(response);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/analyze").param("fileName", fileName))
				.andExpect(MockMvcResultMatchers.view().name("metaResult"))
				.andExpect(MockMvcResultMatchers.model().attribute("meta", response));
	}

	@Test
	public void shouldContainIndexResource() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
	}

	@Test
	public void shouldContainAnalyzeResource() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/analyze"))
				.andExpect(status().isOk());
	}

}
