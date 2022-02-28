package scadb.DAO;

import DTO.cuentas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class cuentasDAO {
    
    public static cuentasDAO instance = null;
    public static cuentasDAO getInstance(){
        if (instance == null) instance = new cuentasDAO();
        return instance;
    }
    private Connection conn;
    private cuentas identidadCuentas;
    
    public List<cuentas> consultaCuentas( List<String> where){
        
        List<cuentas>  lstCuentas= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_cuenta, nombre_cuenta, monto_cuenta FROM cuentas where flag != 3 "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadCuentas = new cuentas();
               identidadCuentas.setId_cuenta(rs.getString("id_cuenta"));
               identidadCuentas.setNombre_cuenta(rs.getString("nombre_cuenta"));
               identidadCuentas.setMonto_cuenta(rs.getFloat("monto_cuenta"));

               lstCuentas.add(identidadCuentas);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstCuentas;
    }    
    
    public int insertarCuenta(cuentas cta){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO cuentas(id_cuenta, nombre_cuenta, monto_cuenta, flag) "
                + "VALUES (?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, cta.getId_cuenta());
             pstmt.setString(2, cta.getNombre_cuenta());
             pstmt.setFloat(3, cta.getMonto_cuenta());
             pstmt.setInt(4, 1);
             regs = pstmt.executeUpdate(pstmt.toString(), Statement.RETURN_GENERATED_KEYS);
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
    
    public int modificarCuentas(cuentas cta){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE categoria(nombre_cuenta, monto_cuenta, flag) "
                + "VALUES (?, ?, ?) WHERE id_cuenta=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, cta.getNombre_cuenta());
             pstmt.setFloat(2, cta.getMonto_cuenta());
             pstmt.setInt(3, 2);
             pstmt.setString(4, cta.getId_cuenta());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarCuentas(int id){
        String sql = "UPDATE cuentas SET flag = 3 WHERE id_cuenta = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteCuentas(int id){
        String sql = "DELETE FROM cuentas WHERE id_cuenta = ?";
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
      cuentasDAO ctaDAO = new cuentasDAO();

      List<cuentas> lstCuentas = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_cuenta  = 1");
      lstCuentas = ctaDAO.consultaCuentas(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstCuentas.size()));
      for (cuentas i:lstCuentas){
           System.out.println(i.getId_cuenta()+":"+i.getNombre_cuenta()+":"+i.getMonto_cuenta());
      }
    }
    
}
