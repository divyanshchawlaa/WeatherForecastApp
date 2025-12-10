import java.util.List;

public class WeatherData {

    private String city;
    private String date;

    private double tempMax;
    private double tempMin;
    private double currentTemp;
    private String condition;

    private int score;
    private String clothingAdvice;

    private List<HourlyWeatherData> hourlyForecast;
    private List<DailyWeatherData> dailyForecast;

    public WeatherData(String city, String date,
                       double tempMax, double tempMin,
                       double currentTemp, String condition,
                       int score, String clothingAdvice,
                       List<HourlyWeatherData> hourlyForecast,
                       List<DailyWeatherData> dailyForecast) {

        this.city = city;
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.currentTemp = currentTemp;
        this.condition = condition;
        this.score = score;
        this.clothingAdvice = clothingAdvice;
        this.hourlyForecast = hourlyForecast;
        this.dailyForecast = dailyForecast;
    }

    public String getCity() { return city; }
    public String getDate() { return date; }
    public double getTempMax() { return tempMax; }
    public double getTempMin() { return tempMin; }
    public double getCurrentTemp() { return currentTemp; }
    public String getCondition() { return condition; }
    public int getScore() { return score; }
    public String getClothingAdvice() { return clothingAdvice; }

    public List<HourlyWeatherData> getHourlyForecast() { return hourlyForecast; }
    public List<DailyWeatherData> getDailyForecast() { return dailyForecast; }
}
