package ventanas;

import javax.swing.*;

public class VentanaEmergente extends JFrame {

    public VentanaEmergente(String titulo) {
        super(titulo);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
