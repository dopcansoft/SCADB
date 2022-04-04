package scadb.DAO;

import DTO.VENTA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ventaDAO {
    
    public static ventaDAO instance = null;
    public static ventaDAO getInstance(){
        if (instance == null) instance = new ventaDAO();
        return instance;
    }
    private Connection conn;
    private VENTA identidadVenta;
    
    public List<VENTA> consultaVenta( List<String> where){

        List<VENTA>  lstVenta= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT codigo_nota_venta, fecha, tipo_venta, codigo_cliente, codigo_factura, id_nota_rem FROM VENTA where flag != 3 and "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadVenta = new VENTA();
               identidadVenta.setCodigo_nota_venta(rs.getInt("codigo_nota_venta"));
               identidadVenta.setFecha(rs.getString("fecha"));
               identidadVenta.setTipo_venta(rs.getString("tipo_venta"));
               identidadVenta.setCodigo_cliente(rs.getInt("codigo_cliente"));
               identidadVenta.setCodigo_factura(rs.getString("codigo_factura"));
               identidadVenta.setId_nota_rem(rs.getInt("id_nota_rem"));
               lstVenta.add(identidadVenta);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstVenta;
    }    
    
    public int insertarVenta(VENTA nota){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO VENTA(fecha, tipo_venta, codigo_cliente, codigo_factura, id_nota_rem, flag) "
                + "VALUES (?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, nota.getFecha());
             pstmt.setString(2, nota.getTipo_venta());
             pstmt.setInt(3, nota.getCodigo_cliente());
             pstmt.setString(4, nota.getCodigo_factura());
             pstmt.setInt(5, nota.getId_nota_rem());
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
    
    public int modificarVenta(VENTA nota){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE VENTA SET fecha = ?, "
                + "tipo_venta = ?, "
                + "codigo_cliente = ?, "
                + "codigo_factura = ?, "
                + "flag = ? "
                + "WHERE codigo_nota_venta = ?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, nota.getFecha());
             pstmt.setString(2, nota.getTipo_venta());
             pstmt.setInt(3, nota.getCodigo_cliente());
             pstmt.setString(4, nota.getCodigo_factura());
             pstmt.setInt(5, 2);
             pstmt.setInt(6, nota.getCodigo_nota_venta());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void cancelarVenta(int id){
        String sql = "UPDATE VENTA SET flag=4 WHERE codigo_nota_venta = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void borrarVenta(int id){
        String sql = "UPDATE VENTA SET flag=3 WHERE codigo_nota_venta = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteVenta(int id){
        String sql = "DELETE FROM VENTA WHERE codigo_nota_venta = ?";
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
      ventaDAO ventDAO = new ventaDAO();

      List<VENTA> lstVenta = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("codigo_nota_venta  = 1");
      lstVenta = ventDAO.consultaVenta(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstVenta.size()));
      for (VENTA i:lstVenta){
           System.out.println(i.getCodigo_nota_venta()+":"+i.getFecha()+":"+i.getTipo_venta()+":"+i.getCodigo_cliente()+":"+i.getCodigo_factura());
      }
    }
    
}
