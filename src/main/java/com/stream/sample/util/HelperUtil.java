package com.stream.sample.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component
public class HelperUtil {

	private RestTemplate rt;
	private Gson gson;

	@Autowired
	public HelperUtil(RestTemplate rt, Gson gson) {
		super();
		this.rt = rt;
		this.gson = gson;
	}

	public String getRandomName() {
		String response = rt.getForObject("https://randomuser.me/api/?nat=us&randomapi", String.class);
		JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
		JsonObject nameObj = jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("name")
				.getAsJsonObject();
		return nameObj.get("first").getAsString() + " " + nameObj.get("last").getAsString();
	}

	public void sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
