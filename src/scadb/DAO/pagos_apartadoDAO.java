package scadb.DAO;

import DTO.pagos_apartado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class pagos_apartadoDAO {
    
    public static pagos_apartadoDAO instance = null;
    public static pagos_apartadoDAO getInstance(){
        if (instance == null) instance = new pagos_apartadoDAO();
        return instance;
    }
    private Connection conn;
    private pagos_apartado identidadPagoAP;
    
    public List<pagos_apartado> consultaPagosAP( List<String> where){

        List<pagos_apartado>  lstPagoAP= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_pago_ap, folio, fecha, monto, id_apartado FROM pagos_apartado where flag !=3 and "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadPagoAP = new pagos_apartado();
               identidadPagoAP.setId_pago_ap(rs.getInt("id_pago_ap"));
               identidadPagoAP.setFolio(rs.getString("folio"));
               identidadPagoAP.setFecha(rs.getString("fecha"));
               identidadPagoAP.setMonto(rs.getFloat("monto"));
               identidadPagoAP.setId_apartado(rs.getInt("id_apartado"));
               lstPagoAP.add(identidadPagoAP);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstPagoAP;
    }    
    
    public int insertarPagosAP(pagos_apartado pago){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO pagos_apartado(folio, fecha, monto, id_apartado, flag) "
                + "VALUES (?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, pago.getFolio());
             pstmt.setString(2, pago.getFecha());
             pstmt.setFloat(3, pago.getMonto());
             pstmt.setInt(4, pago.getId_apartado());
             pstmt.setInt(5, 1);
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
    
    public int modificarPagosAP(pagos_apartado pago){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE pagos_apartado(folio, fecha, monto, id_apartado, flag) "
                + "VALUES (?, ?, ?, ?, ?) WHERE id_pago_ap=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, pago.getFolio());
             pstmt.setString(2, pago.getFecha());
             pstmt.setFloat(3, pago.getMonto());
             pstmt.setInt(4, pago.getId_apartado());
             pstmt.setInt(5, 2);
             pstmt.setInt(6, pago.getId_pago_ap());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarPagosAP(int id){
        String sql = "UPDATE pagos_apartado SET flag = 3 WHERE id_pago_ap = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deletePagosAP(int id){
        String sql = "DELETE FROM pagos_apartado WHERE id_pago_ap = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] Args){
      pagos_apartadoDAO pagosDAO = new pagos_apartadoDAO();

      List<pagos_apartado> lstPagosAP = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_pago_ap  = 1");
      lstPagosAP = pagosDAO.consultaPagosAP(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstPagosAP.size()));
      for (pagos_apartado i:lstPagosAP){
           System.out.println(i.getId_pago_ap()+":"+i.getFecha()+":"+i.getMonto()+":"+i.getId_apartado());
      }
    }
    
}
