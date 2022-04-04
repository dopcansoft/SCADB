/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.VENTA;
import DTO.catalogoGasto;
import DTO.gasto;
import DTO.notas_remision;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import scadb.DAO.catalogo_gastosDAO;
import scadb.DAO.gastoDAO;
import scadb.DAO.notas_remisionDAO;
import scadb.DAO.ventaDAO;

/**
 *
 * @author dopcan
 */
public class PantallaModificarGastos {
    gastoDAO gasDAO = new gastoDAO();
    catalogo_gastosDAO catGastoDAO = new catalogo_gastosDAO();
    notas_remisionDAO notasDAO = new notas_remisionDAO();
    notas_remision notRem = new notas_remision();
    ventaDAO ventDAO = new ventaDAO();
    
    ObservableList obList = FXCollections.observableArrayList();
    List<VENTA> lstVentas = new ArrayList<VENTA>();
    
    List<String> lstWhereConcepto = new ArrayList<>();
    ObservableList<String> lstConceptosGastos = FXCollections.observableArrayList();
    
    public VBox vistaModificarGasto(VBox vbAreaTrabajo){
                VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("MODIFICAR GASTO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        if (!lstConceptosGastos.isEmpty()){
            lstConceptosGastos = FXCollections.observableArrayList();
        }
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }        
        //concepto, fecha, monto, flag
        TableView<gasto> tvGastos = new TableView();
        tvGastos.setMinWidth(480);
        tvGastos.setMaxHeight(480);
        tvGastos.setPrefWidth(480);
        
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbConcepto = new RadioButton("Concepto");
        RadioButton rbFecha = new RadioButton("Fecha");
        
        rbFecha.setToggleGroup(tgBusquedas);
        rbConcepto.setToggleGroup(tgBusquedas);
        rbTodos.setToggleGroup(tgBusquedas);        
        rbFecha.setSelected(true);
        
        Label lbFecha = new Label("Por Fecha: ");
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfConcepto = new TextField();
        Label lbConcepto = new Label("Por Concepto: ");
        Label lbCatalogoConcepto = new Label("Catalogo Conceptos: ");
        ComboBox cbCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbCatalogoConcepto.setPrefWidth(140);
        cbCatalogoConcepto.setOnAction((event) -> {
            tfConcepto.setText(cbCatalogoConcepto.getValue().toString());
        });
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarGasto = new Button("Seleccionar");
        btnBuscarGasto.setMaxHeight(50);
        btnBuscarGasto.setOnAction((ActionEvent e)->{
         if (rbTodos.isSelected()){
           lstWhereConcepto.add("id_gasto is not null");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }    
            
         if (rbConcepto.isSelected()){
           lstWhereConcepto.add("concepto = '"+tfConcepto.getText()+"'");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }
         
         if (rbFecha.isSelected()){
           lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
           tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
         }
         
           String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
           lbTituloVista.setText(titulo);
         
        });
        
        VBox vbConcepto = new VBox();
        VBox vbFecha = new VBox();
        VBox vbCatalogoConceptos = new VBox();
        vbConcepto.getChildren().addAll(lbConcepto, tfConcepto);
        vbCatalogoConceptos.getChildren().addAll(lbCatalogoConcepto, cbCatalogoConcepto);
        vbFecha.getChildren().addAll(lbFecha, dpFecha);
        
        
        hbCompSeleccion.getChildren().addAll(vbFecha, vbConcepto, vbCatalogoConceptos, btnBuscarGasto);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbFecha, rbConcepto, rbTodos);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        Label lbIdGastos = new Label("Id Gasto: ");
        Label lbConceptoGasto = new Label("Concepto: ");
        Label lbFechaGasto = new Label("Fecha: ");
        Label lbMontoGasto = new Label("Monto: ");
        
        TextField tfIdGasto = new TextField();
        tfIdGasto.setMaxWidth(120);
        tfIdGasto.setEditable(false);
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        DatePicker dpFechaGasto = new DatePicker();
        dpFechaGasto.setPrefWidth(320);
        TextField tfMontoGasto = new TextField();

        TableColumn<gasto, Integer> idGastoColumna = new TableColumn<>("Id Gasto");
        idGastoColumna.setMinWidth(120);
        idGastoColumna.setCellValueFactory(new PropertyValueFactory<>("id_gasto"));

        TableColumn<gasto, String> conceptoGastoColumna = new TableColumn<>("concepto");
        conceptoGastoColumna.setMinWidth(120);
        conceptoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("concepto"));        
        
        TableColumn<gasto, String> fechaGastoColumna = new TableColumn<>("Fecha");
        fechaGastoColumna.setMinWidth(120);
        fechaGastoColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));        
        
        TableColumn<gasto, Integer> montoGastoColumna = new TableColumn<>("Monto");
        montoGastoColumna.setMinWidth(120);
        montoGastoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));   
        
        tvGastos.getColumns().addAll(idGastoColumna, conceptoGastoColumna, montoGastoColumna, fechaGastoColumna);
        lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"'");
        tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
        String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
        lbTituloVista.setText(titulo);
        
        tvGastos.setOnMouseClicked((event) -> {
            gasto gasTemp = tvGastos.getSelectionModel().getSelectedItem();
            tfIdGasto.setText(String.valueOf(gasTemp.getId_gasto()));
            tfConceptoGasto.setText(gasTemp.getConcepto());
            tfMontoGasto.setText(String.valueOf(gasTemp.getMonto()));
            dpFechaGasto.setValue(LocalDate.parse(gasTemp.getFecha()));
        });
        
        /*lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }*/
        
        ComboBox cbSelecCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbSelecCatalogoConcepto.setPrefWidth(140); 
        cbSelecCatalogoConcepto.setOnAction((event) -> {
            tfConceptoGasto.setText(cbSelecCatalogoConcepto.getValue().toString());
        });   
       
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((ActionEvent e)->{
            gasto gastTemp = new gasto();
            gastTemp.setId_gasto(Integer.parseInt(tfIdGasto.getText()));
            gastTemp.setConcepto(tfConceptoGasto.getText());
            gastTemp.setFecha(dpFechaGasto.getValue().toString());
            gastTemp.setMonto(Float.valueOf(tfMontoGasto.getText()));
            gasDAO.modificarGasto(gastTemp);
            Alert aleMensaje = new Alert(Alert.AlertType.INFORMATION);
            aleMensaje.setContentText("Gasto Actualizado.");
            aleMensaje.setTitle("Informacion");
            aleMensaje.show();
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnModificar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbIdGastos, 0, 0);
        gpDatosGasto.add(tfIdGasto, 1, 0);
        gpDatosGasto.add(lbFechaGasto, 0, 1);
        gpDatosGasto.add(dpFechaGasto, 1, 1);
        gpDatosGasto.add(lbConceptoGasto, 0, 2);
        gpDatosGasto.add(tfConceptoGasto, 1, 2);
        gpDatosGasto.add(cbSelecCatalogoConcepto, 2, 2);
        gpDatosGasto.add(lbMontoGasto, 0, 3);
        gpDatosGasto.add(tfMontoGasto, 1, 3);
        gpDatosGasto.add(hbBotones, 1, 4);
        
        vbPpal.getChildren().addAll(lbTituloVista, hbTipoSeleccion, hbCompSeleccion, tvGastos, gpDatosGasto);
        return vbPpal;     
    }
    
//    private void removerVistas(VBox vbAreaTrabajo){
//       if (vbAreaTrabajo.getChildren().size()>0){
//          vbAreaTrabajo.getChildren().remove(0);
//       }
//    }
    
}
