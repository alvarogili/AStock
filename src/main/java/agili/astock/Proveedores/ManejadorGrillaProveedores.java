/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Proveedores;

import BD.Proveedores.Proveedor;
import agili.astock.BD.AccesosBD;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que Administra los datos de la grilla de proveedores
 * @author Alvaro Gili
 */
public class ManejadorGrillaProveedores {

    /**
     * Lista completa de proveedores
     */
    private static Vector<Object> proveedores = new Vector<Object>();
    private int countProveedores = 0;
    private AccesosBD abd = AccesosBD.getInstance();

    /**
     * Constructor
     */
    public ManejadorGrillaProveedores() {
    }

    /**
     * Actualiza la grilla de proveedores solo por cambios
     * @param proveedoresjTable Tabla
     */
    public void updateProvGridByChanges(JTable proveedoresjTable) {

        int temp = abd.getCount(BD.Proveedores.Proveedor.class);
        if (temp != countProveedores) {
            countProveedores = temp;
            updateProvGrid(proveedoresjTable);
        }

    }

    /**
     * Actualiza la grilla de proveedores
     * @param proveedoresjTable Tabla
     */
    public void updateProvGrid(JTable proveedoresjTable) {

        DefaultTableModel proveedorTableModel = (DefaultTableModel) proveedoresjTable.getModel();
        // limpio la tabla
        while (proveedorTableModel.getRowCount() > 0) {
            proveedorTableModel.removeRow(0);
        }

        //por si tiene algo, limpio el vector
        proveedores.clear();

        Object objects[] = abd.getObjetos(BD.Proveedores.Proveedor.class, null);

        for (int i = 0; i < objects.length; i++) {
            Proveedor proveedor = (Proveedor) objects[i];
            proveedores.add(proveedor);
            //agrego a la grilla
            addProveedor(proveedor, proveedorTableModel);
        }

        if (proveedoresjTable.getRowCount() > 0) {
            proveedoresjTable.setRowSelectionInterval(0, 0);
        }
        /////////////////////////////////////////////////////////

    }

    /**
     * Aplica el filtro al nombre de todos los proveedores y retona los que cumplan
     * @param filtro Texto al pricipio del nombre
     * @return Lista de proveedores válidos
     */
    public void getProveedoresFiltradosPorNombre(String filtro, JTable proveedoresjTable) {

        DefaultTableModel proveedorTableModel = (DefaultTableModel) proveedoresjTable.getModel();
        // limpio la tabla
        while (proveedorTableModel.getRowCount() > 0) {
            proveedorTableModel.removeRow(0);
        }

        filtro = filtro.toUpperCase();

        //cargo todos los que cumplen esa condición
        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i) instanceof Proveedor) {
                Proveedor proveedor = (Proveedor) proveedores.get(i);
                if (proveedor.appGetNombre().toUpperCase().startsWith(filtro)) {

                    //agrego a la grilla
                    addProveedor(proveedor, proveedorTableModel);
                }
            }
        }
        if (proveedoresjTable.getRowCount() > 0) {
            //lo posiciono en la columna 1
            proveedoresjTable.setRowSelectionInterval(0, 0);
        }
    }

    /**
     * Aplica el filtro al nombre de todos los proveedores y retona los que cumplan
     * @param filtro Texto al pricipio del nombre
     * @return Lista de proveedores válidos
     */
    public void getProveedoresFiltradosPorCUIT(String filtro, JTable proveedoresjTable) {

        DefaultTableModel proveedoresTableModel = (DefaultTableModel) proveedoresjTable.getModel();
        // limpio la tabla
        while (proveedoresTableModel.getRowCount() > 0) {
            proveedoresTableModel.removeRow(0);
        }

        filtro = filtro.toUpperCase();
        //cargo todos los que cumplen esa condición
        for (int i = 0; i < proveedores.size(); i++) {
            Proveedor proveedor = (Proveedor) proveedores.get(i);
            String cuit = proveedor.appGetCuit();
            if (cuit.toUpperCase().startsWith(filtro)) {
                //agrego a la grilla
                addProveedor(proveedor, proveedoresTableModel);
            }
        }
        if (proveedoresjTable.getRowCount() > 0) {
            //lo posiciono en la columna 1
            proveedoresjTable.setRowSelectionInterval(0, 0);
        }
    }

    /**
     * Agrega una persona a la grilla de proveedores
     * @param proveedor Objeto proveedor
     * @param proveedorTableModel TableModel
     */
    public void addProveedor(Proveedor proveedor, DefaultTableModel proveedorTableModel) {

        proveedorTableModel.addRow(new Object[]{proveedor.appGetNombre(),
                    proveedor.appGetCuit(),
                    proveedor.appGetDireccion(),
                    proveedor.appGetCodigoPostal(),
                    proveedor.appGetLocalidad(),
                    proveedor.appGetProvincia(),
                    proveedor.appGetTelefono(),
                    proveedor.appGetTelefono2(),
                    proveedor.appGetCorreo(),
                    proveedor.appGetFax()});
    }
}
