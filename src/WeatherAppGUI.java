import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class WeatherAppGUI {
    private DataProvider provider;
    private HistoryManager history;

    public WeatherAppGUI(DataProvider provider) {
        this.provider = provider;
        this.history = new HistoryManager();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("🌤️ OOP Weather Forecast");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout());

        // Top panel with city input
        JPanel topPanel = new JPanel();
        JLabel cityLabel = new JLabel("Enter City: ");
        JTextField cityField = new JTextField(20);
        JButton fetchButton = new JButton("Get Forecast");
        topPanel.add(cityLabel);
        topPanel.add(cityField);
        topPanel.add(fetchButton);
        frame.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Date", "Temp(°C)", "Condition", "Clothing", "Score", "Alerts"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        JTable table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        table.setRowHeight(70);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Action listener for fetch button & Enter key
        ActionListener fetchForecast = e -> {
            String city = cityField.getText().trim();
            if (city.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a city name.");
                return;
            }
            tableModel.setRowCount(0); // clear previous data

            Forecast forecast = provider.getForecast(city, 3); // fetch 3 days
            if (forecast.getDays().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No data found for this city.");
                return;
            }

            for (WeatherData wd : forecast.getDays()) {
                String alerts = "";
                if (wd.getTemperature() >= 35) alerts += "🔥 Hot ";
                if (wd.getTemperature() <= 0) alerts += "❄️ Freezing ";
                if (wd.getCondition().contains("Rain")) alerts += "☔ Rain ";

                Object[] rowData = {
                        wd.getCity() + "\n" + wd.getDate(),
                        String.format("%.1f", wd.getTemperature()),
                        wd.getIcon() + " " + wd.getCondition(),
                        ClothingAdvisor.suggestClothes(wd.getTemperature()),
                        WeatherScorer.getScore(wd),
                        alerts
                };
                tableModel.addRow(rowData);
                history.addToHistory(wd);
            }
        };

        fetchButton.addActionListener(fetchForecast);
        cityField.addActionListener(fetchForecast);

        frame.setVisible(true);
    }

    // Multi-line cell renderer with colored alerts
    static class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText(value != null ? value.toString() : "");

            // Alerts coloring
            if (column == 5) {
                if (getText().contains("🔥")) setForeground(Color.RED);
                else if (getText().contains("❄️")) setForeground(Color.BLUE);
                else if (getText().contains("☔")) setForeground(Color.ORANGE);
                else setForeground(Color.BLACK);
            } else {
                setForeground(Color.BLACK);
            }

            setFont(new Font("SansSerif", Font.PLAIN, 14));
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
            return this;
        }
    }
}
