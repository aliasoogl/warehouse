package com.sample.warehouse.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.warehouse.dto.RecordDto;

/**
 * This processes the record for validation and for wrapping.
 * @author Pranish Dahal
 * 
 */
public class RecordProcessor extends RecordValidator {

	private static final Logger LOG = LoggerFactory.getLogger(RecordProcessor.class);

	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * The list of data is partitioned into two list for faster operation.
	 * @param data
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public List<RecordDto> initRecord(String data) {
		String[] datas = data.split("\n");
		List<RecordDto> records = new ArrayList<>();
		for (String record : datas) {
			if (!record.trim().isEmpty()) {
				record = record.trim();
				RecordDto parsedRecord = parse(record);
				validateRecords(parsedRecord);
				if (!parsedRecord.isIgnorable())
					records.add(parsedRecord);
			}
		}
		return records;
	}

	/**
	 * Converts the provided string value to RecordDto object(Parse to records).
	 * @param record
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public RecordDto parse(String record) {
		RecordDto recordState = new RecordDto();
		String[] records = record.split(",");
		RecordDto lengthValidatedRecord = getLengthValidatedRecord(records);
		recordState = new RecordDto(lengthValidatedRecord.getId(),
				lengthValidatedRecord.getOrderingCurrency(),
				lengthValidatedRecord.getToCurrency(),
				lengthValidatedRecord.getCreatedDate(),
				lengthValidatedRecord.getDealAmount());
		return recordState;
	}

	/**
	 * Validates the length of the data with the general length of required data.
	 * @param records
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private RecordDto getLengthValidatedRecord(String[] records) {
		Integer recordLength = records.length;
		String id = "";
		if (recordLength > 0)
			id = records[0];
		String oCurrency = "";
		if (recordLength > 1)
			oCurrency = records[1];
		String tCurrency = "";
		if (recordLength > 2)
			tCurrency = records[2];
		String date = "";
		if (recordLength > 3)
			date = records[3];
		String amount = "";
		if (recordLength > 4)
			amount = records[4];
		return new RecordDto(id, oCurrency, tCurrency, parseDate(date), amount);
	}

	/**
	 * Parses the provided timestamp to date.If the date is not valid null is returned.
	 * @param toDate
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private static Date parseDate(String toDate) {
		if (toDate == null || toDate.trim().isEmpty())
			return null;
		try {
			Date timestamp = df.parse(toDate);
			return timestamp;
		}
		catch (Exception e) {
			LOG.error("Error while parsing date: {}", e.getMessage());
		}
		return null;
	}

}
