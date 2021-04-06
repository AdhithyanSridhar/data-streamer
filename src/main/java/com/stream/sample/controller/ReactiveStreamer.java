package com.stream.sample.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.stream.sample.util.HelperUtil;

import reactor.core.publisher.Flux;

/**
 * @author adhithyan
 *
 */
@RestController
@RequestMapping("/api/stream/reactive")
public class ReactiveStreamer {

	private RestTemplate rt;
	private Gson gson;
	private HelperUtil util;

	@Autowired
	public ReactiveStreamer(RestTemplate rt, Gson gson, HelperUtil util) {
		super();
		this.rt = rt;
		this.gson = gson;
		this.util = util;
	}

	@GetMapping(value = "/data", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Object> streamReactiveData() {

		Flux<Object> dataFlux = Flux.interval(Duration.ofSeconds(1)).map(i -> " Processing-" + i);

		return dataFlux;
	}

}
