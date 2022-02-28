package DTO;



/**
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:39 p. m.
 */
public class movimientos_cuenta {

	private String id_movimiento;
	private String descripcion_mov;
	private float monto;
	private String id_cuenta_origen;
	private String id_cuenta_destino;
	private String id_cuenta;
        private int bandera;

    public String getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(String id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public String getDescripcion_mov() {
        return descripcion_mov;
    }

    public void setDescripcion_mov(String descripcion_mov) {
        this.descripcion_mov = descripcion_mov;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getId_cuenta_origen() {
        return id_cuenta_origen;
    }

    public void setId_cuenta_origen(String id_cuenta_origen) {
        this.id_cuenta_origen = id_cuenta_origen;
    }

    public String getId_cuenta_destino() {
        return id_cuenta_destino;
    }

    public void setId_cuenta_destino(String id_cuenta_destino) {
        this.id_cuenta_destino = id_cuenta_destino;
    }

    public String getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(String id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        

}//end movimientos_cuenta