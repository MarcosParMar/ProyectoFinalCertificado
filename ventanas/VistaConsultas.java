package ventanas;

import principal.Conexion;
import principal.OperacionesBaseDatos;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


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
    private JButton guardarConsultaButton;

    public VistaConsultas(boolean opcionGuardar) {

        guardarConsultaButton.setEnabled(false);
        guardarConsultaButton.setVisible(false);

        if (opcionGuardar) {
            guardarConsultaButton.setVisible(opcionGuardar);
        }

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
                guardarConsultaButton.setEnabled(opcionGuardar);
            }
        });
        guardarConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegarArchivos();
            }
        });
    }

    private void navegarArchivos() {
        JFileChooser selectorArchivos = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(null, "xlsx", "xls");
        selectorArchivos.setFileFilter(filtro);
        selectorArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = selectorArchivos.showSaveDialog(this.panel);
        if (seleccion == JFileChooser.APPROVE_OPTION){
            File archivo = selectorArchivos.getSelectedFile();
            if ((archivo == null) || (archivo.getName().equals(""))) {
                JOptionPane.showMessageDialog(null, "Nombre del archivo inválido", "Nombre del archivo inválido", JOptionPane.ERROR_MESSAGE);
            } else {
                OperacionesBaseDatos.grabarConsultaEnExcel(archivo, consultasArea.getText());
            }
        }
    }

}
