package DTO;


/**
 * @author Marcos
 * @version 1.0
 * @created 28-ene.-2020 08:54:23 p. m.
 */
public class apartado {

	private int id_apartado;
	private String fecha;
	private int codigo_cliente;
        private int codigo_nota_venta;
        private float monto;
        private int bandera;

    public int getCodigo_nota_venta() {
        return codigo_nota_venta;
    }

    public void setCodigo_nota_venta(int codigo_nota_venta) {
        this.codigo_nota_venta = codigo_nota_venta;
    }

    public int getId_apartado() {
        return id_apartado;
    }

    public void setId_apartado(int id_apartado) {
        this.id_apartado = id_apartado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
    
    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        

}//end apartado