package DTO;

public class traspaso {

	private int id_traspaso;
	private String fecha;
	private String tienda_traspaso;
	private String responsable_tienda_envia;
        private String responsable_tienda_recibe;
        private String responsable_transportar_prod;
        private int bandera;

    public int getId_traspaso() {
        return id_traspaso;
    }
    public void setId_traspaso(int id_traspaso) {
        this.id_traspaso = id_traspaso;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTienda_traspaso() {
        return tienda_traspaso;
    }
    public void setTienda_traspaso(String tienda_traspaso) {
        this.tienda_traspaso = tienda_traspaso;
    }

    public String getResponsable_tienda_envia() {
        return responsable_tienda_envia;
    }
    public void setResponsable_tienda_envia(String responsable_tienda_envia) {
        this.responsable_tienda_envia = responsable_tienda_envia;
    }
    
    public String getResponsable_tienda_recibe() {
        return responsable_tienda_recibe;
    }
    public void setResponsable_tienda_recibe(String responsable_tienda_recibe) {
        this.responsable_tienda_recibe = responsable_tienda_recibe;
    }
    
    public String getResponsable_transportar_prod() {
        return responsable_transportar_prod;
    }
    public void setResponsable_transportar_prod(String responsable_transportar_prod) {
        this.responsable_transportar_prod = responsable_transportar_prod;
    }

    public int getBandera() {
        return bandera;
    }
    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        
}//end ubicacion