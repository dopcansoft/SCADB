package DTO;

/**
 * Almacena informaci�n de los clientes
 * @author Marcos
 * @version 1.0
 * @created 28-ene.-2020 08:54:24 p. m.
 */
public class CLIENTE {

	private int codigo_cliente;
	private String nombre;
	private String razon_social;
	private String domicilio_fiscal;
	private String telefono;
	private String rfc;
	private String email;
        private int bandera;

	public int getCodigo_cliente(){
		return codigo_cliente;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setCodigo_cliente(int newVal){
		codigo_cliente = newVal;
	}

	public String getNombre(){
		return nombre;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setNombre(String newVal){
		nombre = newVal;
	}

	public String getRazon_social(){
		return razon_social;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setRazon_social(String newVal){
		razon_social = newVal;
	}

	public String getDomicilio_fiscal(){
		return domicilio_fiscal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDomicilio_fiscal(String newVal){
		domicilio_fiscal = newVal;
	}

	public String getTelefono(){
		return telefono;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setTelefono(String newVal){
		telefono = newVal;
	}

	public String getRfc(){
		return rfc;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setRfc(String newVal){
		rfc = newVal;
	}

	public String getEmail(){
		return email;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setEmail(String newVal){
		email = newVal;
	}

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }


}