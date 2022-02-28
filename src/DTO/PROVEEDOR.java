package DTO;
/**
 * Almacena informacion de los proveedores
 * @author Marcos
 * @version 1.0
 * @created 28-ene.-2020 08:54:25 p. m.
 */
public class PROVEEDOR {

	private int codigo_prov;
	private String nombre;
	private String telefono;
        private int bandera;

        public int getCodigo_prov(){
		return codigo_prov;
	}

	public void setCodigo_prov(int newVal){
		codigo_prov = newVal;
	}

	public String getNombre(){
		return nombre;
	}

	public void setNombre(String newVal){
		nombre = newVal;
	}

	public String getTelefono(){
		return telefono;
	}

	public void setTelefono(String newVal){
		telefono = newVal;
	}

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }

        
}//end PROVEEDOR