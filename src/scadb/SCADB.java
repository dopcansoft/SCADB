/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb;

import java.awt.Dimension;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scadb.IG.PantallaAgregarProducto;
import scadb.IG.PantallaEliminarProducto;
import scadb.IG.PantallaImportarProducto;
import scadb.IG.PantallaModificarProducto;
import scadb.IG.pantallaCancelarVenta;
import scadb.IG.pantallaConsultarVenta;
import scadb.IG.pantallaCrearVenta;
import scadb.IG.pantallaExportarDbVenta;
import scadb.IG.pantallaExportarXlsVenta;

/**
 *
 * @author dopcan
 */
public class SCADB extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // pantallas
        
        pantallaCrearVenta pCrearVenta = new pantallaCrearVenta();
        pantallaConsultarVenta pConsultarVenta = new pantallaConsultarVenta();
        pantallaCancelarVenta pCancelarVenta = new pantallaCancelarVenta();
        pantallaExportarXlsVenta pExportaXlsVenta = new pantallaExportarXlsVenta();
        pantallaExportarDbVenta pExportaDbVenta = new pantallaExportarDbVenta();
        
        
        
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double h= screenSize.getHeight();
        double w= screenSize.getWidth(); 

        VBox vbPrincipal = new VBox();
        VBox vbAreTrabajo = new VBox();
        
        MenuItem miCrearVentas = new MenuItem("Crear");
        MenuItem miConsultarVentas = new MenuItem("Consultar");
        MenuItem miCancelarVentas = new MenuItem("Cancelar");
        
        Menu meVentas = new Menu("Ventas");
        meVentas.getItems().addAll(miCrearVentas, miConsultarVentas, miCancelarVentas);
        
        MenuItem miExportaXLS = new MenuItem("Exportar xls");
        MenuItem miExportaDB = new MenuItem("Exportar DB");
        
        Menu meHtasExport = new Menu("Herramientas de Exportacion");
        meHtasExport.getItems().addAll(miExportaXLS, miExportaDB);
        
        MenuItem miAgregarProducto = new MenuItem("Agregar");
        MenuItem miEliminarProducto = new MenuItem("Eliminar");
        MenuItem miModificarProducto = new MenuItem("Modificar");
        MenuItem miImportarProducto = new MenuItem("Importar");
        
        Menu meListaProductos = new Menu("Productos");
        meListaProductos.getItems().addAll(miAgregarProducto, miEliminarProducto, miModificarProducto, miImportarProducto);
        
        
        MenuBar mbPincipal = new MenuBar(meVentas, meHtasExport, meListaProductos);
        
        //Eventos del Menu
        
        miCrearVentas.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear(); 
            }
            vbAreTrabajo.getChildren().add(pCrearVenta.vistaCrearVenta());
        });       
        miEliminarProducto.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear(); 
            }
            vbAreTrabajo.getChildren().add(PantallaEliminarProducto.vistaEliminarProducto());
        });
        
               miImportarProducto.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear(); 
            }
            vbAreTrabajo.getChildren().add(PantallaImportarProducto.VistaImportarProducto());
        });
        
                miAgregarProducto.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear(); 
            }
            vbAreTrabajo.getChildren().add(PantallaAgregarProducto.vistaNuevoProducto());
        }); 
                
                 miModificarProducto.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear(); 
            }
            vbAreTrabajo.getChildren().add(PantallaModificarProducto.vistaModificarProducto());
        });

        miConsultarVentas.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pConsultarVenta.vistaConsultarVenta());
        });
        
        miCancelarVentas.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pCancelarVenta.vistaCancelarVenta());
            
        });
        
        miExportaXLS.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pExportaXlsVenta.vistaExportarXLS());
            
        });
        miExportaDB.setOnAction((event) -> {
            if(vbAreTrabajo.getChildren().size()>0){
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pExportaDbVenta.vistaExportarDB());
            
        });
        
             
        vbPrincipal.getChildren().addAll(mbPincipal,vbAreTrabajo);
        
        StackPane root = new StackPane();
        root.getChildren().add(vbPrincipal);
        
        Scene scene = new Scene(root, w, h);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
