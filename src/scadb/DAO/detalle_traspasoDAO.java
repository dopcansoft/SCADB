package scadb.DAO;

import DTO.detalle_traspaso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class detalle_traspasoDAO {
    
    public static detalle_traspasoDAO instance = null;
    public static detalle_traspasoDAO getInstance(){
        if (instance == null) instance = new detalle_traspasoDAO();
        return instance;
    }
    private Connection conn;
    private detalle_traspaso identidadDetTraspaso;
    
    public List<detalle_traspaso> consultaDetTraspaso( List<String> where){
            
            List<detalle_traspaso>  lstDetVenta= new ArrayList<>();
            StringBuilder Filtro = new StringBuilder();
            Filtro.append(where.get(0));
            where.remove(0);
            if (!where.isEmpty()){
                    for (String i:where){
                            Filtro.append(" AND "+i);
                    }			
            }		
            String sql = "SELECT id_detalle_traspaso, cantidad, codigo_prod, descrprod, id_traspaso "
                    + "FROM detalle_traspaso where flag != 3 and "+Filtro.toString();
            Conexion conecta = new Conexion("DBPLAMAR.db");
            System.out.println(sql);

            try (Connection con = conecta.conectaDB();
                 Statement stmt  = con.createStatement();
                 ResultSet rs  = stmt.executeQuery(sql);){
                 while (rs.next()){
                   identidadDetTraspaso = new detalle_traspaso();
                   identidadDetTraspaso.setId_detalle_traspaso(rs.getInt("id_detalle_traspaso"));
                   identidadDetTraspaso.setCantidad(rs.getInt("cantidad"));
                   identidadDetTraspaso.setCodigo_prod(rs.getInt("codigo_prod"));                  
                   identidadDetTraspaso.setDescrprod(rs.getString("descrprod"));                  
                   identidadDetTraspaso.setId_traspaso(rs.getInt("id_traspaso"));
                   lstDetVenta.add(identidadDetTraspaso);
                 }
                 con.close();

            } catch (SQLException e) {
               System.out.println(e.getMessage());
            }
            return lstDetVenta;
	}    
    
    public int insertarDetTraspaso(detalle_traspaso detalle){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO detalle_traspaso(cantidad, codigo_prod, descrprod, id_traspaso, flag) "
                + "VALUES (?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setInt(1, detalle.getCantidad());
             pstmt.setInt(2, detalle.getCodigo_prod());
             pstmt.setString(3, detalle.getDescrprod());
             pstmt.setInt(4, detalle.getId_traspaso());
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
    
    public int modificarDetTraspaso(detalle_traspaso detalle){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE detalle_traspaso SET cantidad=?, codigo_prod=?, descrprod=?, id_traspaso=?, flag=? "
                + "WHERE id_detalle_traspaso=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setInt(1, detalle.getCantidad());
             pstmt.setInt(2, detalle.getCodigo_prod());
             pstmt.setString(3, detalle.getDescrprod());
             pstmt.setInt(4, detalle.getId_traspaso());
             pstmt.setInt(5, 2);
             pstmt.setInt(6, detalle.getId_detalle_traspaso());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarDetTraspaso(int id){
        String sql = "UPDATE detalle_traspaso SET flag = 3 WHERE id_detalle_traspaso = ?"; 
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteDetTraspaso(int id){
        String sql = "DELETE FROM detalle_traspaso WHERE id_detalle_traspaso = ?"; 
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
      detalle_traspasoDAO detTraspasoDAO = new detalle_traspasoDAO();

      List<detalle_traspaso> lstDetTraspaso = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_detalle_traspaso  = 1");
      lstDetTraspaso = detTraspasoDAO.consultaDetTraspaso(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstDetTraspaso.size()));
      for (detalle_traspaso i:lstDetTraspaso){
           System.out.println(i.getId_detalle_traspaso()+":"+i.getCodigo_prod()+":"+i.getCantidad()+":"+i.getId_traspaso());
      }
}
    
}
