import APIManagement.APIManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DemoMain {
    
    private static final String BASE_URL = "http://127.0.0.1:8080";
    private static final String API_KEY = "YOUR_API_KEY";
    
    public static void main(String[] args) {
        APIManager apiManager = new APIManager();
        
        try {
            // List all instances
            System.out.println("Fetching all WhatsApp instances...\n");
            String url = BASE_URL + "/instance/fetchInstances";
            
            Map<String, String> headers = new HashMap<>();
            headers.put("apikey", API_KEY);
            headers.put("Content-Type", "application/json");
            
            String response = apiManager.executeGET(url, headers);
            
            if (response != null) {
                System.out.println("Raw Response:");
                System.out.println(response);
                System.out.println("\n" + "=".repeat(60) + "\n");
                
                // Parse and display instances
                try {
                    JSONArray instances = new JSONArray(response);
                    
                    if (instances.length() == 0) {
                        System.out.println("No instances found!");
                        System.out.println("\nYou need to create an instance first.");
                        System.out.println("You can do this through the Evolution API Manager UI at:");
                        System.out.println("http://127.0.0.1:8080/manager");
                    } else {
                        System.out.println("Found " + instances.length() + " instance(s):\n");
                        
                        for (int i = 0; i < instances.length(); i++) {
                            JSONObject instance = instances.getJSONObject(i);
                            
                            System.out.println("Instance #" + (i + 1) + ":");
                            System.out.println("  Name: " + instance.optString("instance", "N/A"));
                            System.out.println("  Status: " + instance.optString("status", "N/A"));
                            
                            // Check if instance has connection info
                            if (instance.has("instance")) {
                                JSONObject instanceDetails = instance.optJSONObject("instance");
                                if (instanceDetails != null) {
                                    System.out.println("  State: " + instanceDetails.optString("state", "N/A"));
                                }
                            }
                            
                            System.out.println();
                        }
                        
                        System.out.println("=".repeat(60));
                        System.out.println("\nTo use an instance in your code, update the INSTANCE");
                        System.out.println("constant in WhatsAppManager.java with one of the names above.");
                    }
                    
                } catch (Exception e) {
                    // Response might not be JSON array, try as object
                    System.out.println("Attempting to parse as JSON object...");
                    try {
                        JSONObject obj = new JSONObject(response);
                        System.out.println("Response structure:");
                        System.out.println(obj.toString(2));
                    } catch (Exception e2) {
                        System.out.println("Could not parse response as JSON");
                    }
                }
                
            } else {
                System.err.println("Failed to fetch instances. Check if Evolution API is running.");
                System.err.println("Expected URL: " + url);
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            apiManager.close();
        }
    }
}
