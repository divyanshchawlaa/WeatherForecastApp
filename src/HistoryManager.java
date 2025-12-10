import java.io.*;
import java.util.*;

public class HistoryManager {

    private static final String FILE = "history.txt";

    public void saveSearch(String city) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(city + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> loadHistory() {
        List<String> list = new ArrayList<>();

        File f = new File(FILE);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) list.add(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // For WeatherAppGUI
    public String getHistoryDialogText() {
        List<String> h = loadHistory();
        if (h.isEmpty()) return "No search history yet.";

        StringBuilder sb = new StringBuilder("Your Search History:\n\n");
        for (String city : h) {
            sb.append("• ").append(city).append("\n");
        }
        return sb.toString();
    }
}
