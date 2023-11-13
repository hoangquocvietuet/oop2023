//Translator.java
package org.oop2023.translateAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.oop2023.utils.exceptions.APIException;
import org.oop2023.utils.exceptions.APIKeyException;
import org.oop2023.utils.exceptions.APITranslationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Translator {
    private static final String API_URI = "https://microsoft-translator-text.p.rapidapi.com/translate";
    private static final String CONFIG_FILE_PATH = "resources/config/translate_api_config.json";
    String APIKey;

    /**
     * Default constructor.
     */
    public Translator() throws RuntimeException {
        try {
            APIKey = getAPIKey();
        } catch (Exception e) {
            System.out.println("API Error.");
            throw new RuntimeException(e);
        }
        System.out.println("API ready.");
    }

    /**
     * Translate a text from English to Vietnamese.
     * @param text The text to be translated
     * @return The translated text
     */
    public String EtoV(String text) throws RuntimeException {
        try {
            String[] lines = text.split("\n");
            StringBuilder translatedText = new StringBuilder();
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    translatedText.append("\n");
                    continue;
                }
                String translatedLine = translate(line, "en", "vi");
                translatedText.append(translatedLine);
                if (i < lines.length - 1) {
                    translatedText.append("\n");
                }
            }
            return translatedText.toString();
        } catch (Exception e) {
            System.out.println("API Error.");
            throw new RuntimeException(e);
        }

    }

    /**
     * Translate a text from Vietnamese to English.
     * @param text The text to be translated
     * @return The translated text
     */
    public String VtoE(String text) throws RuntimeException {
        try {
            String[] lines = text.split("\n");
            StringBuilder translatedText = new StringBuilder();
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    translatedText.append("\n");
                    continue;
                }
                String translatedLine = translate(line, "vi", "en");
                translatedText.append(translatedLine);
                if (i < lines.length - 1) {
                    translatedText.append("\n");
                }
            }
            return translatedText.toString();
        } catch (Exception e) {
            System.out.println("API Error.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Translate a text from a language to another.
     * @param text The text to be translated
     * @param sLang The source language
     * @param dLang The destination language
     * @return The translated text
     */
    private String translate(String text, String sLang, String dLang)
            throws IOException, JSONException, APIException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URI
                        + "?to=" + dLang
                        + "&api-version=3.0"
                        + "&from=" + sLang
                        + "&profanityAction=NoAction"
                        + "&textType=plain"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", APIKey)
                .header("X-RapidAPI-Host", "microsoft-translator-text.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "[\r "
                                    + "{\r\""
                                        + "Text\": \"" + text + "\"\r"
                                    + "}\r"
                                + "]"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String responseJSON = response.body();
        if (responseJSON.contains("You are not subscribed to this API.")) {
            throw new APIKeyException("API key error.");
        }
        if (responseJSON.contains("The API is unreachable, please contact the API provider")) {
            throw new APITranslationException("Cannot translate this text.");
        }
        JSONArray jsonArray = new JSONArray(responseJSON);
        return new JSONArray(jsonArray.getJSONObject(0).get("translations").toString())
                .getJSONObject(0).get("text").toString();
    }

    /**
     * API test method.
     * @param APIKey The API key to be tested
     */
    private void testAPI(String APIKey)
            throws IOException, JSONException, APIException, InterruptedException {
        this.APIKey = APIKey;
        translate("test", "en", "vi");
    }

    /**
     * Get the API key from the config file.
     * @return The API key
     */
    private String getAPIKey()
            throws IOException, JSONException, APIException, InterruptedException {
        BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH));
        StringBuilder jsonBuilder = new StringBuilder();
        while (reader.ready()) {
            jsonBuilder.append(reader.readLine());
        }
        JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
        String APIKey = jsonArray.getJSONObject(0).get("apiKey").toString();
        testAPI(APIKey);
        return APIKey;
    }

    /**
     * Test client.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Translator translator = new Translator();
        try {
            System.out.println(translator.EtoV("Never gonna give you up\nNever gonna let you down\nNever gonna run around and desert you."));
            System.out.println(translator.VtoE("Siuuuu"));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

}
