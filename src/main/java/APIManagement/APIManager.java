package APIManagement;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class APIManager {

    private OkHttpClient client;

    // Constructor to initialize OkHttpClient
    public APIManager() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Execute GET request
     * @param url The URL to send GET request to
     * @param headers Map of headers to include in the request
     * @return Response body as String, or null if request fails
     */
    public String executeGET(String url, Map<String, String> headers) {
        try {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .get();

            // Add headers if provided
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            }

            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                System.err.println("GET Request failed with code: " + response.code());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error executing GET request: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Execute POST request with JSON body
     * @param url The URL to send POST request to
     * @param jsonBody The JSON body as String
     * @param headers Map of headers to include in the request
     * @return Response body as String, or null if request fails
     */
    public String executePOST(String url, String jsonBody, Map<String, String> headers) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(jsonBody, mediaType);

            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .post(body);

            // Add headers if provided
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            }

            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                String errorBody = response.body() != null ? response.body().string() : "No response body";
                System.err.println("POST Request failed with code: " + response.code());
                System.err.println("Response body: " + errorBody);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error executing POST request: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Execute PUT request with JSON body
     * @param url The URL to send PUT request to
     * @param jsonBody The JSON body as String
     * @param headers Map of headers to include in the request
     * @return Response body as String, or null if request fails
     */
    public String executePUT(String url, String jsonBody, Map<String, String> headers) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(jsonBody, mediaType);

            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .put(body);

            // Add headers if provided
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            }

            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                System.err.println("PUT Request failed with code: " + response.code());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error executing PUT request: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Execute DELETE request
     * @param url The URL to send DELETE request to
     * @param headers Map of headers to include in the request
     * @return Response body as String, or null if request fails
     */
    public String executeDELETE(String url, Map<String, String> headers) {
        try {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .delete();

            // Add headers if provided
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            }

            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                System.err.println("DELETE Request failed with code: " + response.code());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error executing DELETE request: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Close the OkHttpClient and release resources
     */
    public void close() {
        try {
            if (client != null) {
                client.dispatcher().executorService().shutdown();
                client.connectionPool().evictAll();
            }
        } catch (Exception e) {
            System.out.println("Error closing APIManager: " + e.getMessage());
        }
    }
}
