package scadb.DAO;

import DTO.bitacora_precio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class bitacora_precioDAO {
    
    public static bitacora_precioDAO instance = null;
    public static bitacora_precioDAO getInstance(){
        if (instance == null) instance = new bitacora_precioDAO();
        return instance;
    }
    private Connection conn;
    private bitacora_precio identidadBitacora;
    
    public List<bitacora_precio> consultaBitacora( List<String> where){

        List<bitacora_precio>  lstBitacora= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_bitacora, fecha, precio_menudeo, precio_mayoreo, id_usuario, nombreUsuario, precio_menudeo_ant, precio_mayoreo_ant, codigo_prod"
                + " FROM bitacora_precio where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadBitacora = new bitacora_precio();
               identidadBitacora.setId_bitacora(rs.getInt("id_bitacora"));
               identidadBitacora.setFecha(rs.getString("fecha"));
               identidadBitacora.setPrecio_menudeo(rs.getFloat("precio_menudeo"));
               identidadBitacora.setPrecio_mayoreo(rs.getFloat("precio_mayoreo"));
               identidadBitacora.setId_usuario(rs.getInt("id_usuario"));
               identidadBitacora.setNombreUsuario(rs.getString("nombreUsuario"));
               identidadBitacora.setPrecio_menudeo_ant(rs.getFloat("precio_menudeo_ant"));
               identidadBitacora.setPrecio_mayoreo_ant(rs.getFloat("precio_mayoreo_ant"));
               identidadBitacora.setCodigo_prod(rs.getInt("codigo_prod"));
               lstBitacora.add(identidadBitacora);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstBitacora;
    }    
    
    public int insertarBitacora(bitacora_precio bitacora){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO bitacora_precio(fecha, precio_menudeo, precio_mayoreo, id_usuario, nombreUsuario,  precio_menudeo_ant, precio_mayoreo_ant, codigo_prod, flag) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, bitacora.getFecha());
             pstmt.setFloat(2, bitacora.getPrecio_menudeo());
             pstmt.setFloat(3, bitacora.getPrecio_mayoreo());
             pstmt.setInt(4, bitacora.getId_usuario());
             pstmt.setString(5, bitacora.getNombreUsuario());
             pstmt.setFloat(6, bitacora.getPrecio_menudeo_ant());
             pstmt.setFloat(7, bitacora.getPrecio_mayoreo_ant());
             pstmt.setInt(8, bitacora.getCodigo_prod());
             pstmt.setInt(9, 1);
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
    
    public int modificarBitacora(bitacora_precio bitacora){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE bitacora_precio SET "
                + "fecha = ?, "
                + "precio_menudeo = ?, "
                + "precio_mayoreo = ?, "
                + "id_usuario = ?, "
                + "nombreUsuario = ?, "
                + "precio_menudeo_ant = ?, "
                + "precio_mayoreo_ant = ?, "
                + "codigo_prod = ?, "
                + "flag =? "
                + " WHERE id_bitacora=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, bitacora.getFecha());
             pstmt.setFloat(2, bitacora.getPrecio_menudeo());
             pstmt.setFloat(3, bitacora.getPrecio_mayoreo());
             pstmt.setInt(4, bitacora.getId_usuario());
             pstmt.setString(4, bitacora.getNombreUsuario());
             pstmt.setFloat(5, bitacora.getPrecio_menudeo_ant());
             pstmt.setFloat(6, bitacora.getPrecio_mayoreo_ant());
             pstmt.setInt(7, bitacora.getCodigo_prod());
             pstmt.setInt(8, 2);
             pstmt.setInt(9, bitacora.getId_bitacora());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarBitacora(int id){
        String sql = "UPDATE bitacora_precio SET flag = 3 WHERE id_bitacora = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deletBitacora(int id){
        String sql = "DELETE FROM bitacora_precio WHERE id_bitacora = ?";
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
      bitacora_precioDAO bitacoraDAO = new bitacora_precioDAO();

      List<bitacora_precio> lstBitacora = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_bitacora  = 1");
      lstBitacora = bitacoraDAO.consultaBitacora(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstBitacora.size()));
      for (bitacora_precio i:lstBitacora){
           System.out.println(i.getId_bitacora()+":"+i.getFecha()+":"+i.getPrecio_menudeo()+":"+i.getPrecio_mayoreo()+":"+i.getId_usuario()
                   +":"+i.getPrecio_menudeo_ant()+":"+i.getPrecio_mayoreo_ant()+":"+i.getCodigo_prod());
      }
    }
    
}
