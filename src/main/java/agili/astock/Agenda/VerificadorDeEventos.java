/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Agenda;

import agili.astock.AStock;
import agili.astock.BD.AccesosBD;
import agili.astock.BD.Agenda.Evento;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread encargado de verificar los eventos minuto a minuto
 * y reportar cuando hay una alarma
 * @author Alvaro Gili
 */
public class VerificadorDeEventos extends Thread {

    private boolean verificarEventosAlInicio = false;
    private AStock aStock;
    private AccesosBD abd;

    /**
     * Constructor
     */
    public VerificadorDeEventos(boolean verificarEventosAlInicio, AStock aStock, AccesosBD accesosBD) {
        this.verificarEventosAlInicio = verificarEventosAlInicio;
        this.aStock = aStock;
        this.abd = accesosBD;
    }

    @Override
    public void run() {        
        GregorianCalendar calendar = new GregorianCalendar();
        int mesCorriente;
        int añoCorriente;
        int diaCorriente;

        mesCorriente = calendar.get(GregorianCalendar.MONTH);
        añoCorriente = calendar.get(GregorianCalendar.YEAR);
        diaCorriente = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        try {
            VerificadorDeEventos.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(VerificadorDeEventos.class.getName()).log(Level.SEVERE, null, ex);
        }


        //En el inicio de la aplicacion verifico si hay eventos para hoy
        if (verificarEventosAlInicio) {

            Date desde = new Date(añoCorriente - 1900, mesCorriente, diaCorriente, 0, 0, 0);
            Date hasta = new Date(añoCorriente - 1900, mesCorriente, diaCorriente, 23, 59, 0);

            Object eventos[] = abd.getIntervaloEventos(desde, hasta);

            if (eventos != null && eventos.length > 0) {
                aStock.mostrarAlertaEventosDia(aStock);
            }
        }

        //ciclo que verifica una vez por minuto si hay algun evento con alarma para mostrar
        while (true) {
            try {
                VerificadorDeEventos.sleep(1000*60);
                //cargo en intervalo de 10 mnutos
                GregorianCalendar desde = new GregorianCalendar();
                GregorianCalendar hasta = new GregorianCalendar();
                hasta.add(GregorianCalendar.MINUTE, 10);

                //obtengo los eventos con alarmas entre ahora y los proximos 10 minutos
                Object eventosAMostrar[] = abd.getIntervaloEventos(desde.getTime(), hasta.getTime());
                if(eventosAMostrar != null && eventosAMostrar.length > 0){
                    //hay posibles alarmas
                    //recorro para ver las que tengan alarma en si y mostrado en no
                    for(int i = 0; i < eventosAMostrar.length; i++){
                        Evento evento = (Evento) eventosAMostrar[i];
                        //ver si ya fue mostrado
                        if(!evento.appGetMostrado()){
                            evento.appSetMostrado(true);
                            String verificacion = "categoria.equals('" + evento.appGetCategoria() +
                                    "') && descripcion.equals('" + evento.appGetDescripción() + "')";
                            abd.actualizarObjeto(evento.getClass(), evento, verificacion);
                            //mostrar una ventana por evento                            
                            aStock.mostrarAlertaEvento(aStock, evento);
                        }
                    }
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(VerificadorDeEventos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
