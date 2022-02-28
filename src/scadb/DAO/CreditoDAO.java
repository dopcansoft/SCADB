package scadb.DAO;

import DTO.Credito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CreditoDAO {
    //id_credito, fecha, codigo_cliente, codigo_nota_venta, monto, flag
    public static CreditoDAO instance = null;
    public static CreditoDAO getInstance(){
        if (instance == null) instance = new CreditoDAO();
        return instance;
    }
    private Connection conn;
    private Credito identidadCredito;
    
    public List<Credito> consultaCredito( List<String> where){
        
        List<Credito>  lstCredito= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_credito, fecha, codigo_cliente, codigo_nota_venta, monto FROM Credito where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadCredito = new Credito();
               identidadCredito.setId_credito(rs.getInt("id_credito"));
               identidadCredito.setFecha(rs.getString("fecha"));
               identidadCredito.setCodigo_cliente(rs.getInt("codigo_cliente"));
               identidadCredito.setCodigo_nota_venta(rs.getInt("codigo_nota_venta"));
               identidadCredito.setMonto(rs.getFloat("monto"));
               lstCredito.add(identidadCredito);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstCredito;
    }    
    
    public int insertarCredito(Credito cred){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db"); 
        
        
        
        sqrString.append("INSERT INTO Credito(fecha, codigo_cliente, codigo_nota_venta, monto, flag) "
                + "VALUES (?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, cred.getFecha());
             pstmt.setInt(2, cred.getCodigo_cliente());
             pstmt.setInt(3, cred.getCodigo_nota_venta());
             pstmt.setFloat(4, cred.getMonto());
             pstmt.setInt(5, 1 );
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
    
    public int modificarCredito(Credito cred){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db"); 
       //id_credito, fecha, codigo_cliente, codigo_nota_venta, monto, flag
        sqrString.append("UPDATE Credito SET fecha = ?, "
                + "codigo_cliente = ?, "
                + "codigo_nota_venta = ?, "
                + "monto = ?, "
                + "flag = ? "
                + "WHERE id_credito = ?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, cred.getFecha());
             pstmt.setInt(2, cred.getCodigo_cliente());
             pstmt.setInt(3, cred.getCodigo_nota_venta());
             pstmt.setFloat(4, cred.getMonto());
             pstmt.setInt(5, 2 );
             pstmt.setInt(4, cred.getId_credito());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarCredito(int id){
        String sqlCredito = "UPDATE Credito SET flag=3 WHERE id_credito = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sqlCredito)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteCredito(int id){
        String sqlCredito = "DELETE FROM Credito WHERE id_credito = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sqlCredito)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] Args){
      CreditoDAO credDAO = new CreditoDAO();

      List<Credito> lstCredito = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_credito  = 1");
      lstCredito = credDAO.consultaCredito(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstCredito.size()));
      for (Credito i:lstCredito){
           System.out.println(i.getId_credito()+":"+i.getFecha()+":"+i.getCodigo_cliente());
      }
}
    
}
