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
import com.sample.warehouse.domain.InvalidRecord;
import com.sample.warehouse.dto.RecordDto;

/**
 * Test class for InValidRecordRepository
 * @author Pranish Dahal
 * 
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class InvalidRecordRepositoryTest extends DataGenerator {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private InvalidRecordRepository invalidRecordRepository;

	@Before
	public void init() {

	}

	@Test
	public void testInValidRecordInsertRecord() {
		String fileName = "filename.csv";
		Deal deal = new Deal();
		deal.setFileName(fileName);
		deal = testEntityManager.persistFlushFind(deal);

		List<RecordDto> recordDtos = getDatas(100, true);
		for (RecordDto r : recordDtos) {
			if (!r.isValid()) {
				InvalidRecord record = new InvalidRecord(r);
				record.setDeal(deal);
				invalidRecordRepository.save(record);
			}
		}

		List<InvalidRecord> invalidRecords = invalidRecordRepository
				.findByDealId(deal.getId());

		Assert.assertTrue(invalidRecords != null && invalidRecords.size() > 0);

		Deal retrievedDeal = warehouseRepository.findByFileName(fileName);
		Assert.assertTrue(
				retrievedDeal.getInvalidRecords().size() == invalidRecords.size());
	}

}
