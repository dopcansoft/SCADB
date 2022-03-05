/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;


import DTO.acumuladosVenta;
import DTO.ventasDiarias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scadb.DAO.Conexion;

/**
 *
 * @author i7
 */
public class SumatoriaVentasDiariasBO {
    
    /* Acumulados
      SELECT tipo_operacion, sum(monto) as total
      from notas_remision 
      where fecha ='2020-02-21' group by tipo_operacion
    */
    
    /*select * from VENTA vt1 
      left join notas_remision vt2 
      on vt1.id_nota_rem = vt2.id_nota_rem 
      LEFT JOIN CLIENTE VT3 ON 
      vt1.codigo_cliente = vt3.codigo_cliente*/
    public static SumatoriaVentasDiariasBO instance = null;
    public static SumatoriaVentasDiariasBO getInstance(){
        if (instance == null) instance = new SumatoriaVentasDiariasBO();
        return instance;
    }
    private Connection conn;
    
    public ObservableList<acumuladosVenta> consultaSumNotasRem(String fecha){

        ObservableList<acumuladosVenta> lstNotas = FXCollections.observableArrayList();
        StringBuilder sbSQL = new StringBuilder(
                "SELECT tipo_operacion, sum(monto) as total " +
                "from notas_remision " +
                "where fecha = ? AND flag !=3 AND tipo_operacion <> 'APARTADO' group by tipo_operacion");
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try {
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sbSQL.toString());
             pstmt.setString(1, fecha);
             System.out.println(sbSQL.toString());
             ResultSet rs = pstmt.executeQuery();
             while (rs.next()){
               acumuladosVenta acvta = new acumuladosVenta();
               acvta.setTipo_operacion(rs.getString("tipo_operacion"));
               acvta.setMonto(rs.getFloat("total"));
               lstNotas.add(acvta);
             }
               con.close();
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstNotas;
    }     

    public ObservableList<ventasDiarias> ventasDelDia(String fecha){

        ObservableList<ventasDiarias> lstNotas = FXCollections.observableArrayList();
        StringBuilder sbSQL = new StringBuilder(
                "select * from VENTA vt1 \n" +
                        "left join notas_remision vt2 \n" +
                        "on vt1.id_nota_rem = vt2.id_nota_rem \n" +
                        "LEFT JOIN CLIENTE VT3 ON \n" +
                        "vt1.codigo_cliente = vt3.codigo_cliente where vt1.fecha = ? AND vt1.flag !=3 AND vt2.flag !=3 AND vt1.tipo_venta <> 'APARTADO'  order by vt1.tipo_venta");
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try {
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sbSQL.toString());
             pstmt.setString(1, fecha);
             System.out.println(sbSQL.toString());
             ResultSet rs = pstmt.executeQuery();
             while (rs.next()){
               ventasDiarias vtaD = new ventasDiarias();
               vtaD.setTipo_venta(rs.getString("tipo_venta"));
               vtaD.setCliente(rs.getString("nombre"));
               vtaD.setCodigo_factura(rs.getString("codigo_factura"));
               vtaD.setFolio_nota(rs.getString("folio"));
               vtaD.setMonto(rs.getFloat("monto"));
               lstNotas.add(vtaD);
             }
               con.close();
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstNotas;
    }

    public ObservableList<acumuladosVenta> ventasAcumMes(String fecha){

        ObservableList<acumuladosVenta> lstVentasAcumMes = FXCollections.observableArrayList();
        StringBuilder sbSQL = new StringBuilder(
                "SELECT tipo_operacion, sum(monto) as total from notas_remision " 
              + "where date(fecha) between date(?) and date(?) AND flag !=3 AND tipo_operacion <> 'APARTADO' "
              + "group by tipo_operacion;");
        String fechaprimerDiaMes = fecha.substring(0, 8)+"01";
        System.out.println("Fecha 1:->"+fechaprimerDiaMes);
        
        Conexion conecta = new Conexion("DBPLAMAR.db");
        try {
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sbSQL.toString());
             pstmt.setString(1, fechaprimerDiaMes);
             pstmt.setString(2, fecha);
             System.out.println(sbSQL.toString());
             ResultSet rs = pstmt.executeQuery();
             while (rs.next()){
               acumuladosVenta vtaAcMes = new acumuladosVenta();
               vtaAcMes.setTipo_operacion(rs.getString("tipo_operacion"));
               vtaAcMes.setMonto(rs.getFloat("total"));
               lstVentasAcumMes.add(vtaAcMes);
             }
               con.close();
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstVentasAcumMes;
    }    
    
    public static void main(String[] Args){
      SumatoriaVentasDiariasBO sumvtasDBO = new SumatoriaVentasDiariasBO();
      List<acumuladosVenta> lstNotas = new ArrayList<>();
      
      String strFecha = "2020-02-24";
      lstNotas = sumvtasDBO.ventasAcumMes(strFecha);
      System.out.println("Registro: "+String.valueOf(lstNotas.size()));
      for (acumuladosVenta i:lstNotas){
           System.out.println(i.getTipo_operacion()+":"+i.getMonto());
      }
    }
    
}
