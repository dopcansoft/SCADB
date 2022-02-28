package DTO;



/**
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:32 p. m.
 */
public class detalle_apartado {

	private int id_detalle_ap;
	private int id_apartado;
	private int id_inventario;
	private float precio;
	private int cantidad;
        private int bandera;

    public int getId_detalle_ap() {
        return id_detalle_ap;
    }

    public void setId_detalle_ap(int id_detalle_ap) {
        this.id_detalle_ap = id_detalle_ap;
    }

    public int getId_apartado() {
        return id_apartado;
    }

    public void setId_apartado(int id_apartado) {
        this.id_apartado = id_apartado;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        
}//end detalle_apartado