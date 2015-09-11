/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundtester;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Panel do przeprowadzenia testu DCR i CCR.
 */
public class PanelDCR extends javax.swing.JPanel {
    
    /**
     * Liczba osób testujących.
     */
    private final int testers;
    /**
     * Nr osoby testującej, która aktualnie testuje próbkę.
     */
    private int actualTester;
    /**
     * Model tabeli z danymi.
     */
    private DataTableModelDCR model;
    /**
     * Nr zaznaczonego wiersza z testowaną próbką.
     */
    private int lastSelectedRow;
    /**
     * Jeśli true, to panel obsługuje test CCR, jeśli false, to DCR.
     */
    private final boolean CCR;

    /**
     * 
     * @param samples Lista próbek w postaci ścieżek do plików.
     * @param testers Liczba osób testujących.
     * @param CCR Czy test CCR, czy DCR.
     */
    public PanelDCR(ArrayList<String> samples, int testers, boolean CCR) {
        this.testers = testers;
        this.actualTester = 1;
        this.lastSelectedRow = 0;
        this.CCR = CCR;
        model = new DataTableModelDCR(testers);
        model.setValueAt(samples);
        initComponents();
        initTableModel();
        getRowData();
        
        if (CCR) {
            sliderEvaluation.setMaximum(7);
            sliderEvaluation.setValue(4);
        }
        
        sliderEvaluation.setMajorTickSpacing(1);
        sliderEvaluation.setMinorTickSpacing(1);
        sliderEvaluation.setPaintTicks(true);
        sliderEvaluation.setPaintLabels(true);
    }
    
    /**
     * Ustawienie atrybutów tabeli (np. szerokość kolumn).
     */
    private void initTableModel(){
        int cols = model.getColumnCount();
	TableColumn col;
	col = table.getColumnModel().getColumn(0);
	col.setPreferredWidth(60);
	col.setMaxWidth(60);
	col = table.getColumnModel().getColumn(1);
	col.setPreferredWidth(150);
	col.setMaxWidth(400);
	col = table.getColumnModel().getColumn(2);
	col.setPreferredWidth(200);
	col.setMaxWidth(1000);
	col = table.getColumnModel().getColumn(3);
	col.setPreferredWidth(150);
	col.setMaxWidth(300);
	col = table.getColumnModel().getColumn(4);
	col.setPreferredWidth(200);
	col.setMaxWidth(600);
        for (int i = 5; i < cols-1; i++) {
            col = table.getColumnModel().getColumn(i);
            col.setPreferredWidth(60);
            col.setMaxWidth(100);
        }
	col = table.getColumnModel().getColumn(cols-1);
	col.setPreferredWidth(60);
	col.setMaxWidth(100);        
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.RIGHT);// wyrównanie do prawej kolumny oceny
        col.setCellRenderer(r);
        
        
        table.setRowSelectionInterval(0, 0);
    }
    
    /**
     * Akcja po zaznaczeniu wiersza tabeli. Pobiera dane z zaznaczonego wiersza modelu tabeli i wstawia je do odpowiednich kontrolek w panelu edycji.
     */
    private void getRowData() {
        int row = this.table.getSelectedRow();
        if (row > -1) {
            this.actualTester = 1;
            this.labelTester.setText("Tester: 1");
            this.textFieldSampleName1.setText((String) model.getValueAt(row, 1));
            this.textFieldSampleName2.setText((String) model.getValueAt(row, 3));
            Integer evaluation = (Integer) model.getValueAt(row, 5);
            if (CCR) {
                this.sliderEvaluation.setValue((evaluation == null || evaluation == 0) ? 4 : evaluation);
            } else {
                this.sliderEvaluation.setValue((evaluation == null || evaluation == 0) ? 3 : evaluation);
            }
            
            this.model.setValueAt(String.format("%.2f", getAverageEvaluation(lastSelectedRow)), lastSelectedRow, model.getColumnCount()-1);
            this.lastSelectedRow = row;
        }
    }
    
    /**
     * Odtwarzanie dźwięku.
     * @param path Ściażka do pliku/próbki.
     */
    private void play(String path) {
        try {
            AudioPlayer.player.start(new AudioStream(new FileInputStream(path)));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Błąd przy odtwarzaniu próbki", null, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 
     * @param row Indeks zaznaczonego wiersza w tabeli.
     * @return Średnia arytmetyczna ocen wszystkich testerów.
     */
    private float getAverageEvaluation(int row) {
        int result = 0;
        for (int col = 5; col < testers+5; col++) {
            result += (Integer) model.getValueAt(row, col);
        }
        
        return (float) result / (float) testers;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textFieldSampleName1 = new javax.swing.JTextField();
        buttonPlay1 = new javax.swing.JButton();
        sliderEvaluation = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        buttonAccept = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        labelTester = new javax.swing.JLabel();
        buttonPlay2 = new javax.swing.JButton();
        textFieldSampleName2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        jLabel1.setText("Próbka wzorcowa:");

        textFieldSampleName1.setEditable(false);
        textFieldSampleName1.setPreferredSize(new java.awt.Dimension(200, 24));

        buttonPlay1.setText("Odtwarzaj (wzorzec)");
        buttonPlay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPlay1ActionPerformed(evt);
            }
        });

        sliderEvaluation.setMaximum(5);
        sliderEvaluation.setMinimum(1);
        sliderEvaluation.setPaintLabels(true);
        sliderEvaluation.setValue(3);

        jLabel2.setText("Ocena próbki:");

        buttonAccept.setText("Zatwierdź ocenę");
        buttonAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAcceptActionPerformed(evt);
            }
        });

        buttonSave.setText("Zapisz wyniki");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        labelTester.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTester.setText("Tester: 1"); // NOI18N
        labelTester.setPreferredSize(new java.awt.Dimension(60, 14));

        buttonPlay2.setText("Odtwarzaj (test)"); // NOI18N
        buttonPlay2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPlay2ActionPerformed(evt);
            }
        });

        textFieldSampleName2.setEditable(false);
        textFieldSampleName2.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Próbka testowa:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textFieldSampleName1, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonPlay1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                        .addComponent(buttonSave))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textFieldSampleName2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonPlay2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sliderEvaluation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonAccept)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldSampleName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSave)
                    .addComponent(buttonPlay1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldSampleName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(buttonPlay2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(sliderEvaluation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAccept))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table.setModel(this.model);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableMouseReleased(evt);
            }
        });
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
/**
 * Wybór wiersza z próbką przez użytkownika. Kliknięcie tabeli myszą.
 * @param evt Zdarzenie.
 */
    private void tableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseReleased
        getRowData();
    }//GEN-LAST:event_tableMouseReleased
/**
 * Wybór wiersza z próbką przez użytkownika. Nawigacja przy użyciu klawiatury.
 * @param evt Zdarzenie.
 */
    private void tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyReleased
        getRowData();
    }//GEN-LAST:event_tableKeyReleased
/**
 * Akcja kliknięcia przycisku odtwarzającego próbkę wzorcową z wybranego wiersza tabeli.
 * @param evt Zdarzenie.
 */
    private void buttonPlay1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPlay1ActionPerformed
        int row = this.table.getSelectedRow();
        if (row > -1) {
                this.play((String) model.getValueAt(row, 2));
        }
    }//GEN-LAST:event_buttonPlay1ActionPerformed
/**
 * Akcja kliknięcia przycisku zatwierdzającego ocenę osoby testującej. Zapisuje wynik oceny do modelu tabeli.
 * Sprawdza czy próbka została przetestowana przez wszystkich testerów. Jeśli nie, to przechodzi do następnego testera.
 * Jeśli tak, to sprawdza, czy są jeszcze nieprzetestowane próbki, jeśli tak, to ustawia zaznaczenie kolejnego wiersza tabeli, jeśli nie, to wyświetla stosowny komunikat.
 * @param evt Zdarzenie.
 */
    private void buttonAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAcceptActionPerformed
        int row = this.table.getSelectedRow();
        int col = 4 + actualTester;
        if (row > -1) {
            int evaluation = this.sliderEvaluation.getValue();
            this.model.setValueAt(evaluation, row, col);
            if (actualTester == testers) {
                this.actualTester = 1;
                this.labelTester.setText("Tester: 1");
                if (row + 1 == model.getRowCount()) {
                    JOptionPane.showMessageDialog(this, "Wszystkie próbki zostały przetestowane", null, JOptionPane.INFORMATION_MESSAGE);
                    this.model.setValueAt(String.format("%.2f", getAverageEvaluation(lastSelectedRow)), lastSelectedRow, model.getColumnCount()-1);
                } else {
                    this.table.setRowSelectionInterval(row + 1, row + 1);
                    getRowData();
                }
            } else {
                this.labelTester.setText("Tester: " + ++actualTester);
            }
        }
    }//GEN-LAST:event_buttonAcceptActionPerformed
/**
 * Akcja kliknięcia przycisku zapisu wyników. Pobiera z modelu tabeli dane w postaci ciągu znaków oddzielonych średnikami (.CSV).
 * @param evt Zdarzenie.
 */
    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
            String now = formatter.format(new Date());
            String path = new java.io.File(".").getCanonicalPath() + "\\Raport" + (CCR ? "CCR" : "DCR")  + "_" + now + ".csv";
            FileWriter fileWriter = new FileWriter(path);
            String result = model.getDataToSave();
            fileWriter.write(result);
            fileWriter.close();
            JOptionPane.showMessageDialog(this, "<html>Wyniki zapisano w pliku:<br>" + path + "</html>", null, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "<html>Nie udało się zapisać wyników do pliku:<br>" + ex + "</html>", null, JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_buttonSaveActionPerformed
/**
 * Akcja kliknięcia przycisku odtwarzającego próbkę testową z wybranego wiersza tabeli.
 * @param evt Zdarzenie.
 */
    private void buttonPlay2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPlay2ActionPerformed
        int row = this.table.getSelectedRow();
        if (row > -1) {
            this.play((String) model.getValueAt(row, 4));
        }
    }//GEN-LAST:event_buttonPlay2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAccept;
    private javax.swing.JButton buttonPlay1;
    private javax.swing.JButton buttonPlay2;
    private javax.swing.JButton buttonSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTester;
    private javax.swing.JSlider sliderEvaluation;
    private javax.swing.JTable table;
    private javax.swing.JTextField textFieldSampleName1;
    private javax.swing.JTextField textFieldSampleName2;
    // End of variables declaration//GEN-END:variables
}
