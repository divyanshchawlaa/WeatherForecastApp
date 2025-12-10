import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class WeatherTableModel extends AbstractTableModel {

    private final String[] columns = {
            "City", "Date", "Temp Min (°C)", "Temp Max (°C)",
            "Current Temp (°C)", "Condition", "Score", "Clothing Advice"
    };

    private List<WeatherData> data = new ArrayList<>();

    public void setWeatherData(WeatherData wd) {
        data.clear();
        if (wd != null) {
            data.add(wd);
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WeatherData wd = data.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> wd.getCity();
            case 1 -> wd.getDate();
            case 2 -> wd.getTempMin();
            case 3 -> wd.getTempMax();
            case 4 -> wd.getCurrentTemp();
            case 5 -> wd.getCondition();
            case 6 -> wd.getScore();
            case 7 -> wd.getClothingAdvice();
            default -> "";
        };
    }
}
