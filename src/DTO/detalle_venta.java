package DTO;



/**
 * Informacion de los productos que se van a vender
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:36 p. m.
 */
public class detalle_venta {

	private int id_detalle_venta;
        private String descrprod;
	private int cantidad;
	private int codigo_nota_venta;
	private int codigo_prod;
	private float precio_venta;
        private float subTotal;
        private int existencia;
        private int bandera;

    public int getId_detalle_venta() {
        return id_detalle_venta;
    }

    public void setId_detalle_venta(int id_detalle_venta) {
        this.id_detalle_venta = id_detalle_venta;
    }

    public String getDescrprod() {
        return descrprod;
    }

    public void setDescrprod(String descrprod) {
        this.descrprod = descrprod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigo_nota_venta() {
        return codigo_nota_venta;
    }

    public void setCodigo_nota_venta(int codigo_nota_venta) {
        this.codigo_nota_venta = codigo_nota_venta;
    }

    public int getCodigo_prod() {
        return codigo_prod;
    }

    public void setCodigo_prod(int codigo_prod) {
        this.codigo_prod = codigo_prod;
    }

    public float getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(float precio_venta) {
        this.precio_venta = precio_venta;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    
    
    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        
        
        


}//end detalle_venta