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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PantallaAgregarProducto{
    public static VBox vistaNuevoProducto(VBox areaTrabajo){
        List<String> lstProveedores = new ArrayList<>();
        
        VBox vbPpal= new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        
        Label lbTituloVista = new Label("AGREGAR PRODUCTO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
	Label lbExistencia = new Label("Existencias: ");
	Label lbId_ubicacion = new Label("Ubicación: ");
	Label lbPrecio_menudeo = new Label("Precio Menudeo: ");
	Label lbPrecio_mayoreo = new Label("Precio Mayoreo: ");
	Label lbDescripcion = new Label("Descripción: ");
	Label lbUnidad_medida = new Label("Unidad Medida: ");
	Label lbCosto_compra = new Label("Costo Compra");
	Label lbCodigo_prov = new Label("Código Proveedor: ");

	TextField tfExistencia = new TextField();
        tfExistencia.setMaxWidth(80);
	TextField tfId_ubicacion = new TextField();
        tfId_ubicacion.setMaxWidth(80);
	TextField tfPrecio_menudeo = new TextField();
        tfPrecio_menudeo.setMaxWidth(120);
	TextField tfPrecio_mayoreo = new TextField();
        tfPrecio_mayoreo.setMaxWidth(120);
	TextArea tfDescripcion = new TextArea();
        tfDescripcion.setMaxHeight(40);
        tfDescripcion.setMaxWidth(280);
        ComboBox cbProveedores = new ComboBox(FXCollections.observableArrayList(lstProveedores));        

        ComboBox tfUnidad_medida = new ComboBox();
        ObservableList<String> Options = FXCollections.observableArrayList(
           "PZA.",
           "Mts.",
           "Unidad",
           "Kg",
           "grm",
           "PQT."
        );
        tfUnidad_medida.setItems(Options);
        
        tfUnidad_medida.setMaxWidth(120);
	TextField tfCosto_compra = new TextField();
        tfCosto_compra.setMaxWidth(120);
	TextField tfCodigo_prov = new TextField();
        tfCodigo_prov.setMaxWidth(120);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((event) -> {
            areaTrabajo.getChildren().clear();
        });
       
        Button btnGuardar = new Button("Guardar");

        
        HBox hbProveedores = new HBox();
        hbProveedores.setSpacing(10);
        hbProveedores.getChildren().addAll(tfCodigo_prov, cbProveedores);
        
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER_RIGHT);
        hbBotones.getChildren().addAll(btnCancelar, btnGuardar); 
        

        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5,5,5,5));
        gpPpal.setVgap(5);
        gpPpal.setHgap(5);
        
        gpPpal.add(lbDescripcion, 2, 0);
        gpPpal.add(tfDescripcion, 3, 0);
        gpPpal.add(lbCosto_compra, 0, 1);
        gpPpal.add(tfCosto_compra, 1, 1);
        gpPpal.add(lbCodigo_prov, 2, 1);
        gpPpal.add(hbProveedores, 3, 1);
        gpPpal.add(lbUnidad_medida, 0, 2);
        gpPpal.add(tfUnidad_medida, 1, 2);
        gpPpal.add(lbExistencia, 2, 2);
        gpPpal.add(tfExistencia, 3, 2);       
        gpPpal.add(lbPrecio_menudeo, 0, 3);
        gpPpal.add(tfPrecio_menudeo, 1, 3);
        gpPpal.add(lbPrecio_mayoreo, 2, 3);
        gpPpal.add(tfPrecio_mayoreo, 3, 3);
        gpPpal.add(lbId_ubicacion, 0, 4);
        gpPpal.add(tfId_ubicacion, 1, 4);
        gpPpal.add(hbBotones, 3, 4);
        gpPpal.setAlignment(Pos.CENTER);
        vbPpal.getChildren().addAll(lbTituloVista, gpPpal);
        return vbPpal;
    
    }
}