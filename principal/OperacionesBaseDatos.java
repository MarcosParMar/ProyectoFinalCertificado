package principal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.*;
import java.util.Iterator;
import java.util.Vector;

public class OperacionesBaseDatos {

    private static Long timepoEjecucion;
    private static Timestamp horaServidor;

    public static Timestamp getHoraServidor() {
        return horaServidor;
    }

    public static void setHoraServidor(Timestamp horaServidor) {
        OperacionesBaseDatos.horaServidor = horaServidor;
    }

    public static Long getTimepoEjecucion() {
        return timepoEjecucion;
    }

    public static void setTimepoEjecucion(Long timepoEjecucion) {
        OperacionesBaseDatos.timepoEjecucion = timepoEjecucion;
    }

    public static void grabarDatosEnBaseDeDatos(File archivo) {

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
                }
            }

            JOptionPane.showMessageDialog(null, "Se han guardado los datos con éxito");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error", null, JOptionPane.ERROR_MESSAGE);
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

    public static DefaultTableModel ejecutarConsulta(String consulta) {

        Long horaInicioConsulta = System.currentTimeMillis();

        DefaultTableModel modelo = new DefaultTableModel();

        try {

            Statement statement = Conexion.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("USE " + Conexion.getDatabase() + "; " + consulta + ";");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                modelo.addColumn(resultSetMetaData.getColumnName(i));
            }

            while (resultSet.next()) {

                Vector<Object> fila = new Vector<>();

                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    fila.addElement(resultSet.getString(i));
                }

                modelo.addRow(fila);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error durante la consulta", null, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        Long horaFinConsulta = System.currentTimeMillis();

        setTimepoEjecucion(horaFinConsulta - horaInicioConsulta);

        try {
            Statement statement = Conexion.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("USE " + Conexion.getDatabase() + "; SELECT GETDATE();");
            while (resultSet.next()) {
                setHoraServidor(resultSet.getTimestamp(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modelo;
    }

    public static void grabarConsultaEnExcel(File archivo, String consulta) {

        try {

            Statement statement = Conexion.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("USE " + Conexion.getDatabase() + "; " + consulta + ";");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();


            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(0, Conexion.getDatabase());

            XSSFRow row = sheet.createRow(0);

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                XSSFCell cell = row.createCell(i - 1);
                cell.setCellValue(resultSetMetaData.getColumnName(i));
            }

            while (resultSet.next()) {

                row = sheet.createRow(resultSet.getRow());

                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(resultSet.getString(i));
                }
            }

            try(FileOutputStream fileOutputStream = new FileOutputStream(archivo)){
                if(archivo.exists()){
                    archivo.delete();
                }

                workbook.write(fileOutputStream);
                fileOutputStream.flush();
                JOptionPane.showMessageDialog(null,"Los datos se han guardado con éxito!");

            }catch (IOException e){
                e.printStackTrace();
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al guardar los datos", null, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
