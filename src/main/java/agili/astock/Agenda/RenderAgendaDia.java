/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 * clase para colorear el dia actual
 * @author Alvaro Gili
 */
public class RenderAgendaDia extends JFormattedTextField implements TableCellRenderer {

    private int mes;
    private int año;

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        JLabel campoTexto = new JLabel();
        campoTexto.setBorder(BorderFactory.createEmptyBorder());
        campoTexto.setHorizontalAlignment(JTextField.CENTER);
        // es una categoria
        if (column == 2) {
            if (value.toString().equals("Académico")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/books_048.png")));
            } else if (value.toString().equals("Aniversario")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/package_favorite.png")));
            } else if (value.toString().equals("Clientes")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/system-users.png")));
            } else if (value.toString().equals("Cumpleaños")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/cumpleaños.png")));
            } else if (value.toString().equals("Día festivo")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/balloons48.png")));
            } else if (value.toString().equals("Feriado")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/date48.png")));
            } else if (value.toString().equals("Importante")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/important_calendar.png")));
            } else if (value.toString().equals("Llamada")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/internet-telephony.png")));
            } else if (value.toString().equals("Médico")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/Ambulance.png")));
            } else if (value.toString().toString().equals("Ocio")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/kteatime.png")));
            } else if (value.toString().toString().equals("Proveedores")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/LorryGreen.png")));
            } else if (value.toString().toString().equals("Proyecto")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/file-roller.png")));
            } else if (value.toString().toString().equals("Recordatorio")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/knotes.png")));
            } else if (value.toString().toString().equals("Trabajo")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/agt_utilities.png")));
            } else if (value.toString().toString().equals("Vacaciones")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/Umbrella-48x48.png")));
            } else if (value.toString().toString().equals("Viaje")) {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/plane.png")));
            } else {
                campoTexto.setIcon(new ImageIcon(getClass().getResource("/img/flag.png")));
            }
        } else {
            //sino coloco el texto
            campoTexto.setText(value.toString());
        }

        return campoTexto;
    }
}
