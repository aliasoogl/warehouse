package com.sample.warehouse.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.warehouse.data.DataGenerator;
import com.sample.warehouse.service.WarehouseService;

/**
 * <<Description Here>>
 * @author Pranish Dahal
 * 
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencySummaryRepositoryTest extends DataGenerator {

	@Autowired
	CurrencySummaryRepository currencySummaryRepository;

	@MockBean
	WarehouseService warehouseService;

	@Autowired
	WarehouseRepository warehouseRepo;

	@Test
	public void insertCurrencyISODetails() {
		String fileName = getRandomFileName();

		MockMultipartFile uploadedFile = new MockMultipartFile("file", fileName,
				"text/plain", getRawData(100).getBytes());
		warehouseService.store(uploadedFile);
		assertThat(currencySummaryRepository.findAll() != null);
		assertThat(currencySummaryRepository.findAll().size() > 0);
	}

	@Test
	public void multipleFileUploadCurrencyUpdate() {
		String fileName = getRandomFileName();
		MockMultipartFile uploadedFile = new MockMultipartFile("file", fileName,
				"text/plain", getRawData(100).getBytes());
		warehouseService.store(uploadedFile);

		fileName = getRandomFileName();
		uploadedFile = new MockMultipartFile("file", fileName, "text/plain",
				getRawData(100).getBytes());
		warehouseService.store(uploadedFile);

		assertThat(currencySummaryRepository.findAll() != null);
		assertThat(currencySummaryRepository.findAll().size() > 0);
	}

}
