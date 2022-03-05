/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BO;


import DTO.Credito;
import DTO.VENTA;
import DTO.apartado;
import DTO.detalle_venta;
import DTO.notas_remision;
import javafx.collections.ObservableList;
import scadb.DAO.CreditoDAO;
import scadb.DAO.apartadoDAO;
import scadb.DAO.detalle_ventaDAO;
import scadb.DAO.inventarioDAO;
import scadb.DAO.notas_remisionDAO;
import scadb.DAO.ventaDAO;

/**
 *
 * @author i7
 */
public class VentaBO {
    
    
public void guardarVenta(VENTA ventaTemp, notas_remision ntaRemTemp, Credito creditoTemp, 
            apartado apartadoTemp, ObservableList<detalle_venta> lstdetalleVentaTemp, int tipoVentaTemp ){
        inventarioDAO inventDAO = new inventarioDAO();
        notas_remisionDAO notaRemDAO = new notas_remisionDAO();
        ventaDAO vtaDAO = new ventaDAO();
        detalle_ventaDAO detalleVtaDAO = new detalle_ventaDAO();
        CreditoDAO credDAO = new CreditoDAO();
        apartadoDAO apartDAO = new apartadoDAO();
        int idnota=0;
        int codigoVenta = 0;
        switch(tipoVentaTemp){
            case 0: //Efectivo
                idnota = notaRemDAO.insertarNotasRem(ntaRemTemp);
                        System.out.println("Agrego Reg. Notas Remision:\n Id Nota Rem:"
                               +idnota
                               +"\n Folio Remision: "
                               +ntaRemTemp.getFolio());
                ventaTemp.setId_nota_rem(idnota);
                codigoVenta = vtaDAO.insertarVenta(ventaTemp);
                        System.out.println("Agrego Reg. Ventas:\n Codigo Nota Venta"
                               +codigoVenta+"\n Id Nota Rem:"
                               +ventaTemp.getId_nota_rem());
                System.out.println("codigo Venta:"+codigoVenta);
                System.out.println("Num Reg. --"+lstdetalleVentaTemp.size());
                for (int i =0; i<lstdetalleVentaTemp.size(); i++){
                  detalle_venta detIden = lstdetalleVentaTemp.get(i);
                  detIden.setCodigo_nota_venta(codigoVenta);
                  detalleVtaDAO.insertarDetVenta(detIden);
                       System.out.println("Agrego Reg. Detalle Ventas:\n Codigo Nota Venta"
                               +detIden.getCodigo_nota_venta()
                               +"\n Codigo Producto: "
                               +detIden.getCodigo_prod());
                  int restaInventario = detIden.getExistencia()-detIden.getCantidad();
                  inventDAO.modificarExistenciaProducto(detIden.getCodigo_prod(), restaInventario);
                        System.out.println("Modifico Exitencia en Inventario:\n Nva. Cant. "+restaInventario+"\n Producto: "
                                            +detIden.getCodigo_prod());
                }
                break;
            case 1: //Credito
                idnota = notaRemDAO.insertarNotasRem(ntaRemTemp);
                        System.out.println("Agrego Reg. Notas Remision:\n Id Nota Rem:"
                               +idnota+"\n Folio Remision: "
                               +ntaRemTemp.getFolio());
                ventaTemp.setId_nota_rem(idnota);
                codigoVenta = vtaDAO.insertarVenta(ventaTemp);
                        System.out.println("Agrego Reg. Ventas:\n Codigo Nota Venta"
                               +codigoVenta+"\n Id Nota Rem:"
                               +ventaTemp.getId_nota_rem());                
                for (detalle_venta i : lstdetalleVentaTemp){
                  i.setCodigo_nota_venta(codigoVenta);
                  detalleVtaDAO.insertarDetVenta(i);
                        System.out.println("Agrego Reg. Detalle Ventas:\n Codigo Nota Venta: "
                               +i.getCodigo_nota_venta()+"\n Codigo Producto: "
                               +i.getCodigo_prod());                  
                  int restaInventario = i.getExistencia()-i.getCantidad();
                  inventDAO.modificarExistenciaProducto(i.getCodigo_prod(), restaInventario);
                        System.out.println("Modifico Exitencia en Inventario:\nNva. Cant. "+restaInventario+"\n Producto: "
                                            +i.getCodigo_prod());
                }
                creditoTemp.setCodigo_nota_venta(codigoVenta);
                credDAO.insertarCredito(creditoTemp);
                        System.out.println("Agrego Reg. Credito:\n Codigo Nota Venta: "
                               +creditoTemp.getCodigo_nota_venta()                               
                               +"\n Monto: "
                               +creditoTemp.getMonto()+"\n Cod. Cliente: "
                               +creditoTemp.getCodigo_cliente());
                break;
            case 2: //Apartado
                idnota = notaRemDAO.insertarNotasRem(ntaRemTemp);
                        System.out.println("Agrego Reg. Notas Remision:\n Id Nota Rem:"
                               +idnota+"\n Folio Remision: "
                               +ntaRemTemp.getFolio());
                ventaTemp.setId_nota_rem(idnota);
                codigoVenta = vtaDAO.insertarVenta(ventaTemp);
                        System.out.println("Agrego Reg. Ventas:\n Codigo Nota Venta"
                               +codigoVenta+"\n Id Nota Rem: "
                               +ventaTemp.getId_nota_rem());
                for (detalle_venta i : lstdetalleVentaTemp){
                   i.setCodigo_nota_venta(codigoVenta);
                   detalleVtaDAO.insertarDetVenta(i);
                  int restaInventario = i.getExistencia()-i.getCantidad();
                  inventDAO.modificarExistenciaProducto(i.getCodigo_prod(), restaInventario);
                        System.out.println("Modifico Exitencia en Inventario:\nNva. Cant. "+restaInventario+"\n Producto: "
                                            +i.getCodigo_prod());
                }
                apartadoTemp.setCodigo_nota_venta(codigoVenta);
                apartDAO.insertarApartado(apartadoTemp);
                        System.out.println("Agrego Reg. Apartado:\n Codigo Nota Venta"
                               +apartadoTemp.getCodigo_nota_venta()
                               +"\n Monto: "
                               +apartadoTemp.getMonto()
                               +"\n Cod. Cliente: "
                               +apartadoTemp.getCodigo_cliente());
                break;
            case 3: //Tarjeta credito
                idnota = notaRemDAO.insertarNotasRem(ntaRemTemp);
                        System.out.println("Agrego Reg. Notas Remision:\n Id Nota Rem:"
                               +idnota+"\n Folio Remision: "
                               +ntaRemTemp.getFolio());
                ventaTemp.setId_nota_rem(idnota);
                codigoVenta = vtaDAO.insertarVenta(ventaTemp);
                        System.out.println("Agrego Reg. Ventas:\n Codigo Nota Venta"
                               +codigoVenta+"\n Folio Remision "
                               +ventaTemp.getId_nota_rem());
                for (detalle_venta i : lstdetalleVentaTemp){
                  i.setCodigo_nota_venta(codigoVenta);  
                  detalleVtaDAO.insertarDetVenta(i);
                        System.out.println("Agrego Reg. Detalle Ventas:\n Codigo Nota Venta"
                               +i.getCodigo_nota_venta()+"\n Codigo Producto: "
                               +i.getCodigo_prod());
                  int restaInventario = i.getExistencia()-i.getCantidad();
                  inventDAO.modificarExistenciaProducto(i.getCodigo_prod(), restaInventario);
                        System.out.println("Modifico Exitencia en Inventario:\nNva. Cant. "+restaInventario+"\n Producto: "
                                            +i.getCodigo_prod());
                }
                break;
            case 4: //trasferencia Bancaria
                idnota = notaRemDAO.insertarNotasRem(ntaRemTemp);
                        System.out.println("Agrego Reg. Notas Remision:\n Id Nota Rem:"
                               +idnota+"\n Folio Remision: "
                               +ntaRemTemp.getFolio());
                ventaTemp.setId_nota_rem(idnota);
                codigoVenta = vtaDAO.insertarVenta(ventaTemp);
                        System.out.println("Agrego Reg. Ventas:\n Codigo Nota Venta"
                               +codigoVenta+"\n Folio Remision "
                               +ventaTemp.getId_nota_rem());
                for (detalle_venta i : lstdetalleVentaTemp){
                  i.setCodigo_nota_venta(codigoVenta);
                  detalleVtaDAO.insertarDetVenta(i);
                        System.out.println("Agrego Reg. Detalle Ventas:\n Codigo Nota Venta"
                               +i.getCodigo_nota_venta()+"\n Codigo Producto: "
                               +i.getCodigo_prod());
                  int restaInventario = i.getExistencia()-i.getCantidad();
                  inventDAO.modificarExistenciaProducto(i.getCodigo_prod(), restaInventario);
                        System.out.println("Modifico Exitencia en Inventario:\nNva. Cant. "+restaInventario+"\n Producto: "
                                            +i.getCodigo_prod());
                }
                break;
        }
    
    }

public void modificarVenta(int codigoVenta, notas_remision ntaRemTemp, Credito creditoTemp, 
            apartado apartadoTemp, ObservableList<detalle_venta> lstdetalleVentaTemp, int tipoVentaTemp ){
        inventarioDAO inventDAO = new inventarioDAO();
        notas_remisionDAO notaRemDAO = new notas_remisionDAO();
        ventaDAO vtaDAO = new ventaDAO();
        detalle_ventaDAO detalleVtaDAO = new detalle_ventaDAO();
        CreditoDAO credDAO = new CreditoDAO();
        apartadoDAO apartDAO = new apartadoDAO();
        int idnota=0;
        switch(tipoVentaTemp){
            case 0: //Efectivo
                notaRemDAO.modificarNotasRem(ntaRemTemp);
                for (int i =0; i<lstdetalleVentaTemp.size(); i++){
                  detalle_venta detIden = lstdetalleVentaTemp.get(i);
                  detIden.setCodigo_nota_venta(codigoVenta);
                  detalleVtaDAO.insertarDetVenta(detIden);
                  int restaInventario = detIden.getExistencia()-detIden.getCantidad();
                  inventDAO.modificarExistenciaProducto(detIden.getCodigo_prod(), restaInventario);               
                }
                break;
            case 1: //Credito
                notaRemDAO.modificarNotasRem(ntaRemTemp);
                for (detalle_venta i : lstdetalleVentaTemp){
                  i.setCodigo_nota_venta(codigoVenta);
                  detalleVtaDAO.insertarDetVenta(i);
                  int restaInventario = i.getExistencia()-i.getCantidad();
                  inventDAO.modificarExistenciaProducto(i.getCodigo_prod(), restaInventario);
                }
                creditoTemp.setCodigo_nota_venta(codigoVenta);
                credDAO.insertarCredito(creditoTemp);
                break;
            case 2: //Apartado
                notaRemDAO.modificarNotasRem(ntaRemTemp);
                for (detalle_venta i : lstdetalleVentaTemp){
                   i.setCodigo_nota_venta(codigoVenta);
                   detalleVtaDAO.insertarDetVenta(i);
                  int restaInventario = i.getExistencia()-i.getCantidad();
                  inventDAO.modificarExistenciaProducto(i.getCodigo_prod(), restaInventario);
                }
                apartadoTemp.setCodigo_nota_venta(codigoVenta);
                apartDAO.insertarApartado(apartadoTemp);
                break;
            case 3: //Tarjeta credito
                notaRemDAO.modificarNotasRem(ntaRemTemp);
                for (detalle_venta i : lstdetalleVentaTemp){
                  i.setCodigo_nota_venta(codigoVenta);  
                  detalleVtaDAO.insertarDetVenta(i);
                  int restaInventario = i.getExistencia()-i.getCantidad();
                  inventDAO.modificarExistenciaProducto(i.getCodigo_prod(), restaInventario);
                }
                break;
            case 4: //trasferencia Bancaria
                notaRemDAO.modificarNotasRem(ntaRemTemp);
                for (detalle_venta i : lstdetalleVentaTemp){
                  i.setCodigo_nota_venta(codigoVenta);
                  detalleVtaDAO.insertarDetVenta(i);
                  int restaInventario = i.getExistencia()-i.getCantidad();
                  inventDAO.modificarExistenciaProducto(i.getCodigo_prod(), restaInventario);
                }
                break;
        }
    
    }

}
