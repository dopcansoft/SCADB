
package DTO;

public class Credito {
    
    private int id_credito;
    private String fecha;
    private int codigo_cliente;
    private int codigo_nota_venta;
    private float monto;
    private int bandera;
    //id_credito, fecha, codigo_cliente, codigo_nota_venta, monto, flag

    public int getId_credito() {
        return id_credito;
    }

    public void setId_credito(int id_credito) {
        this.id_credito = id_credito;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public int getCodigo_nota_venta() {
        return codigo_nota_venta;
    }

    public void setCodigo_nota_venta(int codigo_nota_venta) {
        this.codigo_nota_venta = codigo_nota_venta;
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
    
   
    
       
}
