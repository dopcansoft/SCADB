/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author dopcan
 */
public class pantallaConsultarVenta {
    
    Label lbTituloPantalla = new Label("CONSULTAR VENTAS");    
    
    private VBox vbPrincipal = new VBox();
    private GridPane gpDatos =  new GridPane();
    private HBox hbBotones = new HBox();
    
    private Label lbNombre = new Label("Nombre");
    private Label lbDireccion = new Label("Direccion");
    
    private TextField tfNombre = new TextField();
    private TextField tfDireccion = new TextField();
    
    private Button btnGuardar = new Button("Guardar");
    private Button btnSalir = new Button("Salir");
    
    public VBox vistaConsultarVenta(){
        Font fuente = new Font("Arial Bold", 36);
        lbTituloPantalla.setFont(fuente);
                
        
        gpDatos.add(lbNombre, 0, 0);
        gpDatos.add(tfNombre, 1, 0);
        gpDatos.add(lbDireccion, 0, 1);
        gpDatos.add(tfDireccion, 1, 1);
        gpDatos.setPadding(new Insets(15));
        gpDatos.setVgap(10);
        gpDatos.setHgap(10);
        
        
        hbBotones.getChildren().addAll(btnGuardar, btnSalir);
        hbBotones.setSpacing(20);
        hbBotones.setPadding(new Insets(10));
        
        vbPrincipal.getChildren().addAll(lbTituloPantalla, gpDatos, hbBotones);
        vbPrincipal.setAlignment(Pos.CENTER);
        
       return vbPrincipal; 
    }
    
}
