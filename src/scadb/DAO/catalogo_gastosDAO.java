package scadb.DAO;

import DTO.catalogoGasto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class catalogo_gastosDAO {
    
    public static catalogo_gastosDAO instance = null;
    public static catalogo_gastosDAO getInstance(){
        if (instance == null) instance = new catalogo_gastosDAO();
        return instance;
    }
    private Connection conn;
    private catalogoGasto identidadCatGastos;
    
    public ObservableList<catalogoGasto> consultarCatGastos( List<String> where){
        
        ObservableList<catalogoGasto>  lstCatGastos= FXCollections.observableArrayList();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_catalogo_gastos, concepto FROM catalogo_gastos where flag != 3 and "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadCatGastos = new catalogoGasto();
               identidadCatGastos.setId_catalogo_gastos(rs.getInt("id_catalogo_gastos"));
               identidadCatGastos.setConcepto(rs.getString("concepto"));
               lstCatGastos.add(identidadCatGastos);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstCatGastos;
    }    
    
    public int insertarCatGastos(catalogoGasto cat){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO catalogo_gastos(concepto, flag) "
                + "VALUES (?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, cat.getConcepto());
             pstmt.setInt(2, 1);
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
    
    public int modificarCatGastos(catalogoGasto cat){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE catalogo_gastos SET concepto= ?, "
                + "flag = ? "
                + "WHERE id_catalogo_gastos = ?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, cat.getConcepto());
             pstmt.setInt(2, 2);
             pstmt.setInt(3, cat.getId_catalogo_gastos());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarCatGastos(int id){
        String sqlCat = "UPDATE catalogo_gastos SET flag = 3 WHERE id_catalogo_gastos = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sqlCat)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteCatGastos(int id){
        String sqlCat = "DELETE FROM catalogo_gastos WHERE id_catalogo_gastos = ?";
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
      catalogo_gastosDAO catDAO = new catalogo_gastosDAO();

      List<catalogoGasto> lstCatGastos = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_catalogo_gastos  = 1");
      lstCatGastos = catDAO.consultarCatGastos(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstCatGastos.size()));
      for (catalogoGasto i:lstCatGastos){
           System.out.println(i.getId_catalogo_gastos()+":"+i.getConcepto());
      }
}
    
}
