package agili.astock.Utilidades;

import agili.astock.AStock;
import agili.astock.Clientes.ManejadorGrillaClientes;
import agili.astock.Productos.ManejadorGrillaProductos;
import agili.astock.Proveedores.ManejadorGrillaProveedores;

/**
 * Actualiza cada un cierto tiempo toda la información
 * @author Alvaro
 */
public class ThreadActualizador extends Thread {

    private AStock parent;
    private int tiempoDeActualizacion;
    private ManejadorGrillaClientes actClientes = new ManejadorGrillaClientes();
    private ManejadorGrillaProveedores actProveedores = new ManejadorGrillaProveedores();
    private ManejadorGrillaProductos actProductos = new ManejadorGrillaProductos();
    private static ThreadActualizador singleton;

    private ThreadActualizador() {
    }

    static public ThreadActualizador getInstance() {
        if (singleton == null) {
            singleton = new ThreadActualizador();
        }
        return singleton;
    }

    public void setParent(AStock parent) {
        this.parent = parent;
    }

    public void setTiempoDeActualizacion(int tiempoDeActualizacion) {
        this.tiempoDeActualizacion = tiempoDeActualizacion;
    }

    @Override
    public void run() {

        while (true) {
            try {
                //////////////////////////////////////////////////////
                //actualizo las 3 grillas principales
                //buscar donde lo uso y sacar y despertar este thread                
                actClientes.updateClientsGridByChanges(parent.getClientesjTable(),
                        parent.getPersonasjCheckBox(),
                        parent.getEmpresasjCheckBox(),
                        parent);
                actProveedores.updateProvGridByChanges(parent.getProveedoresjTable());
                actProductos.updateProdGridByChanges(parent.getProductosjTable());
                /////////////////////////////////////////////////////
                synchronized (this) {
                    wait(tiempoDeActualizacion * 1000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    public void actualizarClientes() {
        actClientes.updateClientsGrid(parent.getClientesjTable(),
                parent.getPersonasjCheckBox(),
                parent.getEmpresasjCheckBox(),
                parent);
    }

    public void actualizarProveedores() {
        actProveedores.updateProvGrid(parent.getProveedoresjTable());
    }
}
