
package DTO;

public class pagos_credito {
    
    private int id_pago_cre;
    private String folio;
    private String fecha;
    private float monto;
    private int id_credito;
    private int bandera;
    private String tipo_pago;

    public int getId_pago_cre() {
        return id_pago_cre;
    }

    public void setId_pago_cre(int id_pago_cred) {
        this.id_pago_cre = id_pago_cred;
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

    public int getId_credito() {
        return id_credito;
    }

    public void setId_credito(int id_apartado) {
        this.id_credito = id_apartado;
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
    
}
