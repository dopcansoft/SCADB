package scadb.DAO;

import DTO.SUCURSAL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class sucursalDAO {
    
    public static sucursalDAO instance = null;
    public static sucursalDAO getInstance(){
        if (instance == null) instance = new sucursalDAO();
        return instance;
    }
    private Connection conn;
    private SUCURSAL identidadSucursal;
    
    public List<SUCURSAL> consultaSucursal( List<String> where){
        
        List<SUCURSAL>  lstSucursal= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_suc, nombre, direccion FROM SUCURSAL where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadSucursal = new SUCURSAL();
               identidadSucursal.setId_suc(rs.getInt("id_suc"));
               identidadSucursal.setNombre(rs.getString("nombre"));
               identidadSucursal.setDireccion(rs.getString("direccion"));
               lstSucursal.add(identidadSucursal);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstSucursal;
    }    
    
    public int insertarSucursal(SUCURSAL suc){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO SUCURSAL(nombre, direccion, flag) "
                + "VALUES (?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, suc.getNombre());
             pstmt.setString(2, suc.getDireccion());
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
    
    public int modificarSucursal(SUCURSAL suc){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE SUCURSAL SET nombre = ?, "
                + "direccion = ?, "
                + "flag= ?"
                + "WHERE id_suc=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, suc.getNombre());
             pstmt.setString(2, suc.getDireccion());
             pstmt.setInt(3, 2);
             pstmt.setInt(4, suc.getId_suc());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarSucursal(int id){
        String sql = "UPDATE SUCURSAL SET flag=3 WHERE id_suc = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public void deleteSucursal(int id){
        String sql = "DELETE FROM SUCURSAL WHERE id_suc = ?";
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
      sucursalDAO sucDAO = new sucursalDAO();

      List<SUCURSAL> lstSucursal = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_suc  = 1");
      lstSucursal = sucDAO.consultaSucursal(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstSucursal.size()));
      for (SUCURSAL i:lstSucursal){
           System.out.println(i.getId_suc()+":"+i.getNombre()+":"+i.getDireccion());
      }
    }
    
}
