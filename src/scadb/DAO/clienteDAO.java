/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.DAO;

import DTO.CLIENTE;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;



/**
 *
 * @author dopcan
 */
public class clienteDAO {
    
    public List<CLIENTE> consultarClientes( List<String> where){
        List<CLIENTE>  lstClientes= new ArrayList<>();

        //StringBuilder sql= null;
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }
        Conexion conecta = new Conexion("DBPLAMAR.db");
        //codigo_cliente, nombre, razon_social, domicilio_fiscal, telefono, rfc, email
        String sql = "SELECT codigo_cliente, nombre, razon_social, domicilio_fiscal, telefono, rfc, email FROM CLIENTE where flag != 3 AND "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                CLIENTE clte = new CLIENTE();
                clte.setCodigo_cliente(rs.getInt(1));
                clte.setNombre(rs.getString(2));
                clte.setRazon_social(rs.getString(3));
                clte.setDomicilio_fiscal(rs.getString(4));
                clte.setTelefono(rs.getString(5));
                clte.setRfc(rs.getString(6));
                clte.setEmail(rs.getString(7));
                lstClientes.add(clte);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstClientes;
    }  


    public int insertarCliente(String nombreP, String razon_socialP, String domicilio_fiscalP, String telefonoP, String rfcP, String emailP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("DBPLAMAR.db");    
        sqrString.append("INSERT INTO CLIENTE(nombre, razon_social, domicilio_fiscal, telefono, rfc, email, flag) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, nombreP);
             pstmt.setString(2, razon_socialP);
             pstmt.setString(3, domicilio_fiscalP);
             pstmt.setString(4, telefonoP);
             pstmt.setString(5, rfcP);
             pstmt.setString(6, emailP);
             pstmt.setInt(7, 1);
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

    public void modificarCliente(int codigoClienteP, String nombreP, String razon_socialP, String domicilio_fiscalP, String telefonoP, String rfcP, String emailP) {
            String sql = "UPDATE CLIENTE SET nombre = ?, "
                    + "razon_social = ?, "
                    + "domicilio_fiscal = ?, "
                    + "telefono = ?,"
                    + "rfc = ?, "
                    + "email = ?, "
                    + "flag = ? "
                    + "WHERE codigo_cliente = ? ";
          Conexion conecta = new Conexion("DBPLAMAR.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, nombreP);
                pstmt.setString(2, razon_socialP);
                pstmt.setString(3, domicilio_fiscalP);
                pstmt.setString(4, telefonoP);
                pstmt.setString(5, rfcP);
                pstmt.setString(6, emailP);
                pstmt.setInt(7, 2);
                pstmt.setInt(8, codigoClienteP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void eliminarLogicamenteCliente(int id) {
        String sql = "UPDATE CLIENTE SET flag = 3 WHERE codigo_cliente = ?";
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

    public void eliminarCliente(int id) {
        String sql = "DELETE FROM CLIENTE WHERE codigo_cliente = ?";
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
