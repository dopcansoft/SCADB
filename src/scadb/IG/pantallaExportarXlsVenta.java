    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.VENTA;
import DTO.detalle_venta;
import DTO.notas_remision;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import scadb.DAO.detalle_ventaDAO;
import scadb.DAO.notas_remisionDAO;
import scadb.DAO.ventaDAO;

/**
 *
 * @author dopcan
 */
public class pantallaExportarXlsVenta {
    
    notas_remisionDAO notasDAO = new notas_remisionDAO();
    notas_remision notRem = new notas_remision();
    ventaDAO ventDAO = new ventaDAO();
    detalle_ventaDAO detalleVentaDAO = new detalle_ventaDAO();
    
    ObservableList obList = FXCollections.observableArrayList();
    List<VENTA> lstVentas = new ArrayList<VENTA>();
    List<detalle_venta> lstDetalleVenta = new ArrayList<detalle_venta>();
    
    public VBox vistaExportarXLS(VBox vbAreaTrabajo){
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("EXPORTAR EXCEL");
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
        
        List<notas_remision> lstNotasRemision = new ArrayList();
        List<String> lstWhere = new ArrayList();
        //lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
        //lstVentas.addAll(ventDAO.consultaVenta(lstWhere));
        //obList.setAll(notasDAO.consultarNotasRem(lstWhere));
        TableView tvNotasRemision = new TableView();
        tvNotasRemision.setPrefWidth(780);
        tvNotasRemision.setMinWidth(780);
        tvNotasRemision.setMaxWidth(780);
        //id_nota_rem, folio,fecha, tipo_operacion ,monto,flag
        TableColumn idNotaREmColumna = new TableColumn("Id Nota Remision");
        idNotaREmColumna.setMinWidth(120);
        idNotaREmColumna.setCellValueFactory(new PropertyValueFactory<>("id_nota_rem"));
        
        TableColumn folioColumna = new TableColumn("Folio");
        folioColumna.setMinWidth(180);
        folioColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));

        TableColumn FechaColumna = new TableColumn("Fecha");
        FechaColumna.setMinWidth(120);
        FechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn tipOperColumna = new TableColumn("Tipo Operacion");
        tipOperColumna.setMinWidth(180);
        tipOperColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_operacion"));

        TableColumn montoColumna = new TableColumn("Monto");
        montoColumna.setMinWidth(180);
        montoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));

        TableColumn descuentoColumna = new TableColumn("Descuento");
        descuentoColumna.setMinWidth(80);
        descuentoColumna.setCellValueFactory(new PropertyValueFactory<>("descuento"));
       
        tvNotasRemision.getColumns().addAll(idNotaREmColumna, folioColumna, FechaColumna, tipOperColumna, montoColumna, descuentoColumna);
        
        btnBuscar.setOnAction((event) -> {
            if (rbPorFecha.isSelected()){
                lstWhere.clear();
                lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
                lstWhere.add("flag != 4");
                
                if(!lstVentas.isEmpty()) lstVentas.clear();
                if(!obList.isEmpty()) obList.clear();
                
                lstVentas.addAll(ventDAO.consultaVenta(lstWhere));
                lstWhere.clear();
                lstWhere.add("fecha = '"+dpFechas.getValue().toString()+"' ");
                lstWhere.add("flag != 4");
                obList.setAll(notasDAO.consultarNotasRem(lstWhere));
                tvNotasRemision.setItems(obList);
            }
            if (rbPorTipoRemision.isSelected()){
                
                if(!obList.isEmpty()) obList.clear();
                lstWhere.clear();
                lstWhere.add("tipo_operacion LIKE '%"+tfTipoVenta.getText()+"%' ");
                lstWhere.add("flag != 4");
                obList.setAll(notasDAO.consultarNotasRem(lstWhere));
                tvNotasRemision.setItems(obList);
                
                if(!lstVentas.isEmpty()) lstVentas.clear();
                lstWhere.clear();
                lstWhere.add("tipo_venta LIKE '%"+tfTipoVenta.getText()+"%' ");
                lstWhere.add("flag != 4");
                lstVentas.addAll(ventDAO.consultaVenta(lstWhere));
            }
        });
        
         Button btnCancelar = new Button("Salir");
         btnCancelar.setOnAction((ActionEvent e)->{
             if(vbAreaTrabajo.getChildren().size()>=0){
                 vbAreaTrabajo.getChildren().remove(0);
             }
         });
         
         Button btnExportar = new Button("Exportar");
         btnExportar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheetNotaRemi= workbook.createSheet();
                HSSFSheet sheetVenta = workbook.createSheet();
                HSSFSheet sheetDetalleVenta = workbook.createSheet();
                workbook.setSheetName(0, "Nota Remi");
                workbook.setSheetName(1, "Venta");
                workbook.setSheetName(2, "Detalle Productos");
                
                String[] headersNotaRemi = new String[]{
                    "id Nota Remision",
                    "Folio",
                    "Fecha",
                    "Tipo Operacion",
                    "Monto",
                    "Descuento",
                };
                String[] headersVenta = new String[]{
                    "Codigo Nota Venta",
                    "Fecha",
                    "Tipo Venta",
                    "Codigo CLiente",
                    "Codigo Factura",
                    "Id Nota Remision",
                };
                String[] headersDetalleVenta = new String[]{
                    "Id Detalle Venta",
                    "Codigo Producto",
                    "Descripcion",
                    "Cantidad",
                    "Codigo Nota Venta",
                    "Precio Venta",
                    "Sub-Total",
                };
                
                HSSFCellStyle headerStyle = workbook.createCellStyle();
                HSSFFont font = workbook.createFont();
                font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
                font.setColor(IndexedColors.BLACK.getIndex());
                font.setFontName("Arial");
                headerStyle.setFont(font);
                headerStyle.setFillBackgroundColor(HSSFColor.DARK_GREEN.index);
                headerStyle.setFillPattern(HSSFCellStyle.SQUARES);
                
                HSSFCellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
                
                HSSFRow headerRowNotaRemi = sheetNotaRemi.createRow(0);
                for (int i = 0; i < headersNotaRemi.length; ++i) {
                    String header = headersNotaRemi[i];
                    HSSFCell cell = headerRowNotaRemi.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(header);
                }
                lstNotasRemision.addAll(obList);
                for (int i = 0; i < lstNotasRemision.size(); ++i ) {
                    HSSFRow datosRowNotaRemi = sheetNotaRemi.createRow(i+1);
                    
                    HSSFCell cell = datosRowNotaRemi.createCell(0);
                    cell.setCellValue(lstNotasRemision.get(i).getId_nota_rem());
                    cell = datosRowNotaRemi.createCell(1);
                    cell.setCellValue(lstNotasRemision.get(i).getFolio());
                    cell = datosRowNotaRemi.createCell(2);
                    cell.setCellValue(lstNotasRemision.get(i).getFecha());
                    cell = datosRowNotaRemi.createCell(3);
                    cell.setCellValue(lstNotasRemision.get(i).getTipo_operacion());
                    cell = datosRowNotaRemi.createCell(4);
                    cell.setCellValue(lstNotasRemision.get(i).getMonto());
                    cell = datosRowNotaRemi.createCell(5);
                    cell.setCellValue(lstNotasRemision.get(i).getDescuento());
                }
                
                HSSFRow headerRowVenta = sheetVenta.createRow(0);
                for (int i = 0; i < headersVenta.length; ++i) {
                    String header = headersVenta[i];
                    HSSFCell cell = headerRowVenta.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(header);
                }
                
                //lstNotasRemision.addAll(obList);
                for (int i = 0; i < lstVentas.size(); ++i ) {
                    HSSFRow datosRowVenta = sheetVenta.createRow(i+1);
                    
                    HSSFCell cell = datosRowVenta.createCell(0);
                    cell.setCellValue(lstVentas.get(i).getCodigo_nota_venta());
                    cell = datosRowVenta.createCell(1);
                    cell.setCellValue(lstVentas.get(i).getFecha());
                    cell = datosRowVenta.createCell(2);
                    cell.setCellValue(lstVentas.get(i).getTipo_venta());
                    cell = datosRowVenta.createCell(3);
                    cell.setCellValue(lstVentas.get(i).getCodigo_cliente());
                    cell = datosRowVenta.createCell(4);
                    cell.setCellValue(lstVentas.get(i).getCodigo_factura());
                    cell = datosRowVenta.createCell(5);
                    cell.setCellValue(lstVentas.get(i).getId_nota_rem());
                }
                
                HSSFRow headerRowDetalleVenta = sheetDetalleVenta.createRow(0);
                for (int i = 0; i < headersDetalleVenta.length; ++i) {
                    String header = headersDetalleVenta[i];
                    HSSFCell cell = headerRowDetalleVenta.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(header);
                }
                
                int rowCount = 1;
                for (int i = 0; i < lstVentas.size(); i++ ) {
                    lstWhere.clear();
                    lstWhere.add("codigo_nota_venta="+ String.valueOf(lstVentas.get(i).getCodigo_nota_venta()));
                    lstDetalleVenta = detalleVentaDAO.consultaDetVenta(lstWhere);
                    for (int x =0; x< lstDetalleVenta.size(); x++){
                        HSSFRow datosRowDetalleVenta = sheetDetalleVenta.createRow(rowCount);
                        
                        HSSFCell cell = datosRowDetalleVenta.createCell(0);
                        cell.setCellValue(lstDetalleVenta.get(x).getId_detalle_venta());
                        cell = datosRowDetalleVenta.createCell(1);
                        cell.setCellValue(lstDetalleVenta.get(x).getCodigo_prod());
                        cell = datosRowDetalleVenta.createCell(2);
                        cell.setCellValue(lstDetalleVenta.get(x).getDescrprod());
                        cell = datosRowDetalleVenta.createCell(3);
                        cell.setCellValue(lstDetalleVenta.get(x).getCantidad());
                        cell = datosRowDetalleVenta.createCell(4);
                        cell.setCellValue(lstDetalleVenta.get(x).getCodigo_nota_venta());
                        cell = datosRowDetalleVenta.createCell(5);
                        cell.setCellValue(lstDetalleVenta.get(x).getPrecio_venta());
                        cell = datosRowDetalleVenta.createCell(6);
                        cell.setCellValue(lstDetalleVenta.get(x).getCantidad()*lstDetalleVenta.get(x).getPrecio_venta());
                        rowCount++;
                    }
                }
                
                LocalDateTime ldt = LocalDateTime.now();
                String cadenaCreacionFecha= String.valueOf(ldt.getDayOfMonth())+String.valueOf(ldt.getMonth())+String.valueOf(ldt.getYear())
                        +"H"+String.valueOf(ldt.getHour())
                        +"M"+String.valueOf(ldt.getMinute())
                        +"S"+String.valueOf(ldt.getSecond())
                        +"ms"+String.valueOf(ldt.getNano());
                
                try {
                    FileOutputStream elFichero = new FileOutputStream("Exportado"+cadenaCreacionFecha+".xls");
                    workbook.write(elFichero);
                    elFichero.close();
                    Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                    altMensaje.setContentText("Archivo excel ventas del dia generado!!");
                    altMensaje.setTitle("Informacion-Venta");
                    altMensaje.show();
                } catch (Exception er) {
                    er.printStackTrace();
                }
                
                
            }         
        });
         
         HBox hbBotones = new HBox(btnCancelar, btnExportar);
         hbBotones.setSpacing(10);
        
         GridPane gpPrincipal = new GridPane();
         gpPrincipal.setHgap(10);
         gpPrincipal.setVgap(10);
         gpPrincipal.add(hbBotones, 0, 0);
        
         vbPpal.getChildren().addAll(lbTituloVista, gpCompSeleccion, tvNotasRemision, gpPrincipal);
        return vbPpal;
    }
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
    
}
