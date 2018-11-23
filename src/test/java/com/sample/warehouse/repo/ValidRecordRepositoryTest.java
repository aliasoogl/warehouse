
package com.sample.warehouse.repo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.warehouse.data.DataGenerator;
import com.sample.warehouse.domain.Deal;
import com.sample.warehouse.domain.ValidRecord;
import com.sample.warehouse.dto.RecordDto;

/**
 * Test class for ValidRecordRepository
 * @author Pranish Dahal
 * 
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ValidRecordRepositoryTest extends DataGenerator {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private ValidRecordRepository validRecordRepository;

	@Before
	public void init() {

	}

	@Test
	public void testValidRecordInsertRecord() {
		String fileName = "filename.csv";
		Deal deal = new Deal();
		deal.setFileName(fileName);
		deal = testEntityManager.persistFlushFind(deal);

		List<RecordDto> recordDtos = getDatas(10);
		for (RecordDto r : recordDtos) {
			ValidRecord validRecord = new ValidRecord(r);
			validRecord.setDeal(deal);
			testEntityManager.persistAndFlush(validRecord);
		}

		List<ValidRecord> validRecords = validRecordRepository.findByDealId(deal.getId());

		Assert.assertTrue(validRecords != null && validRecords.size() > 0);

		Deal retrievedDeal = warehouseRepository.findByFileName(fileName);
		Assert.assertTrue(retrievedDeal.getValidRecords().size() == recordDtos.size());

	}
}
