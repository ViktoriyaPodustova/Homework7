import javax.json.JsonArray;
import javax.json.JsonObject;

public class WeatherResponse {
    private final DailyForecast[] dailyForecasts;

    public WeatherResponse(JsonObject jsonObject) {
        JsonArray jsonDailyForecastsArray = jsonObject.getJsonArray("DailyForecasts");
        dailyForecasts = new DailyForecast[jsonDailyForecastsArray.size()];
        for (int i = 0; i < dailyForecasts.length; i++) {
            JsonObject jsonForecast = jsonDailyForecastsArray.getJsonObject(i);
            DailyForecast dailyForecast = new DailyForecast(jsonForecast);
            dailyForecasts[i] = dailyForecast;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Погода на 5 дней в Санкт-Петербурге: \n");

        for (DailyForecast dailyForecast : dailyForecasts) {
            stringBuilder.append(dailyForecast.toString() + "\n");
        }

        return stringBuilder.toString();
    }
}