/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Agenda;

import java.awt.Color;
import java.awt.Component;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 * clase para colorear el dia actual
 * @author Alvaro Gili
 */
public class RenderCalendario extends JFormattedTextField implements TableCellRenderer {

    private int mes;
    private int año;

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        JFormattedTextField campoTexto = new JFormattedTextField();
        campoTexto.setBorder(BorderFactory.createEmptyBorder());

        //si lo selecciona lo pinto azul sino celeste si es el dia corriente
        if (value instanceof Integer) {
            Integer i = new Integer(value.toString());
            String valor = value.toString();
            campoTexto.setText(valor);
            campoTexto.setHorizontalAlignment(JTextField.CENTER);
            /********************************************************/
            //obtengo el hoy y comparo para ver si estoy en el mes corriente y coloreo el dia de hoy
            GregorianCalendar calendar = new GregorianCalendar();
            int mesCorriente = calendar.get(GregorianCalendar.MONTH);
            int añoCorriente = calendar.get(GregorianCalendar.YEAR);
            int diaCorriente = calendar.get(GregorianCalendar.DAY_OF_MONTH);
            if (i == diaCorriente && mes == mesCorriente && año == añoCorriente) {
                if (isSelected) {
                    campoTexto.setBackground(new Color(163, 190, 224));
                } else {
                    //le doy color celeste al dia de hoy
                    campoTexto.setBackground(new Color(128, 255, 255));
                }
            } else if (isSelected) {
                campoTexto.setBackground(new Color(163, 190, 224));
            }
        }

        return campoTexto;
    }

    /**
     * Cargo el mes y el año para colorear el dia correcto
     * @param mes
     * @param año
     */
    public void setMesYAño(int mes, int año) {
        this.mes = mes;
        this.año = año;
    }
}

