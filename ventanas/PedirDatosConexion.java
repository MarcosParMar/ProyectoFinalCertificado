package ventanas;

import principal.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PedirDatosConexion {
    private JLabel servidorLabel;
    private JLabel contrasenaLabel;
    private JPanel panel;
    private JLabel usuarioLabel;
    private JPasswordField passwordField;
    private JTextField usuarioTextField;
    private JTextField servidorTextField;
    private JButton salirButton;
    private JLabel puertoLabel;
    private JTextField puertoTextField;
    private JButton aceptarButton;

    public PedirDatosConexion() {

        VentanaEmergente ventana = new VentanaEmergente("Datos de Conexión");
        ventana.setContentPane(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);


        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
            }
        });
        salirButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ventana.dispose();
                }
            }
        });
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 new Conexion(componerURL());
            }
        });
    }

    private String componerURL(){
        String servidor = servidorTextField.getText();
        String puerto = puertoTextField.getText();
        String user = usuarioTextField.getText();
        char[] arrayPassword = passwordField.getPassword();
        String password = String.valueOf(arrayPassword);

        String url = "jdbc:sqlserver://" + servidor + ":" + puerto + ";user=" + user + ";password=" + password + ";";
        return url;
    }
}
