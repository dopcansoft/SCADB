/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.CLIENTE;
import DTO.apartado;
import DTO.pagos_apartado;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import scadb.DAO.apartadoDAO;
import scadb.DAO.clienteDAO;
import scadb.DAO.pagos_apartadoDAO;

public class PantallaGestionDeAparatados {
    
    clienteDAO cliDAO = new clienteDAO();
    apartadoDAO apaDAO = new apartadoDAO();
    pagos_apartadoDAO pagapaDAO = new pagos_apartadoDAO();
    PantallaVerProductosComprados pVerPorductosComprados = new PantallaVerProductosComprados();
    
    ObservableList<pagos_apartado> lstPagosApa = FXCollections.observableArrayList();

    public VBox vistaGestionApartadosCliente(VBox vbAreaTrabajo){
        int folioSiguiente = pagapaDAO.obtenerMaximoId()+1;
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("GESTION PAGOS A APARTADOS DE CLIENTES");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        Label lbEtiquetaTotalApartado = new Label("Total Apartado:   ");
        lbEtiquetaTotalApartado.setFont(fuente);
        Label lbTotalApartado = new Label("$ 0.0");
        lbTotalApartado.setFont(fuente);
        lbTotalApartado.setAlignment(Pos.CENTER_RIGHT);
        Label lbEtiquetaTotalCubierto = new Label("Total Cubierto: ");
        lbEtiquetaTotalCubierto.setFont(fuente);
        Label lbTotalCubierto = new Label("$ 0.0");
        lbTotalCubierto.setFont(fuente);
        lbTotalCubierto.setAlignment(Pos.CENTER_RIGHT);
        Label lbEtiquetaResto = new Label("Resta :");
        lbEtiquetaResto.setFont(fuente);
        Label lbResto = new Label("$ 0.0");
        lbResto.setFont(fuente);
        lbResto.setAlignment(Pos.CENTER_RIGHT);
        
        Label lbTablaClientes = new Label("Tabla Clientes: ");
        Label lbTablaApartados = new Label("Tabla Apartados: ");
        Label lbTablaPagos = new Label("Tabla Pagos: ");
        Label lbidApartado = new Label("Id Apartado: ");
        Label lbfolio = new Label("Folio: ");
        Label lbFechaPago = new Label("Fecha Pago: ");
        Label lbMonto = new Label("Monto del Pago: ");
        Label lbTipoPago = new Label("Tipo Pago: ");
        
        TextField tfClientes = new TextField();
        TextField tfidApartado = new TextField();
        TextField tffolio = new TextField();
        tffolio.setText("A-"+String.valueOf(folioSiguiente));
        
        DatePicker dpFechaPago = new DatePicker(LocalDate.now());
        TextField tfMonto = new TextField();
        
        ObservableList<String> lstOpcionesTipoPago = FXCollections.observableArrayList("EFECTIVO", "TRANSFERENCIA",  "TARJETA");
        ComboBox cbTipoPago = new ComboBox(lstOpcionesTipoPago);
        cbTipoPago.setPrefWidth(180);
  
        Button btnCancelar = new Button("Cancelar");
        Button btnRegPago = new Button("Reg. Pago");
        Button btnGenerarNota = new Button("Generar Nota");
        
        GridPane gpBotonesPagos= new GridPane();
        gpBotonesPagos.setVgap(10);
        gpBotonesPagos.setHgap(10);
        gpBotonesPagos.add(btnRegPago, 0, 0);
        gpBotonesPagos.add(btnGenerarNota, 0, 1);

        GridPane gpDatosPago = new GridPane();
        gpDatosPago.setVgap(10);
        gpDatosPago.setHgap(10);
        
        gpDatosPago.add(lbidApartado, 0, 0);
        gpDatosPago.add(tfidApartado, 1, 0);
        
        gpDatosPago.add(lbfolio, 2, 0);
        gpDatosPago.add(tffolio, 3, 0);
        
        gpDatosPago.add(lbFechaPago, 0, 1);
        gpDatosPago.add(dpFechaPago, 1, 1);
        
        gpDatosPago.add(lbTipoPago, 0, 2);
        gpDatosPago.add(cbTipoPago, 1, 2);
        
        gpDatosPago.add(lbMonto, 2, 1);
        gpDatosPago.add(tfMonto, 3, 1);

        gpDatosPago.add(btnCancelar, 2, 2);
        gpDatosPago.add(gpBotonesPagos, 3, 2);
        
        gpDatosPago.add(lbEtiquetaTotalApartado, 1, 3);
        gpDatosPago.add(lbTotalApartado, 2, 3);
        gpDatosPago.add(lbEtiquetaTotalCubierto, 1, 4);
        gpDatosPago.add(lbTotalCubierto, 2, 4);
        gpDatosPago.add(lbEtiquetaResto, 1, 5);
        gpDatosPago.add(lbResto, 2, 5);
        
        TableView tvClientes = new TableView(); 
        
        TableColumn<CLIENTE, String> codClienteColumna = new TableColumn<>("Codigo Cliente");
        codClienteColumna.setMinWidth(120);
        codClienteColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));
        
        TableColumn<CLIENTE, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(320);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tvClientes.getColumns().addAll(codClienteColumna, nombreColumna);
        
         List<CLIENTE> lstClte = new ArrayList<>();
         List<String> lstWhere = new ArrayList<>();
         lstWhere.add("codigo_cliente is not null");
         lstClte=cliDAO.consultarClientes(lstWhere);
         ObservableList obList = FXCollections.observableArrayList(lstClte);
         tvClientes.setItems(obList);

         TableView tvApartados = new TableView();
         
        TableColumn<apartado, Integer> idApartadoColumna = new TableColumn<>("Id Apartado");
        idApartadoColumna.setMinWidth(120);
        idApartadoColumna.setCellValueFactory(new PropertyValueFactory<>("id_apartado"));         
  
        TableColumn<apartado, Integer> codigoClienteApaColumna = new TableColumn<>("Codigo Cliente");
        codigoClienteApaColumna.setMinWidth(120);
        codigoClienteApaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente")); 
        
        TableColumn<apartado, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota de Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta")); 
        
        TableColumn<apartado, String> fechaColumna = new TableColumn<>("Fecha");
        fechaColumna.setMinWidth(120);
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<apartado, Float> montoColumna = new TableColumn<>("Monto Apartado");
        montoColumna.setMinWidth(120);
        montoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvApartados.getColumns().addAll(idApartadoColumna, codigoClienteApaColumna, codigoNotaVentaColumna, 
                fechaColumna, montoColumna);
        
        List<String> lstWhereApa = new ArrayList<>();
        
        tvClientes.setOnMouseClicked((event) -> {
            lstPagosApa.clear();
            tfClientes.setText("");
            tfidApartado.setText("");
            //tffolio.setText("");
            tfMonto.setText("");
            CLIENTE clTemp = (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
            lstWhereApa.clear();
            lstWhereApa.add("codigo_cliente = "+clTemp.getCodigo_cliente());
            ObservableList<apartado> obsLstApartado = FXCollections.observableArrayList();
            obsLstApartado.addAll(apaDAO.consultaApartado(lstWhereApa));
            tvApartados.setItems(obsLstApartado);
            lbTotalApartado.setText("$ 0.0");
            lbTotalCubierto.setText("$ 0.0");
            lbResto.setText("$ 0.0");
        });
       vbPpal.setSpacing(5);
       
       btnCancelar.setOnAction((ActionEvent e)->{
           removerVistas(vbAreaTrabajo);
       });
      
       TableView tvPagos = new TableView();
       
        TableColumn<pagos_apartado, String> folioPagosAparColumna = new TableColumn<>("Folio Remision");
        folioPagosAparColumna.setMinWidth(120);
        folioPagosAparColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));
       
        TableColumn<pagos_apartado, String> fechaPagosAparColumna = new TableColumn<>("Fecha Pago");
        fechaPagosAparColumna.setMinWidth(120);
        fechaPagosAparColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<pagos_apartado, String> idPagosAparColumna = new TableColumn<>("id Apartado");
        idPagosAparColumna.setMinWidth(120);
        idPagosAparColumna.setCellValueFactory(new PropertyValueFactory<>("id_apartado"));   
        
        TableColumn<pagos_apartado, Float> montoPagosAparColumna = new TableColumn<>("Monto Pago");
        montoPagosAparColumna.setMinWidth(120);
        montoPagosAparColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        TableColumn<pagos_apartado, String> tipoPagoColumna = new TableColumn<>("Tipo pago");
        tipoPagoColumna.setMinWidth(120);
        tipoPagoColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_pago"));
   
        tvPagos.getColumns().addAll(folioPagosAparColumna, idPagosAparColumna, fechaPagosAparColumna, 
                montoPagosAparColumna, tipoPagoColumna);
        tvPagos.setItems(lstPagosApa);

        MenuItem miEliminarPagos = new MenuItem("Eliminar Pago");
        miEliminarPagos.setOnAction((event) -> {
            pagos_apartado pagApaTemp = (pagos_apartado)tvPagos.getSelectionModel().getSelectedItem();
            pagapaDAO.borrarPagosAP(pagApaTemp.getId_pago_ap());
            lstPagosApa.clear();
            lstWhere.clear();
            lstWhere.add("id_apartado = "+pagApaTemp.getId_apartado());
            lstPagosApa = FXCollections.observableArrayList(pagapaDAO.consultaPagosAP(lstWhere));
            tvPagos.setItems(lstPagosApa);
            apartado apaIdent = (apartado) tvApartados.getSelectionModel().getSelectedItem();
            float sumTotApa = apaIdent.getMonto();
            lbTotalApartado.setText(" $ "+String.valueOf(sumTotApa));
            float sumPagado=0;
            for (pagos_apartado pg:lstPagosApa){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0) lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            float resta = sumTotApa - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            
        });
        ContextMenu cmMenuPagos = new ContextMenu();
        cmMenuPagos.getItems().addAll(miEliminarPagos);
        tvPagos.setContextMenu(cmMenuPagos);
        tvApartados.setOnMouseClicked((event) -> {
            apartado apaIdent = (apartado) tvApartados.getSelectionModel().getSelectedItem();
            tfidApartado.setText(String.valueOf(apaIdent.getId_apartado()));
            float sumTotApa = apaIdent.getMonto();
            lbTotalApartado.setText(" $ "+String.valueOf(sumTotApa));
            lstWhere.add("id_apartado = "+apaIdent.getId_apartado());
            lstPagosApa = FXCollections.observableArrayList(pagapaDAO.consultaPagosAP(lstWhere));
            float sumPagado=0;
            for (pagos_apartado pg:lstPagosApa){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0){ 
                lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            }
            else lbTotalCubierto.setText(" $ 0.0");
            float resta = sumTotApa - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            tvPagos.setItems(lstPagosApa);
        });        
        
        MenuItem miVerproductos = new MenuItem("Ver Productos..");
        miVerproductos.setOnAction((event) -> {
            apartado apaTemp = (apartado) tvApartados.getSelectionModel().getSelectedItem();
            pVerPorductosComprados.ventanaVerProductosComprados(apaTemp.getCodigo_nota_venta());
        }); 
        
        ContextMenu cmMenuCreditos = new ContextMenu(miVerproductos);
        tvApartados.setContextMenu(cmMenuCreditos);
        btnRegPago.setOnAction((event) -> {
            
            apartado apaIdent = (apartado) tvApartados.getSelectionModel().getSelectedItem();
            float sumTotApar = apaIdent.getMonto();
            lbTotalApartado.setText(" $ "+String.valueOf(sumTotApar));
            pagos_apartado pagapa = new pagos_apartado();
            pagapa.setId_apartado(Integer.parseInt(tfidApartado.getText()));
            pagapa.setFolio(tffolio.getText());
            pagapa.setFecha(dpFechaPago.getValue().toString());
            pagapa.setMonto(Float.parseFloat(tfMonto.getText()));
            pagapa.setTipo_pago(cbTipoPago.getValue().toString());
            lstPagosApa.add(pagapa);
            pagapaDAO.insertarPagosAP(pagapa);
            float sumPagado=0;
            for (pagos_apartado pg:lstPagosApa){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0) lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            float resta = sumTotApar - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            tfMonto.setText("");
            tffolio.setText("");
            //tvPagos.setItems(lstPagosCre);
            Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
            altMensaje.setContentText("Pago a credito Registrado");
            altMensaje.setTitle("Informacion-Credito");
            altMensaje.show();           
        });
         
                btnGenerarNota.setOnAction((event) -> {
                  try{

                   /* User home directory location */
                   // String userHomeDirectory = System.getProperty("user.home");
                    /* Output file location */

                    File file;
                    JasperReport jasperReport;
                    file = new File("Reportes/Formatos/notaRemiApart.jasper");
                    jasperReport = (JasperReport) JRLoader.loadObject(file);
                    LocalDateTime ld = LocalDateTime.now();
                    String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
                    //String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
                    String outputFile = "Reportes/NotasRemision/" + File.separatorChar + "PagosApartado"+fechaFile+".pdf";
                   //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
                   JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvPagos.getItems());
                   CLIENTE cliente = (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
                   apartado apart = (apartado) tvApartados.getSelectionModel().getSelectedItem();
                   System.out.println("Hay "+tvPagos.getItems().size());
                   Map<String, Object> parameters = new HashMap<>();
                   parameters.put("ItemsDataSource", itemsJRBean);
                   parameters.put("ProductosDataSource", itemsJRBean);
                   String totalCubierto = lbTotalCubierto.getText();
                   parameters.put("totalCubierto", totalCubierto);
                   parameters.put("totalCredito", String.valueOf(apart.getMonto()));
                   parameters.put("totalResto", lbResto.getText());
                   parameters.put("nombreCliente", cliente.getNombre());
                   parameters.put("domicilioFiscal", cliente.getDomicilio_fiscal());
                   parameters.put("numeroCredito", String.valueOf(apart.getId_apartado()));
                   
                   
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

        
       VBox vbIzq = new VBox();
       vbIzq.setSpacing(10);
       vbIzq.setAlignment(Pos.CENTER_LEFT);
       
       VBox vbDer = new VBox();
       vbDer.setSpacing(10);
       vbDer.setAlignment(Pos.TOP_LEFT);
       
       vbIzq.getChildren().addAll(lbTablaClientes, tvClientes, lbTablaApartados, tvApartados);
       vbDer.getChildren().addAll(lbTablaPagos, tvPagos, gpDatosPago);
       HBox hbCenter = new HBox(vbIzq, vbDer);
       hbCenter.setSpacing(10);
       vbPpal.getChildren().addAll(lbTituloVista, hbCenter);       
        
        return vbPpal;
    }
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
}
