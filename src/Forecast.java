import java.util.ArrayList;
import java.util.List;

public class Forecast {
    private List<WeatherData> days;

    public Forecast() {
        this.days = new ArrayList<>();
    }

    public void addDay(WeatherData wd) { days.add(wd); }
    public List<WeatherData> getDays() { return days; }
}
