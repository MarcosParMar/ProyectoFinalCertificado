package ventanas;

import principal.Conexion;
import principal.OperacionesBaseDatos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class VistaConsultas {
    private JPanel panel;
    private JTextArea consultasArea;
    private JScrollPane scrollPaneDos;
    private JTable table;
    private JScrollPane scrollPaneUno;
    private JButton ejecutarButton;
    private JButton salirButton;
    private JLabel tiempoRespuestaLablel;
    private JLabel horaServidorLabel;

    public VistaConsultas() {

        VentanaEmergente ventana = new VentanaEmergente("Consulta SQL sobre la base de datos " + Conexion.getDatabase());
        ventana.setContentPane(panel);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
            }
        });

        ejecutarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setModel(OperacionesBaseDatos.ejecutarConsulta(consultasArea.getText()));
                tiempoRespuestaLablel.setText(String.valueOf("Tiempo de ejecución de la consulta: " + OperacionesBaseDatos.getTimepoEjecucion() + " milisegundos"));
                horaServidorLabel.setText(OperacionesBaseDatos.getHoraServidor().toString());
            }
        });
    }

}
