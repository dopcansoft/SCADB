package scadb.DAO;

import DTO.ubicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ubicacionDAO {
    
    public static ubicacionDAO instance = null;
    public static ubicacionDAO getInstance(){
        if (instance == null) instance = new ubicacionDAO();
        return instance;
    }
    private Connection conn;
    private ubicacion identidadUbicacion;
    
    public List<ubicacion> consultaUbicacion( List<String> where){
        
        List<ubicacion>  lstUbicacion= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_ubicacion, bodega, anaquel, estante FROM ubicacion where flag !=3 "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadUbicacion = new ubicacion();
               identidadUbicacion.setId_ubicacion(rs.getInt("id_ubicacion"));
               identidadUbicacion.setBodega(rs.getString("bodega"));
               identidadUbicacion.setAnaquel(rs.getString("anaquel"));
               identidadUbicacion.setEstante(rs.getString("estante"));
               lstUbicacion.add(identidadUbicacion);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstUbicacion;
    }    
    
    public int insertarUbicacion(ubicacion ubic){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO usuario(bodega, anaquel, estante, flag) "
                + "VALUES (?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, ubic.getBodega());
             pstmt.setString(2, ubic.getAnaquel());
             pstmt.setString(3, ubic.getEstante());
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
    
    public int modificarUbicacion(ubicacion ubic){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE ubicacion(bodega, anaquel, estante, flag) "
                + "VALUES (?, ?, ?, ?) WHERE id_ubicacion=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, ubic.getBodega());
             pstmt.setString(2, ubic.getAnaquel());
             pstmt.setString(3, ubic.getEstante());
             pstmt.setInt(4, 2);
             pstmt.setInt(5, ubic.getId_ubicacion());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarUbicacion(int id){
        String sql = "UPDATE ubicacion SET flag = 3 WHERE id_ubicacion = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public void deleteUbicacion(int id){
        String sql = "DELETE FROM ubicacion WHERE id_ubicacion = ?";
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
      ubicacionDAO ubicDAO = new ubicacionDAO();

      List<ubicacion> lstUbicacion = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_ubicacion  = 1");
      lstUbicacion = ubicDAO.consultaUbicacion(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstUbicacion.size()));
      for (ubicacion i:lstUbicacion){
           System.out.println(i.getId_ubicacion()+":"+i.getBodega()+":"+i.getAnaquel()+":"+i.getEstante());
      }
    }
    
}
