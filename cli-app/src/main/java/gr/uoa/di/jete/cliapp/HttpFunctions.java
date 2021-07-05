package gr.uoa.di.jete.cliapp;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpFunctions {

    private HttpFunctions() {
    }

    public static CloseableHttpResponse Post(String url, JsonObject object) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        StringEntity stringEntity = new StringEntity(object.toString());
        request.setEntity(stringEntity);
        CloseableHttpClient client = HttpClients.createDefault();
        return client.execute(request);
    }

    public static CloseableHttpResponse Post(String url, JsonObject object,String type, String token) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization",type + " " + token);
        StringEntity stringEntity = new StringEntity(object.toString());
        request.setEntity(stringEntity);
        CloseableHttpClient client = HttpClients.createDefault();
        return client.execute(request);
    }
    public static CloseableHttpResponse Put(String url, JsonObject object,String type, String token) throws IOException {
        HttpPut request = new HttpPut(url);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Authorization",type + " " + token);
        StringEntity stringEntity = new StringEntity(object.toString());
        request.setEntity(stringEntity);
        CloseableHttpClient client = HttpClients.createDefault();
        return client.execute(request);
    }

    public static CloseableHttpResponse Get(String url,String type, String token) throws IOException {
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization",type + " " + token);
        CloseableHttpClient client = HttpClients.createDefault();
        return client.execute(request);
    }

    public static CloseableHttpResponse Get(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json");
        CloseableHttpClient client = HttpClients.createDefault();
        return client.execute(request);
    }


    public static HttpResponse Put(String url, String type, String token) throws IOException {
        HttpPut request = new HttpPut(url);
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization",type + " " + token);
        CloseableHttpClient client = HttpClients.createDefault();
        return client.execute(request);
    }
}