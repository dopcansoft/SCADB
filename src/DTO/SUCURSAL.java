package DTO;



/**
 * Contiene informaci�n de la sucursal en la que se esta operando
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:43 p. m.
 */
public class SUCURSAL {

	private int id_suc;
	private String nombre;
	private String direccion;
        private int bandera;

    public int getId_suc() {
        return id_suc;
    }

    public void setId_suc(int id_suc) {
        this.id_suc = id_suc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        
}//end SUCURSAL