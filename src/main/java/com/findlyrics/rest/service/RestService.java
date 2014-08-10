package com.findlyrics.rest.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findlyrics.rest.model.SongPojo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Padonag on 10.08.2014.
 */
public class RestService {

    public static final String REST_URL = "http://api.lyricsnmusic.com/songs?api_key=3699a6ba6f1ecdc9b9e208123fd382&lyrics=";

    public RestService() {
    }

    private String queryToHttp(String query) {
        String httpString = REST_URL + query.replace(" ", "%20");
        return httpString;
    }

    public String getJsonFromRest(String query) {
        String fullUrl = queryToHttp(query);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseBody = null;
        try {
            HttpGet httpget = new HttpGet(fullUrl);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };

            try {
                responseBody = httpclient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    public ArrayList<SongPojo> jsonToPojo(String json) {
        ArrayList<SongPojo> result = new ArrayList<SongPojo>();
        ObjectMapper mapper = new ObjectMapper();
        try {

            result = new ObjectMapper().readValue(json, new TypeReference<ArrayList<SongPojo>>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

