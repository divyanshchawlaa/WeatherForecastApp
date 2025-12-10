import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DailyTableModel extends AbstractTableModel {

    private List<DailyWeatherData> dailyDataList;
    private final String[] columnNames = {"Date", "Min Temp", "Max Temp", "Condition"};

    public DailyTableModel(List<DailyWeatherData> dailyDataList) {
        this.dailyDataList = dailyDataList;
    }

    @Override
    public int getRowCount() {
        return dailyDataList.size();
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
        DailyWeatherData d = dailyDataList.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> d.getDate();
            case 1 -> d.getTempMin();
            case 2 -> d.getTempMax();
            case 3 -> d.getCondition();
            default -> null;
        };
    }

    public void setDailyDataList(List<DailyWeatherData> dailyDataList) {
        this.dailyDataList = dailyDataList;
        fireTableDataChanged();
    }
}
