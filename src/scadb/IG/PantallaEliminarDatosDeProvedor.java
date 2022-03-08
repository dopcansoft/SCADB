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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import scadb.DAO.inventarioDAO;
import scadb.DAO.proveedorDAO;

public class PantallaEliminarDatosDeProvedor {
    
    proveedorDAO provDAO = new proveedorDAO();
    
    ObservableList obList = FXCollections.observableArrayList();

    public VBox vistaEliminarProveedor(VBox vbAreaTrabajo){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("ELIMINAR PROVEEDOR");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        List<PROVEEDOR> lstProv = new ArrayList();
        List<String> lstWhere = new ArrayList();
        lstWhere.add("codigo_prov is not null");
        obList.setAll(provDAO.consultaProveedores(lstWhere));
        TableView tvProveedores = new TableView();
        TableColumn codProvColumna = new TableColumn("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn telColumna = new TableColumn("Telefono");
        telColumna.setMinWidth(220);
        telColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
       
        tvProveedores.getColumns().addAll(codProvColumna, nombreColumna, telColumna);
        tvProveedores.setItems(obList);
        
         Label lbCodigoProveedor = new Label("Codigo Proveedor: ");
         Label lbNombreProveedor = new Label("Nombre : ");
         Label lbTelefonoProveedor = new Label("Telefono: ");
         
         TextField tfCodigoProveedor = new TextField();
         tfCodigoProveedor.setPrefWidth(80);
         tfCodigoProveedor.setMaxWidth(80);
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
         
         Button btnEliminar = new Button("Eliminar");
         btnEliminar.setOnAction((ActionEvent e)->{
           proveedorDAO prvDAO = new proveedorDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              prvDAO.eliminarLogicamenteProveedor(Integer.parseInt(tfCodigoProveedor.getText()));
              removerVistas(vbAreaTrabajo);
           } else {
              removerVistas(vbAreaTrabajo);
           }
         });
         
        tvProveedores.setOnMouseClicked((event) -> {
          //tfNombreProveedor.setText("Seleccionaste: "+ tvProveedores.getSelectionModel().getSelectedIndex());
          PROVEEDOR prvItem = (PROVEEDOR)tvProveedores.getSelectionModel().getSelectedItem();
          tfCodigoProveedor.setText(String.valueOf(prvItem.getCodigo_prov()));
          tfNombreProveedor.setText(prvItem.getNombre());
          tfTelefonoProveedor.setText(prvItem.getTelefono());
         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnEliminar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbCodigoProveedor, 0, 0);
         gpPrincipal.add(tfCodigoProveedor, 1, 0);
         gpPrincipal.add(lbNombreProveedor, 0, 1);
         gpPrincipal.add(tfNombreProveedor, 1, 1);
         gpPrincipal.add(lbTelefonoProveedor, 0, 2);
         gpPrincipal.add(tfTelefonoProveedor, 1, 2);
         gpPrincipal.add(hbBotones, 1, 3);
        
         vbPpal.getChildren().addAll(lbTituloVista, tvProveedores, gpPrincipal);
        return vbPpal;    
    
    }
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
}
