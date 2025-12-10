import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIWeatherProvider {

    public static class WeatherResult {
        public List<HourlyWeatherData> hourly;
        public List<DailyWeatherData> daily;
        public String city;

        public WeatherResult(String city, List<HourlyWeatherData> hourly, List<DailyWeatherData> daily) {
            this.city = city;
            this.hourly = hourly;
            this.daily = daily;
        }
    }

    public WeatherResult fetchWeather(String city, int dayCount) throws Exception {

        // --- Geocode city to lat/lon ---
        Geocoder.GeoResult geo = Geocoder.geocode(city);
        if (geo == null) return null;

        String urlStr = "https://api.open-meteo.com/v1/forecast?latitude=" + geo.lat +
                "&longitude=" + geo.lon +
                "&hourly=temperature_2m,weathercode" +
                "&daily=temperature_2m_max,temperature_2m_min,weathercode" +
                "&forecast_days=" + dayCount +
                "&timezone=auto";

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();

        JSONObject root = new JSONObject(sb.toString());

        // --- Parse Hourly ---
        List<HourlyWeatherData> hourlyList = new ArrayList<>();
        JSONObject hourly = root.getJSONObject("hourly");

        JSONArray times = hourly.getJSONArray("time");
        JSONArray temps = hourly.getJSONArray("temperature_2m");
        JSONArray codes = hourly.getJSONArray("weathercode");

        for (int i = 0; i < times.length(); i++) {
            String t = times.getString(i);
            double temp = temps.getDouble(i);
            String cond = codeToCondition(codes.getInt(i));

            // --- Generate clothing, alert, score ---
            String clothing;
            if (temp >= 25) clothing = "T-shirt";
            else if (temp >= 18) clothing = "Shirt";
            else if (temp >= 10) clothing = "Jacket";
            else clothing = "Coat";

            String alert = switch (cond) {
                case "Rain", "Drizzle", "Snow" -> "⚠️ Weather Alert";
                default -> "None";
            };

            int score = (int)Math.max(1, Math.min(10, 10 - Math.abs(22 - temp)));

            hourlyList.add(new HourlyWeatherData(t, temp, cond, clothing, alert, score));
        }

        // --- Parse Daily ---
        List<DailyWeatherData> dailyList = new ArrayList<>();
        JSONObject daily = root.getJSONObject("daily");

        JSONArray dDates = daily.getJSONArray("time");
        JSONArray tMin = daily.getJSONArray("temperature_2m_min");
        JSONArray tMax = daily.getJSONArray("temperature_2m_max");
        JSONArray dCodes = daily.getJSONArray("weathercode");

        for (int i = 0; i < dDates.length(); i++) {
            dailyList.add(new DailyWeatherData(
                    dDates.getString(i),
                    tMin.getDouble(i),
                    tMax.getDouble(i),
                    codeToCondition(dCodes.getInt(i))
            ));
        }

        return new WeatherResult(city, hourlyList, dailyList);
    }

    // --- Map Open-Meteo weather codes to conditions ---
    private String codeToCondition(int code) {
        return switch (code) {
            case 0 -> "Clear";
            case 1, 2 -> "Partly Cloudy";
            case 3 -> "Cloudy";
            case 45, 48 -> "Fog";
            case 51, 53, 55 -> "Drizzle";
            case 61, 63, 65 -> "Rain";
            case 71, 73, 75 -> "Snow";
            default -> "Unknown";
        };
    }
}
