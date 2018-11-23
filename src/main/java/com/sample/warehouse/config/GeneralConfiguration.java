package com.sample.warehouse.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Holds the general configuration
 * @author Pranish Dahal
 * 
 */
@Component
public class GeneralConfiguration {

	@Value("${storageLocation}")
	private String storageLocation;

	@Value("${validExtenstion}")
	private String validExtenstion;

	public GeneralConfiguration() {

	}

	/**
	 * @param storageLocation
	 * @param validExtenstion
	 */
	public GeneralConfiguration(String storageLocation, String validExtenstion) {
		this.storageLocation = storageLocation;
		this.validExtenstion = validExtenstion;
	}

	/**
	 * @return the storageLocation
	 */
	public String getStorageLocation() {
		return storageLocation;
	}

	/**
	 * @param storageLocation the storageLocation to set
	 */
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public Path getPath() {
		return Paths.get(storageLocation);
	}

	public String getValidFileExtenstion() {
		return validExtenstion;
	}

}
