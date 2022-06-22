package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Service
public class DeliveryService {
    
    private String email="coviran@ua.pt";
    private String data="vascovasco";
    private  String url= "http://localhost:8080/";

    private String getAuthenticationToken() throws IOException, InterruptedException, ParseException{
        Map<String,String> map = new HashMap<>();
        map.put("email", this.email);
        map.put("password", this.data);
        HttpRequest request = HttpRequest.newBuilder().setHeader("Content-Type", "application/json")
            .uri(URI.create(url+"login")).
            POST(HttpRequest.BodyPublishers.ofString(new JSONObject(map).toJSONString()))
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request,BodyHandlers.ofString() );
        JSONParser parser = new JSONParser();  
        JSONObject json = (JSONObject) parser.parse(response.body()); 

        return (String) json.get("token");

    }

    public Long postOrder(Map<String,Object> map) throws IOException, InterruptedException, ParseException{

        HttpRequest request = HttpRequest.newBuilder().setHeader("Authorization", "Bearer "+this.getAuthenticationToken())
            .setHeader("Content-Type", "application/json")
            .uri(URI.create(url+"delivery")).
            POST(HttpRequest.BodyPublishers.ofString(new JSONObject(map).toJSONString()))
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request,BodyHandlers.ofString() );
        JSONParser parser = new JSONParser();  
        JSONObject json = (JSONObject) parser.parse(response.body());


        return (Long) json.get("id");
    }


    public HashMap<String,Object> getDelivery(long id) throws IOException, InterruptedException, ParseException{
        HttpRequest request = HttpRequest.newBuilder().setHeader("Authorization", "Bearer "+this.getAuthenticationToken())
        .setHeader("Content-Type", "application/json")
        .uri(URI.create(url+"delivery/"+id)).GET()
        .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request,BodyHandlers.ofString() );
        JSONParser parser = new JSONParser();  
        JSONObject json = (JSONObject) parser.parse(response.body());


        return (HashMap<String, Object>) json;
    }

    public HashMap<String,Object> cancelDelivery(long id) throws IOException, InterruptedException, ParseException{
        HttpRequest request = HttpRequest.newBuilder().setHeader("Authorization", "Bearer "+this.getAuthenticationToken())
        .setHeader("Content-Type", "application/json")
        .uri(URI.create(url+"delivery/"+id)).DELETE()
        .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request,BodyHandlers.ofString() );
        JSONParser parser = new JSONParser();  
        JSONObject json = (JSONObject) parser.parse(response.body());


        return (HashMap<String, Object>) json;
    }

    public HashMap<String,Double> getFee(long id) throws IOException, InterruptedException, ParseException{
        HttpRequest request = HttpRequest.newBuilder().setHeader("Authorization", "Bearer "+this.getAuthenticationToken())
        .setHeader("Content-Type", "application/json")
        .uri(URI.create(url+"delivery/fee")).GET()
        .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request,BodyHandlers.ofString() );
        JSONParser parser = new JSONParser();  
        JSONObject json = (JSONObject) parser.parse(response.body());


        return (HashMap<String, Double>) json;
    }
    
    
}
