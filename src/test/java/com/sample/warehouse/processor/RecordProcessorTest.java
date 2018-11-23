
package com.sample.warehouse.processor;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sample.warehouse.data.DataGenerator;
import com.sample.warehouse.dto.RecordDto;

/**
 * <<Description Here>>
 * @author Pranish Dahal
 * @version 2.0.0
 * @since , Nov 20, 2018
 */

public class RecordProcessorTest extends DataGenerator {

	private RecordProcessor recordProcessor;

	@Before
	public void init() {
		recordProcessor = new RecordProcessor();
	}

	@Test
	public void testMultipleRecords() {
		int dataSize = 10;
		String multipleRawData = getRawData(dataSize);
		List<RecordDto> records = recordProcessor.initRecord(multipleRawData);
		Assert.assertTrue(dataSize == records.size());
	}

	@Test
	public void testSingleRecord() {
		int dataSize = 2;
		String multipleRawData = getRawData(dataSize);
		List<RecordDto> records = recordProcessor.initRecord(multipleRawData);
		assertThat(dataSize == records.size());
		String singleRecord = multipleRawData.split(",")[0];
		Assert.assertTrue(records.get(0).getId().equals(singleRecord.split(",")[0]));
	}

	@Test
	public void testSingleRecordDateFailure() {
		int dataSize = 1;
		String multipleRawData = getRawData(dataSize);
		RecordDto record = recordProcessor.initRecord(multipleRawData).get(0);
		record.setCreatedDate(null);
		RecordDto parsedRecord = recordProcessor.parse(record.toString());
		Assert.assertTrue(parsedRecord.getRecord().getCreatedDate() == null);
	}

	@Test
	public void generateDataFileTest() throws IOException {
		File dataFile = new File(getRandomFileName());
		FileWriter writer = new FileWriter(dataFile);
		List<RecordDto> records = getDatas(100000,true);
		StringBuffer bufferRecord = new StringBuffer();
		for (RecordDto record : records) {
			bufferRecord.append(record).append("\n");
		}
		writer.write(bufferRecord.toString());
		writer.close();
	}

}
