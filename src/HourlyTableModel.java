import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HourlyTableModel extends AbstractTableModel {

    private List<HourlyWeatherData> hourlyDataList;
    private final String[] columnNames = {"Time", "Temp (°C)", "Condition", "Clothing", "Alert", "Score"};

    public HourlyTableModel(List<HourlyWeatherData> hourlyDataList) {
        this.hourlyDataList = hourlyDataList;
    }

    @Override
    public int getRowCount() {
        return hourlyDataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HourlyWeatherData h = hourlyDataList.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> h.getTime();
            case 1 -> h.getTemperature();
            case 2 -> h.getCondition();
            case 3 -> h.getClothing();
            case 4 -> h.getAlert();
            case 5 -> h.getScore();
            default -> null;
        };
    }

    public void setHourlyDataList(List<HourlyWeatherData> hourlyDataList) {
        this.hourlyDataList = hourlyDataList;
        fireTableDataChanged();
    }
}
