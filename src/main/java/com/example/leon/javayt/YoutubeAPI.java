package com.example.leon.javayt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Arrays;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class YoutubeAPI {

	List<Item> items = null;

	@Autowired
	private JmsTemplate jmsTemplate;

	@RequestMapping(value = "/api", headers = "Accept=application/json")
	public void youtube(@RequestParam String part, @RequestParam String q, @RequestParam String key) {
		
		// Enter this URL: http://localhost:8081/api?part=snippet&q=%22telecom%22&key=AIzaSyCtwmCm3gxZgqjXdgjhshAiuaMNHalLiZE
		String url = "https://youtube.googleapis.com/youtube/v3/search?part={part}&q={q}&key={key}";

		HashMap<String, String> params = new HashMap<>();
		params.put("part", part);
		params.put("q", q);
		params.put("key", key);

		Queue<String> queueA = new LinkedList<>();

		RestTemplate rest = new RestTemplate();
		Result res = rest.getForObject(url, Result.class, params);
		System.out.println(res.getItems());

		for (Item i : res.getItems()) {
			System.out.println(i.getSnippet().getTitle());
			System.out.println(i.getId().getVideoId());
			String title = "<title>" + i.getSnippet().getTitle() + "</title>";
			String videoUrl = "<url>" + i.getId().getVideoId() + "</url>";
			String message = "<message>" + title + videoUrl + "</message>";

			queueA.add(message);
		}

		while (!queueA.isEmpty()) {
			String itemXML = queueA.poll();
			System.out.println(itemXML);
			// Modiftcia

			jmsTemplate.convertAndSend("inmemory1", itemXML);
		}

	}

}
