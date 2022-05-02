/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.gasto;
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
import scadb.DAO.gastoDAO;

/**
 *
 * @author dopcan
 */
//vistaExportarGastoCredito
public class pantallaExportarXLSGasto {
 
    gastoDAO gasDAO = new gastoDAO();
    
    ObservableList obList = FXCollections.observableArrayList();
    
    public VBox vistaExportarGasto(VBox vbAreaTrabajo){
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("EXPORTAR GASTO A EXCEL");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Componentes de seleccion
        
        Label lbBuscarPor = new Label("Buscar por:");
        Label lbBuscarPorFecha = new Label("fecha:");  
        Label lbBuscarPorTipo = new Label("Tipo venta:");  
        
        RadioButton rbPorFecha = new RadioButton("Buscar por fechas");
        RadioButton rbPorTipoRemision = new RadioButton("Buscar por Tipo Gasto");
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
        
        List<gasto> lstGasto = new ArrayList();
        List<String> lstWhere = new ArrayList();
        //lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
        //lstVentas.addAll(ventDAO.consultaVenta(lstWhere));
        //obList.setAll(notasDAO.consultarNotasRem(lstWhere));
        TableView tvGasto = new TableView();
        tvGasto.setPrefWidth(780);
        tvGasto.setMinWidth(780);
        tvGasto.setMaxWidth(780);
        //id_nota_rem, folio,fecha, tipo_operacion ,monto,flag
        TableColumn idGastoColumna = new TableColumn("Id Gasto");
        idGastoColumna.setMinWidth(120);
        idGastoColumna.setCellValueFactory(new PropertyValueFactory<>("id_gasto"));
        
        TableColumn conceptoColumna = new TableColumn("Concepto");
        conceptoColumna.setMinWidth(180);
        conceptoColumna.setCellValueFactory(new PropertyValueFactory<>("concepto"));

        TableColumn FechaColumna = new TableColumn("Fecha");
        FechaColumna.setMinWidth(120);
        FechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn montoColumna = new TableColumn("Monto");
        montoColumna.setMinWidth(180);
        montoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));

       
        tvGasto.getColumns().addAll(idGastoColumna, FechaColumna, conceptoColumna, montoColumna);
        //tvNotasRemision.setItems(obList);
        
        btnBuscar.setOnAction((event) -> {
            if (rbPorFecha.isSelected()){
                lstWhere.clear();
                lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
                
                if(!obList.isEmpty()) obList.clear();
                
                lstWhere.clear();
                lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
                obList.setAll(gasDAO.consultarGasto(lstWhere));
                tvGasto.setItems(obList);
            }
            if (rbPorTipoRemision.isSelected()){
                if(!obList.isEmpty()) obList.clear();
                lstWhere.clear();
                lstWhere.add("tipo_operacion LIKE '%"+tfTipoVenta.getText()+"%' ");
                obList.setAll(gasDAO.consultarGasto(lstWhere));
                tvGasto.setItems(obList);
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
             HSSFSheet sheetGasto= workbook.createSheet();
             
             workbook.setSheetName(0, "Gasto");
             
             String[] headersGasto = new String[]{
                 "id_gasto",
                 "concepto",
                 "fecha",
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
             
             HSSFRow headerGasto = sheetGasto.createRow(0);
             for (int i = 0; i < headersGasto.length; ++i) {
                 String header = headersGasto[i];
                 HSSFCell cell = headerGasto.createCell(i);
                 cell.setCellStyle(headerStyle);
                 cell.setCellValue(header);
             }
             lstGasto.addAll(obList);
             for (int i = 0; i < lstGasto.size(); ++i ) {
                 HSSFRow datosRowGasto = sheetGasto.createRow(i+1);
                 
                 HSSFCell cell = datosRowGasto.createCell(0);
                 cell.setCellValue(lstGasto.get(i).getId_gasto());
                 cell = datosRowGasto.createCell(1);
                 cell.setCellValue(lstGasto.get(i).getConcepto());
                 cell = datosRowGasto.createCell(2);
                 cell.setCellValue(lstGasto.get(i).getFecha());
                 cell = datosRowGasto.createCell(3);
                 cell.setCellValue(lstGasto.get(i).getMonto());
             }
             
             //HSSFCell celda = headerRowNotaRemi.createCell(10);
             LocalDateTime ldt = LocalDateTime.now();
             String cadenaCreacionFecha= String.valueOf(ldt.getDayOfMonth())+String.valueOf(ldt.getMonth())+String.valueOf(ldt.getYear())
                     +"H"+String.valueOf(ldt.getHour())
                     +"M"+String.valueOf(ldt.getMinute())
                     +"S"+String.valueOf(ldt.getSecond())
                     +"ms"+String.valueOf(ldt.getNano());
             
             try {
                 FileOutputStream elFichero = new FileOutputStream("GastoExportado"+cadenaCreacionFecha+".xls");
                 workbook.write(elFichero);
                 elFichero.close();
                 Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                 altMensaje.setContentText("Archivo excel de gastos generado!!");
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
        
         vbPpal.getChildren().addAll(lbTituloVista, gpCompSeleccion, tvGasto, gpPrincipal);
        return vbPpal;
    }
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
    
}
