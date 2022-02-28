package scadb.DAO;

import DTO.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class usuarioDAO {
    
    public static usuarioDAO instance = null;
    public static usuarioDAO getInstance(){
        if (instance == null) instance = new usuarioDAO();
        return instance;
    }
    private Connection conn;
    private usuario identidadUsuario;
    
    public List<usuario> consultaUsuario( List<String> where){
        
        List<usuario>  lstUsuario= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_usuario, nombre_completo, tipo, usuario, clave FROM usuario where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("DBPLAMAR.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadUsuario = new usuario();
               identidadUsuario.setId_usuario(rs.getInt("id_usuario"));
               identidadUsuario.setNombre_completo(rs.getString("nombre_completo"));
               identidadUsuario.setTipo(rs.getString("tipo"));
               identidadUsuario.setUsuario(rs.getString("usuario"));
               identidadUsuario.setClave(rs.getString("clave"));
               lstUsuario.add(identidadUsuario);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstUsuario;
    }    
    
    public int insertarUsuario(usuario user){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO usuario(nombre_completo, tipo, usuario, clave, flag) "
                + "VALUES (?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, user.getNombre_completo());
             pstmt.setString(2, user.getTipo());
             pstmt.setString(3, user.getUsuario());
             pstmt.setString(4, user.getClave());
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
    
    public int modificarUsuario(usuario user){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("UPDATE usuario SET "
                + "nombre_completo=?, "
                + "tipo=?, "
                + "usuario=?, "
                + "clave=?, "
                + "flag=? "
                + "WHERE id_usuario=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, user.getNombre_completo().toString());
             pstmt.setString(2, user.getTipo());
             pstmt.setString(3, user.getUsuario().toString());
             pstmt.setString(4, user.getClave().toString());
             pstmt.setInt(5, 2);
             pstmt.setInt(6, user.getId_usuario());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarUsuario(int id){
        String sql = "UPDATE usuario SET flag=3 WHERE id_usuario = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public void deleteUsuario(int id){
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
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
      usuarioDAO userDAO = new usuarioDAO();

      List<usuario> lstUsuario = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_usuario  = 1");
      lstUsuario = userDAO.consultaUsuario(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstUsuario.size()));
      for (usuario i:lstUsuario){
           System.out.println(i.getId_usuario()+":"+i.getNombre_completo()+":"+i.getTipo()+":"+i.getUsuario()+":"+i.getClave());
      }
    }
    
}
