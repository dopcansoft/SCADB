/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.DAO;


import DTO.inventario;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
/**
 *
 * @author dopcan
 */
public class inventarioDAO {

    public static inventarioDAO instance = null;
    public static inventarioDAO getInstance(){
        if (instance == null) instance = new inventarioDAO();
        return instance;
    }
    private Connection conn;
    private inventario identidadInventario;
    
    public void seleccionarTodo(){
        String sql = "SELECT codigo_prod, existencia, id_ubicacion, precio_menudeo, precio_mayoreo, descripcion, unidad_medida, costo_compra, codigo_prov FROM inventario WHERE flag !=3 ";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("codigo_prod") +  "\t" + 
                                   rs.getString("existencia") + "\t" +
                                   rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public List<inventario> consultaInventario( List<String> where){
        //ObservableList obList = FXCollections.observableArrayList(
        List<inventario>  lstInventario= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT codigo_prod, existencia, id_ubicacion, precio_menudeo, precio_mayoreo, descripcion, unidad_medida, costo_compra, codigo_prov FROM inventario where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);
        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadInventario = new inventario();
               identidadInventario.setCodigo_prod(rs.getInt("codigo_prod"));
               identidadInventario.setExistencia(rs.getInt("existencia"));
               identidadInventario.setId_ubicacion(rs.getInt("id_ubicacion"));
               identidadInventario.setPrecio_menudeo(rs.getFloat("precio_menudeo"));
               identidadInventario.setPrecio_mayoreo(rs.getFloat("precio_mayoreo"));
               identidadInventario.setDescripcion(rs.getString("descripcion"));
               identidadInventario.setUnidad_medida(rs.getString("unidad_medida"));
               identidadInventario.setCosto_compra(rs.getFloat("costo_compra"));
               identidadInventario.setCodigo_prov(rs.getInt("codigo_prov"));
               lstInventario.add(identidadInventario);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstInventario;
    }    

    public ObservableList<inventario> consultarInventario( List<String> where){

        ObservableList<inventario> obList = FXCollections.observableArrayList();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }
        
        String sql = "SELECT codigo_prod, existencia, id_ubicacion, precio_menudeo, precio_mayoreo, descripcion, unidad_medida, costo_compra, codigo_prov, id_categoria FROM inventario where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);
        
        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadInventario = new inventario();
               identidadInventario.setCodigo_prod(rs.getInt("codigo_prod"));
               identidadInventario.setExistencia(rs.getInt("existencia"));
               identidadInventario.setId_ubicacion(rs.getInt("id_ubicacion"));
               identidadInventario.setPrecio_menudeo(rs.getFloat("precio_menudeo"));
               identidadInventario.setPrecio_mayoreo(rs.getFloat("precio_mayoreo"));
               identidadInventario.setDescripcion(rs.getString("descripcion"));
               identidadInventario.setUnidad_medida(rs.getString("unidad_medida"));
               identidadInventario.setCosto_compra(rs.getFloat("costo_compra"));
               identidadInventario.setCodigo_prov(rs.getInt("codigo_prov"));
               identidadInventario.setId_categoria(rs.getInt("id_categoria"));
               obList.add(identidadInventario);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        
        return obList;
    }    
    
    public int insertarProducto(int existenciaP, int id_ubicacionP, 
            float precio_menudeoP, float precio_mayoreoP, String descripcionP, 
            String unidad_medidaP, float costo_compraP, int codigo_provP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO inventario(existencia, id_ubicacion,"
                + " precio_menudeo, precio_mayoreo,"
                + " descripcion, "
                + "unidad_medida, "
                + " costo_compra, "
                + " codigo_prov, flag) "
                + "VALUES (?,?,?,?,?,?,?,?,?)");
        System.out.print(sqrString.toString());
        try{
             conn = conecta.conectaDB();
             PreparedStatement pstmt = conn.prepareStatement(sqrString.toString());
             pstmt.setInt(1, existenciaP);
             pstmt.setInt(2, id_ubicacionP);
             pstmt.setFloat(3, precio_menudeoP);
             pstmt.setFloat(4, precio_mayoreoP);
             pstmt.setString(5, descripcionP);
             pstmt.setString(6, unidad_medidaP);
             pstmt.setFloat(7, costo_compraP);
             pstmt.setInt(8, codigo_provP);
             pstmt.setInt(9, 1);

             regs = pstmt.executeUpdate();
             ResultSet rs = pstmt.getGeneratedKeys();
             if (regs== 1){
               regs = rs.getInt(1);
             }
             pstmt.close();
             conn.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
    }

    public void modificarProducto(int codigo_prodP, int existenciaP, int id_ubicacionP, 
    float precio_menudeoP, float precio_mayoreoP, String descripcionP, 
    String unidad_medidaP, float costo_compraP, int codigo_provP, int id_CategoriaP) {
        
        String sql = "UPDATE inventario SET "
                + "existencia = ?, "
                + "id_ubicacion = ?, "
                + "precio_menudeo = ?, "
                + "precio_mayoreo = ?, "
                + "descripcion = ?, "
                + "unidad_medida = ?, "
                + "costo_compra = ?, "
                + "codigo_prov = ?, "
                + "id_categoria = ?, "
                + "flag = ? "
                + "WHERE codigo_prod = ?";  
      Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, existenciaP);
            pstmt.setInt(2, id_ubicacionP);
            pstmt.setFloat(3, precio_menudeoP);
            pstmt.setFloat(4, precio_mayoreoP);
            pstmt.setString(5, descripcionP);
            pstmt.setString(6, unidad_medidaP);
            pstmt.setFloat(7, costo_compraP);
            pstmt.setInt(8, codigo_provP);
            pstmt.setInt(9, id_CategoriaP);
            pstmt.setInt(10, 2);
            pstmt.setInt(11, codigo_prodP);
            // update 
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modificarPrecioMenudeo(int codigo_prodP, float precio_menudeoP) {
        System.out.println("codigo Producto:"+codigo_prodP+" Precio Menudeo:"+ precio_menudeoP);
        String sql = "UPDATE inventario SET "
                + " precio_menudeo = ?, "
                + "flag = ? "
                + "WHERE codigo_prod = ?";  
      System.out.print(sql);
      Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            // set the corresponding param
            pstmt.setFloat(1, precio_menudeoP);
            pstmt.setInt(2, 2);
            pstmt.setInt(3, codigo_prodP);
            // update
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    
    public void modificarPrecioMayoreo(int codigo_prodP, float precio_mayoreoP) {
        System.out.println("codigo Producto:"+codigo_prodP+" Precio Mayoreo:"+ precio_mayoreoP);
        String sql = "UPDATE inventario SET "
                + "precio_mayoreo = ?, "
                + "flag = ? "
                + "WHERE codigo_prod = ?"; 
        System.out.print(sql);
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setFloat(1, precio_mayoreoP);
            pstmt.setInt(2, 2);
            pstmt.setInt(3, codigo_prodP);
            // update 
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    
    
    public void modificarExistenciaProducto(int codigo_prodP, int existenciaP) {
      System.out.println("codigo Producto:"+codigo_prodP+" Existencia:"+ existenciaP);
      String sql = "UPDATE inventario SET "
                + "existencia = ?, "
                + "flag = ? "
                + "WHERE codigo_prod = ?";  
      Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, existenciaP);
            pstmt.setInt(2, 2);
            pstmt.setInt(3, codigo_prodP);
            // update 
            pstmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void EliminarLogicamenteProducto(int id) {
        String sql = "UPDATE inventario SET flag = 3 WHERE codigo_prod = ?"; 
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            pstmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteInventario(int id) {
        String sql = "DELETE FROM inventario WHERE codigo_prod = ?"; 
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] Args){
          inventarioDAO invDAO = new inventarioDAO();
          //invDAO.seleccionarTodo();
          //Concepto ctp = new Concepto(2004,"Pago Anticipo","1.002-ISFSI"," Pago del 25%");
          //int regs=provDAO.insertarProveedor(31010500, "Israel Amador", "2294283925");
          //System.out.println("Respuesta: "+regs);
          List<inventario> lstInv = new ArrayList<>();
          List<String> lstWhere = new ArrayList<>();
          lstWhere.add("codigo_prov  = 78");
          lstInv = invDAO.consultaInventario(lstWhere);
          System.out.print("Resgistro: "+String.valueOf(lstInv.size()));
          for (inventario i:lstInv){
               System.out.println(i.getCodigo_prod()+":"+i.getDescripcion()+":"+i.getExistencia()+":"+i.getCodigo_prov());
          }
    }   
  

}
