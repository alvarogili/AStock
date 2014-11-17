/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Agenda;

import agili.astock.BD.AccesosBD;
import agili.astock.BD.Agenda.Evento;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que Administra los datos de la grilla de eventos
 * @author Alvaro Gili
 */
public class ManejadorGrillaEventos {

    private AccesosBD abd;
    private JTable tablaDia = new JTable();
    private Object eventosDia[];

    /**
     * Contructor
     * @param tablaDia Tabla donde se carga el tablaDia
     */
    public ManejadorGrillaEventos(JTable dia, AccesosBD abd) {
        this.tablaDia = dia;
        this.abd = abd;
    }

    /**
     * Actualiza la tabla de eventos diarios
     * @param año Año corriente
     * @param mes Mes corriente
     * @param tablaDia Dia corriente
     */
    public void actualizaGrilla(int año, int mes, int dia) {
        Date desde = new Date(año - 1900, mes, dia, 0, 0, 0);
        Date hasta = new Date(año - 1900, mes, dia, 23, 59, 0);
        eventosDia = abd.getIntervaloEventos(desde, hasta);

        DefaultTableModel tableModel = (DefaultTableModel) tablaDia.getModel();
        // limpio la tabla
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        for (int i = 0; i < eventosDia.length; i++) {
            Evento evento = (Evento) eventosDia[i];

            String hora = "";
            if(evento.appGetHoraYFecha().getHours() < 10){
                hora = "0";
            }
            hora += evento.appGetHoraYFecha().getHours() + ":";
            if(evento.appGetHoraYFecha().getMinutes()<10){
                hora += "0";
            }
            hora += evento.appGetHoraYFecha().getMinutes();
            String descripcion = "" + evento.appGetDescripción();
            String categoria = "";
            if(evento.appGetCategoria() != null)
                categoria = evento.appGetCategoria();
            else
                categoria = "";
            String alarma;

            if (evento.appGetAlarma()) {
                alarma = "Si";
            } else {
                alarma = "No";
            }
            tableModel.addRow(new Object[]{hora,
                            descripcion,
                            categoria,
                            alarma});

        }

        if (tablaDia.getRowCount() > 0) {
            tablaDia.setRowSelectionInterval(0, 0);
        }
        /////////////////////////////////////////////////////////
    }

    /**
     * Retorna la lista de eventos de hoy
     * @return Lista de eventos
     */
    Object[] getListaEventos(){
        return eventosDia;
    }
}
