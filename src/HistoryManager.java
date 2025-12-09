import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private List<WeatherData> history;

    public HistoryManager() {
        history = new ArrayList<>();
    }

    // Add a WeatherData entry to history
    public void addToHistory(WeatherData wd) {
        history.add(wd);
    }

    // Get the full history list
    public List<WeatherData> getHistory() {
        return history;
    }

    // Print all history entries to the console
    public void printHistory() {
        if (history.isEmpty()) {
            System.out.println("No history available.");
            return;
        }
        System.out.println("=== Weather History ===");
        for (WeatherData wd : history) {
            System.out.println(
                    wd.getCity() + " | " +
                            wd.getDate() + " | " +
                            String.format("%.1f°C", wd.getTemperature()) + " | " +
                            wd.getCondition()
            );
        }
    }
}
