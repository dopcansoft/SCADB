/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.CLIENTE;
import DTO.Credito;
import DTO.VENTA;
import DTO.apartado;
import DTO.detalle_venta;
import DTO.inventario;
import DTO.notas_remision;
import DTO.pagos_apartado;
import DTO.pagos_credito;
import DTO.usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import scadb.DAO.CreditoDAO;
import scadb.DAO.apartadoDAO;
import scadb.DAO.clienteDAO;
import scadb.DAO.detalle_ventaDAO;
import scadb.DAO.inventarioDAO;
import scadb.DAO.notas_remisionDAO;
import scadb.DAO.pagos_apartadoDAO;
import scadb.DAO.pagos_creditoDAO;
import scadb.DAO.ventaDAO;

/**
 *
 * @author dopcan
 */
public class pantallaCancelarVenta {
    
    ventaDAO ventDAO = new ventaDAO();
    detalle_ventaDAO detventaDAO = new detalle_ventaDAO();
    clienteDAO cliDAO = new clienteDAO();
    notas_remisionDAO notasDAO = new notas_remisionDAO();
    CreditoDAO creDao = new CreditoDAO();
    pagos_creditoDAO pagcreDAO = new pagos_creditoDAO();
    apartadoDAO apaDAO = new apartadoDAO();
    pagos_apartadoDAO pagapaDAO = new pagos_apartadoDAO();
    
    ObservableList<detalle_venta> detventa = FXCollections.observableArrayList();
    ObservableList<VENTA> lstventa = FXCollections.observableArrayList();
    inventarioDAO invent = new inventarioDAO();
    List<String> lstWhereConcepto = new ArrayList<>();
    
    usuario usrActivo;
    TextField tfCodigoCliente = new TextField();
    TextField tfNombre = new TextField();
    TextField tfRazonSocial = new TextField();
    TextField tfDomicilioFiscal = new TextField();
    TextField tfTelefono = new TextField();
    TextField tfRFC = new TextField();
    TextField tfEmail = new TextField();
    
    public VBox vistaCancelarVenta(VBox vbAreaTrabajo){
        usrActivo = new usuario();
        usrActivo.setBandera(1);
        usrActivo.setClave("SuperUser");
        usrActivo.setId_usuario(10);
        usrActivo.setNombre_completo("Administrador");
        usrActivo.setTipo("SuperUser");
        usrActivo.setUsuario("SuperUser");
        
         if (detventa.size()>0){
            detventa = FXCollections.observableArrayList();
        }
        
        VBox vbVistaPpal = new VBox();
        
        //Etiquetas/Datos de Cliente;
        Label lbCodigo_cliente  = new Label("Codigo_Cliente");
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
	Label lbTipo_venta = new Label("Tipo de Venta: ");
        Label lbCodigoFactura = new Label("Codigo Factura");
        
        Label lbTituloVista = new Label("CANCELAR VENTA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Etiquetas/datos de la Remisión
        Label lbFolio = new Label("Folio de Nota: ");
        Label lbFecha = new Label("Fecha: ");
        
        TextField tfFolio = new TextField();
        TextField tfCodigoFactura = new TextField();
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("EFECTIVO","VENTA A CREDITO", "APARTADO", "TARJETA", "TRANSFERENCIA");
        ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
        cbTipoVenta.setPrefWidth(180);
        
        //Etiquetas/Datos de la Venta
        Label lbEtiquetaMonto = new Label("TOTAL: $ ");
        Label lbMontoTotal = new Label("0.0");
        lbEtiquetaMonto.setFont(fuente);
        lbMontoTotal.setFont(fuente);
        
        //Componentes para busqueda de ventas
        Label lbBuscarPorFecha = new  Label("Fecha:");
        Label lbBuscarPorTipo = new Label("Tipo Venta :");
        DatePicker dpBuscarPorFecha = new DatePicker(LocalDate.now());
        if (usrActivo.getTipo().toString().equals("VENTA")){
            dpBuscarPorFecha.setEditable(false);
            dpBuscarPorFecha.setDisable(true);
        }
        TextField tfBuscarPorTipo = new TextField();
        if (usrActivo.getTipo().toString().equals("VENTA")){
            tfBuscarPorTipo.setDisable(true);
            tfBuscarPorTipo.setEditable(false);
        }        
        
        Button btnBuscarVenta = new Button("Buscar Ventas");
        
        RadioButton rbBuscarPorFecha = new RadioButton("Buscar por Fecha");
        RadioButton rbBuscarPorTipo = new RadioButton("Buscar por Tipo Venta");
        
        ToggleGroup tgOpcionesBusqueda = new ToggleGroup();
        rbBuscarPorFecha.setToggleGroup(tgOpcionesBusqueda);
        rbBuscarPorTipo.setToggleGroup(tgOpcionesBusqueda);
        rbBuscarPorFecha.setSelected(true);
        
        if (usrActivo.getTipo().toString().equals("VENTA"))rbBuscarPorTipo.setDisable(true);
        
        tfCodigoCliente.setText("");
        tfNombre.setText("");
        tfRazonSocial.setText("");
        tfDomicilioFiscal.setText("");
        tfTelefono.setText("");
        tfRFC.setText("");
        tfEmail.setText(""); 
        tfCodigoCliente.setEditable(false);
        tfNombre.setEditable(false);
        tfRazonSocial.setEditable(false);
        tfDomicilioFiscal.setEditable(false);
        tfTelefono.setEditable(false);
        tfRFC.setEditable(false);
        tfEmail.setEditable(false);

        
        TableView tvTablaVentas = new TableView();
        
        TableColumn<VENTA, Integer> idVentaColumna = new TableColumn<>("Id Venta");
        idVentaColumna.setMinWidth(120);
        idVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta")); 
        
        TableColumn<VENTA, String> FechaVentaColumna = new TableColumn<>("Fecha");
        FechaVentaColumna.setMinWidth(120);
        FechaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha")); 
        
        TableColumn<VENTA, String> tipoVentaColumna = new TableColumn<>("Tipo");
        tipoVentaColumna.setMinWidth(120);
        tipoVentaColumna.setCellValueFactory(new PropertyValueFactory<>("tipo_venta"));
        
        TableColumn<VENTA, Integer> clienteVentaColumna = new TableColumn<>("Codigo Cliente");
        clienteVentaColumna.setMinWidth(120);
        clienteVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));        
        
        tvTablaVentas.getColumns().addAll(idVentaColumna, FechaVentaColumna, tipoVentaColumna, clienteVentaColumna);

        MenuItem miEliminarDetVenta = new MenuItem("Eliminar");
        
        ContextMenu cmTabVentas = new ContextMenu();
        cmTabVentas.getItems().add(miEliminarDetVenta);
        
        btnBuscarVenta.setOnAction((event) -> {
            lbMontoTotal.setText(" 0.0 ");
            tfCodigoCliente.setText("");
            tfDomicilioFiscal.setText("");
            tfEmail.setText("");
            tfNombre.setText("");
            tfRFC.setText("");
            tfRazonSocial.setText("");
            tfTelefono.setText("");
            tfFolio.setText("");
            tfCodigoFactura.setText("");
            cbTipoVenta.setValue("");
            dpFecha.setValue(LocalDate.now());
            //dpFecha = new DatePicker(LocalDate.now());;
            
            if (!lstventa.isEmpty()){
                lstventa.clear();
            }
            if (!detventa.isEmpty()){
               detventa.clear();
            }
            if (rbBuscarPorFecha.isSelected()==true){
                  lstWhereConcepto.clear();
                  lstWhereConcepto.add("fecha = '"+dpBuscarPorFecha.getValue().toString()+"' ");
                  lstWhereConcepto.add("flag != 4");
                  lstWhereConcepto.add("flag != 3");
                  lstventa = FXCollections.observableArrayList(ventDAO.consultaVenta(lstWhereConcepto));
                  tvTablaVentas.setItems(lstventa);
            }
            
            if (rbBuscarPorTipo.isSelected()==true){
                  lstWhereConcepto.clear();
                  lstWhereConcepto.add("tipo_venta = '"+tfBuscarPorTipo.getText()+"' ");
                  lstWhereConcepto.add("flag != 4");
                  lstWhereConcepto.add("flag != 3");
                  lstventa = FXCollections.observableArrayList(ventDAO.consultaVenta(lstWhereConcepto));
                  tvTablaVentas.setItems(lstventa);
            }
        });
        
        GridPane gpCBV = new GridPane();
        gpCBV.setHgap(10);
        gpCBV.setVgap(5);
        gpCBV.add(rbBuscarPorFecha, 0, 0);
        gpCBV.add(rbBuscarPorTipo, 1, 0);
        gpCBV.add(lbBuscarPorFecha, 0, 1);
        gpCBV.add(dpBuscarPorFecha, 1, 1);
        gpCBV.add(lbBuscarPorTipo, 2, 1);
        gpCBV.add(tfBuscarPorTipo, 3, 1);
        gpCBV.add(btnBuscarVenta, 4, 1);
        
        Pane tvPane = new Pane();
        
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");

        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
        Label lbVenta = new Label("Tabla Ventas Seleccionadas: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        tvProductosSelecc.setContextMenu(cmTabVentas);
        //id_detalle_venta clave_prod cantidad codigo_nota_venta 
        //codigo_prod precio_menudeo precio_mayoreo descrprod 
        
        TableColumn<detalle_venta, Integer> idDetalleVentaColumna = new TableColumn<>("Id Detalle Venta");
        idDetalleVentaColumna.setMinWidth(120);
        idDetalleVentaColumna.setCellValueFactory(new PropertyValueFactory<>("id_detalle_venta"));
        
        TableColumn<detalle_venta, Integer> codigoProductColumna = new TableColumn<>("Codigo Producto");
        codigoProductColumna.setMinWidth(80);
        codigoProductColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, String> descrProductColumna = new TableColumn<>("Descripción Producto");
        descrProductColumna.setMinWidth(220);
        descrProductColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_venta, Integer> cantidadProductColumna = new TableColumn<>("Cantidad");
        cantidadProductColumna.setMinWidth(80);
        cantidadProductColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_venta, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta"));

        TableColumn<detalle_venta, Integer> precioVentaColumna = new TableColumn<>("Precio Venta");
        precioVentaColumna.setMinWidth(120);
        precioVentaColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<detalle_venta, Integer> subtotalColumna = new TableColumn<>("Sub-total");
        subtotalColumna.setMinWidth(120);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductosSelecc.getColumns().addAll(codigoProductColumna, descrProductColumna, cantidadProductColumna, precioVentaColumna, subtotalColumna);
        tvProductosSelecc.setItems(detventa);
        
        miEliminarDetVenta.setOnAction((event) -> {
            detalle_venta dtvtaTemp = (detalle_venta) tvProductosSelecc.getSelectionModel().getSelectedItem();
            lstWhere.clear();
            lstWhere.add("codigo_prod = "+ dtvtaTemp.getCodigo_prod());
            inventario iv =  invent.consultaInventario(lstWhere).get(0);
            int nvaCantidad = iv.getExistencia()+dtvtaTemp.getCantidad();
            invent.modificarExistenciaProducto(dtvtaTemp.getCodigo_prod(), nvaCantidad);
            detventaDAO.borrarDetVenta(dtvtaTemp.getId_detalle_venta());
            detventa.clear();
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_nota_venta = "+dtvtaTemp.getCodigo_nota_venta());
            detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhereConcepto));
            lbMontoTotal.setText("0.0");
            for (detalle_venta detv : detventa){
                lstWhere.clear();
                lstWhere.add("codigo_prod = "+detv.getCodigo_prod());
                inventario ivTemp = invent.consultaInventario(lstWhere).get(0);
                detv.setExistencia(ivTemp.getExistencia());
                detv.setSubTotal(detv.getCantidad()* detv.getPrecio_venta());
                float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));               
            }
            tvProductosSelecc.setItems(detventa);
            
        });

       
        tvTablaVentas.setOnMouseClicked((event) -> {
            
            VENTA vtaTemp = (VENTA) tvTablaVentas.getSelectionModel().getSelectedItem();
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_cliente = "+vtaTemp.getCodigo_cliente());
            CLIENTE cliIden = cliDAO.consultarClientes(lstWhereConcepto).get(0);
            tfCodigoCliente.setText(String.valueOf(cliIden.getCodigo_cliente()));
            tfNombre.setText(cliIden.getNombre());
            tfRazonSocial.setText(cliIden.getRazon_social());
            tfDomicilioFiscal.setText(cliIden.getDomicilio_fiscal());
            tfEmail.setText(cliIden.getEmail());
            tfTelefono.setText(cliIden.getTelefono());
            tfRFC.setText(cliIden.getRfc());
            cbTipoVenta.setValue(vtaTemp.getTipo_venta());
            dpFecha.setValue(LocalDate.parse(vtaTemp.getFecha()));
            tfCodigoFactura.setText(vtaTemp.getCodigo_factura());
            lstWhereConcepto.clear();
            lstWhereConcepto.add("id_nota_rem = "+vtaTemp.getId_nota_rem());
            notas_remision notasRemTemp = notasDAO.consultarNotasRem(lstWhereConcepto).get(0);
            tfFolio.setText(String.valueOf(notasRemTemp.getFolio()));
            if(!detventa.isEmpty()){
               detventa.clear();
            }
            lstWhereConcepto.clear();
            lstWhereConcepto.add("codigo_nota_venta = "+vtaTemp.getCodigo_nota_venta());
            detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhereConcepto));
            lbMontoTotal.setText("0.0");
            for (detalle_venta detv : detventa){
                lstWhere.clear();
                lstWhere.add("codigo_prod = "+detv.getCodigo_prod());
                inventario ivTemp = invent.consultaInventario(lstWhere).get(0);
                detv.setExistencia(ivTemp.getExistencia());
                detv.setSubTotal(detv.getCantidad()* detv.getPrecio_venta());
                float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));               
            }
            tvProductosSelecc.setItems(detventa);
        });        
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbCodigo_cliente, 0, 0);
        gpClienteSeleccionado.add(tfCodigoCliente, 1, 0);
        gpClienteSeleccionado.add(lbNombre, 2, 0);
        gpClienteSeleccionado.add(tfNombre, 3, 0);
        
        gpClienteSeleccionado.add(lbRazon_social, 0, 1);
        gpClienteSeleccionado.add(tfRazonSocial, 1, 1);
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 2, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 3, 1);
        
        gpClienteSeleccionado.add(lbTelefono, 0, 2);
        gpClienteSeleccionado.add(tfTelefono, 1, 2);
        gpClienteSeleccionado.add(lbRFC, 2, 2);
        gpClienteSeleccionado.add(tfRFC, 3, 2);
        
        gpClienteSeleccionado.add(lbEmail, 0, 3);
        gpClienteSeleccionado.add(tfEmail, 1, 3);
        
        VBox vbHead = new VBox();

        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(gpCBV);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        HBox hbTotal = new HBox();
        hbTotal.setPrefWidth(400);
        hbTotal.setMaxWidth(500);
        hbTotal.setMinWidth(400);
        hbTotal.setSpacing(10);
        hbTotal.setAlignment(Pos.CENTER_RIGHT);
        hbTotal.getChildren().addAll(lbEtiquetaMonto, lbMontoTotal);  
        
        HBox hbCompSeleccion = new HBox();
        
        hbCompSeleccion.getChildren().addAll(hbTipoSeleccion, hbTotal);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabVenta = new VBox();
        vbTabVenta.setPadding(new Insets(5, 5, 5, 5));
        vbTabVenta.setSpacing(5);
        
        vbTabVenta.getChildren().addAll(lbVenta, tvTablaVentas, lbProducto, tvProductosSelecc);
        
        Separator spVenta = new Separator();
        Separator spProducto =  new Separator();
        
        GridPane gpBloqueVenta = new GridPane();
        gpBloqueVenta.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueVenta.setVgap(10);
        gpBloqueVenta.setHgap(10);
        
        gpBloqueVenta.add(lbTipo_venta, 0, 0);
        gpBloqueVenta.add(cbTipoVenta , 1, 0);

        gpBloqueVenta.add(lbFecha , 2, 0);
        gpBloqueVenta.add(dpFecha , 3, 0);

        
        gpBloqueVenta.add(lbCodigoFactura , 0, 1);
        gpBloqueVenta.add(tfCodigoFactura , 1, 1);
               
        gpBloqueVenta.add(lbFolio , 2, 1);
        gpBloqueVenta.add(tfFolio, 3, 1);

        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnCancelar = new Button("Cancelar Venta");
        btnCancelar.setOnAction((event) -> {
            VENTA vta = (VENTA) tvTablaVentas.getSelectionModel().getSelectedItem();
            ventDAO.cancelarVenta(vta.getCodigo_nota_venta());
            if (!lstWhere.isEmpty()) lstWhere.clear();
            lstWhere.add("codigo_nota_venta="+vta.getCodigo_nota_venta());
            Credito creditoBorrar = creDao.consultaCredito(lstWhere).get(0);
            creDao.borrarCredito(creditoBorrar.getId_credito());
             removerVistas(vbAreaTrabajo);
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnSalir, btnCancelar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(gpClienteSeleccionado, spVenta, gpBloqueVenta, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabVenta, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead, hbBody);
        
        return vbVistaPpal; 
    } 
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
    
}
