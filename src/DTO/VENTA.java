package DTO;

/**
 * Almacena informacion sobre las ventas de la sucursal
 * @author Marcos
 * @version 1.0
 * @created 28-ene.-2020 08:54:25 p. m.
 */
public class VENTA {

	private int codigo_nota_venta;
	private String fecha;
	private String tipo_venta;
	private int codigo_cliente;
	private String codigo_factura;
	private int id_nota_rem;
        private int flag;

	public int getCodigo_nota_venta(){
		return codigo_nota_venta;
	}

	public void setCodigo_nota_venta(int newVal){
		codigo_nota_venta = newVal;
	}

	public String getFecha(){
		return fecha;
	}

	public void setFecha(String newVal){
		fecha = newVal;
	}

	public String getTipo_venta(){
		return tipo_venta;
	}


	public void setTipo_venta(String newVal){
		tipo_venta = newVal;
	}

	public int getCodigo_cliente(){
		return codigo_cliente;
	}

	public void setCodigo_cliente(int newVal){
		codigo_cliente = newVal;
	}

	public String getCodigo_factura(){
		return codigo_factura;
	}

	public void setCodigo_factura(String newVal){
		codigo_factura = newVal;
	}

	public int getId_nota_rem(){
		return id_nota_rem;
	}

	public void setId_nota_rem(int newVal){
		id_nota_rem = newVal;
	}

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

        
}//end VENTA