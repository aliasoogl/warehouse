
package com.sample.warehouse.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sample.warehouse.config.GeneralConfiguration;
import com.sample.warehouse.domain.CurrencySummary;
import com.sample.warehouse.domain.Deal;
import com.sample.warehouse.dto.RecordDto;
import com.sample.warehouse.exception.StorageException;
import com.sample.warehouse.processor.RecordProcessor;
import com.sample.warehouse.repo.CurrencySummaryRepository;
import com.sample.warehouse.repo.InvalidRecordRepository;
import com.sample.warehouse.repo.ValidRecordRepository;
import com.sample.warehouse.repo.WarehouseRepository;
import com.sample.warehouse.response.UploadedFileMetaInfoResponse;

/**
 * Warehouse service
 * @author Pranish Dahal
 */
@Service
public class WarehouseServiceImpl extends RecordProcessor implements WarehouseService {

	private static final Logger LOG = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	@Autowired
	private GeneralConfiguration generalConfiguration;

	@Autowired
	private WarehouseRepository warehouseRepo;
	@Autowired
	private ValidRecordRepository validRecordRepo;
	@Autowired
	private InvalidRecordRepository invalidRecordRepo;
	@Autowired
	private CurrencySummaryRepository currencySummaryRepo;

	/**
	 * 
	 */
	public WarehouseServiceImpl() {

	}

	public WarehouseServiceImpl(WarehouseRepository warehouseRepo,
			ValidRecordRepository validRecordRepo,
			InvalidRecordRepository invalidRecordRepo,
			CurrencySummaryRepository currencySummaryRepo,
			GeneralConfiguration generalConfig) {
		this.warehouseRepo = warehouseRepo;
		this.validRecordRepo = validRecordRepo;
		this.invalidRecordRepo = invalidRecordRepo;
		this.generalConfiguration = generalConfig;
		this.currencySummaryRepo = currencySummaryRepo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.service.WarehouseService#store(org.springframework.web.multipart.
	 * MultipartFile)
	 */

	@Override
	public void store(MultipartFile file) {
		try {
			if (file == null || file.isEmpty() || file.getSize() == 0) {
				throw new StorageException("File is empty.");
			}
			if (validateFile(file)) {

				Long startTime = System.currentTimeMillis();

				Deal deal = saveDeal(file.getOriginalFilename());
				List<RecordDto> records = appendDealInfoToFile(file, deal);

				splitDataAndSave(records);

				Long numberOfValidRecordSize = records.stream().filter(r -> r.isValid())
						.count();
				Long numberOfInvalidRecordSize = records.stream()
						.filter(r -> !r.isValid()).count();

				Long endTime = System.currentTimeMillis();
				Long exportTime = endTime - startTime;
				deal.setProcessDuration(exportTime);
				deal.setInvalidRecordSize(numberOfInvalidRecordSize);
				deal.setValidRecordSize(numberOfValidRecordSize);
				warehouseRepo.save(deal);

				saveCurrencySummary(records);
			}
			else {
				throw new StorageException(file.getOriginalFilename()
						+ " is already imported.Please use another file.");
			}

		}
		catch (Exception e) {
			throw new StorageException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sample.warehouse.service.WarehouseService#saveCurrencySummary(java.util.List)
	 */
	@Override
	public void saveCurrencySummary(List<RecordDto> records) {
		Map<String, List<RecordDto>> groupedByOrderingCurrency = records.stream()
				.collect(Collectors.groupingBy(RecordDto::getOrderingCurrency));

		List<CurrencySummary> summaries = new ArrayList<>();

		groupedByOrderingCurrency.entrySet().stream().forEach(k -> {

			Long totalRecords = new Long(k.getValue().size());
			Long numberOfValidRecordSize = k.getValue().stream().filter(r -> r.isValid())
					.count();
			Long numberOfInvalidRecordSize = k.getValue().stream()
					.filter(r -> !r.isValid()).count();

			CurrencySummary currencySummary = new CurrencySummary();
			currencySummary.setCurrencyISOCode(k.getKey());
			currencySummary.setDealCounts(totalRecords);
			currencySummary.setInvalidDealCounts(numberOfInvalidRecordSize);
			currencySummary.setValidDealCounts(numberOfValidRecordSize);

			summaries.add(currencySummary);
		});

		List<CurrencySummary> savedSummaries = currencySummaryRepo.findAll();
		if (savedSummaries == null || savedSummaries.isEmpty()) {
			currencySummaryRepo.saveAll(summaries);
		}
		else {
			Map<String, List<CurrencySummary>> currenciesGroup = summaries.stream()
					.collect(Collectors.groupingBy(CurrencySummary::getCurrencyISOCode));

			Map<String, List<CurrencySummary>> savedCurrenciesGroup = savedSummaries
					.stream()
					.collect(Collectors.groupingBy(CurrencySummary::getCurrencyISOCode));

			currenciesGroup.entrySet().forEach(currency -> {
				CurrencySummary toBeSavedSummary = currency.getValue().get(0);
				List<CurrencySummary> savedSummariesListOfACurrency = savedCurrenciesGroup
						.get(currency.getKey());
				if (savedSummariesListOfACurrency != null
						&& savedSummariesListOfACurrency.size() > 0) {
					CurrencySummary savedSummaryOfACurrency = savedSummariesListOfACurrency
							.get(0);
					if (savedSummaryOfACurrency.getDealCounts() != null)
						savedSummaryOfACurrency
								.setDealCounts(savedSummaryOfACurrency.getDealCounts()
										+ toBeSavedSummary.getDealCounts());
					if (savedSummaryOfACurrency.getValidDealCounts() != null)
						savedSummaryOfACurrency.setValidDealCounts(
								savedSummaryOfACurrency.getValidDealCounts()
										+ toBeSavedSummary.getValidDealCounts());
					else
						savedSummaryOfACurrency.setValidDealCounts(
								toBeSavedSummary.getValidDealCounts());
					if (savedSummaryOfACurrency.getInvalidDealCounts() != null)
						savedSummaryOfACurrency.setInvalidDealCounts(
								savedSummaryOfACurrency.getInvalidDealCounts()
										+ toBeSavedSummary.getInvalidDealCounts());
					else
						savedSummaryOfACurrency.setInvalidDealCounts(
								toBeSavedSummary.getInvalidDealCounts());
				}
			});
			savedSummaries.forEach(s -> {

			});
			currencySummaryRepo.saveAll(savedSummaries);
		}
	}

	/**
	 * Split the records into valid and invalid records and upload to DB.
	 * @param records
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private void splitDataAndSave(List<RecordDto> records) {
		Map<Boolean, List<RecordDto>> groupedRecords = records.stream()
				.collect(Collectors.groupingBy(RecordDto::isValid));

		File validFile = null;
		File invalidFile = null;

		if (groupedRecords.containsKey(Boolean.TRUE)) {
			List<RecordDto> validRecords = groupedRecords.get(Boolean.TRUE);
			String tempFileName = "validRecord.csv";
			validFile = makeFileFromData(tempFileName, validRecords);
			validRecordRepo.uploadFile(tempFileName);
		}
		if (groupedRecords.containsKey(Boolean.FALSE)) {
			List<RecordDto> invalidRecords = groupedRecords.get(Boolean.FALSE);
			String tempFileName = "invalidRecord.csv";
			invalidFile = makeFileFromData(tempFileName, invalidRecords);
			invalidRecordRepo.uploadFile(tempFileName);
		}

		if (validFile != null)
			validFile.delete();

		if (invalidFile != null)
			invalidFile.delete();

	}

	/**
	 * Makes the temporary file from the provided records.
	 * @param tempFileName
	 * @param records
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private File makeFileFromData(String tempFileName, List<RecordDto> records) {
		File dataFile = new File(tempFileName);
		FileWriter writer = null;
		try {
			writer = new FileWriter(dataFile);
			StringBuffer bufferRecord = new StringBuffer();
			for (RecordDto record : records) {
				bufferRecord.append(record.generateForUpload()).append("\n");
			}
			writer.write(bufferRecord.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (writer != null)
				try {
					writer.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
		return dataFile;
	}

	/**
	 * Converts the file data to records appending the deal information to the records.
	 * @param file
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private List<RecordDto> appendDealInfoToFile(MultipartFile file, Deal deal) {
		List<RecordDto> records = new ArrayList<>();
		try {
			String data = new String(file.getBytes());
			records.addAll(initRecord(data, deal));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return records;
	}

	/**
	 * Appends the deal information to the records.
	 * @param data
	 * @param deal
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private List<RecordDto> initRecord(String data, Deal deal) {
		List<RecordDto> records = initRecord(data);
		records.forEach(r -> r.setDeal(deal));
		return records;
	}

	/**
	 * Saves the file info into the deal.
	 * @param originalFilename
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private Deal saveDeal(String originalFilename) {
		Deal deal = new Deal();
		deal.setFileName(originalFilename);
		return warehouseRepo.save(deal);
	}

	/**
	 * Validate the file name along with extension.1st it finds whether file is already
	 * uploaded or not and then checks for the file extension.
	 * @param file
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private boolean validateFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (doFileExists(fileName))
			return false;
		if (!checkExtenstion(fileName))
			throw new StorageException(
					"Only files with ( " + generalConfiguration.getValidFileExtenstion()
							+ " ) extensions are allowed.");
		return true;
	}

	/**
	 * Checks whether the file is imported or not
	 * @param fileName
	 * @return
	 * @author zurelsoft
	 * @since , Modified In: @version, By @author
	 */
	private boolean doFileExists(String fileName) {
		return warehouseRepo.existsByFileName(fileName);
	}

	/**
	 * Checks the file extension with the allowed file extension.
	 * @param fileName
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private boolean checkExtenstion(String fileName) {
		int fileLength = fileName.length();
		int dotIndex = fileName.lastIndexOf(".");
		String extenstionType = fileName.substring(dotIndex + 1, fileLength);
		return generalConfiguration.getValidFileExtenstion().contains(extenstionType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.warehouse.service.WarehouseService#analyze(java.lang.String)
	 */
	@Override
	public UploadedFileMetaInfoResponse analyze(String fileName) {
		Boolean hasFile = warehouseRepo.existsByFileName(fileName);
		UploadedFileMetaInfoResponse response = new UploadedFileMetaInfoResponse();
		if (hasFile) {
			response.setIsValid(Boolean.TRUE);
			Deal deal = warehouseRepo.findByFileName(fileName);
			response.setProcessDuration(deal.getProcessDuration() / 1000);
			response.setInvalidImportedRecords(deal.getInvalidRecordSize());
			response.setValidImportedRecords(deal.getValidRecordSize());
		}
		else {
			response.setIsValid(Boolean.FALSE);
		}
		return response;
	}

}
