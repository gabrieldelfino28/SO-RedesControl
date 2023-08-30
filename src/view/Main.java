package view;

import controller.RedesController;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        RedesController rc = new RedesController();
        String Menu;
        do {
            Menu = JOptionPane.showInputDialog("Menu Redes Control\n1- IpConfig\n2- Ping\n9- Fim");
            switch (Menu) {
                case "1" -> rc.IPIdentify();
                case "2" -> rc.Ping();
                case "9" -> System.exit(0);
                default -> JOptionPane.showMessageDialog(null, "Valor inválido, tente novamente");
            }
        } while (true);
    }

}
