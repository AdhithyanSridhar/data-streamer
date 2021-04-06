package com.stream.sample.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.google.gson.Gson;
import com.stream.sample.domain.People;
import com.stream.sample.util.HelperUtil;

/**
 * @author adhithyan
 *
 */
@RestController
@RequestMapping("/api/stream/regular")
public class NormalStreamer {

	private RestTemplate rt;
	private Gson gson;
	private HelperUtil util;

	@Autowired
	public NormalStreamer(RestTemplate rt, Gson gson, HelperUtil util) {
		super();
		this.rt = rt;
		this.gson = gson;
		this.util = util;
	}
	
	@GetMapping(value = "/data")
	public ResponseEntity<StreamingResponseBody> streamData() {

		StreamingResponseBody responseBody = null;

		responseBody = response -> {
			for (int i = 0; i < 1000; i++) {
				try {
					Thread.sleep(10);
					response.write(("Processing # - " + i).getBytes());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(responseBody);
	}

	@GetMapping("/json")
	public ResponseEntity<StreamingResponseBody> streamJson() {

		int maxRecords = 100;
		StreamingResponseBody streamingResponseBody = null;
		streamingResponseBody = responseBody -> {
			for (int i = 0; i < maxRecords; i++) {
				People people = new People(UUID.randomUUID().toString(), util.getRandomName());
				responseBody.write(gson.toJson(people).getBytes());
				responseBody.flush();

				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}
		};

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(streamingResponseBody);
	}

	@GetMapping("/textfile")
	public ResponseEntity<StreamingResponseBody> streamTextFile() {

		StreamingResponseBody responseBody = response -> {
			for (int i = 0; i < 1000; i++) {
				response.write(("write " + i + "\n").getBytes());
				if (i % 5 == 0) {
					response.flush();
				}
				util.sleep(10);
			}
		};

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.txt")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(responseBody);
	}

	@GetMapping("/pdf")
	public ResponseEntity<StreamingResponseBody> downloadPdfFile() throws FileNotFoundException {

		String fileName = "Application Form.pdf";
		File file = ResourceUtils.getFile("classpath:static/" + fileName);
		StreamingResponseBody responseBody = response -> {
			Files.copy(file.toPath(), response);
		};
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PDF_" + fileName)
				.contentType(MediaType.APPLICATION_PDF).body(responseBody);
	}
}
