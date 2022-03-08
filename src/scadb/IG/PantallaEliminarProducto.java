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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import scadb.DAO.categoriaDAO;
import scadb.DAO.inventarioDAO;

public class PantallaEliminarProducto{
    
    inventarioDAO invent = new inventarioDAO();
    categoriaDAO categDAO = new categoriaDAO();
    
public VBox vistaEliminarProducto(VBox vbAreaTrabajo){
        VBox vbPpal= new VBox();
        Label lbTituloVista = new Label("ELIMINAR PRODUCTOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        Label lbTablaProducto = new Label("Productos");
        TableView tvProductos = new TableView();
        tvProductos.setMaxHeight(320);
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");
        List<String> lstCategorias = new ArrayList<>();
        lstCategorias.add("");
        List<String> lstWherecat = new ArrayList<>();
        lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        //Componentes de Interfaz--------------------------------------------------------------
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        RadioButton rbTodos = new RadioButton("Todos");
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);        
        rbTodos.setToggleGroup(tgBusquedas); 
        rbTodos.setSelected(true);
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbBusquedaDescripcion = new Label("Por Descripcion: ");
        TextField tfBusquedaDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableList(lstCategorias));
        cbCategoria.setPrefWidth(140);        
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         
         if (rbTodos.isSelected()){
           lstWhere.add("codigo_prod is not null");
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }    
            
         if (rbCodigo.isSelected()){
           lstWhere.add("codigo_prod = "+tfCodigo.getText());
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }
         
         if (rbDescripcion.isSelected()){
           lstWhere.add("descripcion like '%"+tfBusquedaDescripcion.getText()+"%' ");
           tvProductos.setItems(invent.consultarInventario(lstWhere));
         }
         
         if (rbCategoria.isSelected()){
           String strCat = cbCategoria.getValue().toString();
           if (strCat.compareTo("")==0){
                  lstWhere.add("id_categoria = 0");
                  tvProductos.setItems(invent.consultarInventario(lstWhere));
           }else{
              lstWhere.add("id_categoria ='"+categDAO.consultaIdCategoria(strCat)+"' ");
              tvProductos.setItems(invent.consultarInventario(lstWhere));
           }
         }

           String titulo = "ELIMINAR PRODUCTOS ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);
        });
        
        VBox vbCodigo = new VBox();
        VBox vbDesc = new VBox();
        VBox vbCat = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbDesc.getChildren().addAll(lbBusquedaDescripcion, tfBusquedaDescripcion);
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        
        TableColumn<inventario, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<inventario, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<inventario, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<inventario, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<inventario, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<inventario, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<inventario, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<inventario, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<inventario, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        TableColumn<inventario, Integer> idCategoriaColumna = new TableColumn<>("Id Categoria");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
        
        tvProductos.getColumns().addAll(claveProdColumna, existenciaColumna, idUbicacionColumna,
                pMenudeoColumna, pMayoreoColumna, descripcionColumna, uMedidaColumna, 
                cCompraColumna, codProvColumna,idCategoriaColumna);
        
         List<inventario> lstInv = new ArrayList<>();
         lstWhere.add("codigo_prod is not null");
         lstInv=invent.consultaInventario(lstWhere);
         ObservableList obList = FXCollections.observableArrayList(lstInv);
         tvProductos.setItems(obList);
         String titulo = lbTituloVista.getText()+" ("+String.valueOf(obList.size())+")";
         lbTituloVista.setText(titulo);
        
        vbPpal.setAlignment(Pos.CENTER);
        Label lbCodigo_prod = new Label("Codigo Producto: ");
	Label lbExistencia = new Label("Existencias: ");
	Label lbId_ubicacion = new Label("Ubicación: ");
	Label lbPrecio_menudeo = new Label("Precio Menudeo: ");
	Label lbPrecio_mayoreo = new Label("Precio Mayoreo: ");
	Label lbDescripcion = new Label("Descripción: ");
	Label lbUnidad_medida = new Label("Unidad Medida: ");
	Label lbCosto_compra = new Label("Costo Compra");
	Label lbCodigo_prov = new Label("Código Proveedor: ");

        TextField tfCodigo_prod = new TextField();
        tfCodigo_prod.setMaxWidth(80);
        tfCodigo_prod.setEditable(false);
	TextField tfExistencia = new TextField();
        tfExistencia.setMaxWidth(80);
        tfExistencia.setEditable(false);
	TextField tfId_ubicacion = new TextField();
        tfId_ubicacion.setMaxWidth(80);
        tfId_ubicacion.setEditable(false);
	TextField tfPrecio_menudeo = new TextField();
        tfPrecio_menudeo.setMaxWidth(120);
        tfPrecio_menudeo.setEditable(false);
	TextField tfPrecio_mayoreo = new TextField();
        tfPrecio_mayoreo.setMaxWidth(120);
        tfPrecio_mayoreo.setEditable(false);
	TextArea tfDescripcion = new TextArea();
        tfDescripcion.setMaxHeight(40);
        tfDescripcion.setMaxWidth(280);
        tfDescripcion.setEditable(false);
	ComboBox tfUnidad_medida = new ComboBox();
        ObservableList<String> options = FXCollections.observableArrayList( 
           "PZA.",
           "Mts.",
           "Unidad",
           "Kg",
           "grm",
           "PQT."
        );
        tfUnidad_medida.setEditable(false);
        tfUnidad_medida.setMaxWidth(120);
	TextField tfCosto_compra = new TextField();
        tfCosto_compra.setMaxWidth(120);
        tfCosto_compra.setEditable(false);
	TextField tfCodigo_prov = new TextField();
        tfCodigo_prov.setMaxWidth(120);
        tfCodigo_prov.setEditable(false);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });

        tvProductos.setOnMouseClicked((event) -> {
            inventario inv= (inventario) tvProductos.getSelectionModel().getSelectedItem();
            tfDescripcion.setText(inv.getDescripcion());
            tfCodigo_prod.setText(String.valueOf(inv.getCodigo_prod()));
            tfCosto_compra.setText(String.valueOf(inv.getCosto_compra()));
            tfCodigo_prov.setText(String.valueOf(inv.getCodigo_prov()));
            tfExistencia.setText(String.valueOf(inv.getExistencia()));
            tfPrecio_menudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
            tfPrecio_mayoreo.setText(String.valueOf(inv.getPrecio_mayoreo()));
            tfId_ubicacion.setText(String.valueOf(inv.getId_ubicacion()));
            tfUnidad_medida.setValue(inv.getUnidad_medida());
        });
        
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction((ActionEvent e)->{
           inventarioDAO invDAO = new inventarioDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
            invDAO.EliminarLogicamenteProducto(Integer.parseInt(tfCodigo_prod.getText()));
            removerVistas(vbAreaTrabajo);
           } else {
            removerVistas(vbAreaTrabajo);
           }
        });
        
        
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER_RIGHT);
        hbBotones.getChildren().addAll(btnCancelar, btnEliminar);        

        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5,5,5,5));
        gpPpal.setVgap(5);
        gpPpal.setHgap(5);
        
        gpPpal.add(lbCodigo_prod, 0, 0);
        gpPpal.add(tfCodigo_prod, 1, 0);
        gpPpal.add(lbDescripcion, 2, 0);
        gpPpal.add(tfDescripcion, 3, 0);
        gpPpal.add(lbCosto_compra, 0, 1);
        gpPpal.add(tfCosto_compra, 1, 1);
        gpPpal.add(lbCodigo_prov, 2, 1);
        gpPpal.add(tfCodigo_prov, 3, 1);
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
        vbPpal.getChildren().addAll(lbTituloVista, hbTipoSeleccion, hbCompSeleccion, tvProductos, gpPpal);
        return vbPpal;
    
    }
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
    
    }
