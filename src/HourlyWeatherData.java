public class HourlyWeatherData {
    private String time;
    private double temperature;
    private String condition;
    private String clothing; // Clothing suggestion
    private String alert;    // Weather alert
    private int score;       // Comfort score

    public HourlyWeatherData(String time, double temperature, String condition,
                             String clothing, String alert, int score) {
        this.time = time;
        this.temperature = temperature;
        this.condition = condition;
        this.clothing = clothing;
        this.alert = alert;
        this.score = score;
    }

    public String getTime() { return time; }
    public double getTemperature() { return temperature; }
    public String getCondition() { return condition; }
    public String getClothing() { return clothing; }
    public String getAlert() { return alert; }
    public int getScore() { return score; }
}
