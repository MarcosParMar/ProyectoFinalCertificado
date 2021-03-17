package ventanas;

import principal.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PedirBasesDatos {
    private JPanel panel;
    private JComboBox baseDatosComboBox;
    private JButton seleccionarButton;
    private JButton atrásButton;

    public PedirBasesDatos() {

        listarBaseDatos();
        VentanaEmergente ventana = new VentanaEmergente("Datos de Conexión");
        ventana.setContentPane(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);

        atrásButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
                new PedirDatosConexion();
            }
        });

        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion.setDatabase(baseDatosComboBox.getSelectedItem().toString());
                new Conexion(Conexion.getUrl() + "database=" + baseDatosComboBox.getSelectedItem() + ";");
                new VentanaPrincipal();
                ventana.dispose();
            }
        });
    }

    private void listarBaseDatos() {
        try {
            Statement statement = Conexion.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM master.sys.databases ");
            while (true) {
                if (!resultSet.next()) break;
                baseDatosComboBox.addItem(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
