package DTO;



/**
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:40 p. m.
 */
public class notas_remision {

	private int id_nota_rem;
	private String folio;
	private String fecha;
	private String tipo_operacion;
	private float monto;
        private int bandera;

    public int getId_nota_rem() {
        return id_nota_rem;
    }

    public void setId_nota_rem(int id_nota_rem) {
        this.id_nota_rem = id_nota_rem;
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

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
}//end notas_remision