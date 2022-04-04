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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import scadb.DAO.catalogo_gastosDAO;
import scadb.DAO.gastoDAO;
import scadb.DAO.notas_remisionDAO;
import scadb.DAO.ventaDAO;
import scadb.SCADB;

/**
 *
 * @author dopcan
 */
public class PantallaConsultarGastos {
    gastoDAO gasDAO = new gastoDAO();
    catalogo_gastosDAO catGastoDAO = new catalogo_gastosDAO();
    notas_remisionDAO notasDAO = new notas_remisionDAO();
    notas_remision notRem = new notas_remision();
    ventaDAO ventDAO = new ventaDAO();
    
    ObservableList obList = FXCollections.observableArrayList();
    List<VENTA> lstVentas = new ArrayList<VENTA>();
    
    List<String> lstWhereConcepto = new ArrayList<>();
    ObservableList<String> lstConceptosGastos = FXCollections.observableArrayList();
    
    public VBox vistaConsultarGasto(VBox vbAreaTrabajo){
          VBox vbPpal = new VBox();
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("CONSULTAR GASTOS");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
         if (!lstConceptosGastos.isEmpty()){
            lstConceptosGastos = FXCollections.observableArrayList();
        }       
        lstWhereConcepto.add("id_catalogo_gastos is not null");
        for(catalogoGasto lstCatGast: catGastoDAO.consultarCatGastos(lstWhereConcepto)){
               lstConceptosGastos.add(lstCatGast.getConcepto());
        }
        
        //id_gasto
        //concepto
	//fecha
	//monto
	//flag
        TableView<gasto> tvGastos = new TableView();
        tvGastos.setMaxHeight(520);
        
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
        lstWhereConcepto.add("fecha = '"+dpFecha.getValue().toString()+"' ");
        tvGastos.setItems(gasDAO.consultarGasto(lstWhereConcepto));
        String titulo = "MODIFICAR GASTO ("+String.valueOf(tvGastos.getItems().size())+" Seleccionados)";
        lbTituloVista.setText(titulo);
        
        Label lbIdGastos = new Label("Id Gasto: ");
        Label lbConceptoGasto = new Label("Concepto: ");
        Label lbFechaGasto = new Label("Fecha: ");
        Label lbMontoGasto = new Label("Monto: ");
        
        TextField tfIdGasto = new TextField();
        tfIdGasto.setMaxWidth(120);
        TextField tfConceptoGasto = new TextField();
        tfConceptoGasto.setPrefWidth(320);
        DatePicker dpFechaGasto = new DatePicker();
        dpFechaGasto.setPrefWidth(320);
        TextField tfMontoGasto = new TextField();

         tvGastos.setOnMouseClicked((event) -> {
            gasto gasTemp = tvGastos.getSelectionModel().getSelectedItem();
            tfIdGasto.setText(String.valueOf(gasTemp.getId_gasto()));
            tfConceptoGasto.setText(gasTemp.getConcepto());
            tfMontoGasto.setText(String.valueOf(gasTemp.getMonto()));
            dpFechaGasto.setValue(LocalDate.parse(gasTemp.getFecha()));
        });
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            if (vbAreaTrabajo.getChildren().size()>0){
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        Button btnPDF = new Button("Reporte PDF");
        btnPDF.setOnAction((ActionEvent e)->{
         try{
            
           /* User home directory location */
            //String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteGastos.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteGastos"+fechaFile+".pdf";
            String outputFile = "Reportes/Gastos/" + File.separatorChar + "ReporteGastos"+fechaFile+".pdf";
           //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvGastos.getItems());
           System.out.println("Hay "+tvGastos.getItems().size());
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemsDataSource", itemsJRBean);
           //parameters.put("Sucursal", "Veracruz");
           //parameters.put("Fecha", LocalDate.now().toString());
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            OutputStream outputStream = new FileOutputStream(new File(outputFile));
            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.close();
              Alert resp = new Alert(Alert.AlertType.INFORMATION);
              resp.setTitle("Informacion");
              resp.setContentText("Reporte Generado carpeta Reportes/Gastos!! ");
              resp.showAndWait();
            
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
                 Logger.getLogger(SCADB.class.getName()).log(Level.SEVERE, null, ex);
             }            
        });

        Button btnOtrosFmto = new Button("Otros Formatos");
        btnOtrosFmto.setOnAction((ActionEvent e)->{
          try{
            
           /* User home directory location */
            String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteGastos.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
           //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvGastos.getItems());
           System.out.println("Hay "+tvGastos.getItems().size());
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemsDataSource", itemsJRBean);
           //parameters.put("Sucursal", "Veracruz");
           //parameters.put("Fecha", LocalDate.now().toString());
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JRViewer jrViewer;
            JPanel jpanel;
            SwingNode swingNode;
            jpanel = new JPanel();
            swingNode = new SwingNode();
            jrViewer = new JRViewer(jasperPrint);
            jrViewer.setBounds(0, 0, 1200, 800);
            jpanel.setLayout(null);
            jpanel.add(jrViewer);
            jpanel.setSize(1200, 800);
            Pane panePreview = new Pane(); 
            panePreview.setPrefSize(1200, 800);
            panePreview.getChildren().add(swingNode);
            swingNode.setContent(jpanel);


            StackPane rootSelectClientes = new StackPane();
            rootSelectClientes.getChildren().addAll(swingNode);
       
            Scene scene = new Scene(rootSelectClientes,1200,800);
            Stage stgPpal = new Stage();
            stgPpal.setScene(scene);
            stgPpal.initModality(Modality.WINDOW_MODAL);
            stgPpal.show();  
        } catch (JRException ex) {
            ex.printStackTrace();
        }           
        });
        
        HBox hbBotones = new HBox(btnCancelar, btnPDF, btnOtrosFmto);
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
