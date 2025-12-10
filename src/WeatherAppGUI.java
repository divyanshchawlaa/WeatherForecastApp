import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class WeatherAppGUI extends JFrame {

    private APIWeatherProvider apiProvider = new APIWeatherProvider();
    private List<String> history = new ArrayList<>();

    private JTextField cityField;
    private JComboBox<Integer> daysSelector;
    private JButton fetchBtn, historyBtn;

    private DefaultTableModel hourlyModel, dailyModel;
    private JTable hourlyTable, dailyTable;

    public WeatherAppGUI() {
        setTitle("Weather Forecast App");
        setSize(1050, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Top Panel ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel("City:"));
        cityField = new JTextField(15);
        topPanel.add(cityField);

        topPanel.add(new JLabel("Forecast Days:"));
        daysSelector = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7});
        topPanel.add(daysSelector);

        fetchBtn = new JButton("Fetch Weather");
        topPanel.add(fetchBtn);

        historyBtn = new JButton("History");
        topPanel.add(historyBtn);

        add(topPanel, BorderLayout.NORTH);

        // --- Tabs ---
        JTabbedPane tabs = new JTabbedPane();

        // Hourly Table
        hourlyModel = new DefaultTableModel(
                new String[]{"Time", "Temp (°C)", "Condition", "Clothing", "Alert", "Score"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        hourlyTable = new JTable(hourlyModel);
        hourlyTable.setDefaultRenderer(Object.class, new HoverCellRenderer(hourlyTable));
        JScrollPane hourlyScroll = new JScrollPane(hourlyTable);
        tabs.addTab("Hourly", hourlyScroll);

        // Daily Table
        dailyModel = new DefaultTableModel(
                new String[]{"Date", "Min Temp", "Max Temp", "Condition"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        dailyTable = new JTable(dailyModel);
        dailyTable.getColumnModel().getColumn(3).setCellRenderer(new ConditionCellRenderer());
        JScrollPane dailyScroll = new JScrollPane(dailyTable);
        tabs.addTab("Daily", dailyScroll);

        add(tabs, BorderLayout.CENTER);

        // --- Button Actions ---
        fetchBtn.addActionListener(e -> fetchWeather());
        historyBtn.addActionListener(e -> showHistory());

        // --- Enter key triggers fetch ---
        cityField.addActionListener(e -> fetchWeather());

        setVisible(true);
    }

    private void fetchWeather() {
        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a city.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dayCount = (Integer) daysSelector.getSelectedItem();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private APIWeatherProvider.WeatherResult result;

            @Override
            protected Void doInBackground() {
                try { result = apiProvider.fetchWeather(city, dayCount); }
                catch (Exception ex) { result = null; }
                return null;
            }

            @Override
            protected void done() {
                if (result == null) {
                    JOptionPane.showMessageDialog(WeatherAppGUI.this,
                            "City not found or API error.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    updateTables(result);
                    if (!history.contains(city)) history.add(city);
                }
            }
        };
        worker.execute();
    }

    private void updateTables(APIWeatherProvider.WeatherResult result) {
        // --- Hourly ---
        hourlyModel.setRowCount(0);
        for (HourlyWeatherData h : result.hourly) {
            hourlyModel.addRow(new Object[]{
                    h.getTime(),
                    h.getTemperature(),
                    h.getCondition(),
                    h.getClothing(),
                    h.getAlert(),
                    h.getScore()
            });
        }

        // --- Daily ---
        dailyModel.setRowCount(0);
        for (DailyWeatherData d : result.daily) {
            dailyModel.addRow(new Object[]{d.getDate(), d.getTempMin(), d.getTempMax(), d.getCondition()});
        }
    }

    private void showHistory() {
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No history yet.", "History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String hist = String.join("\n", history);
        JTextArea textArea = new JTextArea(hist);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(300, 200));

        JOptionPane.showMessageDialog(this, scroll, "City History", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- Renderer for Condition Column ---
    static class ConditionCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String cond = value.toString();
            String icon = switch (cond) {
                case "Clear" -> "☀️";
                case "Partly Cloudy" -> "🌤️";
                case "Cloudy" -> "☁️";
                case "Fog" -> "🌫️";
                case "Drizzle" -> "🌦️";
                case "Rain" -> "🌧️";
                case "Snow" -> "❄️";
                default -> "❓";
            };
            label.setText(icon + " " + cond);
            return label;
        }
    }

    // --- Renderer for Hover coloring ---
    static class HoverCellRenderer extends DefaultTableCellRenderer {
        private final JTable table;

        public HoverCellRenderer(JTable table) { this.table = table; }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Default
            label.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);

            int hoverRow = -1;
            if (table.getMousePosition() != null)
                hoverRow = table.rowAtPoint(table.getMousePosition());

            if (row == hoverRow) {
                try {
                    // Temperature coloring (column 1)
                    double temp = Double.parseDouble(table.getValueAt(row, 1).toString());
                    if (temp >= 30) label.setBackground(new Color(255,102,102));       // red
                    else if (temp >= 20) label.setBackground(new Color(255,178,102));  // orange
                    else if (temp >= 10) label.setBackground(new Color(102,178,255));  // blue
                    else label.setBackground(new Color(153,102,255));                  // purple
                } catch (Exception ignored) {}
            }

            return label;
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(WeatherAppGUI::new);
    }
}
