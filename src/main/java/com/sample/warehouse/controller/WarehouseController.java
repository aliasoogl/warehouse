package com.sample.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sample.warehouse.response.UploadedFileMetaInfoResponse;
import com.sample.warehouse.service.WarehouseService;

/**
 * Controller for Warehouse ( To import deals details from files into DB,provide general
 * information about the uploaded file.)
 * @author Pranish Dahal
 * 
 */
@Controller
public class WarehouseController {

	@Autowired
	WarehouseService warehouseService;

	@RequestMapping("/")
	String index() {
		return "index";
	}

	@RequestMapping("/upload")
	String uploadPage() {
		return "upload";
	}

	@RequestMapping("/analyze")
	String analyzeUploadedDocument() {
		return "analyze";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			Model model) {
		warehouseService.store(file);
		UploadedFileMetaInfoResponse analysis = warehouseService
				.analyze(file.getOriginalFilename());
		model.addAttribute("meta", analysis);
		return "metaResult";
	}

	@PostMapping("/analyze")
	public String analyzeUploadedFile(@RequestParam String fileName, Model model) {
		model.addAttribute("meta", warehouseService.analyze(fileName));
		return "metaResult";
	}

}
