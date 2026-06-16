package APIManagement;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhatsAppManager {

    private static final Logger LOGGER = Logger.getLogger(WhatsAppManager.class.getName());

    // Evolution API base URL
    private static final String BASE_URL = "http://127.0.0.1:8080";

    // ✅ Instance name from logs
    private static final String INSTANCE = "teste";

    // ✅ Value from docker-compose.yml (NOT the variable name)
    private static final String API_KEY ="YOUR_API_KEY";

    private final APIManager apiManager;

    public WhatsAppManager() {
        this.apiManager = new APIManager();
    }

    // ================= SEND TEXT =================
    public boolean sendText(String phoneNumber, String message) {
        try {
            String url = BASE_URL + "/message/sendText/" + INSTANCE;

            JSONObject body = new JSONObject();
            body.put("number", formatPhoneNumber(phoneNumber));

            JSONObject textMessage = new JSONObject();
            textMessage.put("text", message);

            body.put("textMessage", textMessage);

            String response = apiManager.executePOST(url, body.toString(), buildHeaders());
            return response != null;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send WhatsApp text", e);
            return false;
        }
    }

    // ================= SEND IMAGE =================
    public boolean sendImage(String phoneNumber, String base64Image, String caption) {
        try {
            String url = BASE_URL + "/message/sendMedia/" + INSTANCE;

            // Remove data URI prefix if present
            if (base64Image.startsWith("data:image")) {
                base64Image = base64Image.split(",")[1];
            }

            // Evolution API v1.8.7 format - requires nested mediaMessage object
            JSONObject body = new JSONObject();
            body.put("number", formatPhoneNumber(phoneNumber));

            JSONObject mediaMessage = new JSONObject();
            mediaMessage.put("mediatype", "image");
            mediaMessage.put("media", base64Image);
            
            if (caption != null && !caption.isEmpty()) {
                mediaMessage.put("caption", caption);
            }

            body.put("mediaMessage", mediaMessage);

            LOGGER.info("Sending WhatsApp image to: " + formatPhoneNumber(phoneNumber));
            String response = apiManager.executePOST(url, body.toString(), buildHeaders());
            
            if (response != null) {
                LOGGER.info("WhatsApp image sent successfully");
                return true;
            } else {
                LOGGER.warning("WhatsApp API returned null response");
                return false;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send WhatsApp image", e);
            return false;
        }
    }


    // ================= HELPERS =================
    private Map<String, String> buildHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("apikey", API_KEY); // ✅ HTTP HEADER
        return headers;
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return null;

        String clean = phoneNumber.replaceAll("[^0-9]", "");

        // Assume India if 10 digits
        if (clean.length() == 10) {
            clean = "91" + clean;
        }

        return clean; // ✅ NO @c.us
    }

    public void close() {
        apiManager.close();
    }
}
