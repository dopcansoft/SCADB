package DTO;

public class detalle_traspaso {

	private int id_detalle_traspaso;
	private int cantidad;
	private int codigo_prod;
        private String descrprod;
	private int id_traspaso;
        private int bandera;

    public int getId_detalle_traspaso() {
        return id_detalle_traspaso;
    }

    public void setId_detalle_traspaso(int id_detalle_traspaso) {
        this.id_detalle_traspaso = id_detalle_traspaso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigo_prod() {
        return codigo_prod;
    }

    public void setCodigo_prod(int codigo_prod) {
        this.codigo_prod = codigo_prod;
    }

    public String getDescrprod() {
        return descrprod;
    }

    public void setDescrprod(String descrprod) {
        this.descrprod = descrprod;
    }

    
    
    public int getId_traspaso() {
        return id_traspaso;
    }

    public void setId_traspaso(int id_traspaso) {
        this.id_traspaso = id_traspaso;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }

}