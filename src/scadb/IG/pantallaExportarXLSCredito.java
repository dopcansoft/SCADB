/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.pagos_credito;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import scadb.DAO.pagos_creditoDAO;

/**
 *
 * @author dopcan
 */
//vistaExportarGastoCredito
public class pantallaExportarXLSCredito {
 
    pagos_creditoDAO pagCreditoDAO = new pagos_creditoDAO();
    
    ObservableList obList = FXCollections.observableArrayList();
    
    public VBox vistaExportarCredito(VBox vbAreaTrabajo){
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("EXPORTAR CREDITO A EXCEL");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Componentes de seleccion
        
        Label lbBuscarPor = new Label("Buscar por:");
        Label lbBuscarPorFecha = new Label("fecha:");  
        Label lbBuscarPorTipo = new Label("Tipo venta:");  
        
        RadioButton rbPorFecha = new RadioButton("Buscar por fechas");
        RadioButton rbPorTipoRemision = new RadioButton("Buscar por Tipo Remision");
        ToggleGroup tgBuscarPor = new ToggleGroup();
        rbPorFecha.setToggleGroup(tgBuscarPor);
        rbPorTipoRemision.setToggleGroup(tgBuscarPor);
        rbPorFecha.setSelected(true);
        
        DatePicker dpFechas = new DatePicker(LocalDate.now());
        TextField tfTipoVenta = new TextField();
        
        Button btnBuscar = new Button("Seleccionar");
        
        GridPane gpCompSeleccion = new GridPane();
        gpCompSeleccion.setVgap(10);
        gpCompSeleccion.setHgap(10);
        gpCompSeleccion.add(lbBuscarPor, 0, 0);
        gpCompSeleccion.add(rbPorFecha, 1, 0);
        gpCompSeleccion.add(rbPorTipoRemision, 2, 0);
        gpCompSeleccion.add(lbBuscarPorFecha, 0, 1);
        gpCompSeleccion.add(dpFechas, 1, 1);
        gpCompSeleccion.add(lbBuscarPorTipo, 0, 2);
        gpCompSeleccion.add(tfTipoVenta, 1, 2);
        gpCompSeleccion.add(btnBuscar, 2, 2);
        
        List<pagos_credito> lstPagosCredito = new ArrayList();
        List<String> lstWhere = new ArrayList();
        //lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
        //lstVentas.addAll(ventDAO.consultaVenta(lstWhere));
        //obList.setAll(notasDAO.consultarNotasRem(lstWhere));
        TableView tvCredito = new TableView();
        tvCredito.setPrefWidth(780);
        tvCredito.setMinWidth(780);
        tvCredito.setMaxWidth(780);
        //id_nota_rem, folio,fecha, tipo_operacion ,monto,flag
        TableColumn idCreditoColumna = new TableColumn("Id Credito");
        idCreditoColumna.setMinWidth(120);
        idCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("id_credito"));
        
        TableColumn FechaColumna = new TableColumn("Fecha");
        FechaColumna.setMinWidth(120);
        FechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn folioColumna = new TableColumn("Folio Remision");
        folioColumna.setMinWidth(180);
        folioColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));

        TableColumn montoColumna = new TableColumn("Monto");
        montoColumna.setMinWidth(180);
        montoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));

       
        tvCredito.getColumns().addAll(folioColumna, idCreditoColumna, FechaColumna, montoColumna);
        //tvNotasRemision.setItems(obList);
        
        btnBuscar.setOnAction((event) -> {
            if (rbPorFecha.isSelected()){
                if(!obList.isEmpty()) obList.clear();
                
                lstWhere.clear();
                lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
                obList.setAll(pagCreditoDAO.consultaPagoCred(lstWhere));
                tvCredito.setItems(obList);
            }
            if (rbPorTipoRemision.isSelected()){
                if(!obList.isEmpty()) obList.clear();
                lstWhere.clear();
                lstWhere.add("tipo_operacion LIKE '%"+tfTipoVenta.getText()+"%' ");
                obList.setAll(pagCreditoDAO.consultaPagoCred(lstWhere));
                tvCredito.setItems(obList);
            }
        });
        
         Button btnCancelar = new Button("Salir");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnExportar = new Button("Exportar");
         btnExportar.setOnAction((ActionEvent e) -> {
             HSSFWorkbook workbook = new HSSFWorkbook();
             HSSFSheet sheetCredito= workbook.createSheet();
             
             workbook.setSheetName(0, "Credito");
             
             String[] headersCredito = new String[]{
                 "Folio Remision",
                 "id Credito",
                 "Fecha",
                 "monto"
             };
             
             
             HSSFCellStyle headerStyle = workbook.createCellStyle();
             HSSFFont font = workbook.createFont();
             font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
             font.setColor(IndexedColors.BLACK.getIndex());
             font.setFontName("Arial");
             headerStyle.setFont(font);
             headerStyle.setFillBackgroundColor(HSSFColor.DARK_GREEN.index);
             headerStyle.setFillPattern(HSSFCellStyle.SQUARES);
             //headerStyle.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
             //headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
             
             HSSFCellStyle style = workbook.createCellStyle();
             style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
             
             HSSFRow headerCredito = sheetCredito.createRow(0);
             for (int i = 0; i < headersCredito.length; ++i) {
                 String header = headersCredito[i];
                 HSSFCell cell = headerCredito.createCell(i);
                 cell.setCellStyle(headerStyle);
                 cell.setCellValue(header);
             }
             lstPagosCredito.addAll(obList);
             for (int i = 0; i < lstPagosCredito.size(); ++i ) {
                 HSSFRow datosRowPagosCre = sheetCredito.createRow(i+1);
                 
                 HSSFCell cell = datosRowPagosCre.createCell(0);
                 cell.setCellValue(lstPagosCredito.get(i).getFolio());
                 cell = datosRowPagosCre.createCell(1);
                 cell.setCellValue(lstPagosCredito.get(i).getId_credito());
                 cell = datosRowPagosCre.createCell(2);
                 cell.setCellValue(lstPagosCredito.get(i).getFecha());
                 cell = datosRowPagosCre.createCell(3);
                 cell.setCellValue(lstPagosCredito.get(i).getMonto());
             }
             
             //HSSFCell celda = headerRowNotaRemi.createCell(10);
             LocalDateTime ldt = LocalDateTime.now();
             String cadenaCreacionFecha= String.valueOf(ldt.getDayOfMonth())+String.valueOf(ldt.getMonth())+String.valueOf(ldt.getYear())
                     +"H"+String.valueOf(ldt.getHour())
                     +"M"+String.valueOf(ldt.getMinute())
                     +"S"+String.valueOf(ldt.getSecond())
                     +"ms"+String.valueOf(ldt.getNano());
             
             try {
                 FileOutputStream elFichero = new FileOutputStream("creditoExportado"+cadenaCreacionFecha+".xls");
                 workbook.write(elFichero);
                 elFichero.close();
                 Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                 altMensaje.setContentText("Archivo excel de apartados generado!!");
                 altMensaje.setTitle("Informacion-Venta");
                 altMensaje.show();
             } catch (Exception er) {
                 er.printStackTrace();
             }         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnExportar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         
         gpPrincipal.add(hbBotones, 0, 0);
        
         vbPpal.getChildren().addAll(lbTituloVista, gpCompSeleccion, tvCredito, gpPrincipal);
        return vbPpal;
    }
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
    
}
