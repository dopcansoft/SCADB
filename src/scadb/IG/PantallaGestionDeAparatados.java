/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.CLIENTE;
import DTO.apartado;
import DTO.pagos_apartado;
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
        
        TextField tfClientes = new TextField();
        TextField tfidApartado = new TextField();
        TextField tffolio = new TextField();
        DatePicker dpFechaPago = new DatePicker(LocalDate.now());
        TextField tfMonto = new TextField();
        
        Button btnCancelar = new Button("Cancelar");
        Button btnRegPago = new Button("Reg. Pago");

        GridPane gpDatosPago = new GridPane();
        gpDatosPago.setVgap(10);
        gpDatosPago.setHgap(10);
        
        gpDatosPago.add(lbidApartado, 0, 0);
        gpDatosPago.add(tfidApartado, 1, 0);
        
        gpDatosPago.add(lbfolio, 2, 0);
        gpDatosPago.add(tffolio, 3, 0);
        
        gpDatosPago.add(lbFechaPago, 0, 1);
        gpDatosPago.add(dpFechaPago, 1, 1);
        
        gpDatosPago.add(lbMonto, 2, 1);
        gpDatosPago.add(tfMonto, 3, 1);

        gpDatosPago.add(btnCancelar, 2, 2);
        gpDatosPago.add(btnRegPago, 3, 2);
        
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
            tffolio.setText("");
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
        
        tvPagos.getColumns().addAll(folioPagosAparColumna, idPagosAparColumna, fechaPagosAparColumna, 
                montoPagosAparColumna);
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
