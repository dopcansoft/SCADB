/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.CLIENTE;
import DTO.Credito;
import DTO.pagos_credito;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import scadb.DAO.CreditoDAO;
import scadb.DAO.clienteDAO;
import scadb.DAO.pagos_creditoDAO;

public class PantallaGestionDeCreditos {
    
    clienteDAO cliDAO = new clienteDAO();
    CreditoDAO creDao = new CreditoDAO();
    pagos_creditoDAO pagcreDAO = new pagos_creditoDAO();
    PantallaVerProductosComprados pVerPorductosComprados = new PantallaVerProductosComprados();
    
    ObservableList<pagos_credito> lstPagosCre = FXCollections.observableArrayList();
    

     public VBox vistaGestionCreditosCliente(VBox vbAreaTrabajo){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER_LEFT);
        Label lbTituloVista = new Label("GESTION PAGOS A CREDITOS DE CLIENTES");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        Label lbEtiquetaTotalCredito = new Label("Total Credito:   ");
        lbEtiquetaTotalCredito.setFont(fuente);
        Label lbTotalCredito = new Label("$ 0.0");
        lbTotalCredito.setFont(fuente);
        lbTotalCredito.setAlignment(Pos.CENTER_RIGHT);
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
        Label lbTablaCreditos = new Label("Tabla Creditos: ");
        Label lbTablaPagos = new Label("Tabla Pagos: ");
        Label lbidCredito = new Label("Id Credito: ");
        Label lbfolio = new Label("Folio: ");
        Label lbFechaPago = new Label("Fecha Pago: ");
        Label lbMonto = new Label("Monto del Pago: ");
        
        TextField tfClientes = new TextField();
        TextField tfidCredito = new TextField();
        TextField tffolio = new TextField();
        DatePicker dpFechaPago = new DatePicker(LocalDate.now());
        TextField tfMonto = new TextField();
        
        Button btnCancelar = new Button("Cancelar");
        Button btnRegPago = new Button("Reg. Pago");

        GridPane gpDatosPago = new GridPane();
        gpDatosPago.setVgap(10);
        gpDatosPago.setHgap(10);
        
        gpDatosPago.add(lbidCredito, 0, 0);
        gpDatosPago.add(tfidCredito, 1, 0);
        
        gpDatosPago.add(lbfolio, 2, 0);
        gpDatosPago.add(tffolio, 3, 0);
        
        gpDatosPago.add(lbFechaPago, 0, 1);
        gpDatosPago.add(dpFechaPago, 1, 1);
        
        gpDatosPago.add(lbMonto, 2, 1);
        gpDatosPago.add(tfMonto, 3, 1);

        gpDatosPago.add(btnCancelar, 2, 2);
        gpDatosPago.add(btnRegPago, 3, 2);
        
        gpDatosPago.add(lbEtiquetaTotalCredito, 1, 3);
        gpDatosPago.add(lbTotalCredito, 2, 3);
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

         TableView tvCreditos = new TableView();
         
        TableColumn<Credito, String> idCreditoColumna = new TableColumn<>("Id Credito");
        idCreditoColumna.setMinWidth(120);
        idCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("id_credito"));         
  
        TableColumn<Credito, String> codigoClienteCreditoColumna = new TableColumn<>("Codigo Cliente");
        codigoClienteCreditoColumna.setMinWidth(120);
        codigoClienteCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente")); 
        
        TableColumn<Credito, String> codigoNotaVentaCreditoColumna = new TableColumn<>("Codigo Nota de Venta");
        codigoNotaVentaCreditoColumna.setMinWidth(120);
        codigoNotaVentaCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta")); 
        
        TableColumn<Credito, String> fechaCreditoColumna = new TableColumn<>("Fecha");
        fechaCreditoColumna.setMinWidth(120);
        fechaCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<Credito, String> montoCreditoColumna = new TableColumn<>("Monto Credito");
        montoCreditoColumna.setMinWidth(120);
        montoCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvCreditos.getColumns().addAll(idCreditoColumna, codigoClienteCreditoColumna, codigoNotaVentaCreditoColumna, 
                fechaCreditoColumna, montoCreditoColumna);
        
        
        
        List<String> lstWhereCre = new ArrayList<>();
        
        tvClientes.setOnMouseClicked((event) -> {
            lstPagosCre.remove(0, lstPagosCre.size());
            tfClientes.setText("");
            tfidCredito.setText("");
            tffolio.setText("");
            tfMonto.setText("");
            CLIENTE clTemp = (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
            lstWhereCre.add("codigo_cliente = "+clTemp.getCodigo_cliente());
            ObservableList<Credito> obsLstCreditos = FXCollections.observableArrayList();
            obsLstCreditos.addAll(creDao.consultaCredito(lstWhereCre));
            tvCreditos.setItems(obsLstCreditos);
            lbTotalCredito.setText("$ 0.0");
            lbTotalCubierto.setText("$ 0.0");
            lbResto.setText("$ 0.0");
        });
       vbPpal.setSpacing(5);
       
       btnCancelar.setOnAction((ActionEvent e)->{
           removerVistas(vbAreaTrabajo);
       });
      
       TableView tvPagos = new TableView();
       
        TableColumn<pagos_credito, String> folioPagosCreditoColumna = new TableColumn<>("Folio Remision");
        folioPagosCreditoColumna.setMinWidth(120);
        folioPagosCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("folio"));
       
        TableColumn<pagos_credito, String> fechaPagosCreditoColumna = new TableColumn<>("Fecha Pago");
        fechaPagosCreditoColumna.setMinWidth(120);
        fechaPagosCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
        TableColumn<pagos_credito, String> idPagosCreditoColumna = new TableColumn<>("id Credito");
        idPagosCreditoColumna.setMinWidth(120);
        idPagosCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("id_credito"));   
        
        TableColumn<pagos_credito, Float> montoPagosCreditoColumna = new TableColumn<>("Monto Pago");
        montoPagosCreditoColumna.setMinWidth(120);
        montoPagosCreditoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
        
        tvPagos.getColumns().addAll(folioPagosCreditoColumna, idPagosCreditoColumna, fechaPagosCreditoColumna, 
                montoPagosCreditoColumna);
        tvPagos.setItems(lstPagosCre);

        
        MenuItem miEliminarPagos = new MenuItem("Eliminar Pago");
        miEliminarPagos.setOnAction((event) -> {
            pagos_credito pagCreTemp = (pagos_credito)tvPagos.getSelectionModel().getSelectedItem();
            pagcreDAO.borrarPagoCred(pagCreTemp.getId_pago_cre());
            lstPagosCre.clear();
            lstWhere.clear();
            lstWhere.add("id_credito = "+pagCreTemp.getId_credito());
            lstPagosCre = FXCollections.observableArrayList(pagcreDAO.consultaPagoCred(lstWhere));
            tvPagos.setItems(lstPagosCre);
            Credito creIdent = (Credito) tvCreditos.getSelectionModel().getSelectedItem();
            float sumTotCred = creIdent.getMonto();
            lbTotalCredito.setText(" $ "+String.valueOf(sumTotCred));
            float sumPagado=0;
            for (pagos_credito pg:lstPagosCre){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0) lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            float resta = sumTotCred - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            
        });
        ContextMenu cmMenuPagos = new ContextMenu();
        cmMenuPagos.getItems().addAll(miEliminarPagos);
        tvPagos.setContextMenu(cmMenuPagos);
        tvCreditos.setOnMouseClicked((event) -> {
            Credito creIdent = (Credito) tvCreditos.getSelectionModel().getSelectedItem();
            tfidCredito.setText(String.valueOf(creIdent.getId_credito()));
            float sumTotCred = creIdent.getMonto();
            lbTotalCredito.setText(" $ "+String.valueOf(sumTotCred));
            lstWhere.add("id_credito = "+creIdent.getId_credito());
            lstPagosCre = FXCollections.observableArrayList(pagcreDAO.consultaPagoCred(lstWhere));
            float sumPagado=0;
            for (pagos_credito pg:lstPagosCre){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0){
                lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            } else lbTotalCubierto.setText(" $ 0.0");
            float resta = sumTotCred - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            tvPagos.setItems(lstPagosCre);
        });        
        
        MenuItem miVerproductos = new MenuItem("Ver Productos..");
        miVerproductos.setOnAction((event) -> {
            Credito creTemp = (Credito) tvCreditos.getSelectionModel().getSelectedItem();
            pVerPorductosComprados.ventanaVerProductosComprados(creTemp.getCodigo_nota_venta());
        }); 
        
        ContextMenu cmMenuCreditos = new ContextMenu(miVerproductos);
        tvCreditos.setContextMenu(cmMenuCreditos);
        btnRegPago.setOnAction((event) -> {
            
            Credito creIdent = (Credito) tvCreditos.getSelectionModel().getSelectedItem();
            float sumTotCred = creIdent.getMonto();
            lbTotalCredito.setText(" $ "+String.valueOf(sumTotCred));
            pagos_credito pagcre = new pagos_credito();
            pagcre.setId_credito(Integer.parseInt(tfidCredito.getText()));
            pagcre.setFolio(tffolio.getText());
            pagcre.setFecha(dpFechaPago.getValue().toString());
            pagcre.setMonto(Float.parseFloat(tfMonto.getText()));
            lstPagosCre.add(pagcre);
            pagcreDAO.insertarPagoCred(pagcre);
            float sumPagado=0;
            for (pagos_credito pg:lstPagosCre){
              sumPagado = sumPagado + pg.getMonto();
            }
            if (sumPagado > 0) lbTotalCubierto.setText(" $ "+String.valueOf(sumPagado));
            float resta = sumTotCred - sumPagado;
            lbResto.setText(" $ "+String.valueOf(resta));
            tfMonto.setText("");
            tffolio.setText("");
            //tvPagos.setItems(lstPagosCre);
            Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
            altMensaje.setContentText("Pago a credito Registrado");
            altMensaje.setTitle("Informacion-Credito");
            altMensaje.show();           
        });
        
       VBox vbIzq = new VBox();
       vbIzq.setSpacing(10);
       vbIzq.setAlignment(Pos.CENTER_LEFT);
       
       VBox vbDer = new VBox();
       vbDer.setSpacing(10);
       vbDer.setAlignment(Pos.TOP_LEFT);
       
       vbIzq.getChildren().addAll(lbTablaClientes, tvClientes, lbTablaCreditos, tvCreditos);
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
