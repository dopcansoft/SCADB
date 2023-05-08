package scadb.DAO;

import DTO.notas_remision;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class notas_remisionDAO {
    
    public static notas_remisionDAO instance = null;
    public static notas_remisionDAO getInstance(){
        if (instance == null) instance = new notas_remisionDAO();
        return instance;
    }
    private Connection conn;
    private notas_remision identidadNotas;
    
    public ObservableList<notas_remision> consultarNotasRem( List<String> where){

        ObservableList<notas_remision> lstNotas = FXCollections.observableArrayList();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_nota_rem, folio, fecha, tipo_operacion, monto, descuento FROM notas_remision where flag !=3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadNotas = new notas_remision();
               identidadNotas.setId_nota_rem(rs.getInt("id_nota_rem"));
               identidadNotas.setFolio(rs.getString("folio"));
               identidadNotas.setFecha(rs.getString("fecha"));
               identidadNotas.setTipo_operacion(rs.getString("tipo_operacion"));
               identidadNotas.setMonto(rs.getFloat("monto"));
               identidadNotas.setDescuento(rs.getFloat("descuento"));
               lstNotas.add(identidadNotas);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstNotas;
    }    
    
    public int insertarNotasRem(notas_remision notas){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO notas_remision( folio, fecha, tipo_operacion, monto, flag, descuento) "
                + "VALUES (?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, notas.getFolio());
             pstmt.setString(2, notas.getFecha());
             pstmt.setString(3, notas.getTipo_operacion());
             pstmt.setFloat(4, notas.getMonto());
             pstmt.setInt(5, 1);
             pstmt.setFloat(6, notas.getDescuento());
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
    
    public int modificarNotasRem(notas_remision notas){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE notas_remision set folio= ?, "
                + "fecha = ?, "
                + "tipo_operacion = ?, "
                + "monto = ?, "
                + "flag = ?, "
                + "descuento = ? "
                + "WHERE id_nota_rem=? ");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, notas.getFolio());
             pstmt.setString(2, notas.getFecha());
             pstmt.setString(3, notas.getTipo_operacion());
             pstmt.setFloat(4, notas.getMonto());
             pstmt.setInt(5, 2);
             pstmt.setInt(6, notas.getId_nota_rem());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarNotasRem(int id){
        String sql = "UPDATE notas_remision SET flag = 3 WHERE id_nota_rem = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteNotasRem(int id){
        String sql = "DELETE FROM notas_remision WHERE id_nota_rem = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int obtenerMaximoId(){
      String sql= "select max(id_nota_rem) as MaxId from notas_remision";
      Conexion conecta = new Conexion("DBPLAMAR.db");
      System.out.println(sql);
      int idMax = 0;
        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               idMax= rs.getInt("MaxId");
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return idMax;
    }
    
    public static void main(String[] Args){
      notas_remisionDAO notasDAO = new notas_remisionDAO();
            int maximoId = notasDAO.obtenerMaximoId();
        System.out.println("Id Maximo: "+ maximoId);
      

//      List<notas_remision> lstNotas = new ArrayList<>();
//      List<String> lstWhere = new ArrayList<>();
//      lstWhere.add("id_nota_rem  = 1");
//      lstNotas = notasDAO.consultarNotasRem(lstWhere);
//      System.out.print("Registro: "+String.valueOf(lstNotas.size()));
//      for (notas_remision i:lstNotas){
//           System.out.println(i.getId_nota_rem()+":"+i.getFolio()+":"+i.getFecha()+":"+i.getTipo_operacion()+":"+i.getMonto());
//      }
    }
    
}
