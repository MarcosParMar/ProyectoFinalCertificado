package ventanas;

import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class VistaPrevia {
    private JPanel panel;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton guardarButton;
    private JButton cancelarButton;

    private File archivo;

    public VistaPrevia(String ruta) {

        VentanaEmergente ventana = new VentanaEmergente("Vista Previa");
        ventana.setContentPane(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);

        archivo = new File(ruta);
        rellenarTabla();

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    private void rellenarTabla() {

        try {
            int iRow = 0;
            InputStream input = new FileInputStream(archivo);
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.createRow(iRow);


            while (row != null) {
                Cell cell = row.createCell(0);
                String value = cell.getStringCellValue();

                //inserta lo que quieres hacer


                //

                iRow++;
                row = sheet.getRow(iRow);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
