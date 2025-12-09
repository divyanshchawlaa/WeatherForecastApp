public class ClothingAdvisor {
    public static String suggestClothes(double temp) {
        if (temp <= 0) return "🧥 Heavy Coat";
        if (temp <= 10) return "🧥 Jacket";
        if (temp <= 20) return "👕 Light Jacket";
        if (temp <= 30) return "👕 T-Shirt";
        return "🩳 Shorts";
    }
}
