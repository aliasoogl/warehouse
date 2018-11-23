
package com.sample.warehouse.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sample.warehouse.config.GeneralConfiguration;
import com.sample.warehouse.data.DataGenerator;
import com.sample.warehouse.domain.CurrencySummary;
import com.sample.warehouse.domain.Deal;
import com.sample.warehouse.dto.RecordDto;
import com.sample.warehouse.exception.StorageException;
import com.sample.warehouse.repo.CurrencySummaryRepository;
import com.sample.warehouse.repo.InvalidRecordRepository;
import com.sample.warehouse.repo.ValidRecordRepository;
import com.sample.warehouse.repo.WarehouseRepository;
import com.sample.warehouse.response.UploadedFileMetaInfoResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(WarehouseServiceImpl.class)
@ContextConfiguration(classes = GeneralConfiguration.class)
public class WarehouseServiceTest extends DataGenerator {

	@Value("${storageLocation}")
	private String storageLocation;

	@Value("${validExtenstion}")
	private String validExtenstion;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private GeneralConfiguration generalConfiguration;

	@MockBean
	private WarehouseRepository warehouseRepo;
	@MockBean
	private ValidRecordRepository validRecordRepo;
	@MockBean
	private InvalidRecordRepository invalidRecordRepo;

	@MockBean
	private CurrencySummaryRepository currencySummaryRepository;

	private WarehouseService warehouseService;

	@Before
	public void init() {
		generalConfiguration = new GeneralConfiguration(storageLocation, validExtenstion);
		warehouseService = new WarehouseServiceImpl(warehouseRepo, validRecordRepo,
				invalidRecordRepo, currencySummaryRepository, generalConfiguration);
	}

	@Test(expected = StorageException.class)
	public void testUploadFailure() {
		String fileName = getRandomFileName();
		Deal deal = new Deal();
		deal.setId(1);
		deal.setFileName(fileName);
		Mockito.when(warehouseRepo.save(Mockito.any(Deal.class))).thenReturn(deal);
		MockMultipartFile uploadedFile = new MockMultipartFile("file", fileName,
				"text/plain", "".getBytes());
		warehouseService.store(uploadedFile);
	}

	@Test
	public void testUploadRecord() {
		String fileName = getRandomFileName();
		Deal deal = new Deal();
		deal.setId(1);
		deal.setFileName(fileName);
		Mockito.when(warehouseRepo.save(Mockito.any(Deal.class))).thenReturn(deal);
		MockMultipartFile uploadedFile = new MockMultipartFile("file", fileName,
				"text/plain", getRawData(100, true).getBytes());
		warehouseService.store(uploadedFile);
	}

	@Test(expected = StorageException.class)
	public void duplicateFileUpload() {
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

		MockMultipartFile uploadedFile = new MockMultipartFile("file", fileName,
				"text/plain", getRawData(100, true).getBytes());
		warehouseService.store(uploadedFile);
		UploadedFileMetaInfoResponse response = warehouseService.analyze(fileName);
		Assert.assertTrue(response.getProcessDuration() == 10L);
		Assert.assertTrue(response.getIsValid());
	}

	@Test
	public void analyzeUploadedFileInfo() {
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

		UploadedFileMetaInfoResponse response = warehouseService.analyze(fileName);
		Assert.assertTrue(response.getProcessDuration() == 10L);
		Assert.assertTrue(response.getIsValid());
	}

	@Test
	public void testSaveCurrencySummaries() {
		List<RecordDto> records = getDatas(100);
		warehouseService.saveCurrencySummary(records);
	}

	@Test
	public void testSaveMultipleCurrencySummaries() {
		String currencyISO = getRandomISOCode();

		List<CurrencySummary> summaries = new ArrayList<>();
		CurrencySummary currency = new CurrencySummary();
		currency.setCurrencyISOCode(currencyISO);
		currency.setDealCounts(1L);
		currency.setInvalidDealCounts(1L);
		currency.setValidDealCounts(1L);
		summaries.add(currency);

		Mockito.when(currencySummaryRepository.findAll()).thenReturn(summaries);
		RecordDto record = getDatas(1).get(0);
		record.setOrderingCurrency(currencyISO);

		warehouseService.saveCurrencySummary(Arrays.asList(record));
	}

}
