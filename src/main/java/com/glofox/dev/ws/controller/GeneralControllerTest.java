package com.glofox.dev.ws.controller;

import org.junit.Test;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;


public class GeneralControllerTest {

    @Test
    public void saveNormalClass() throws IOException {
        String jsonInputString = "{\n" +
                "    \"name\": \"Pilates\",\n" +
                "    \"start_date\": \"2022-08-09\",\n" +
                "    \"end_date\": \"2022-08-10\",\n" +
                "    \"capacity\": 15\n" +
                "}";


        int HttpResult = apiCall("http://localhost:8080/classes", "POST", jsonInputString);

        assertEquals(200, HttpResult);
    }

    @Test
    public void saveInvalidClass() throws IOException {
        String jsonInputString = "{\n" +
                "    \"name\": \"\",\n" +
                "    \"start_date\": \"2022-08-09\",\n" +
                "    \"end_date\": \"2022-08-11\",\n" +
                "    \"capacity\": 15\n" +
                "}";


        int HttpResult = apiCall("http://localhost:8080/orders", "POST", jsonInputString);
        assertEquals(500, HttpResult);
    }

    @Test
    public void save2ClassesWithinSameDate() throws IOException {
        String jsonInputString1 = "{\n" +
                "    \"name\": \"GymSession\",\n" +
                "    \"start_date\": \"2022-08-12\",\n" +
                "    \"end_date\": \"2022-08-18\",\n" +
                "    \"capacity\": 15\n" +
                "}";
        String jsonInputString2 = "{\n" +
                "    \"name\": \"PowerliftingSession\",\n" +
                "    \"start_date\": \"2022-08-14\",\n" +
                "    \"end_date\": \"2022-08-16\",\n" +
                "    \"capacity\": 10\n" +
                "}";

        apiCall("http://localhost:8080/classes", "POST", jsonInputString1);
        int finalHttpResult = apiCall("http://localhost:8080/classes", "POST", jsonInputString2);

        assertEquals(500, finalHttpResult);
    }

    @Test
    public void saveNormalBooking() throws IOException {
        String jsonInputString1 = "{\n" +
                "    \"name\": \"Pilates\",\n" +
                "    \"start_date\": \"2022-08-09\",\n" +
                "    \"end_date\": \"2022-08-10\",\n" +
                "    \"capacity\": 15\n" +
                "}";
        String jsonInputString2 = "{\n" +
                "    \"name\": \"Alex\",\n" +
                "    \"date\": \"2022-08-11\",\n" +
                "    \"aClass\": {\n" +
                "        \"name\": \"Pilates\",\n" +
                "        \"start_date\": \"2022-08-09\",\n" +
                "        \"end_date\": \"2022-08-11\",\n" +
                "        \"capacity\": 15\n" +
                "    }\n" +
                "}";

        apiCall("http://localhost:8080/classes", "POST", jsonInputString1);
        int finalHttpResult = apiCall("http://localhost:8080/bookings", "POST", jsonInputString2);

        assertEquals(200, finalHttpResult);
    }

    @Test
    public void saveDuplicatedBooking() throws IOException {
        String jsonInputString1 = "{\n" +
                "    \"name\": \"Pilates\",\n" +
                "    \"start_date\": \"2022-08-09\",\n" +
                "    \"end_date\": \"2022-08-10\",\n" +
                "    \"capacity\": 15\n" +
                "}";
        String jsonInputString2 = "{\n" +
                "    \"name\": \"Alex\",\n" +
                "    \"date\": \"2022-08-11\",\n" +
                "    \"aClass\": {\n" +
                "        \"name\": \"Pilates\",\n" +
                "        \"start_date\": \"2022-08-09\",\n" +
                "        \"end_date\": \"2022-08-11\",\n" +
                "        \"capacity\": 15\n" +
                "    }\n" +
                "}";
        String jsonInputString3 = "{\n" +
                "    \"name\": \"Alex\",\n" +
                "    \"date\": \"2022-08-11\",\n" +
                "    \"aClass\": {\n" +
                "        \"name\": \"Pilates\",\n" +
                "        \"start_date\": \"2022-08-09\",\n" +
                "        \"end_date\": \"2022-08-11\",\n" +
                "        \"capacity\": 15\n" +
                "    }\n" +
                "}";

        apiCall("http://localhost:8080/classes", "POST", jsonInputString1);
        apiCall("http://localhost:8080/bookings", "POST", jsonInputString2);

        int finalHttpResult = apiCall("http://localhost:8080/bookings", "POST", jsonInputString3);

        assertEquals(500, finalHttpResult);
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
}
