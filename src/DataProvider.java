public interface DataProvider {
    Forecast getForecast(String city, int daysCount);
}
