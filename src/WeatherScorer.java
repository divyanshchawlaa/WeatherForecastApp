public class WeatherScorer {
    public static int getScore(WeatherData wd) {
        int score = 100;
        if (wd.getTemperature() < 0 || wd.getTemperature() > 35) score -= 30;
        if (wd.getCondition().contains("Rain")) score -= 20;
        return score;
    }
}
