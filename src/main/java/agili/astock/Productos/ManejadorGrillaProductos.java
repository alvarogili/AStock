
package agili.astock.Productos;

import BD.Productos.Producto;
import agili.astock.BD.AccesosBD;
import agili.astock.Herramientas.DatosDeConfiguracion;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que Administra los datos de la grilla de productos
 * @author Alvaro Gili
 */
public class ManejadorGrillaProductos {

    /**
     * Lista completa de productos
     */
    private static Vector<Object> productos = new Vector<Object>();
    private int countProductos = 0;
    private AccesosBD abd = AccesosBD.getInstance();

    /**
     * Constructor
     */
    public ManejadorGrillaProductos() {
    }

    /**
     * Actualiza la grilla de productos solo por cambios
     * @param productosjTable Tabla
     */
    public void updateProdGridByChanges(JTable productosjTable) {

        int temp = abd.getCount(BD.Productos.Producto.class);
        if (temp != countProductos) {
            countProductos = temp;
            updateProdGrid(productosjTable);
        }

    }

    /**
     * Actualiza la grilla de productos
     * @param productosjTable Tabla
     */
    public void updateProdGrid(JTable productosjTable) {

        DefaultTableModel productosTableModel = (DefaultTableModel) productosjTable.getModel();
        // limpio la tabla
        while (productosTableModel.getRowCount() > 0) {
            productosTableModel.removeRow(0);
        }

        //por si tiene algo, limpio el vector
        productos.clear();

        Object objects[] = abd.getObjetos(BD.Productos.Producto.class, null);

        for (int i = 0; i < objects.length; i++) {
            Producto producto = (Producto) objects[i];
            productos.add(producto);
            //agrego a la grilla
            addProducto(producto, productosTableModel);
        }

        if (productosjTable.getRowCount() > 0) {
            productosjTable.setRowSelectionInterval(0, 0);
        }
        /////////////////////////////////////////////////////////

    }

 
    /**
     * Agrega un producto a la grilla de productos
     * @param producto Objeto producto
     * @param productosTableModel TableModel
     */
    public void addProducto(Producto producto, DefaultTableModel productoTableModel) {

        DatosDeConfiguracion ddc = DatosDeConfiguracion.getInstance();
        double precioConIva = (ddc.getIva() * producto.appGetPrecio())/100;

        productoTableModel.addRow(new Object[]{producto.appGetCodigo(),
                    producto.appGetNombre(),
                    producto.appGetCategoria(),
                    producto.appGetPrecioCosto(),
                    producto.appGetPrecio(),
                    precioConIva,
                    producto.appGetStockDisponible(),
                    producto.appGetStockMinimo(),
                    producto.appGetProveedor()});
    }
}
