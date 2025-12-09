public class Main {
    public static void main(String[] args) {
        DataProvider provider = new APIWeatherProvider();
        new WeatherAppGUI(provider);
    }
}
