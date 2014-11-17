
package BD.Productos;

import BD.Proveedores.Proveedor;

/**
 * Clase que representa un producto
 * @author Alvaro Gili
 */
public class Producto {

    private long codigo;
    private String nombre;
    private Categoria categoria;
    private double precioCosto;
    private double precio;//precio sin IVA, se agrega el IVA cuando se muestra
    private long stockDisponible;
    private long stockMinimo;
    private Proveedor proveedor;

    public Producto() {
    }

    public long appGetCodigo() {
        return codigo;
    }

    public void appSetCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Categoria appGetCategoria() {
        return categoria;
    }

    public void appSetCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String appGetNombre() {
        return nombre;
    }

    public void appSetNombre(String nombre) {
        this.nombre = nombre;
    }

    public double appGetPrecio() {
        return precio;
    }

    public void appSetPrecio(double precio) {
        this.precio = precio;
    }

    public Proveedor appGetProveedor() {
        return proveedor;
    }

    public void appSetProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public long appGetStockDisponible() {
        return stockDisponible;
    }

    public void appSetStockDisponible(long stock) {
        this.stockDisponible = stock;
    }

    public long appGetStockMinimo() {
        return stockMinimo;
    }

    public void appSetStockMinimo(long stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public double appGetPrecioCosto() {
        return precioCosto;
    }

    public void appSetPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }



}
