package DTO;



/**
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:41 p. m.
 */
public class pagos_apartado {

	private int id_pago_ap;
        private String folio;
	private String fecha;
	private float monto;
	private int id_apartado;
        private int bandera;
        private String tipo_pago;

    public int getId_pago_ap() {
        return id_pago_ap;
    }

    public void setId_pago_ap(int id_pago_ap) {
        this.id_pago_ap = id_pago_ap;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }    
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getId_apartado() {
        return id_apartado;
    }

    public void setId_apartado(int id_apartado) {
        this.id_apartado = id_apartado;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }
    
}//end pagos_apartado