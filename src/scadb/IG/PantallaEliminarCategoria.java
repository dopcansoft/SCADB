/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.categoria;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import scadb.DAO.categoriaDAO;

public class PantallaEliminarCategoria {
    
     ObservableList obList = FXCollections.observableArrayList();
     categoriaDAO categDAO = new categoriaDAO();
        categoria catIdent = new categoria();

  public VBox vistaEliminarCategoria(VBox vbAreaTrabajo){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("ELIMINAR CATEGORIA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        List<categoria> lstCategoria = new ArrayList();
        List<String> lstWhere = new ArrayList();
        lstWhere.add("id_categoria is not null");
        obList.setAll(categDAO.consultarCategoria(lstWhere));
        TableView tvCategorias = new TableView();

        TableColumn idCategoriaColumna = new TableColumn("Id Categoria");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        
        TableColumn categoriaColumna = new TableColumn("Categoria");
        categoriaColumna.setMinWidth(320);
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn parenIdColumna = new TableColumn("Id Categoria Padre");
        parenIdColumna.setMinWidth(220);
        parenIdColumna.setCellValueFactory(new PropertyValueFactory<>("parent_id"));
       
        tvCategorias.getColumns().addAll(idCategoriaColumna, categoriaColumna, parenIdColumna);
        tvCategorias.setItems(obList);
        
         Label lbIdCAtegoria = new Label("Id Categoria: ");
         Label lbCategoria = new Label("Categoria : ");
         Label lbIdCategoriaPadre = new Label("Id Categoria Padre: ");
         
         TextField tfIdCategoria = new TextField();
           tfIdCategoria.setPrefWidth(80);
           tfIdCategoria.setMaxWidth(80);
           tfIdCategoria.setEditable(false);
         TextField tfCategoria = new TextField();
           tfCategoria.setPrefWidth(200);
           tfCategoria.setEditable(false);
         TextField tfIdCategoriaPadre = new TextField();
           tfIdCategoriaPadre.setMaxWidth(80);
           tfIdCategoriaPadre.setEditable(false);
         
         Button btnCancelar = new Button("Cancelar");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnModificar = new Button("Eliminar");
         btnModificar.setOnAction((ActionEvent e)->{
           catIdent.setId_categoria(Integer.parseInt(tfIdCategoria.getText()));
           catIdent.setCategoria(tfCategoria.getText().toUpperCase());
           catIdent.setParent_id(Integer.parseInt(tfIdCategoriaPadre.getText()));
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              categDAO.borrarCategoria(catIdent.getId_categoria());
              removerVistas(vbAreaTrabajo);
           } else {
              removerVistas(vbAreaTrabajo);
           }            
            
         });
         
        tvCategorias.setOnMouseClicked((event) -> {
          //tfNombreProveedor.setText("Seleccionaste: "+ tvProveedores.getSelectionModel().getSelectedIndex());
          categoria catIde = (categoria)tvCategorias.getSelectionModel().getSelectedItem();
          tfIdCategoria.setText(String.valueOf(catIde.getId_categoria()));
          tfCategoria.setText(catIde.getCategoria());
          tfIdCategoriaPadre.setText(String.valueOf(catIde.getParent_id()));
         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnModificar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setPadding(new Insets(5, 5, 5, 5));
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(lbIdCAtegoria, 0, 0);
         gpPrincipal.add(tfIdCategoria, 1, 0);
         gpPrincipal.add(lbCategoria, 0, 1);
         gpPrincipal.add(tfCategoria, 1, 1);
         gpPrincipal.add(lbIdCategoriaPadre, 0, 2);
         gpPrincipal.add(tfIdCategoriaPadre, 1, 2);
         gpPrincipal.add(hbBotones, 1, 3);
        
         vbPpal.getChildren().addAll(lbTituloVista, tvCategorias, gpPrincipal);
        return vbPpal;

    }
 

    private void removerVistas(VBox vbAreaTrabajo) {
        if (vbAreaTrabajo.getChildren().size() > 0) {
            vbAreaTrabajo.getChildren().remove(0);
        }
    }
}
