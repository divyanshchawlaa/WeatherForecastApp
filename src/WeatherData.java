public class WeatherData {
    private String city;
    private String date;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private String condition;
    private String icon;

    public WeatherData(String city, String date, double temperature, double humidity, double windSpeed, String condition) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.condition = condition;
    }

    // Getters and setters
    public String getCity() { return city; }
    public String getDate() { return date; }
    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public double getWindSpeed() { return windSpeed; }
    public String getCondition() { return condition; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    @Override
    public String toString() {
        return date + " | " + temperature + "°C | " + condition;
    }
}
