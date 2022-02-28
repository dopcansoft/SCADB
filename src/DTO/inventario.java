package DTO;

/**
 * Almacena informacion de los productos en el inventario de la sucursal.
 * @author Marcos
 * @version 1.0
 * @created 28-ene.-2020 08:54:24 p. m.
 */
public class inventario {

	private int codigo_prod;
	private int existencia;
	private int id_ubicacion;
	private float precio_menudeo;
	private float precio_mayoreo;
	private String descripcion;
	private String unidad_medida;
	private float costo_compra;
	private int codigo_prov;
        private int id_categoria;
        private int bandera;

    public int getCodigo_prod() {
        return codigo_prod;
    }

    public void setCodigo_prod(int codigo_prod) {
        this.codigo_prod = codigo_prod;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public float getPrecio_menudeo() {
        return precio_menudeo;
    }

    public void setPrecio_menudeo(float precio_menudeo) {
        this.precio_menudeo = precio_menudeo;
    }

    public float getPrecio_mayoreo() {
        return precio_mayoreo;
    }

    public void setPrecio_mayoreo(float precio_mayoreo) {
        this.precio_mayoreo = precio_mayoreo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public float getCosto_compra() {
        return costo_compra;
    }

    public void setCosto_compra(float costo_compra) {
        this.costo_compra = costo_compra;
    }

    public int getCodigo_prov() {
        return codigo_prov;
    }

    public void setCodigo_prov(int codigo_prov) {
        this.codigo_prov = codigo_prov;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }

    

}//end inventario