public class AlertManager {
    public static void checkAlerts(WeatherData wd) {
        if (wd.getTemperature() >= 35)
            System.out.println("ALERT: It's extremely hot today!");
        if (wd.getTemperature() <= 0)
            System.out.println("ALERT: Freezing temperature! Dress warmly.");
        if (wd.getCondition().contains("Rainy"))
            System.out.println("ALERT: Rainy weather, carry an umbrella!");
    }
}
