package scadb.DAO;

import DTO.pagos_credito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class pagos_creditoDAO {
    
    public static pagos_creditoDAO instance = null;
    public static pagos_creditoDAO getInstance(){
        if (instance == null) instance = new pagos_creditoDAO();
        return instance;
    }
    private Connection conn;
    private pagos_credito identidadPagoCred;
    
    public List<pagos_credito> consultaPagoCred( List<String> where){
        
        List<pagos_credito>  lstPago= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_pago_cre, folio, fecha, monto, id_credito, tipo_pago FROM pagos_credito where flag != 3 and "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadPagoCred = new pagos_credito();
               identidadPagoCred.setId_pago_cre(rs.getInt("id_pago_cre"));
               identidadPagoCred.setFolio(rs.getString("folio"));
               identidadPagoCred.setFecha(rs.getString("fecha"));
               identidadPagoCred.setMonto(rs.getFloat("monto"));
               identidadPagoCred.setId_credito(rs.getInt("id_credito"));
               identidadPagoCred.setTipo_pago(rs.getString("tipo_pago"));
               lstPago.add(identidadPagoCred);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstPago;
    }    
    
    public int insertarPagoCred(pagos_credito pago){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO pagos_credito(folio, fecha, monto, id_credito, tipo_pago, flag ) "
                + "VALUES (?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, pago.getFolio());
             pstmt.setString(2, pago.getFecha());
             pstmt.setFloat(3, pago.getMonto());
             pstmt.setInt(4, pago.getId_credito());
             pstmt.setString(5, pago.getTipo_pago());
             pstmt.setInt(6, 1);
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
    
    public int modificarPagoCred(pagos_credito pago){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE pagos_credito(folio, fecha, monto, id_credito, tipo_pago, flag) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) WHERE id_pago_cre=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, pago.getFolio());
             pstmt.setString(2, pago.getFecha());
             pstmt.setFloat(3, pago.getMonto());
             pstmt.setInt(4, pago.getId_credito());
             pstmt.setString(5, pago.getTipo_pago());
             pstmt.setInt(6, 2);
             pstmt.setInt(7, pago.getId_pago_cre());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarPagoCred(int id){
        String sql = "UPDATE pagos_credito SET flag=3 WHERE id_pago_cre = ?"; 
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        //se elimina la nota
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deletePagoCred(int id){
        String sql = "DELETE FROM pagos_credito WHERE id_pago_cre = ?"; 
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
      String sql= "select max(id_pago_cre) as MaxId from pagos_credito";
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
      pagos_creditoDAO pagoDAO = new pagos_creditoDAO();
      int maximoId = pagoDAO.obtenerMaximoId();
        System.out.println("Id Maximo: "+ maximoId);
//      List<pagos_credito> lstPagos = new ArrayList<>();
//      List<String> lstWhere = new ArrayList<>();
//      lstWhere.add("id_pago_ap  = 1");
//      lstPagos = pagoDAO.consultaPagoCred(lstWhere);
//      System.out.print("Registro: "+String.valueOf(lstPagos.size()));
//      for (pagos_credito i:lstPagos){
//           System.out.println(i.getId_pago_cre()+":"+i.getFecha()+":"+i.getMonto()+":"+i.getId_credito());
//      }
}
    
}
