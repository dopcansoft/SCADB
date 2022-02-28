package scadb.DAO;

import DTO.apartado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class apartadoDAO {
    
    public static apartadoDAO instance = null;
    public static apartadoDAO getInstance(){
        if (instance == null) instance = new apartadoDAO();
        return instance;
    }
    private Connection conn;
    private apartado identidadApartado;
    
    public List<apartado> consultaApartado( List<String> where){

        List<apartado>  lstApartado= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_apartado, fecha, codigo_cliente, codigo_nota_venta, monto FROM apartado where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadApartado = new apartado();
               identidadApartado.setId_apartado(rs.getInt("id_apartado"));
               identidadApartado.setFecha(rs.getString("fecha"));
               identidadApartado.setCodigo_cliente(rs.getInt("codigo_cliente"));
               identidadApartado.setCodigo_nota_venta(rs.getInt("codigo_nota_venta"));
               identidadApartado.setMonto(rs.getFloat("monto"));

               lstApartado.add(identidadApartado);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstApartado;
    }    
    
    public int insertarApartado(apartado pago){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO apartado(fecha, codigo_cliente, codigo_nota_venta, monto, flag) "
                + "VALUES (?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, pago.getFecha());
             pstmt.setFloat(2, pago.getCodigo_cliente());
             pstmt.setInt(3, pago.getCodigo_nota_venta());
             pstmt.setFloat(4, pago.getMonto());
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
    
    public int modificarApartado(apartado ap){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE apartado(fecha, codigo_cliente, codigo_nota_venta, monto, flag) "
                + "VALUES (?, ?, ?, ?, ?) WHERE id_apartado=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, ap.getFecha());
             pstmt.setInt(2, ap.getCodigo_cliente());
             pstmt.setInt(3, ap.getCodigo_nota_venta());
             pstmt.setFloat(4, ap.getMonto());
             pstmt.setInt(5, 2);
             pstmt.setInt(6, ap.getId_apartado());
             regs = pstmt.executeUpdate();
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarApartado(int id){
        String sql = "UPDATE apartado SET flag=3 WHERE id_apartado = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteApartado(int id){
        String sql = "DELETE FROM  apartado WHERE id_apartado = ?";
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
      apartadoDAO apartadoDAO = new apartadoDAO();

      List<apartado> lstApartado = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_apartado  = 1");
      lstApartado = apartadoDAO.consultaApartado(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstApartado.size()));
      for (apartado i:lstApartado){
           System.out.println(i.getId_apartado()+":"+i.getFecha()+":"+i.getCodigo_cliente()+":"+i.getCodigo_nota_venta());
      }
    }
    
}
