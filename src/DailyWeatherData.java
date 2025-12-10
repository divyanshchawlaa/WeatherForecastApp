public class DailyWeatherData {
    private String date;
    private double tempMin;
    private double tempMax;
    private String condition;

    public DailyWeatherData(String date, double tempMin, double tempMax, String condition) {
        this.date = date;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.condition = condition;
    }

    public String getDate() { return date; }
    public double getTempMin() { return tempMin; }
    public double getTempMax() { return tempMax; }
    public String getCondition() { return condition; }
}
