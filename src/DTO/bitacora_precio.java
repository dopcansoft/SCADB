package DTO;



/**
 * Almacena la informacion de los precios de los productos
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:25 p. m.
 */
public class bitacora_precio {

	private int id_bitacora;
	private String fecha;
	private float precio_menudeo;
	private float precio_mayoreo;
	private int id_usuario;
        private String nombreUsuario;
        private float precio_menudeo_ant;
	private float precio_mayoreo_ant;
        private int codigo_prod;
        private int bandera;

    public int getId_bitacora() {
        return id_bitacora;
    }

    public void setId_bitacora(int id_bitacora) {
        this.id_bitacora = id_bitacora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    
    
    public float getPrecio_menudeo_ant() {
        return precio_menudeo_ant;
    }

    public void setPrecio_menudeo_ant(float precio_menudeo_ant) {
        this.precio_menudeo_ant = precio_menudeo_ant;
    }

    public float getPrecio_mayoreo_ant() {
        return precio_mayoreo_ant;
    }

    public void setPrecio_mayoreo_ant(float precio_mayoreo_ant) {
        this.precio_mayoreo_ant = precio_mayoreo_ant;
    }

    public int getCodigo_prod() {
        return codigo_prod;
    }

    public void setCodigo_prod(int codigo_prod) {
        this.codigo_prod = codigo_prod;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }



}//end bitacora_precio