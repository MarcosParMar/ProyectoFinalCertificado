package ventanas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JMenuBar barraMenu;
    private JMenu menu;
    private JMenuItem opcionUnoMenuItem;
    private JMenuItem opcionDosMenuItem;
    private JMenuItem opcionTresMenuItem;
    private JMenuItem opcionSalirMenuItem;

    public VentanaPrincipal(){
        super();
        setLayout(null);

        barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        menu = new JMenu("Opciones");
        barraMenu.add(menu);

        opcionUnoMenuItem = new JMenuItem("Importar Datos de un Archivo");
        opcionDosMenuItem = new JMenuItem("Ejecutar una consulta sobre una Base de Datos");
        opcionTresMenuItem = new JMenuItem("Ejecutar una consulta y guardarla en un Archivo");
        opcionSalirMenuItem = new JMenuItem("Salir");

        menu.add(opcionUnoMenuItem);
        menu.add(opcionDosMenuItem);
        menu.add(opcionTresMenuItem);
        menu.add(opcionSalirMenuItem);


        opcionUnoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PedirDatosConexion();
            }
        });

        opcionDosMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        opcionTresMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        opcionSalirMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
