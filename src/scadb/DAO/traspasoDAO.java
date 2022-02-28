package scadb.DAO;

import DTO.traspaso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class traspasoDAO {
    
    public static traspasoDAO instance = null;
    public static traspasoDAO getInstance(){
        if (instance == null) instance = new traspasoDAO();
        return instance;
    }
    private Connection conn;
    private traspaso identidadTraspaso;
    
    public List<traspaso> consultaTraspaso( List<String> where){
        
        List<traspaso>  lstTraspaso= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_traspaso, fecha, tienda_traspaso, responsable_tienda_envia, "
                + "responsable_tienda_recibe, responsable_transportar_prod, flag FROM traspaso where flag !=3 and "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadTraspaso = new traspaso();
               identidadTraspaso.setId_traspaso(rs.getInt("id_traspaso"));
               identidadTraspaso.setFecha(rs.getString("fecha"));
               identidadTraspaso.setTienda_traspaso(rs.getString("tienda_traspaso"));
               identidadTraspaso.setResponsable_tienda_envia(rs.getString("responsable_tienda_envia"));
               identidadTraspaso.setResponsable_tienda_recibe(rs.getString("responsable_tienda_recibe"));
               identidadTraspaso.setResponsable_transportar_prod(rs.getString("responsable_transportar_prod"));
               identidadTraspaso.setBandera(rs.getInt("flag"));
               lstTraspaso.add(identidadTraspaso);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstTraspaso;
    }    
    
    public int insertarTraspaso(traspaso tras){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO traspaso(fecha, tienda_traspaso, responsable_tienda_envia, "
                + "responsable_tienda_recibe, responsable_transportar_prod, flag) "
                + "VALUES (?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, tras.getFecha());
             pstmt.setString(2, tras.getTienda_traspaso());
             pstmt.setString(3, tras.getResponsable_tienda_envia());
             pstmt.setString(4, tras.getResponsable_tienda_recibe());
             pstmt.setString(5, tras.getResponsable_transportar_prod());
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
    
    public int modificarTraspaso(traspaso tras){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE traspaso SET fecha=?, tienda_traspaso=?, responsable_tienda_envia=?, "
                + "responsable_tienda_recibe=?, responsable_transportar_prod=?, flag=? "
                + "WHERE id_traspaso=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, tras.getFecha());
             pstmt.setString(2, tras.getTienda_traspaso());
             pstmt.setString(3, tras.getResponsable_tienda_envia());
             pstmt.setString(4, tras.getResponsable_tienda_recibe());
             pstmt.setString(5, tras.getResponsable_transportar_prod());
             pstmt.setInt(6, 2);
             pstmt.setInt(7, tras.getId_traspaso());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarTraspaso(int id){
        String sql = "UPDATE traspaso SET flag = 3 WHERE id_traspaso = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public void deleteTraspaso(int id){
        String sql = "DELETE FROM traspaso WHERE id_traspaso = ?";
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
      traspasoDAO traspasoDAO = new traspasoDAO();

      List<traspaso> lstTraspaso= new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_traspaso  = 1");
      lstTraspaso = traspasoDAO.consultaTraspaso(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstTraspaso.size()));
      for (traspaso i:lstTraspaso){
           System.out.println(i.getId_traspaso()+":"+i.getFecha()+":"+i.getTienda_traspaso()+":"+i.getResponsable_tienda_envia()
           +":"+i.getResponsable_tienda_recibe()+":"+i.getResponsable_transportar_prod());
      }
    }
    
}
