import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String forecastJson = jsStringOrNull();

        if (forecastJson != null) {
            StringReader forecastJsonReader = new StringReader(forecastJson);
            JsonReader jsonReader = Json.createReader(forecastJsonReader);
            JsonObject weatherResponseJson = jsonReader.readObject();
            WeatherResponse weatherResponse = new WeatherResponse(weatherResponseJson);
            System.out.println(weatherResponse);
        } else {
            System.out.println("Не удалось прочитать данные с сервера.");
        }
    }

    public static String jsStringOrNull() {
        try{
            URL weatherURL = new URL("http://dataservice.accuweather.com/forecasts/v1/daily/5day/295212?apikey=lMmY2qLZX7Nl8xuPDSvbssFf0qWwPfUY&metric=true");
            HttpURLConnection urlConnection = (HttpURLConnection)weatherURL.openConnection();

            if(urlConnection.getResponseCode() == 200){
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))){
                    StringBuilder responseContent = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {responseContent.append(line); }
                    return responseContent.toString();
                }catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } catch (Exception ex){
            ex.getMessage();
        }
        return null;
    }

}
