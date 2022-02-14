package com.example.leon.javayt.listener;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.example.leon.javayt.Item;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Component
public class Consumer {
	Queue<String> queueB = new LinkedList<>();

    @JmsListener(destination = "inmemory1")
    public void listener(String itemXML) {
   
        Pattern pattern = Pattern.compile("telecom", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(itemXML);
        String data="";
        while (matcher.find()) {
            // Print matched Patterns
            System.out.println("Match found: "+matcher.group());
            data = matcher.replaceAll("teleco"); 
        }
        
        queueB.add(data);
//    	Queue<Item> queue = new LinkedList<>();
//    	Gson gson = new Gson();
//    	String jsonInString = itemXML;
//    	Item item = gson.fromJson(jsonInString, Item.class);
//    	

    
    	
    	//STORE
        System.out.println("Received Message: " + data);
    }}