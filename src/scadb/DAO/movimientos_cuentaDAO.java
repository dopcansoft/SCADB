package scadb.DAO;

import DTO.movimientos_cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class movimientos_cuentaDAO {
    
    public static movimientos_cuentaDAO instance = null;
    public static movimientos_cuentaDAO getInstance(){
        if (instance == null) instance = new movimientos_cuentaDAO();
        return instance;
    }
    private Connection conn;
    private movimientos_cuenta identidadMov;
    
    public List<movimientos_cuenta> consultaMov( List<String> where){

        List<movimientos_cuenta>  lstMov= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_movimiento, descripcion_mov, monto, id_cuenta_origen, id_cuenta_destino, id_cuenta FROM movimientos_cuenta where flag != 3 "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadMov = new movimientos_cuenta();
               identidadMov.setId_movimiento(rs.getString("id_movimiento"));
               identidadMov.setDescripcion_mov(rs.getString("descripcion_mov"));
               identidadMov.setMonto(rs.getFloat("monto"));
               identidadMov.setId_cuenta_origen(rs.getString("id_cuenta_origen"));
               identidadMov.setId_cuenta_origen(rs.getString("id_cuenta_destino"));
               identidadMov.setId_cuenta(rs.getString("id_cuenta"));
               lstMov.add(identidadMov);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstMov;
    }    
    
    public int insertarMov(movimientos_cuenta mov){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO movimientos_cuenta(id_movimiento, descripcion_mov, monto, id_cuenta_origen, id_cuenta_destino, id_cuenta, flag) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, mov.getId_movimiento());
             pstmt.setString(2, mov.getDescripcion_mov());
             pstmt.setFloat(3, mov.getMonto());
             pstmt.setString(4, mov.getId_cuenta_origen());
             pstmt.setString(5, mov.getId_cuenta_destino());
             pstmt.setString(6, mov.getId_cuenta());
             pstmt.setInt(7, 1);
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
    
    public int modificarMov(movimientos_cuenta mov){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE movimientos_cuenta(descripcion_mov, monto, id_cuenta_origen, id_cuenta_destino, id_cuenta, flag) "
                + "VALUES (?, ?, ?, ?, ?, ?) WHERE id_movimiento=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, mov.getDescripcion_mov());
             pstmt.setFloat(2, mov.getMonto());
             pstmt.setString(3, mov.getId_cuenta_origen());
             pstmt.setString(4, mov.getId_cuenta_destino());
             pstmt.setString(5, mov.getId_cuenta());
             pstmt.setInt(6, 1);
             pstmt.setString(7, mov.getId_movimiento());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarMov(int id){
        String sql = "UPDATE movimientos_cuenta SET flag = 3 WHERE id_movimiento = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteMov(int id){
        String sql = "DELETE FROM movimientos_cuenta WHERE id_movimiento = ?";
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
      movimientos_cuentaDAO movDAO = new movimientos_cuentaDAO();

      List<movimientos_cuenta> lstMov = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_movimiento  = 1");
      lstMov = movDAO.consultaMov(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstMov.size()));
      for (movimientos_cuenta i:lstMov){
           System.out.println(i.getId_movimiento()+":"+i.getDescripcion_mov()+":"+i.getMonto()+":"+i.getId_cuenta_origen()+":"+i.getId_cuenta_destino()+":"+i.getId_cuenta());
      }
    }
    
}
