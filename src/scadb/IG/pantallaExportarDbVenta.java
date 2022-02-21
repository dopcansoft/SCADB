/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author dopcan
 */
public class pantallaExportarDbVenta {
    
    VBox vbPrincipal = new VBox();
    HBox hbFecha = new HBox();
    HBox hbBotones = new HBox();
    
    DatePicker dtFecha = new DatePicker();
    
    Label lbFecha = new Label("Fecha");
    Label lbTituloPantalla = new Label("EXPORTAR DB");   
    
    Button btntExportarDB = new Button("Exportar DB");
    Button btntSalir = new Button("Salir");
    Button btntConsultar = new Button("Consultar");
    
    TableView tvProductos = new TableView();
    
    
    public VBox vistaExportarDB(){
        Font fuente = new Font("Arial Bold", 36);
        lbTituloPantalla.setFont(fuente);


        hbBotones.setHgrow(hbFecha, Priority.ALWAYS);
        hbBotones.setAlignment(Pos.CENTER.CENTER_RIGHT);
        hbBotones.setSpacing(30);
        
        hbFecha.getChildren().addAll(lbFecha, dtFecha, btntConsultar);
        hbFecha.setSpacing(30);
        
        hbBotones.getChildren().addAll(btntSalir, btntExportarDB);
        
        vbPrincipal.getChildren().addAll(lbTituloPantalla, hbFecha, tvProductos, hbBotones);
        vbPrincipal.setSpacing(30);
        
       return vbPrincipal; 
    }
    
}
