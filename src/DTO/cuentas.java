package DTO;



/**
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:30 p. m.
 */
public class cuentas {

	private String id_cuenta;
	private String nombre_cuenta;
	private float monto_cuenta;
        private int bandera;

    public String getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(String id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getNombre_cuenta() {
        return nombre_cuenta;
    }

    public void setNombre_cuenta(String nombre_cuenta) {
        this.nombre_cuenta = nombre_cuenta;
    }

    public float getMonto_cuenta() {
        return monto_cuenta;
    }

    public void setMonto_cuenta(float monto_cuenta) {
        this.monto_cuenta = monto_cuenta;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        
}//end cuentas