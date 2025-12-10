import java.util.ArrayList;
import java.util.List;

public class AlertManager {

    private List<String> alerts = new ArrayList<>();

    public List<String> getAlerts() {
        return alerts;
    }

    public void clear() {
        alerts.clear();
    }

    public void checkHourly(List<HourlyWeatherData> hourlyData) {
        for (HourlyWeatherData wd : hourlyData) {
            double t = wd.getTemperature();

            if (t < 0) alerts.add("⚠ Freezing temperature at " + wd.getTime());
            if (t > 35) alerts.add("🔥 Extreme heat at " + wd.getTime());
        }
    }

    public void checkDaily(List<DailyWeatherData> dailyData) {
        for (DailyWeatherData d : dailyData) {
            if (d.getTempMax() > 35) alerts.add("🔥 Heatwave expected on " + d.getDate());
            if (d.getTempMin() < 0) alerts.add("❄ Freezing night on " + d.getDate());
        }
    }
}
