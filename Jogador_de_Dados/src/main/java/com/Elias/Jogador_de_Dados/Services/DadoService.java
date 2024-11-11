package com.Elias.Jogador_de_Dados.Services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.Elias.Jogador_de_Dados.Secrets.Secret;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import org.springframework.stereotype.Service;

@Service
public class DadoService {
    private Secret secret = new Secret();
    private String apiUrl = "https://diceroller.p.rapidapi.com/rollforstats";
    private String apiKey = secret.getApiToken();
    private List<String> rolls = new ArrayList<>();

    public String rollDice(String diceSides, String numDice) {
        try {
            String response = makeRequest(apiUrl, apiKey, diceSides, numDice);
            rolls.add(response);
            return response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<String> getAllLRolls() {
        return rolls;
    }

    private String makeRequest(String url, String token, String diceSides, String numDice) throws URISyntaxException {
        try {
            URL apiUrl = new URI(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("x-rapidapi-key", token);
            connection.setRequestProperty("x-rapidapi-host", "diceroller.p.rapidapi.com/rollforstats");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);



            
            String jsonInputString = String.format("{\"diceSides\":%s,\"numDice\":%s,\"numStats\":1,\"rollType\":\"no_drop\"}", diceSides, numDice);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Log the raw response to inspect its structure
                System.out.println("Response: " + response.toString());

                // Use JsonElement to handle both JsonObject and JsonPrimitive
                JsonElement jsonElement = JsonParser.parseString(response.toString());

                if (jsonElement.isJsonArray()) {
                    JsonArray jsonArray = jsonElement.getAsJsonArray();
                    
                    // Check if the array contains at least one element and that the first element is an object
                    if (jsonArray.size() > 0 && jsonArray.get(0).isJsonObject()) {
                        JsonObject jsonResponse = jsonArray.get(0).getAsJsonObject();
                        
                        if (jsonResponse.has("total")) {  // Check if "total" exists to avoid errors
                            return jsonResponse.get("total").getAsString();  // Return total as string
                        } else {
                            System.out.println("Total field is missing in the response.");
                            return null;
                        }
                    } else {
                        System.out.println("Array is empty or does not contain a JSON object.");
                        return null;
                    }
                } else if (jsonElement.isJsonObject()) {
                    // Handle case where response is a single JSON object as in your original code
                    JsonObject jsonResponse = jsonElement.getAsJsonObject();
                    if (jsonResponse.get("success").getAsBoolean()) {
                        return jsonResponse.get("total").getAsString();
                    } else {
                        System.out.println("Request was not successful.");
                        return null;
                    }
                } else {
                    System.out.println("Unexpected JSON structure.");
                    return null;
                }
            } else {
                // Handle any errors that occurred during the request
                System.out.println("Request failed with response code: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}