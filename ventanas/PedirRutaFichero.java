package ventanas;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PedirRutaFichero {
    private JPanel panel;
    private JButton abrirButton;
    private JLabel rutaLabel;
    private JPanel rutaPanel;
    private JButton seleccionarButton;
    private JButton salirButton;

    private VentanaEmergente ventana;

    public PedirRutaFichero() {

        ventana = new VentanaEmergente("Seleccionar Fichero");
        ventana.setContentPane(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);

        abrirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegarArchivos();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
            }
        });

        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void navegarArchivos() {
        JFileChooser selectorArchivos = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(null, "xlsx", "xls");
        selectorArchivos.setFileFilter(filtro);
        selectorArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = selectorArchivos.showOpenDialog(ventana);
        File archivo = selectorArchivos.getSelectedFile();
        if ((archivo == null) || (archivo.getName().equals(""))) {
            JOptionPane.showMessageDialog(null, "Nombre del archivo inválido", "Nombre del archivo inválido", JOptionPane.ERROR_MESSAGE);
        } else {
            rutaLabel.setText(archivo.getAbsolutePath());
            ventana.pack();
            ventana.setLocationRelativeTo(null);
        }
    }
}
