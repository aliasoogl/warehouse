package com.sample.warehouse.processor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sample.warehouse.data.DataGenerator;
import com.sample.warehouse.dto.RecordDto;

/**
 * Test the recordValidator
 * @author Pranish Dahal
 * 
 */
public class RecordValidatorTest extends DataGenerator {

	private RecordValidator recordValidator;

	@Before
	public void init() {
		recordValidator = new RecordValidator();
	}

	@Test
	public void testValidRecord() {
		RecordDto record = getDatas(1).get(0);
		recordValidator.validateRecords(record);
		Assert.assertTrue(record.isValid() == Boolean.TRUE);
	}

	@Test
	public void testInvalidAmountRecord() {
		RecordDto record = getDatas(1).get(0);
		record.setDealAmount("AA");
		recordValidator.validateRecords(record);
		Assert.assertTrue(record.isValid() == Boolean.FALSE);
	}

	@Test
	public void testInvalidAmountLessThanZeroRecord() {
		RecordDto record = getDatas(1).get(0);
		record.setDealAmount("-5");
		recordValidator.validateRecords(record);
		Assert.assertTrue(record.isValid() == Boolean.FALSE);
	}

	@Test
	public void testInvalidCurrencyISORecord() {
		RecordDto record = getDatas(1).get(0);
		record.setOrderingCurrency("AA");
		record.setToCurrency("BB");
		recordValidator.validateRecords(record);
		Assert.assertTrue(record.isValid() == Boolean.FALSE);
	}

	@Test
	public void testInvalidTimeRecord() {
		RecordDto record = getDatas(1).get(0);
		record.setCreatedDate(null);
		recordValidator.validateRecords(record);
		Assert.assertTrue(record.isValid() == Boolean.FALSE);
	}

	@Test
	public void testMissingRecord() {
		RecordDto record = getDatas(1).get(0);
		record.setValid(Boolean.TRUE);
		record.setCreatedDate(null);
		record.setOrderingCurrency("");
		record.setDealAmount("");
		record.setToCurrency("");
		record.setId("");
		recordValidator.validateRecords(record);
		Assert.assertTrue(record.isValid() == Boolean.FALSE);
		Assert.assertTrue(record.isIgnorable());
	}

}
