package com.tui.proof.ws.controller;

import org.junit.Test;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;


public class PilotesControllerTest {

    @Test
    public void saveNormalOrder() throws IOException {
        String jsonInputString = "{\n" +
                "    \"pilotes\": 5,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"C Saturn 5 2-1\",\n" +
                "        \"postcode\": 25003,\n" +
                "        \"city\": \"Lleida\",\n" +
                "        \"country\": \"Spain\"\n" +
                "    },\n" +
                "    \"client\":{\n" +
                "        \"firstName\": \"Alex\",\n" +
                "        \"lastName\": \"Mart\",\n" +
                "        \"telephone\": 600355441\n" +
                "    }\n" +
                "}";


        int HttpResult = apiCall("http://localhost:8080/orders", "POST", jsonInputString);

        assertEquals(200, HttpResult);
    }

    @Test
    public void saveInvalidAmountOfPilotesOrder() throws IOException {
        String jsonInputString = "{\n" +
                "    \"pilotes\": 6,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"C Saturn 5 2-1\",\n" +
                "        \"postcode\": 25003,\n" +
                "        \"city\": \"Lleida\",\n" +
                "        \"country\": \"Spain\"\n" +
                "    },\n" +
                "    \"client\":{\n" +
                "        \"firstName\": \"Alex\",\n" +
                "        \"lastName\": \"Mart\",\n" +
                "        \"telephone\": 600355441\n" +
                "    }\n" +
                "}";


        int HttpResult = apiCall("http://localhost:8080/orders", "POST", jsonInputString);
        assertEquals(500, HttpResult);
    }

    @Test
    public void saveOrderWithInvalidClientInformation() throws IOException {
        String jsonInputString = "{\n" +
                "    \"pilotes\": 5,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"C Saturn 5 2-1\",\n" +
                "        \"postcode\": 25003,\n" +
                "        \"city\": \"Lleida\",\n" +
                "        \"country\": \"Spain\"\n" +
                "    },\n" +
                "    \"client\":{\n" +
                "        \"firstName\": \"\",\n" +
                "        \"lastName\": \"\",\n" +
                "        \"telephone\": 600355441\n" +
                "    }\n" +
                "}";


        int HttpResult = apiCall("http://localhost:8080/orders", "POST", jsonInputString);
        assertEquals(500, HttpResult);
    }

    @Test
    public void updateOrder() throws IOException{
        String jsonInputString = "{\n" +
                "    \"pilotes\": 15,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"C Saturn 5 2-1\",\n" +
                "        \"postcode\": 25003,\n" +
                "        \"city\": \"Lleida\",\n" +
                "        \"country\": \"Spain\"\n" +
                "    },\n" +
                "    \"client\":{\n" +
                "        \"firstName\": \"Alex\",\n" +
                "        \"lastName\": \"Mart\",\n" +
                "        \"telephone\": 600355441\n" +
                "    }\n" +
                "}";

        int HttpResult = apiCall("http://localhost:8080/orders/" + 1, "PUT", jsonInputString);
        assertEquals(200, HttpResult);
    }

    @Test
    public void getOrder() throws IOException{
        int HttpResult = apiCallWithoutJson("http://localhost:8080/orders/" + 1, "GET");
        assertEquals(HttpResult, 200);
    }

    public int apiCall(String url, String methodType, String json) throws IOException{
        URL u = new URL (url);
        HttpURLConnection con = (HttpURLConnection)u.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod(methodType);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(json);
        wr.close();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }catch(Exception e){
            return 500;
        }

        return con.getResponseCode();
    }

    public int apiCallWithoutJson(String url, String methodType) throws IOException{
        URL u = new URL (url);
        HttpURLConnection con = (HttpURLConnection)u.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod(methodType);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }catch(Exception e){
            return 500;
        }

        return con.getResponseCode();
    }
}
