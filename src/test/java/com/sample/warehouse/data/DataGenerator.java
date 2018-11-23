
package com.sample.warehouse.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.sample.warehouse.dto.RecordDto;

/**
 * Generates random data for the project.
 * @author Pranish Dahal
 * @version 2.0.0
 * @since , Nov 20, 2018
 */
public class DataGenerator {

	private final static String allCurrencies = "AED,AFA,ALL,ANG,AOK,ARP,ATS,AUD,BBD,BDT,BEC,BEF,BEL,BGL,BHD,BIF,BMD,BND,BOP,BRC,BSD,BUK,BWP,BZD,CAD,CHF,CLP,CNY,COP,CRC,CSK,CUP,CVE,CYP,DDM,DEM,DJF,DKK,DOP,DZD,ECS,EGP,ESA,ESB,ESP,ETB,EUR,FIM,FJD,FKP,FRF,GBP,GHC,GIP,GMD,GNS,GQE,GRD,GTQ,GWP,GYD,HKD,HNL,HTG,HUF,IDR,IEP,ILS,INR,IQD,IRR,ISK,ITL,JMD,JOD,JPY,KES,KHR,KMF,KPW,KRW,KWD,KYD,LAK,LBP,LKR,LRD,LSM,LUF,LYD,MAD,	MGF,MLF,MNT,MOP,MRO,	MTP,MUR,	MVR,MWK,MXP,MYR,MZM,NGN,NIC,NLG,NOK,NPR,NZD,OMR,PAB,PES,PGK,PHP,PKR,PLZ,PTE,PYG,QAR,ROL,RWF,SAR,SBD,SCR,SDP,SEK,SGD,SHP,SLL,SOS,SRG,STD,SUR,SVC,SYP,SZL,THB,TND,TOP,TPE,TRL,TTD,TWD,TZS,UGS,USD,USN,USS,UYP,VND,VEB,VUV,WST,XAF,XAU,XBA,XBB,XBC,XBD,XCD,XDR,XEU,XOF,XPF,XXX,YDD,YER,YUD,ZAL,ZAR,ZMK,ZRZ,ZWD";
	private final static String[] currenciesList = allCurrencies.split(",");

	/**
	 * Contains the record files as: Deal Unique Id, From Currency ISO Code "Ordering
	 * Currency", To Currency ISO Code, Deal timestamp, Deal Amount in ordering currency
	 * @param size
	 * @return
	 * @since , Modified In: @version, By @author
	 */
	public List<RecordDto> getDatas(int size) {
		List<RecordDto> records = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			int orderingCurrencyRandom = (int) (Math.random() * currenciesList.length
					- 1);
			int toCurrencyRandom = (int) (Math.random() * currenciesList.length - 1);
			int amount = (int) (Math.random() * 100) + 1;

			records.add(new RecordDto(i + "", currenciesList[orderingCurrencyRandom],
					currenciesList[toCurrencyRandom], new Date(), amount + ""));
		}
		return records;
	}

	/**
	 * This returns data containing at least one invalid if true. Contains the record
	 * files as: Deal Unique Id, From Currency ISO Code "Ordering Currency", To Currency
	 * ISO Code, Deal timestamp, Deal Amount in ordering currency
	 * @param size
	 * @param generateInvalid
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public List<RecordDto> getDatas(int size, Boolean generateInvalid) {
		if (!generateInvalid)
			return getDatas(size);
		int numberOfInvalid = new Random().nextInt(size);
		if (numberOfInvalid <= 0)
			numberOfInvalid = 1;
		List<RecordDto> records = getDatas(size);
		Collections.shuffle(records);
		RecordInvalidator invalidator = new RecordInvalidator();
		for (int i = 0; i < numberOfInvalid; i++) {
			invalidator.invalidate(records.get(i));
		}
		return records;
	}

	/**
	 * Returns the raw generated data.(In string)
	 * @param size
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public String getRawData(int size) {
		List<RecordDto> records = getDatas(size);
		StringBuffer rawData = new StringBuffer();
		for (RecordDto record : records) {
			rawData.append(record).append("\n");
		}
		return rawData.toString();
	}

	/**
	 * Returns the raw generated data.(In string)
	 * @param size
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public String getRawData(int size, Boolean includeInvalid) {
		if (!includeInvalid)
			return getRawData(size);
		List<RecordDto> records = getDatas(size, includeInvalid);
		StringBuffer rawData = new StringBuffer();
		for (RecordDto record : records) {
			rawData.append(record).append("\n");
		}
		return rawData.toString();
	}

	/**
	 * Generates random file name of length 5 with csv as extension.
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public String getRandomFileName() {
		String holder = "abcdefghijklmnopqrstuvwxyz";
		int size = 5;
		StringBuffer name = new StringBuffer();
		for (int i = 0; i < size; i++) {
			int index = (int) (Math.random() * holder.length());
			name.append(holder.charAt(index));
		}
		return name.toString().concat(".csv");
	}

	/**
	 * Generates a random ISO Currency code .
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public String getRandomISOCode() {
		return currenciesList[new Random().nextInt(currenciesList.length)];
	}

}

class RecordInvalidator {

	/**
	 * Invalidates the record in random way.
	 * @param record
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public void invalidate(RecordDto record) {
		List<Invalidator> invalidators = Arrays.asList(new MissingValueInValidator(),
				new CurrencyInValidator(), new AmountInValidator());
		Integer choice = new Random().nextInt(invalidators.size());
		invalidators.get(choice).invalidate(record);
		record.setValid(Boolean.FALSE);
	}

}

interface Invalidator {
	/**
	 * Invalidates the record based on the type.
	 * @param record
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	void invalidate(RecordDto record);
}

/**
 * This class set the ordering currency and deal amount as missing.
 * @author Pranish Dahal
 * 
 */
class MissingValueInValidator implements Invalidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.warehouse.data.Invalidator#invalidate(com.sample.warehouse.dto.
	 * RecordDto)
	 */
	@Override
	public void invalidate(RecordDto record) {
		record.setOrderingCurrency("");
		record.setDealAmount("");
	}

}

/**
 * This sets the random currencyISO code.
 * @author Pranish Dahal
 * 
 */
class CurrencyInValidator implements Invalidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.warehouse.data.Invalidator#invalidate(com.sample.warehouse.dto.
	 * RecordDto)
	 */
	@Override
	public void invalidate(RecordDto record) {
		record.setOrderingCurrency("AAA");
	}

}

/**
 * This set non-numeric value in deal amount.
 * @author Pranish Dahal
 * 
 */
class AmountInValidator implements Invalidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sample.warehouse.data.Invalidator#invalidate(com.sample.warehouse.dto.
	 * RecordDto)
	 */
	@Override
	public void invalidate(RecordDto record) {
		record.setDealAmount("AAA");
	}

}
