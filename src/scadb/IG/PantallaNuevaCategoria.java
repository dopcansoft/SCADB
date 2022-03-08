/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.categoria;
import DTO.inventario;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class PantallaNuevaCategoria {

    public VBox vistaNuevaCategoria(VBox vbAreaTrabajo) {

        categoriaDAO catDAO = new categoriaDAO();

        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("AGREGAR CATEGORIA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        Label lbNombreCategoria = new Label("Nombre Categoria");
        Label lbIdParentCategoria = new Label("Id Padre Categoria");

        TextField tfNombreCategoria = new TextField();
        TextField tfIdParentCategoria = new TextField();

        TableView tvCategorias = new TableView();
        tvCategorias.setPrefHeight(320);
        tvCategorias.setMaxWidth(480);

        TableColumn<categoria, Integer> idCategoriaColumna = new TableColumn<>("Id Categoria");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));

        TableColumn<categoria, Integer> nombreCategoriaColumna = new TableColumn<>("Nombre Categoria");
        nombreCategoriaColumna.setMinWidth(220);
        nombreCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<categoria, Integer> parentIdCategoriaColumna = new TableColumn<>("Parent Id Categoria");
        parentIdCategoriaColumna.setMinWidth(120);
        parentIdCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("parent_id"));

        tvCategorias.getColumns().addAll(idCategoriaColumna, nombreCategoriaColumna, parentIdCategoriaColumna);

        List<inventario> lstInv = new ArrayList<>();
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("id_categoria is not null");
        tvCategorias.setItems(catDAO.consultarCategoria(lstWhere));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e) -> {
            removerVistas(vbAreaTrabajo);
        });

        Button btnGuardar = new Button("Agregar");
        btnGuardar.setOnAction((ActionEvent e) -> {
            categoria catIdent = new categoria();
            catIdent.setCategoria(tfNombreCategoria.getText().toUpperCase());
            catIdent.setParent_id(Integer.parseInt(tfIdParentCategoria.getText()));
            catDAO.insertarCategoria(catIdent);
            removerVistas(vbAreaTrabajo);
        });

        HBox hbBotones = new HBox();
        hbBotones.setPadding(new Insets(5, 5, 5, 5));
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnCancelar, btnGuardar);

        GridPane gpDatosCAegoria = new GridPane();
        gpDatosCAegoria.setPadding(new Insets(5, 5, 5, 5));
        gpDatosCAegoria.setVgap(5);
        gpDatosCAegoria.setHgap(5);

        gpDatosCAegoria.add(lbNombreCategoria, 0, 1);
        gpDatosCAegoria.add(tfNombreCategoria, 1, 1);
        gpDatosCAegoria.add(lbIdParentCategoria, 0, 2);
        gpDatosCAegoria.add(tfIdParentCategoria, 1, 2);
        gpDatosCAegoria.add(hbBotones, 1, 3);
        gpDatosCAegoria.setAlignment(Pos.CENTER);

        vbPpal.getChildren().addAll(lbTituloVista, gpDatosCAegoria, tvCategorias);

        return vbPpal;
    }

    private void removerVistas(VBox vbAreaTrabajo) {
        if (vbAreaTrabajo.getChildren().size() > 0) {
            vbAreaTrabajo.getChildren().remove(0);
        }
    }
}
