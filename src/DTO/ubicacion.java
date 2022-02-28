package DTO;



/**
 * Almacena las ubicaciones fisicas de los productos en bodegas
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:44 p. m.
 */
public class ubicacion {

	private int id_ubicacion;
	private String bodega;
	private String anaquel;
	private String estante;
        private int bandera;

    public int getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getAnaquel() {
        return anaquel;
    }

    public void setAnaquel(String anaquel) {
        this.anaquel = anaquel;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        
}//end ubicacion