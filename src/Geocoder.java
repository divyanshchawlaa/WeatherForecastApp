import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Geocoder {

    public static class GeoResult {
        public double lat;
        public double lon;

        public GeoResult(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }

    public static GeoResult geocode(String city) throws Exception {
        String query = city.replace(" ", "+");
        String urlStr = "https://geocoding-api.open-meteo.com/v1/search?name=" + query + "&count=1";

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) sb.append(line);
        br.close();

        JSONObject root = new JSONObject(sb.toString());

        if (!root.has("results")) return null;

        JSONArray arr = root.getJSONArray("results");
        if (arr.length() == 0) return null;

        JSONObject obj = arr.getJSONObject(0);

        double lat = obj.getDouble("latitude");
        double lon = obj.getDouble("longitude");

        return new GeoResult(lat, lon);
    }
}
