package utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONUtility {

    private static final Logger LOGGER = Logger.getLogger(JSONUtility.class.getName());

    /**
     * Extracts the text content from the Gemini API JSON response.
     * Expected structure:
     * {
     *   "candidates": [
     *     {
     *       "content": {
     *         "parts": [
     *           {
     *             "text": "The actual text..."
     *           }
     *         ]
     *       }
     *     }
     *   ]
     * }
     *
     * @param jsonResponse The raw JSON response string
     * @return The extracted text, or null if parsing fails
     */
    public static String extractTextFromGeminiResponse(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return null;
        }

        try {
            JSONObject root = new JSONObject(jsonResponse);
            
            if (!root.has("candidates")) {
                return null;
            }

            JSONArray candidates = root.getJSONArray("candidates");
            if (candidates.isEmpty()) {
                return null;
            }

            JSONObject firstCandidate = candidates.getJSONObject(0);
            if (!firstCandidate.has("content")) {
                return null;
            }

            JSONObject content = firstCandidate.getJSONObject("content");
            if (!content.has("parts")) {
                return null;
            }

            JSONArray parts = content.getJSONArray("parts");
            if (parts.isEmpty()) {
                return null;
            }

            JSONObject firstPart = parts.getJSONObject(0);
            if (firstPart.has("text")) {
                return firstPart.getString("text");
            }

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error parsing JSON response", e);
        }

        return null;
    }
}
