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
import scadb.IG.pantallaCrearVenta;

/**
 *
 * @author dopcan
 */
public class SCADB extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // pantallas
        
        pantallaCrearVenta pCrearVenta = new pantallaCrearVenta();
        
        
        
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
            vbAreTrabajo.getChildren().add(pCrearVenta.mostrarVentanaCrearVenta());
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
