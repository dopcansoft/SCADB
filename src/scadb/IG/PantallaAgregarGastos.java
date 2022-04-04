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
import javafx.scene.control.TextField;
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
public class PantallaAgregarGastos {
    gastoDAO gasDAO = new gastoDAO();
    catalogo_gastosDAO catGastoDAO = new catalogo_gastosDAO();
    notas_remisionDAO notasDAO = new notas_remisionDAO();
    notas_remision notRem = new notas_remision();
    ventaDAO ventDAO = new ventaDAO();
    
    ObservableList obList = FXCollections.observableArrayList();
    List<VENTA> lstVentas = new ArrayList<VENTA>();
    
    List<String> lstWhereConcepto = new ArrayList<>();
    ObservableList<String> lstConceptosGastos = FXCollections.observableArrayList();
    
    public VBox vistaAgregarGasto(VBox vbAreaTrabajo){
        VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("GREGAR GASTO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }
        
        Label lbConceptoGasto = new Label("Concepto: ");
        Label lbFechaGasto = new Label("Fecha: ");
        Label lbMontoGasto = new Label("Monto: ");
        
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        DatePicker dpFechaGasto = new DatePicker();
        dpFechaGasto.setPrefWidth(320);
        TextField tfMontoGasto = new TextField();
         ComboBox cbCatalogoConcepto = new ComboBox(lstConceptosGastos);//FXCollections.observableList(lstCategorias));
        cbCatalogoConcepto.setPrefWidth(140); 
        cbCatalogoConcepto.setOnAction((event) -> {
            tfConceptoGasto.setText(cbCatalogoConcepto.getValue().toString());
        });   
       
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((ActionEvent e)->{
            gasto gasIdent = new gasto();
            gasIdent.setConcepto(tfConceptoGasto.getText());
            gasIdent.setFecha(dpFechaGasto.getValue().toString());
            gasIdent.setFlag(1);
            gasIdent.setMonto(Float.parseFloat(tfMontoGasto.getText()));
            int resultado = gasDAO.insertarGasto(gasIdent);
            if (resultado == 1){
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setHeaderText("Informacion");
              alert.setTitle("Informacion");
              alert.setContentText("Gasto Resgistrado.");
              alert.show();
              removerVistas(vbAreaTrabajo);
            }
            else{
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setHeaderText("Informacion");
              alert.setTitle("Informacion");
              alert.setContentText("Gasto No se Resgistro.");
            }
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnGuardar);
        hbBotones.setSpacing(5);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(10);
        gpDatosGasto.setHgap(10);
        gpDatosGasto.add(lbFechaGasto, 0, 0);
        gpDatosGasto.add(dpFechaGasto, 1, 0);
        gpDatosGasto.add(lbConceptoGasto, 0, 1);
        gpDatosGasto.add(tfConceptoGasto, 1, 1);
        gpDatosGasto.add(cbCatalogoConcepto, 2, 1);
        gpDatosGasto.add(lbMontoGasto, 0, 2);
        gpDatosGasto.add(tfMontoGasto, 1, 2);
        gpDatosGasto.add(hbBotones, 1, 3);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpDatosGasto);
        return vbPpal;    
    }
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
    
}
