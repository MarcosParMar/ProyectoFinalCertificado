package principal;

import org.apache.commons.collections4.functors.WhileClosure;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

public class OperacionesBaseDatos {

    public static void grabarDatos(File archivo) {

        try {

            FileInputStream fileInputStream = new FileInputStream(archivo);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            Vector<Object> cabeceras = new Vector<>();
            Statement statement = Conexion.getConnection().createStatement();

            while (rowIterator.hasNext()) {

                Vector<Object> fila = new Vector<>();

                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    if (row.getRowNum() == 0) {
                        cabeceras.addElement(cell.getStringCellValue());
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
                if (row.getRowNum() != 0) {
                    statement.executeUpdate("INSERT INTO " + Conexion.getTabla() + " " + imprimirCabecera(cabeceras) + " VALUES " + imprimirFila(fila));
                    JOptionPane.showMessageDialog(null, "Se han guardado los datos con éxito");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String imprimirFila(Vector vector) {
        String vectorImpreso = new String();
        int posicion = 1;
        for (Object o : vector) {
            String tipoDato = o.getClass().getSimpleName();
            if (posicion == 1 && posicion < vector.size()) {
                switch (tipoDato) {
                    case "String":
                        vectorImpreso = "('" + o.toString() + "', ";
                        break;
                    case "Double":
                        vectorImpreso = "(" + o.toString() + ", ";
                        break;
                }
            } else if (posicion < vector.size()) {
                switch (tipoDato) {
                    case "String":
                        vectorImpreso = vectorImpreso + "'" + o.toString() + "', ";
                        break;
                    case "Double":
                        vectorImpreso = vectorImpreso + o.toString() + ", ";
                        break;
                }
            } else {
                switch (tipoDato) {
                    case "String":
                        vectorImpreso = vectorImpreso + "'" + o.toString() + "')";
                        break;
                    case "Double":
                        vectorImpreso = vectorImpreso + o.toString() + ")";
                        break;
                }
            }
            posicion++;
        }
        return vectorImpreso;
    }

    public static String imprimirCabecera(Vector vector) {
        String vectorImpreso = new String();
        int posicion = 1;
        for (Object o : vector) {
            if (posicion == 1 && posicion < vector.size()) {
                vectorImpreso = "(" + o.toString() + ", ";
            } else if (posicion < vector.size()) {
                vectorImpreso = vectorImpreso + o.toString() + ", ";
            } else {
                vectorImpreso = vectorImpreso + o.toString() + ")";
            }
            posicion++;
        }
        return vectorImpreso;
    }
}
