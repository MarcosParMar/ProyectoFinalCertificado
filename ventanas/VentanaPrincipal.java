package ventanas;

import principal.Conexion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class VentanaPrincipal extends JFrame {

    private JPanel panel;
    private JLabel conexionLabel;
    private JButton importarDatosButton;
    private JButton ejecutarConsultaButton;
    private JButton exportarConsultaButton;

    public VentanaPrincipal() {

        super();
        setContentPane(panel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        conexionLabel.setText("Conexión seralizada con el servidor " + Conexion.getServidor() + " y la base de datos " + Conexion.getDatabase());

        importarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegarArchivos();
            }
        });
        ejecutarConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaConsultas(false);
            }
        });
        exportarConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaConsultas(true);
            }
        });
    }

    private void navegarArchivos() {
        JFileChooser selectorArchivos = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(null, "xlsx", "xls");
        selectorArchivos.setFileFilter(filtro);
        selectorArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = selectorArchivos.showOpenDialog(this);
        File archivo = selectorArchivos.getSelectedFile();
        if ((archivo == null) || (archivo.getName().equals(""))) {
            JOptionPane.showMessageDialog(null, "Nombre del archivo inválido", "Nombre del archivo inválido", JOptionPane.ERROR_MESSAGE);
        } else {
            new VistaPrevia(archivo.getAbsolutePath());
        }
    }
}
