/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.SUCURSAL;
import DTO.categoria;
import DTO.inventario;
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
import scadb.DAO.categoriaDAO;
import scadb.DAO.inventarioDAO;
import scadb.DAO.sucursalDAO;

/**
 *
 * @author dopcan
 */
public class pantallaReporteInventario {
    
    public VBox vistaReporteInventario(VBox vbAreaTrabajo){
        categoriaDAO categDAO = new categoriaDAO();
        inventarioDAO invent = new inventarioDAO();
        sucursalDAO sucDAO = new sucursalDAO();
        
        VBox vbPpal= new VBox();
        int totalProductos = 0;
        Label lbTituloVista = new Label("REPORTES DE INVENTARIO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        Label lbTablaProducto = new Label("Productos");

        TableView<inventario> tvProductos = new TableView();
        
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
        ComboBox cbCategoria = new ComboBox();
        lstCategorias.add("");
        cbCategoria.getItems().addAll(FXCollections.observableList(lstCategorias));
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

           String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
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

        tvProductos.setMaxHeight(320);

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
        
        TableColumn<inventario, Integer> idCategoriaColumna = new TableColumn<>("Codigo Proveedor");
        idCategoriaColumna.setMinWidth(120);
        idCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        tvProductos.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna, 
                idUbicacionColumna, pMenudeoColumna, pMayoreoColumna,  uMedidaColumna, 
                cCompraColumna, codProvColumna);
        

         tvProductos.setItems(invent.consultarInventario(lstWhere));
         String titulo = lbTituloVista.getText()+" ("+String.valueOf(tvProductos.getItems().size())+" Seleccionados)";
         lbTituloVista.setText(titulo);
        
        Label lbCodigo_prod = new Label("Codigo Producto: ");
	Label lbExistencia = new Label("Existencias: ");
	Label lbId_ubicacion = new Label("Ubicación: ");
	Label lbPrecio_menudeo = new Label("Precio Menudeo: ");
	Label lbPrecio_mayoreo = new Label("Precio Mayoreo: ");
	Label lbDescripcion = new Label("Descripción: ");
	Label lbUnidad_medida = new Label("Unidad Medida: ");
	Label lbCosto_compra = new Label("Costo Compra");
	Label lbCodigo_prov = new Label("Código Proveedor: ");
	Label lbCategoriaMod = new Label("Categoria: ");

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
        ObservableList<String> options = FXCollections.observableArrayList(
        "PZA.", "Mts.", "Unidad",  "Kg", "grm", "PQT."
        );
	ComboBox tfUnidad_medida = new ComboBox(options);
        ComboBox cbCategoriaMod = new ComboBox(FXCollections.observableList(lstCategorias));
        cbCategoriaMod.setPrefWidth(140);
        cbCategoria.setEditable(false);
        
        tfUnidad_medida.setMaxWidth(120);
	TextField tfCosto_compra = new TextField();
        tfCosto_compra.setMaxWidth(120);
        tfCosto_compra.setEditable(false);
	TextField tfCodigo_prov = new TextField();
        tfCodigo_prov.setMaxWidth(120);
        tfCodigo_prov.setEditable(false);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent event) -> {
            vbAreaTrabajo.getChildren().remove(0);
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
           lstWherecat.add("id_categoria is not null"); 
           
           for (categoria i : categDAO.consultarCategoria(lstWherecat)){
             Integer idCategoria =  i.getId_categoria();
             
             if (idCategoria.compareTo(inv.getId_categoria()) == 0){
                  int optionT = cbCategoriaMod.getItems().size();
                  for (int ii =0; ii<optionT; ii++){
                      String categoriaA = i.getCategoria();
                     if (categoriaA.compareTo(cbCategoriaMod.getItems().get(ii).toString())==0){
                         cbCategoriaMod.getSelectionModel().select(ii);
                     }
                  }
             }
           }
        });
        Button btnReportePdf = new Button("Generar Reporte PDF");
        btnReportePdf.setOnAction((ActionEvent e)->{
         try{
            
           /* User home directory location */
           // String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteInventario.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
            String outputFile = "Reportes/Inventario/" + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
           //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems());
           System.out.println("Hay "+tvProductos.getItems().size());
            List<String> listWhere = new ArrayList<>();
            listWhere.add("id_suc = 1");
            List<SUCURSAL> sucList = sucDAO.consultaSucursal(listWhere);
            SUCURSAL sucIdent = sucList.get(0);
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemDataSource", itemsJRBean);
           parameters.put("Sucursal", sucIdent.getNombre());
           parameters.put("Fecha", LocalDate.now().toString());
           
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
              resp.setContentText("Reporte Generado en Carpeta Reportes/Inventario!! ");
              resp.showAndWait();
            
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
                 Logger.getLogger(pantallaReporteInventario.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
        
        Button btnReporteOtrosFormatos = new Button("Reporte Otros Formatos..");
        btnReporteOtrosFormatos.setOnAction((ActionEvent e)->{
         try{
            
           /* User home directory location */
            String userHomeDirectory = System.getProperty("user.home");
            /* Output file location */
            
            File file;
            JasperReport jasperReport;
            file = new File("Reportes/Formatos/reporteInventario.jasper");
            jasperReport = (JasperReport) JRLoader.loadObject(file);
            LocalDateTime ld = LocalDateTime.now();
            String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
            //String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
            //String outputFile = "Reportes/Inventario/" + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
            //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
           JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems());
           System.out.println("Hay "+tvProductos.getItems().size());
            List<String> listWhere = new ArrayList<>();
            listWhere.add("id_suc = 1");
            List<SUCURSAL> sucList = sucDAO.consultaSucursal(listWhere);
            SUCURSAL sucIdent = sucList.get(0);
           Map<String, Object> parameters = new HashMap<>();
           parameters.put("ItemDataSource", itemsJRBean);
           parameters.put("Sucursal", sucIdent.getNombre());
           parameters.put("Fecha", LocalDate.now().toString());
           /* Generando el PDF */
            //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JRViewer jrViewer;
            JPanel jpanel;
            SwingNode swingNode;
            jpanel = new JPanel();
            swingNode = new SwingNode();
            jrViewer = new JRViewer(jasperPrint);
            jrViewer.setBounds(0, 0, 1100, 700);
            jpanel.setLayout(null);
            jpanel.add(jrViewer);
            jpanel.setSize(1100, 700);
            Pane panePreview = new Pane(); 
            panePreview.setPrefSize(1100, 700);
            panePreview.getChildren().add(swingNode);
            swingNode.setContent(jpanel);


            StackPane rootSelectClientes = new StackPane();
            rootSelectClientes.getChildren().addAll(swingNode);
       
            Scene scene = new Scene(rootSelectClientes,1100,700);
            Stage stgPpal = new Stage();
            stgPpal.setScene(scene);
            stgPpal.initModality(Modality.WINDOW_MODAL);
            stgPpal.show();  
        } catch (JRException ex) {
            ex.printStackTrace();
        } 

        });       
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER_RIGHT);
        hbBotones.setSpacing(10);
        hbBotones.getChildren().addAll(btnCancelar, btnReportePdf, btnReporteOtrosFormatos);        

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
        gpPpal.add(lbCategoriaMod, 2, 4);
        gpPpal.add(cbCategoriaMod, 3, 4);
        gpPpal.add(hbBotones, 3, 5);
        vbPpal.getChildren().addAll(lbTituloVista, lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, tvProductos, gpPpal);
        return vbPpal;
    }
    
}
