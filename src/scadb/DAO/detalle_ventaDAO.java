package scadb.DAO;

import DTO.detalle_venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class detalle_ventaDAO {
    
    public static detalle_ventaDAO instance = null;
    public static detalle_ventaDAO getInstance(){
        if (instance == null) instance = new detalle_ventaDAO();
        return instance;
    }
    private Connection conn;
    private detalle_venta identidadDetVenta;
    
    public List<detalle_venta> consultaDetVenta( List<String> where){
            
            List<detalle_venta>  lstDetVenta= new ArrayList<>();
            StringBuilder Filtro = new StringBuilder();
            Filtro.append(where.get(0));
            where.remove(0);
            if (!where.isEmpty()){
                    for (String i:where){
                            Filtro.append(" AND "+i);
                    }			
            }		
            String sql = "SELECT id_detalle_venta, codigo_prod, descrprod, cantidad, codigo_nota_venta, precio_venta FROM detalle_venta where flag != 3 AND "+Filtro.toString();
            Conexion conecta = new Conexion("DBPLAMAR.db");
            System.out.println(sql);

            try (Connection con = conecta.conectaDB();
                 Statement stmt  = con.createStatement();
                 ResultSet rs  = stmt.executeQuery(sql);){
                 while (rs.next()){
                   identidadDetVenta = new detalle_venta();
                   identidadDetVenta.setId_detalle_venta(rs.getInt("id_detalle_venta"));
                   identidadDetVenta.setCodigo_prod(rs.getInt("codigo_prod"));
                   identidadDetVenta.setDescrprod(rs.getString("descrprod"));
                   identidadDetVenta.setCantidad(rs.getInt("cantidad"));
                   identidadDetVenta.setCodigo_nota_venta(rs.getInt("codigo_nota_venta"));
                   identidadDetVenta.setPrecio_venta(rs.getFloat("precio_venta"));
                   lstDetVenta.add(identidadDetVenta);
                 }
                 con.close();

            } catch (SQLException e) {
               System.out.println(e.getMessage());
            }
            return lstDetVenta;
	}    

    public List<detalle_venta> consultaDistinctDetVenta( List<String> where){
            
            List<detalle_venta>  lstDetVenta= new ArrayList<>();
            StringBuilder Filtro = new StringBuilder();
            Filtro.append(where.get(0));
            where.remove(0);
            if (!where.isEmpty()){
                    for (String i:where){
                            Filtro.append(" AND "+i);
                    }			
            }		
            String sql = "SELECT distinct codigo_nota_venta FROM detalle_venta where flag != 3 AND "+Filtro.toString();
            Conexion conecta = new Conexion("DBPLAMAR.db");
            System.out.println(sql);

            try (Connection con = conecta.conectaDB();
                 Statement stmt  = con.createStatement();
                 ResultSet rs  = stmt.executeQuery(sql);){
                 while (rs.next()){
                   identidadDetVenta = new detalle_venta();
                   identidadDetVenta.setCodigo_nota_venta(rs.getInt("codigo_nota_venta"));
                   lstDetVenta.add(identidadDetVenta);
                 }
                 con.close();

            } catch (SQLException e) {
               System.out.println(e.getMessage());
            }
            return lstDetVenta;
	}
    
    public int insertarDetVenta(detalle_venta detalle){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO detalle_venta(codigo_prod, descrprod, cantidad, codigo_nota_venta, precio_venta, flag) "
                + "VALUES (?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setInt(1, detalle.getCodigo_prod());
             pstmt.setString(2, detalle.getDescrprod());
             pstmt.setInt(3, detalle.getCantidad());
             pstmt.setFloat(4, detalle.getCodigo_nota_venta());
             pstmt.setFloat(5, detalle.getPrecio_venta());
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
    
    public int modificarDetVenta(detalle_venta detalle){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE detalle_venta SET "
                + " codigo_prod= ?, "
                + "descrprod = ?, "
                + "cantidad = ?, "
                + "codigo_nota_venta = ?, "
                + "precio_venta = ?, "
                + "flag = ? "
                + "WHERE id_detalle_venta = ?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setInt(1, detalle.getCodigo_prod());
             pstmt.setString(2, detalle.getDescrprod());
             pstmt.setInt(3, detalle.getCantidad());
             pstmt.setFloat(4, detalle.getCodigo_nota_venta());
             pstmt.setFloat(5, detalle.getPrecio_venta());
             pstmt.setInt(6, 2);
             pstmt.setInt(7, detalle.getId_detalle_venta());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarDetVenta(int id){
        String sql = "UPDATE detalle_venta SET flag = 3 WHERE id_detalle_venta = ?"; 
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteDetVenta(int id){
        String sql = "DELETE FROM detalle_venta WHERE id_detalle_venta = ?"; 
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
      detalle_ventaDAO detVentDAO = new detalle_ventaDAO();

      List<detalle_venta> lstDetVenta = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_detalle_venta  = 1");
      lstDetVenta = detVentDAO.consultaDetVenta(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstDetVenta.size()));
      for (detalle_venta i:lstDetVenta){
           System.out.println(i.getId_detalle_venta()+":"+i.getCodigo_prod()+":"+i.getDescrprod()+":"+i.getCantidad()+":"+i.getCodigo_nota_venta()+":"
                   +i.getCodigo_prod()+":"+i.getPrecio_venta());
      }
}
    
}
