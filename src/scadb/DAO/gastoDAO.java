/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.DAO;

import DTO.gasto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author i7
 */
public class gastoDAO {
        
    public static gastoDAO instance = null;
    public static gastoDAO getInstance(){
        if (instance == null) instance = new gastoDAO();
        return instance;
    }
    private Connection conn;
    private gasto identidadGasto;
    
    public ObservableList<gasto> consultarGasto( List<String> where){
        //ObservableList obList = FXCollections.observableArrayList(
        ObservableList<gasto>  lstGasto= FXCollections.observableArrayList();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }
     	//id_gasto, concepto, fecha, monto, flag
        
        String sql = "SELECT id_gasto, concepto, fecha, monto, flag FROM gastos where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadGasto = new gasto();
               identidadGasto.setId_gasto(rs.getInt("id_gasto"));
               identidadGasto.setConcepto(rs.getString("concepto"));
               identidadGasto.setFecha(rs.getString("fecha"));
               identidadGasto.setMonto(rs.getFloat("monto"));
               identidadGasto.setFlag(rs.getInt("flag"));

               lstGasto.add(identidadGasto);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstGasto;
    }
    
    public List<gasto> consultaCategoria( List<String> where){
        //ObservableList obList = FXCollections.observableArrayList(
        List<gasto>  lstGasto= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_gasto, concepto, fecha, monto, flag FROM gasto where "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadGasto = new gasto();
               identidadGasto.setId_gasto(rs.getInt("id_gasto"));
               identidadGasto.setConcepto(rs.getString("concepto"));
               identidadGasto.setFecha(rs.getString("fecha"));
               identidadGasto.setMonto(rs.getFloat("monto"));
               identidadGasto.setFlag(rs.getInt("flag"));

               lstGasto.add(identidadGasto);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstGasto;
    }    
    
    public int insertarGasto(gasto gas){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO gastos(concepto, fecha, monto, flag) "
                + "VALUES (?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try(Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sqrString.toString()) ){
             pstmt.setString(1, gas.getConcepto());
             pstmt.setString(2, gas.getFecha());
             pstmt.setFloat(3, gas.getMonto());
             pstmt.setInt(4, gas.getFlag());
             regs = pstmt.executeUpdate();
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
  }
    
    public int modificarGasto(gasto gas){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE gastos SET concepto= ?, fecha= ?, monto= ?, flag= ? "
                + " WHERE id_gasto = ? ");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, gas.getConcepto());
             pstmt.setString(2, gas.getFecha());
             pstmt.setFloat(3, gas.getMonto());
             pstmt.setInt(4, gas.getFlag());
             pstmt.setInt(5, gas.getId_gasto());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarGasto(int id){
        String sqlCat = "DELETE FROM categoria WHERE id_categoria = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        //se elimina la categoria
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sqlCat)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public static void main(String[] Args){
      gastoDAO gasDAO = new gastoDAO();

      List<gasto> lstGasto = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_categoria  = 1");
      lstGasto = gasDAO.consultarGasto(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstGasto.size()));
      for (gasto i:lstGasto){
           System.out.println(i.getId_gasto()+":"+i.getConcepto()+":"+i.getMonto());
      }
}
    
}
