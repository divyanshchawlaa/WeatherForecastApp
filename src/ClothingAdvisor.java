public class ClothingAdvisor {

    public static String recommend(double avgTemp) {
        if (avgTemp < 0) return "Heavy Jacket 🧥";
        if (avgTemp < 10) return "Jacket";
        if (avgTemp < 20) return "Light Layer";
        return "T-Shirt 👕";
    }
}
