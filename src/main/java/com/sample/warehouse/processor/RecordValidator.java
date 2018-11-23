package com.sample.warehouse.processor;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.warehouse.dto.RecordDto;

/**
 * Here Records are validated based on their conditions.
 * @author Pranish Dahal
 * 
 */
public class RecordValidator {

	/**
	 * Records are validated based on their conditions.<br>
	 * 1.Missing Value,<br>
	 * 2.Currency Code,<br>
	 * 3.Deal Amount<br>
	 * @param record
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public void validateRecords(RecordDto record) {
		validateByChain(record);
	}

	/**
	 * This calls all the validator for the validation.
	 * @param record
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	private void validateByChain(RecordDto record) {
		List<Validator> validators = Arrays.asList(new IgnoringValidator(),
				new MissingValueValidator(), new CurrencyValidator(),
				new AmountValidator());
		for (Validator validator : validators)
			validator.validate(record);

	}

}

interface Validator {
	/**
	 * If the record is valid , it is marked as valid and vice versa.
	 * @param record
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	void validate(RecordDto record);
}

/**
 * This marks the record as ignorable if all the fields are empty.
 * @author Pranish Dahal
 * 
 */
class IgnoringValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.warehouse.processor.Validator#validate(com.sample.warehouse.dto.
	 * RecordDto)
	 */
	@Override
	public void validate(RecordDto record) {
		if (record.isValid()) {
			if (record.getCreatedDate() == null && record.getDealAmount().trim().isEmpty()
					&& record.getId().trim().isEmpty()
					&& record.getOrderingCurrency().trim().isEmpty()
					&& record.getToCurrency().trim().isEmpty()) {
				record.setIgnorable(Boolean.TRUE);
			}
		}

	}

}

/**
 * This validates the deal amount whether the amount is less than zero or not.
 * @author Pranish Dahal
 * 
 */
class AmountValidator implements Validator {

	private static final Logger LOG = LoggerFactory.getLogger(AmountValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.warehouse.processor.Validator#validate(com.sample.warehouse.dto.
	 * RecordDto)
	 */
	@Override
	public void validate(RecordDto record) {
		if (record.isValid()) {
			try {
				Long dealAmount = Long.valueOf(record.getDealAmount());
				if (dealAmount < 0)
					record.setValid(Boolean.FALSE);
			}
			catch (Exception e) {
				LOG.error("Error during number parsing. {}", e.getMessage());
				record.setValid(Boolean.FALSE);
			}
		}

	}

}

/**
 * If any of the fields is missing or empty or null the record is considered as invalid.
 * @author Pranish Dahal
 * 
 */
class MissingValueValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.warehouse.processor.Validator#validate(com.sample.warehouse.domain.
	 * Record)
	 */
	@Override
	public void validate(RecordDto record) {
		if (record.isValid()) {
			if (record.getCreatedDate() == null)
				record.setValid(Boolean.FALSE);
			if (record.getDealAmount().trim().isEmpty())
				record.setValid(Boolean.FALSE);
			if (record.getId().trim().isEmpty())
				record.setValid(Boolean.FALSE);
			if (record.getOrderingCurrency().trim().isEmpty())
				record.setValid(Boolean.FALSE);
			if (record.getToCurrency().trim().isEmpty())
				record.setValid(Boolean.FALSE);
		}
	}

}

/**
 * Validates the currency whether the currency matches with the codes or not.
 * @author Pranish Dahal
 * 
 */
class CurrencyValidator implements Validator {

	private final static String allCurrencies = "AED,AFA,ALL,ANG,AOK,ARP,ATS,AUD,BBD,BDT,BEC,BEF,BEL,BGL,BHD,BIF,BMD,BND,BOP,BRC,BSD,BUK,BWP,BZD,CAD,CHF,CLP,CNY,COP,CRC,CSK,CUP,CVE,CYP,DDM,DEM,DJF,DKK,DOP,DZD,ECS,EGP,ESA,ESB,ESP,ETB,EUR,FIM,FJD,FKP,FRF,GBP,GHC,GIP,GMD,GNS,GQE,GRD,GTQ,GWP,GYD,HKD,HNL,HTG,HUF,IDR,IEP,ILS,INR,IQD,IRR,ISK,ITL,JMD,JOD,JPY,KES,KHR,KMF,KPW,KRW,KWD,KYD,LAK,LBP,LKR,LRD,LSM,LUF,LYD,MAD,	MGF,MLF,MNT,MOP,MRO,	MTP,MUR,	MVR,MWK,MXP,MYR,MZM,NGN,NIC,NLG,NOK,NPR,NZD,OMR,PAB,PES,PGK,PHP,PKR,PLZ,PTE,PYG,QAR,ROL,RWF,SAR,SBD,SCR,SDP,SEK,SGD,SHP,SLL,SOS,SRG,STD,SUR,SVC,SYP,SZL,THB,TND,TOP,TPE,TRL,TTD,TWD,TZS,UGS,USD,USN,USS,UYP,VND,VEB,VUV,WST,XAF,XAU,XBA,XBB,XBC,XBD,XCD,XDR,XEU,XOF,XPF,XXX,YDD,YER,YUD,ZAL,ZAR,ZMK,ZRZ,ZWD";
	private final static List<String> currenciesList = Arrays
			.asList(allCurrencies.split(","));

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.warehouse.processor.Validator#validate(com.sample.warehouse.dto.
	 * RecordDto)
	 */
	@Override
	public void validate(RecordDto record) {
		if (record.isValid()) {
			if (!currenciesList.contains(record.getToCurrency().toUpperCase()))
				record.setValid(Boolean.FALSE);
			if (!currenciesList.contains(record.getOrderingCurrency().toUpperCase()))
				record.setValid(Boolean.FALSE);
		}

	}

}
