/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.DAO;

import DTO.PROVEEDOR;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author dopcan
 */
public class proveedorDAO {
    
    public static proveedorDAO instance = null;
        
    public static proveedorDAO getInstance(){
        if (instance == null) instance = new proveedorDAO();
        return instance;
    }
    private PROVEEDOR idemPrvdr;
       
    
    public List<PROVEEDOR> consultaProveedores( List<String> where){
        List<PROVEEDOR>  lstProveedores= new ArrayList<>();
        //StringBuilder sql= null;
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }
        String sql = "SELECT codigo_prov, nombre, telefono FROM PROVEEDOR where flag !=3 AND "+Filtro.toString();
        System.out.println(sql);
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection conn = conecta.conectaDB();
             Statement stmt  = conn.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
                PROVEEDOR prvdr = new PROVEEDOR();
                prvdr.setCodigo_prov(rs.getInt(1));
                prvdr.setNombre(rs.getString(2));
                prvdr.setTelefono(rs.getString(3));
                lstProveedores.add(prvdr);
             }
             conn.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstProveedores;
    }  

    public int insertarProveedor(String nombre, String telefono){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO PROVEEDOR(nombre, telefono, flag) "
                + "VALUES (?,?,?)");
        System.out.print(sqrString.toString());
        try (Connection conn = conecta.conectaDB();
             PreparedStatement pstmt = conn.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS))
        {
             pstmt.setString(1, nombre);
             pstmt.setString(2, telefono);
             pstmt.setInt(3, 1);
             regs = pstmt.executeUpdate();
             ResultSet rs = pstmt.getGeneratedKeys();
             if (regs== 1){
               regs = rs.getInt(1);
             }
             conn.close();

        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }

    public void modificarProveedor(int codigoProvP, String nombreP, String telefonoP) {
            String sql = "UPDATE PROVEEDOR SET "
                    + "nombre = ?, "
                    + "telefono = ?, "
                    + "flag = ? "
                    + "WHERE codigo_prov = ? ";
          Conexion conecta = new Conexion("DBPLAMAR.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, nombreP);
                pstmt.setString(2, telefonoP);
                pstmt.setInt(3, 2);
                pstmt.setInt(4, codigoProvP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void eliminarLogicamenteProveedor(int id) {
        String sql = "UPDATE PROVEEDOR SET flag=3 WHERE codigo_prov = ?"; 
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void delProveedor(int id) {
        String sql = "DELETE FROM PROVEEDOR WHERE codigo_prov = ?"; 
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int ObtenerIdProveedor(String proveedor){
        int idProveedor;

        String sql = "SELECT codigo_prov, nombre, telefono FROM PROVEEDOR where flag !=3 AND nombre = '"+proveedor+"' ";
        System.out.println(sql);
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection conn = conecta.conectaDB();
             Statement stmt  = conn.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
                idemPrvdr = new PROVEEDOR();
                idemPrvdr.setCodigo_prov(rs.getInt(1));
                idemPrvdr.setNombre(rs.getString(2));
                idemPrvdr.setTelefono(rs.getString(3));
             }
             conn.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }        
        return idemPrvdr.getCodigo_prov();
    }

    public String ObtenerNombreProveedor(int idProv){
        int idProveedor;

        String sql = "SELECT codigo_prov, nombre, telefono FROM PROVEEDOR where flag !=3 AND codigo_prov = "+idProv;
        System.out.println(sql);
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection conn = conecta.conectaDB();
             Statement stmt  = conn.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
                idemPrvdr = new PROVEEDOR();
                idemPrvdr.setCodigo_prov(rs.getInt(1));
                idemPrvdr.setNombre(rs.getString(2));
                idemPrvdr.setTelefono(rs.getString(3));
             }
             conn.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }        
        return idemPrvdr.getNombre();
    }
    
    public static void main(String[] Args){
          proveedorDAO provDAO = new proveedorDAO();
          //Concepto ctp = new Concepto(2004,"Pago Anticipo","1.002-ISFSI"," Pago del 25%");
          //int regs=provDAO.insertarProveedor(31010500, "Israel Amador", "2294283925");
          //System.out.println("Respuesta: "+regs);
          List<PROVEEDOR> lstProv = new ArrayList<PROVEEDOR>();
          List<String> lstWhere = new ArrayList<String>();
          lstWhere.add("codigo_prov is not null");
          lstProv = provDAO.consultaProveedores(lstWhere);
          System.out.print("Registro: "+String.valueOf(lstProv.size()));
          for (PROVEEDOR i:lstProv){
               System.out.print(i.getCodigo_prov()+":"+i.getNombre()+":"+i.getTelefono());
          }
    }

}
