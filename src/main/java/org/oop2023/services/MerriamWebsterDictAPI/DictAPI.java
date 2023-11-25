package org.oop2023.services.MerriamWebsterDictAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.oop2023.utils.exceptions.APIAudioNotFoundException;
import org.oop2023.utils.exceptions.APIKeyException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class DictAPI {
    public static String API_KEY = "4e3c0ced-a6d8-492b-b0e3-bdbfcc432dd5";
    public static String API_URL = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/";
    public static String AUDIO_URL = "https://media.merriam-webster.com/audio/prons/en/us/mp3/";

    /**
     * Default constructor.
     */
    private DictAPI() {

    }

    private static String getAudioFileName(String word)
            throws RuntimeException, IOException, InterruptedException, APIKeyException, APIAudioNotFoundException {
        word = parseStringToURL(word);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL
                        + "/" + word
                        + "?key=" + API_KEY))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String responseJSON = response.body();
        if (responseJSON.equals("Invalid API key. Not subscribed for this reference.")) {
            throw new APIKeyException("Invalid API key. Not subscribed for this reference.");
        }
        JSONArray jsonArray = new JSONArray(responseJSON);
        try {
            return jsonArray.getJSONObject(0)
                    .getJSONObject("hwi")
                    .getJSONArray("prs").getJSONObject(0)
                    .getJSONObject("sound")
                    .get("audio").toString();
        } catch (JSONException e) {
            throw new APIAudioNotFoundException("No audio found for this word.");
        }
    }

    public static String getAudioURL(String word) {
        try {
            String audioFileName = getAudioFileName(word);
            System.out.println(audioFileName);
            String subDirectory = audioFileName.substring(0, 1);
            if (audioFileName.startsWith("bix")) {
                subDirectory = "bix";
            }
            if (audioFileName.startsWith("gg")) {
                subDirectory = "gg";
            }
            if (audioFileName.startsWith("_")) {
                subDirectory = "number";
            }
            for (int i = 0; i <= 9; i ++) {
                if (audioFileName.startsWith(String.valueOf(i))) {
                    subDirectory = "number";
                    break;
                }
            }
            return AUDIO_URL + subDirectory + "/" + audioFileName + ".mp3";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String parseStringToURL(String word) {
        return word.replaceAll(" ", "%20");
    }

    public static void main(String[] args) throws APIKeyException, IOException, InterruptedException, APIAudioNotFoundException {
        getAudio("croissant");
    }

}