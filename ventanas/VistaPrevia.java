package ventanas;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Iterator;
import java.util.Vector;

public class VistaPrevia {
    private JPanel panel;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton guardarButton;
    private JButton cancelarButton;

    private File archivo;

    public VistaPrevia(String ruta) {

        archivo = new File(ruta);
        rellenarTabla();

        VentanaEmergente ventana = new VentanaEmergente("Vista Previa de " + archivo.getName());
        ventana.setContentPane(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PedirTabla(archivo);
            }
        });
    }

    private void rellenarTabla() {

        DefaultTableModel modelo = new DefaultTableModel();

        try {

            FileInputStream fileInputStream = new FileInputStream(archivo);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {

                Vector<Object> fila = new Vector<>();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (row.getRowNum() == 0) {
                        modelo.addColumn(cell.getStringCellValue());
                    } else {
                        String tipoDeDato = cell.getCellType().toString();

                        switch (tipoDeDato) {
                            case "STRING":
                                fila.addElement(cell.getStringCellValue());
                                break;
                            case "NUMERIC":
                                fila.addElement(cell.getNumericCellValue());
                                break;
                        }
                    }
                }
                if (row.getRowNum() != 0) modelo.addRow(fila);
            }

            table.setModel(modelo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
