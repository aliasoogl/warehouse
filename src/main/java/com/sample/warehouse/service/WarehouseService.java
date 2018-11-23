
package com.sample.warehouse.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sample.warehouse.dto.RecordDto;
import com.sample.warehouse.response.UploadedFileMetaInfoResponse;

/**
 * Services for Warehouse
 * @author Pranish Dahal
 */
public interface WarehouseService {

	/**
	 * Stores the file in DB within certain time.First validate the file and then follow
	 * the following steps: <br>
	 * 1.Save the file info as deal.<br>
	 * 2.Append the deal information to the record in the received data.<br>
	 * 3.Upload the new appended data (file) to db .<br>
	 * 4.Update the deal table with the number of invalid records and valid records.<br>
	 * 5.Update the currency deal table with the currency count deals.
	 * @param file
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public void store(MultipartFile file);

	/**
	 * This returns the taken to upload the file to DB , number of valid and invalid
	 * records .
	 * @param fileName
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public UploadedFileMetaInfoResponse analyze(String fileName);

	/**
	 * This saves the summaries of the currencies used in records.(maintains accumulative
	 * count of deals per Ordering Currency.)
	 * @param records
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public void saveCurrencySummary(List<RecordDto> records);

}
