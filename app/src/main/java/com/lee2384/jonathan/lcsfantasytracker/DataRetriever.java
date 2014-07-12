package com.lee2384.jonathan.lcsfantasytracker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.StringBuilder;

/**
 * Created by Jonathan on 7/11/2014.
 */
public class DataRetriever {

    public DataRetriever() {

    }

    private String getData(String type, int id) {

        DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

        HttpPost httppost = new HttpPost("http://na.lolesports.com/api/" + type + id + ".json");
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String target = null;

        try {
            HttpResponse response = httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            target = sb.toString();

        } catch (Exception e) {
            // dead
        }
        finally {
            try {
                if (inputStream !=null)
                    inputStream.close();
            } catch (Exception squish) {

            }
        }

        return target;

    }

}
