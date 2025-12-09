import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class APIWeatherProvider implements DataProvider {

    @Override
    public Forecast getForecast(String city, int days) {
        Forecast forecast = new Forecast();

        String[] coords = Geocoder.getCoordinates(city);
        if (coords == null) {
            System.out.println("Location not found — using default (Berlin).");
            coords = new String[]{"52.52","13.41"};
        }
        String lat = coords[0];
        String lon = coords[1];

        try {
            String url = String.format(
                    "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s" +
                            "&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=auto",
                    lat, lon
            );

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                System.out.println("Weather API returned status " + resp.statusCode());
                return forecast;
            }

            JSONObject json = new JSONObject(resp.body());
            JSONObject daily = json.getJSONObject("daily");
            JSONArray times = daily.getJSONArray("time");
            JSONArray tMax = daily.getJSONArray("temperature_2m_max");
            JSONArray tMin = daily.getJSONArray("temperature_2m_min");
            JSONArray wCodes = daily.getJSONArray("weathercode");

            for (int i = 0; i < Math.min(days, times.length()); i++) {
                String dateStr = times.getString(i); // e.g. "2025-12-10"
                LocalDate date = LocalDate.parse(dateStr);
                LocalDateTime dt = LocalDateTime.of(date, LocalTime.NOON);

                double tempAvg = (tMax.getDouble(i) + tMin.getDouble(i)) / 2.0;
                String condition = weatherCodeToCondition(wCodes.getInt(i));
                String icon = weatherCodeToIcon(wCodes.getInt(i));

                WeatherData wd = new WeatherData(city, dt.toString(), tempAvg, 0, 0, condition);
                wd.setIcon(icon);
                forecast.addDay(wd);
            }

        } catch (Exception e) {
            System.err.println("Error fetching weather data: " + e.getMessage());
        }

        return forecast;
    }

    private String weatherCodeToCondition(int code) {
        if (code == 0) return "Clear";
        if (code == 1) return "Mainly Clear";
        if (code <= 3) return "Partly Cloudy";
        if (code >= 45 && code <= 48) return "Foggy";
        if (code >= 51 && code <= 67) return "Rainy";
        if (code >= 80 && code <= 99) return "Rainy";
        return "Unknown";
    }

    private String weatherCodeToIcon(int code) {
        if (code == 0) return "☀️";
        if (code == 1) return "🌤️";
        if (code <= 3) return "⛅";
        if (code >= 45 && code <= 48) return "🌫️";
        if (code >= 51 && code <= 67) return "🌧️";
        if (code >= 80 && code <= 99) return "🌧️";
        return "❓";
    }
}
