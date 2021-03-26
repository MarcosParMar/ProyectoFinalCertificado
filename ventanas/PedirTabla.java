package ventanas;

import principal.Conexion;
import principal.OperacionesBaseDatos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PedirTabla {
    private JPanel panel;
    private JList tablasList;
    private JButton seleccionarButton;
    private JButton cancelarButton;

    public PedirTabla(File archivo) {

        rellenarLista();

        VentanaEmergente ventana = new VentanaEmergente("Base de datos " + Conexion.getDatabase());
        ventana.setContentPane(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);


        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
            }
        });
        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion.setTabla(tablasList.getSelectedValue().toString());
                JOptionPane.showConfirmDialog(null, "Se guardarán los datos en la tabla '" + Conexion.getTabla() + "' de la base de datos '" + Conexion.getDatabase() + "'. \n" +
                        "¿Está Seguro?", null, JOptionPane.CANCEL_OPTION);
                OperacionesBaseDatos.grabarDatosEnBaseDeDatos(archivo);
                ventana.dispose();
            }
        });
    }

    private void rellenarLista() {

        DefaultListModel modelo = new DefaultListModel();

        try {
            Statement statement = Conexion.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("USE " + Conexion.getDatabase() + " SELECT CAST(table_name as varchar)  FROM INFORMATION_SCHEMA.TABLES");
            while (true) {
                if (!resultSet.next()) break;
                modelo.addElement(resultSet.getString(1));
            }
            tablasList.setModel(modelo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
