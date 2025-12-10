public class WeatherScorer {

    // Simple scoring function (0–10)
    public static int scoreTemperature(double temp) {
        if (temp < 0) return 2;
        if (temp < 10) return 5;
        if (temp < 20) return 8;
        return 10;
    }

    // Score for daily weather
    public static int scoreDaily(double min, double max) {
        return scoreTemperature((min + max) / 2.0);
    }
}
