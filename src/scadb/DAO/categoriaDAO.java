package scadb.DAO;

import DTO.categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class categoriaDAO {
    
    public static categoriaDAO instance = null;
    public static categoriaDAO getInstance(){
        if (instance == null) instance = new categoriaDAO();
        return instance;
    }
    private Connection conn;
    private categoria identidadCategoria;
    
    public ObservableList<categoria> consultarCategoria( List<String> where){
        
        ObservableList<categoria>  lstCategoria=  FXCollections.observableArrayList();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_categoria, categoria, parent_id FROM categoria where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadCategoria = new categoria();
               identidadCategoria.setId_categoria(rs.getInt("id_categoria"));
               identidadCategoria.setCategoria(rs.getString("categoria"));
               identidadCategoria.setParent_id(rs.getInt("parent_id"));

               lstCategoria.add(identidadCategoria);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstCategoria;
    }    

    public String consultarCategoria( int Id){
        
	
        String sql = "SELECT id_categoria, categoria, parent_id FROM categoria where id_categoria = "+Id;
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadCategoria = new categoria();
               identidadCategoria.setId_categoria(rs.getInt("id_categoria"));
               identidadCategoria.setCategoria(rs.getString("categoria"));
               identidadCategoria.setParent_id(rs.getInt("parent_id"));
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return identidadCategoria.getCategoria();
    }

    public int consultaIdCategoria(String strCategoria){
        
	
        String sql = "SELECT id_categoria, categoria, parent_id FROM categoria where categoria = '"+strCategoria+"'";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadCategoria = new categoria();
               identidadCategoria.setId_categoria(rs.getInt("id_categoria"));
               identidadCategoria.setCategoria(rs.getString("categoria"));
               identidadCategoria.setParent_id(rs.getInt("parent_id"));
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return identidadCategoria.getId_categoria();
    }
    
    public int insertarCategoria(categoria cat){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO categoria(categoria, parent_id, flag) "
                + "VALUES (?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, cat.getCategoria());
             pstmt.setInt(2, cat.getParent_id());
             pstmt.setInt(3, 1);
             regs = pstmt.executeUpdate();
             ResultSet rs = pstmt.getGeneratedKeys();
             if (regs== 1){
               regs = rs.getInt(1);
             }

             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
  }
    
    public int modificarCategoria(categoria cat){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE categoria SET "
                + "categoria = ?, "
                + "parent_id = ?, "
                + "flag = ? "
                + "WHERE id_categoria = ?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, cat.getCategoria());
             pstmt.setInt(2, cat.getParent_id());
             pstmt.setInt(3, 2);
             pstmt.setInt(4, cat.getId_categoria());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarCategoria(int id){
        String sqlCat = "UPDATE categoria SET flag = 3 WHERE id_categoria = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sqlCat)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteCategoria(int id){
        String sqlCat = "DELETE FROM categoria WHERE id_categoria = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sqlCat)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] Args){
      categoriaDAO catDAO = new categoriaDAO();

      List<categoria> lstCategoria = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_categoria  = 1");
      lstCategoria = catDAO.consultarCategoria(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstCategoria.size()));
      for (categoria i:lstCategoria){
           System.out.println(i.getId_categoria()+":"+i.getCategoria()+":"+i.getParent_id());
      }
}
    
}
