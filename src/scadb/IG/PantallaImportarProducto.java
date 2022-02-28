/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PantallaImportarProducto{


    
    
    public static VBox VistaImportarProducto(){
        
    VBox vbPrincipal = new VBox();
    HBox hbFecha = new HBox();
    HBox hbBotones = new HBox();
    
    DatePicker dtFecha = new DatePicker();
    
    Label lbFecha = new Label("Fecha");
    Label lbTituloPantalla = new Label("Importar Producto");
    
    Button btntExportarDB = new Button("Importar Producto");
    Button btntSalir = new Button("Salir");
    Button btntConsultar = new Button("Consultar");
    
    TableView tvProductos = new TableView();
    
    
        Font fuente = new Font("Arial Bold", 36);
        lbTituloPantalla.setFont(fuente);
        
        vbPrincipal.setSpacing(10);
        vbPrincipal.setAlignment(Pos.CENTER);
        
        tvProductos.setPadding(new Insets(5,5,5,5));
        tvProductos.setPrefSize(800, 400);
        tvProductos.setMinSize(800, 400);
        tvProductos.setMaxSize(800, 400);
        

        hbBotones.setHgrow(hbFecha, Priority.ALWAYS);
        hbBotones.setAlignment(Pos.CENTER.CENTER_RIGHT);
        hbBotones.setSpacing(30);
        hbBotones.setPrefWidth(800);
        hbBotones.setMinWidth(800);
        hbBotones.setMaxWidth(800);
        
        hbFecha.getChildren().addAll(lbFecha, dtFecha, btntConsultar);
        hbFecha.setSpacing(30);
        hbFecha.setPrefWidth(800);
        hbFecha.setMaxWidth(800);
        hbFecha.setMinWidth(800);
        
        hbBotones.getChildren().addAll(btntSalir, btntExportarDB);
        
        vbPrincipal.getChildren().addAll(lbTituloPantalla, hbFecha, tvProductos, hbBotones);
        vbPrincipal.setSpacing(30);
        
       return vbPrincipal; 
    }
   
}