/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soundtester;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Model danych dla tabeli testu ACR
 */
public class DataTableModelACR extends AbstractTableModel{
    
    private Class[] classes;
    private String[] headers;
    private Object data[][];
    
    private int rows;
    private int cols;
    
    /**
     * 
     * @param testers Liczba osób testujących.
     */
    public DataTableModelACR(int testers){
        this.rows = 0;
        this.cols = 4 + testers;
        this.data = new Object[0][this.cols];
        this.classes = new Class[cols];
        this.classes[0] = Integer.class;
        this.classes[1] = String.class;
        this.classes[2] = String.class;
        this.headers = new String[cols];
        this.headers[0] = "L.p.";
        this.headers[1] = "Nazwa pliku";
        this.headers[2] = "Ścieżka";
        for (int i = 3, j = 1; i < cols-1; i++, j++) {
            this.classes[i] = Integer.class;
            this.headers[i] = "Tester " + j;
        }
        this.classes[cols-1] = String.class;
        this.headers[cols-1] = "Ocena";
    }
    
    @Override
    public int getRowCount() {
	return rows;
    }

    @Override
    public int getColumnCount() {
	return headers.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
	return headers[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
	return classes[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	data[rowIndex][columnIndex] = aValue;
	fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    /**
     * Wstawia wartości do modelu tabeli.
     * @param samples lista próbek, w postaci ścieżek do plików, wstawia: id próbki, nazwę pliku, zeruje kolumny z wynikami
     */
    public void setValueAt(ArrayList<String> samples) {
        rows = samples.size();
        data = new Object[rows][cols];
        for(int i = 0; i < rows; i++) {
            data[i][0] = (i+1);
            data[i][1] = getFileName(samples.get(i));
            data[i][2] = samples.get(i);
            for (int j = 3; j < cols - 1; j++) {
                data[i][j] = 0;
            }
            data[i][cols-1] = 0;
        }
        fireTableDataChanged();
    }
    
    /**
     * Pobiera nazwę próbki ze ścieżki do pliku.
     * @param path Ścieżka do pliku próbki.
     * @return Nazwa pliku próbki.
     */
    private String getFileName(String path) {
        String[] splitPathName;
        if (path.contains("\\")) {
            splitPathName = path.split("\\\\");
        } else {
            splitPathName = path.split("/");
        }
        return splitPathName[splitPathName.length-1];
    }
    
    /**
     * 
     * @return Dane do zapisu w postaci .CSV (kolumny odseparowane średnikiem)
     */
    public String getDataToSave() {
        String result = "";
        for (String str : this.headers) {
            if (!str.equals("Ścieżka")) {
                result += str + ";";
            }
        }
        result += "\n";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j != 2) {
                    result += this.data[i][j] + ";";
                }
            }
            result += "\n";
        }
        return result;
    }
}
