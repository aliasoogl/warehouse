package com.sample.warehouse.repo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.warehouse.domain.Deal;

/**
 * <<Description Here>>
 * @author Pranish Dahal
 * @version 2.0.0
 * @since , Nov 20, 2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DealRepositoryTest {

	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Before
	public void init() {

	}

	@Test
	public void storeDealInfo() {
		String fileName = "filename.csv";
		Deal deal = new Deal();
		deal.setFileName(fileName);

		Deal savedData = testEntityManager.persistFlushFind(deal);

		boolean hasFileName = warehouseRepository.existsByFileName(fileName);

		Assert.assertTrue(hasFileName == Boolean.TRUE);
		Assert.assertTrue(savedData.getFileName().equals(fileName));

	}

}
