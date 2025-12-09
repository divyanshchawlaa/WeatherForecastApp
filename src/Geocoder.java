import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class Geocoder {
    public static String[] getCoordinates(String place) {
        try {
            String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + place;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) return null;

            JSONObject obj = new JSONObject(response.body());
            JSONArray results = obj.optJSONArray("results");
            if (results == null || results.length() == 0) return null;

            // Take the first result (works for city, state, town)
            JSONObject first = results.getJSONObject(0);
            String lat = String.valueOf(first.getDouble("latitude"));
            String lon = String.valueOf(first.getDouble("longitude"));
            return new String[]{lat, lon};

        } catch (Exception e) {
            System.out.println("Error fetching coordinates: " + e.getMessage());
            return null;
        }
    }
}
