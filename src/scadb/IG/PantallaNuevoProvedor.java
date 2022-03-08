/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.PROVEEDOR;
import DTO.detalle_venta;
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
import scadb.DAO.inventarioDAO;
import scadb.DAO.proveedorDAO;

public class PantallaNuevoProvedor {

    public VBox vistaNuevoProveedor(VBox vbAreaTrabajo){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("AGREGAR PROVEEDOR");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        proveedorDAO prvDAO = new proveedorDAO();
        
         Label lbNombreProveedor = new Label("Nombre : ");
         Label lbTelefonoProveedor = new Label("Telefono: ");
         
         
         TextField tfNombreProveedor = new TextField();
         tfNombreProveedor.setPrefWidth(320);
         TextField tfTelefonoProveedor = new TextField();
         tfTelefonoProveedor.setMaxWidth(200);
         
         Button btnCancelar = new Button("Cancelar");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnGuardar = new Button("Guardar");
         btnGuardar.setOnAction((ActionEvent e)->{
             prvDAO.insertarProveedor(tfNombreProveedor.getText(), tfTelefonoProveedor.getText());
             removerVistas(vbAreaTrabajo); //RvbAreaTrabajo.getChildren().remove(0);
         });
         
         HBox hbBotones = new HBox(btnCancelar, btnGuardar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbNombreProveedor, 0, 1);
         gpPrincipal.add(tfNombreProveedor, 1, 1);
         gpPrincipal.add(lbTelefonoProveedor, 0, 2);
         gpPrincipal.add(tfTelefonoProveedor, 1, 2);
         gpPrincipal.add(hbBotones, 1, 3);
         
         vbPpal.getChildren().addAll(lbTituloVista, gpPrincipal);
        return vbPpal;
    }
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
}
