/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import BO.VentaBO;
import DTO.CLIENTE;
import DTO.Credito;
import DTO.VENTA;
import DTO.apartado;
import DTO.bitacora_precio;
import DTO.categoria;
import DTO.detalle_venta;
import DTO.inventario;
import DTO.notas_remision;
import DTO.usuario;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
//import net.sf.jasperreports.engine.JasperCompileManager;
import scadb.DAO.bitacora_precioDAO;
import scadb.DAO.categoriaDAO;
import scadb.DAO.clienteDAO;
import scadb.DAO.inventarioDAO;
import scadb.DAO.notas_remisionDAO;

/**
 *
 * @author dopcan
 */
public class pantallaCrearVenta {
    
   TextField tfCodigoCliente = new TextField();
   TextField tfNombre = new TextField();
   TextField tfCdSucursal = new TextField();
   TextField tfRazonSocial = new TextField();
   TextField tfDomicilioFiscal= new TextField();
   TextField tfTelefono = new TextField();
   TextField tfRFC = new TextField();
   TextField tfEmail = new TextField();
   TextField tfdescuento = new TextField();

   
  
    float MontoTotal = 0.0f;
    float Descuento = 0.0f;
   float MontoOriginal = 0.0f;
   
   usuario usrActivo;
   ObservableList<detalle_venta> detventa = FXCollections.observableArrayList();
   categoriaDAO categDAO = new categoriaDAO();
   inventarioDAO invent = new inventarioDAO();
   bitacora_precioDAO bitacoraDAO = new bitacora_precioDAO();
   clienteDAO cliDAO = new clienteDAO();
   CLIENTE clIdent = new CLIENTE();
   notas_remisionDAO notasRemiDAO = new notas_remisionDAO();
   
   TableView tvProductosSelecc = new TableView();
   Label lbMontoTotal = new Label("0.0");
   TextField tfFolio = new TextField();
   
   ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("EFECTIVO","VENTA A CREDITO", "APARTADO", "TARJETA", "TRANSFERENCIA");
   ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
   
    public VBox vistaCrearVenta(VBox vbAreaTrabajo){
        int folioSiguiente = notasRemiDAO.obtenerMaximoId();
        tfFolio.setText("MT-"+String.valueOf(folioSiguiente+1));
        Label lbDescuento = new Label();
        Label lbCdSucursal = new Label("Cd. Suc.");
        Button btntDescuento = new Button();
        Label lbCanDescuento = new Label();
        HBox botonDescuento = new HBox();
        
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
        
        tfdescuento.setText("");
        lbDescuento.setText("Descuento");
        btntDescuento.setText("Aplicar");
        tfCodigoCliente.setText("");
        tfNombre.setText("");
        tfRazonSocial.setText("");
        tfDomicilioFiscal.setText("");
        tfTelefono.setText("");
        tfRFC.setText("");
        tfEmail.setText(""); 
        
        
        VBox vbVistaPpal = new VBox();

        Label lbTituloVista = new Label("REGISTRAR UNA VENTA");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        Font fuenteMonto = new Font("Arial Bold", 22);
        lbTituloVista.setFont(fuente);
        
        //Etiquetas/datos de la Remisión
        Label lbFolio = new Label("Folio de Nota: ");
        Label lbFecha = new Label("Fecha: ");
        
//        TextField tfFolio = new TextField();
        DatePicker dpFecha = new DatePicker(LocalDate.now());
//        if(usrActivo.getTipo().equals("VENTA")){
//          dpFecha.setEditable(false);
//          dpFecha.setDisable(true);
//        }
        //Etiquetas/Datos de la Venta
        Label lbEtiquetaMonto = new Label("TOTAL: $ ");
//        Label lbMontoTotal = new Label("0.0");
        lbEtiquetaMonto.setFont(fuenteMonto);
        lbMontoTotal.setFont(fuenteMonto);
        
        btntDescuento.setOnAction((event) -> {
        
        Descuento = MontoOriginal / 100 * Float.parseFloat(tfdescuento.getText());
        MontoTotal = MontoOriginal - Descuento;
        lbMontoTotal.setText(String.valueOf(MontoTotal));
        lbCanDescuento.setText("Descuento:"+Descuento);
        });
        botonDescuento.getChildren().addAll(btntDescuento, lbCanDescuento);
        
	Label lbTipo_venta = new Label("Tipo de Venta: ");
        Label lbCodigoFactura = new Label("Codigo Factura");
        
	Label lbCantidad  = new Label("Cantidad");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
	Label lbPrecioMenudeo  = new Label("Precio Menudeo: ");
	Label lbPrecioMayoreo  = new Label("Precrio Mayoreo: ");
        Label lbPrecioVenta = new Label("Precio Venta");
        
//        ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("EFECTIVO","VENTA A CREDITO", "APARTADO", "TARJETA", "TRANSFERENCIA");
//        ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
        cbTipoVenta.setPrefWidth(180);
        
        TextField tfCantidad = new TextField();
        tfCantidad.setMaxWidth(80);
        TextField tfCodigoFactura = new TextField();
        
        TextField tfCodigoProducto = new TextField();
        TextField tfDescrProd = new TextField();
        tfDescrProd.setMaxWidth(300);
        tfDescrProd.setPrefWidth(300);
        TextField tfPrecioMayor = new TextField();
        tfPrecioMayor.setMaxWidth(120);
        tfPrecioMayor.setPrefWidth(120);
        tfPrecioMayor.setEditable(false);
        TextField tfPrecioMenudeo = new TextField();
        tfPrecioMenudeo.setEditable(false);
        TextField tfPrecioVenta = new TextField();
        
        
        //Etiquetas/Datos de Cliente;
        Label lbCodigo_cliente  = new Label("Codigo_Cliente");
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
        
        //Componentes de Interfaz
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        ToggleGroup tgPrecio = new ToggleGroup();
        RadioButton rbPrecioMenudeo =  new RadioButton("Precio Menudeo");
        rbPrecioMenudeo.setOnAction((ActionEvent e)->{
            tfPrecioVenta.setText(tfPrecioMenudeo.getText());
        });
        
        RadioButton rbPrecioMayoreo = new RadioButton("Precio Mayoreo");
        rbPrecioMayoreo.setOnAction((ActionEvent e)->{
            tfPrecioVenta.setText(tfPrecioMayor.getText());
        });
                
        rbPrecioMenudeo.setToggleGroup(tgPrecio);
        rbPrecioMayoreo.setToggleGroup(tgPrecio);
        
       List<String> lstCategorias = new ArrayList<>();
       List<String> lstWherecat = new ArrayList<>();
       lstWherecat.add("id_categoria is not null");
       for (categoria i : categDAO.consultarCategoria(lstWherecat)){
            lstCategorias.add(i.getCategoria());
       }
        
        Label lbCodigo = new Label("Codigo: ");
        TextField tfCodigo = new TextField();
        Label lbDescripcion = new Label("Descripción: ");
        TextField tfDescripcion = new TextField();
        Label lbCategoria = new Label("Categoria: ");
        ComboBox cbCategoria = new ComboBox(FXCollections.observableArrayList(lstCategorias));
        cbCategoria.setPrefWidth(140);
        
        Label lbInventario = new Label("Tabla Inventario: ");
        TableView tvInventario = new TableView();
        tvInventario.setPrefHeight(350);
        tvInventario.setPrefWidth(550);
        
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
        
        tvInventario.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna,
                pMenudeoColumna, pMayoreoColumna, uMedidaColumna, idUbicacionColumna, 
                codProvColumna, cCompraColumna);
        List<String> lstWhere = new ArrayList<>();
        lstWhere.add("codigo_prod is not null");
        tvInventario.setItems(invent.consultarInventario(lstWhere));
        
        tvInventario.setOnMouseClicked((event) -> {
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(String.valueOf(inv.getCodigo_prod()));
            tfDescrProd.setText(inv.getDescripcion());
            tfPrecioMenudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
            tfPrecioMayor.setText(String.valueOf(inv.getPrecio_mayoreo()));
            rbPrecioMenudeo.setSelected(true);
            tfPrecioVenta.setText(tfPrecioMenudeo.getText());
        });
        
        tvInventario.setOnKeyPressed((event) ->{ 
            if(event.getCode()==KeyCode.ENTER){
                inventario inv = new inventario();
                inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
                tfCodigoProducto.setText(String.valueOf(inv.getCodigo_prod()));
                tfDescrProd.setText(inv.getDescripcion());
                tfPrecioMenudeo.setText(String.valueOf(inv.getPrecio_menudeo()));
                tfPrecioMayor.setText(String.valueOf(inv.getPrecio_mayoreo()));
                rbPrecioMenudeo.setSelected(true);
                tfPrecioVenta.setText(tfPrecioMenudeo.getText());
            }
        });
        MenuItem miEliminarDetVenta = new MenuItem("Eliminar");
        ContextMenu cmTabVentas = new ContextMenu();
        cmTabVentas.getItems().add(miEliminarDetVenta);
        
        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
//        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
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
        tvProductosSelecc.setContextMenu(cmTabVentas);
        miEliminarDetVenta.setOnAction((event) -> {
            tvProductosSelecc.getItems().remove(
                    tvProductosSelecc.getSelectionModel().getSelectedIndex());
            lbMontoTotal.setText("0.0");
            float MontoTotal = 0.0f;
            for (detalle_venta detv : detventa){
                MontoTotal = MontoTotal + detv.getSubTotal();
                lbMontoTotal.setText(Float.toString(MontoTotal));  
            }
        });       

        Button btnAgregarProducto = new Button("Agregar Producto");
        btnAgregarProducto.setOnAction((ActionEvent e)->{
            inventario inv = new inventario();
            inv = (inventario) tvInventario.getSelectionModel().getSelectedItem();
            if (tfCodigoProducto.getText().length()>0){
             if (tfCantidad.getText().length()>0){
                 if (inv.getExistencia()>= Integer.parseInt(tfCantidad.getText())){
                     if (tfPrecioVenta.getText().length()> 0){
                         detalle_venta detvta = new detalle_venta();
                         detvta.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
                         detvta.setDescrprod(tfDescrProd.getText());
                         detvta.setCantidad(Integer.parseInt(tfCantidad.getText()));
                         detvta.setExistencia(inv.getExistencia());
                         float pventa = Float.parseFloat(tfPrecioVenta.getText());
                         if (rbPrecioMenudeo.isSelected()){
                             float pmenudeo = Float.parseFloat(tfPrecioMenudeo.getText());
                             if ( pventa != pmenudeo){
                                Alert alertmjs = new Alert(Alert.AlertType.CONFIRMATION);
                                alertmjs.setTitle("Mensaje de Confirmacion");
                                alertmjs.setContentText("El precio de venta es diferente del precio de menudeo, "
                                        + "Deseas almacenarlo como precio oficial?");
                                Optional<ButtonType> action = alertmjs.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    invent.modificarPrecioMenudeo(Integer.parseInt(tfCodigoProducto.getText()), pventa);
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: ");
                                    System.out.println("Modifico Inventario Precio Menudeo:\n precio"+pventa+"\n Producto: "
                                            +tfCodigoProducto.getText());
                                }
                                bitacora_precio bitCambioPrecio = new bitacora_precio();
                                bitCambioPrecio.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
                                bitCambioPrecio.setId_usuario(usrActivo.getId_usuario());
                                bitCambioPrecio.setNombreUsuario(usrActivo.getNombre_completo());
                                bitCambioPrecio.setPrecio_menudeo_ant(Float.parseFloat(tfPrecioMenudeo.getText()));
                                bitCambioPrecio.setPrecio_menudeo(Float.parseFloat(tfPrecioVenta.getText()));
                                bitCambioPrecio.setFecha(LocalDateTime.now().toString());
                                bitCambioPrecio.setBandera(1);
                                bitacoraDAO.insertarBitacora(bitCambioPrecio);
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: ");
                                    System.out.println("Agrego Reg. Bitacora Precio:\n precio"+pventa+"\n Producto: "
                                            +tfCodigoProducto.getText());
                             } 
                         }
                         if (rbPrecioMayoreo.isSelected()){
                             float pmayoreo = Float.parseFloat(tfPrecioMayor.getText());
                             if ( pventa != pmayoreo){
                                Alert alertmjs = new Alert(Alert.AlertType.CONFIRMATION);
                                alertmjs.setTitle("Mensaje de Confirmacion");
                                alertmjs.setContentText("El precio de venta es diferente del precio de mayoreo, "
                                        + "Deseas almacenarlo como precio oficial?");
                                Optional<ButtonType> action = alertmjs.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    invent.modificarPrecioMayoreo(Integer.parseInt(tfCodigoProducto.getText()), pventa);
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: ");
                                    System.out.println("Modifico Inventario Precio Mayoreo:\n precio"+pventa+"\n Producto: "
                                            +tfCodigoProducto.getText());
                                }
                                bitacora_precio bitCambioPrecio = new bitacora_precio();
                                bitCambioPrecio.setCodigo_prod(Integer.parseInt(tfCodigoProducto.getText()));
                                bitCambioPrecio.setId_usuario(usrActivo.getId_usuario());
                                bitCambioPrecio.setNombreUsuario(usrActivo.getNombre_completo());
                                bitCambioPrecio.setPrecio_mayoreo_ant(Float.parseFloat(tfPrecioMayor.getText()));
                                bitCambioPrecio.setPrecio_mayoreo(pventa);
                                bitCambioPrecio.setFecha(LocalDateTime.now().toString());
                                bitCambioPrecio.setBandera(1);
                                bitacoraDAO.insertarBitacora(bitCambioPrecio);
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: ");
                                    System.out.println("Agrego Reg. Bitacora Precio:\n precio"+pventa+"\n Producto: "
                                            +tfCodigoProducto.getText());
                             } 
                         }
                            
                            detvta.setPrecio_venta(Float.parseFloat(tfPrecioVenta.getText()));
                            detvta.setSubTotal(Integer.parseInt(tfCantidad.getText())* Float.parseFloat(tfPrecioVenta.getText()));
                            float MontoTotal = Float.parseFloat(lbMontoTotal.getText());
                            MontoTotal = MontoTotal + Integer.parseInt(tfCantidad.getText())* Float.parseFloat(tfPrecioVenta.getText());
                            lbMontoTotal.setText(Float.toString(MontoTotal));
                            //MontoOriginal = Float.parseFloat(lbMontoTotal.getText());
                            MontoOriginal = MontoTotal;
                            detventa.add(detvta);
                            //System.out.println("Cuantos van:"+detventa.size());
                        }else {
                         Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                         MensajeError.setTitle("Error");
                         MensajeError.setContentText("Falta Costo de Venta");
                         MensajeError.show();
                        }
                    }else {
                       Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                       MensajeError.setTitle("Error");
                       MensajeError.setContentText("No puedes poner una cantidad mayor a la \n de la existencia en inventario");
                       MensajeError.show();
                    }
                }else {
               Alert MensajeError = new Alert(Alert.AlertType.ERROR);
               MensajeError.setTitle("Error");
               MensajeError.setContentText("Falta Cantidad de compra");
               MensajeError.show();
              }
            } else{
               Alert MensajeError = new Alert(Alert.AlertType.ERROR);
               MensajeError.setTitle("Error");
               MensajeError.setContentText("Falta Seleccionar Producto");
               MensajeError.show();           
            }
        });
        
        
        Button btnSeleccionarCliente = new Button("Seleccionar Cliente..");
        btnSeleccionarCliente.setOnAction((ActionEvent e)->{
            ventanaSeleccionarClientes();
        });
        
        GridPane gpDesscuento = new GridPane();
        botonDescuento.setSpacing(5);
        gpDesscuento.add(lbDescuento, 0,0);
        gpDesscuento.add(tfdescuento, 1,0);
        gpDesscuento.add(botonDescuento, 1,1);
        gpDesscuento.setHgap(10);
        gpDesscuento.setVgap(10);
        
        
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
        
        gpClienteSeleccionado.add(lbCdSucursal, 2, 3);
        gpClienteSeleccionado.add(tfCdSucursal, 3, 3);
        
        
        VBox vbHead = new VBox();

        VBox vbClienteSeleccion = new VBox();
        vbClienteSeleccion.setSpacing(10);
        vbClienteSeleccion.getChildren().addAll(btnSeleccionarCliente, gpClienteSeleccionado);
        
        HBox hbTipoSeleccion = new HBox();
        hbTipoSeleccion.getChildren().addAll(rbTodos, rbCodigo, rbDescripcion, rbCategoria);
        hbTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        hbTipoSeleccion.setSpacing(5);
        
        VBox vbCodigo = new VBox();
        vbCodigo.getChildren().addAll(lbCodigo, tfCodigo);
        vbCodigo.setSpacing(5);
        
        VBox vbDesc = new VBox();
        vbDesc.getChildren().addAll(lbDescripcion, tfDescripcion);
        vbDesc.setSpacing(5);
        
        VBox vbCat = new VBox();
        vbCat.getChildren().addAll(lbCategoria, cbCategoria);
        vbCat.setSpacing(5);
        
        
        HBox hbCompSeleccion = new HBox();
        Button btnBuscarProductos = new Button("Seleccionar");
        btnBuscarProductos.setMaxHeight(50);
        btnBuscarProductos.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent e) {
                   List<inventario> lstInv = new ArrayList<>();
                   List<String> lstWherelc = new ArrayList<>();
                   
                   if (rbTodos.isSelected()){
                       lstWherelc.add("codigo_prod is not null");
                       tvInventario.setItems(invent.consultarInventario(lstWherelc));
                       //String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvInventario.getItems().size())+" Seleccionados)";
                   }
                   
                   if (rbCodigo.isSelected()){
                       lstWhere.add("codigo_prod = "+tfCodigo.getText());
                       //lstInv=invent.consultarInventario(lstWhere);
                       //ObservableList obList = FXCollections.observableArrayList(lstInv);
                       tvInventario.setItems(invent.consultarInventario(lstWhere));
                   }
                   if (rbDescripcion.isSelected()){
                       
                       lstWherelc.add("descripcion like '"+tfDescripcion.getText()+"%'");
                       //lstInv=invent.consultarInventario(lstWhere);
                       //ObservableList obList = FXCollections.observableArrayList(lstInv);
                       tvInventario.setItems(invent.consultarInventario(lstWherelc));
                   }
                   
                   if (rbCategoria.isSelected()){
                       lstWherecat.add("id_categoria is not null");
                       categDAO.consultarCategoria(lstWherecat).forEach((i) -> {
                           String strCategoria =  i.getCategoria();
                           if (strCategoria.compareTo(cbCategoria.getSelectionModel().getSelectedItem().toString())==0) {
                               lstWhere.add("id_categoria = "+i.getId_categoria());
                               tvInventario.setItems(invent.consultarInventario(lstWhere));
                           }
                       });
                   }     }
           });
        
        HBox hbTotal = new HBox();
        hbTotal.setPrefWidth(6000);
        hbTotal.setMaxWidth(600);
        hbTotal.setMinWidth(400);
        hbTotal.setSpacing(10);
        hbTotal.setHgrow(vbHead, Priority.ALWAYS);
        hbTotal.setAlignment(Pos.CENTER_RIGHT);
        hbTotal.getChildren().addAll(lbEtiquetaMonto, lbMontoTotal, gpDesscuento);
        
        hbCompSeleccion.getChildren().addAll(vbCodigo, vbDesc, vbCat, btnBuscarProductos, hbTotal);
        hbCompSeleccion.setPadding(new Insets(10, 10, 10, 10));
        hbCompSeleccion.setSpacing(5);

        Separator spSeleccionProductos = new Separator();
        vbHead.getChildren().addAll(lbTipoBusqueda, hbTipoSeleccion, hbCompSeleccion, spSeleccionProductos);
        
        VBox vbTabInventario = new VBox();
        vbTabInventario.setPadding(new Insets(5, 5, 5, 5));
        
        
        vbTabInventario.getChildren().addAll(lbInventario, tvInventario, lbProducto, tvProductosSelecc);
        
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

        GridPane gpBloqueProducto = new GridPane();
        gpBloqueProducto.setPadding(new Insets(5, 5, 5, 5));
        gpBloqueProducto.setVgap(10);
        gpBloqueProducto.setHgap(10);
        
        gpBloqueProducto.add(lbCodigoProd , 0, 0);
        gpBloqueProducto.add(tfCodigoProducto , 1, 0);
        
        gpBloqueProducto.add(lbCantidad , 2, 0);
        gpBloqueProducto.add(tfCantidad , 3, 0);
        
        gpBloqueProducto.add(lbDescProducto , 2, 1);
        gpBloqueProducto.add(tfDescrProd , 3, 1);
        
        gpBloqueProducto.add(rbPrecioMenudeo, 0, 2);
        gpBloqueProducto.add(rbPrecioMayoreo, 2, 2);

        gpBloqueProducto.add(lbPrecioMenudeo , 0, 3);
        gpBloqueProducto.add(tfPrecioMenudeo , 1, 3);

        gpBloqueProducto.add(lbPrecioMayoreo , 2, 3);
        gpBloqueProducto.add(tfPrecioMayor , 3, 3);
        
        gpBloqueProducto.add(lbPrecioVenta, 0, 4);
        gpBloqueProducto.add(tfPrecioVenta, 1, 4);
        
        gpBloqueProducto.add(btnAgregarProducto, 2, 4);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });
        
        Button btnGuardar = new Button("Registrar Venta");
        btnGuardar.setOnAction((event) -> {
            
           
            
           if (tfCodigoCliente.getText().length()>0){
               if (!detventa.isEmpty()){
                   if (!cbTipoVenta.getSelectionModel().isEmpty()){
                       //System.out.println("Longitud Tipo Venta -->"+ cbTipoVenta.getValue().toString().length());
                       if (tfFolio.getText().length()>0){
                           VENTA vta = new VENTA();
                           vta.setCodigo_cliente(Integer.parseInt(tfCodigoCliente.getText()));
                           vta.setCodigo_factura(tfCodigoFactura.getText());
                           vta.setCodigo_nota_venta(0);
                           vta.setFecha(dpFecha.getValue().toString());
                           if (tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("EFECTIVO")==0 )
                               vta.setTipo_venta("REMISION "+cbTipoVenta.getValue().toString());
                           else if (!tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("EFECTIVO")==0 )   
                               vta.setTipo_venta("SISTEMA "+cbTipoVenta.getValue().toString());
                           else vta.setTipo_venta(cbTipoVenta.getValue().toString());
                           
            
                           notas_remision notaRem = new notas_remision();
                           notaRem.setBandera(1);
                           notaRem.setFecha(dpFecha.getValue().toString());
                           notaRem.setFolio(tfFolio.getText());
                           notaRem.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           if (tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("EFECTIVO")==0 )
                             notaRem.setTipo_operacion("REMISION "+cbTipoVenta.getValue().toString());
                           else if (!tfCodigoFactura.getText().isEmpty() && cbTipoVenta.getValue().toString().compareTo("EFECTIVO")==0 )
                             notaRem.setTipo_operacion("SISTEMA "+cbTipoVenta.getValue().toString());
                           else notaRem.setTipo_operacion(cbTipoVenta.getValue().toString());
                           if (Float.parseFloat(tfdescuento.getText()) <= 0)
                             notaRem.setDescuento(0.0f);
                           else
                             notaRem.setDescuento(Float.parseFloat(tfdescuento.getText()));
                           System.out.println(Float.parseFloat(tfdescuento.getText()));
                           
                           Credito cred = new Credito();
                           cred.setBandera(1);
                           cred.setCodigo_cliente(Integer.parseInt(tfCodigoCliente.getText()));
                           cred.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           cred.setFecha(dpFecha.getValue().toString());
            
                           apartado apart = new apartado();
                           apart.setBandera(1);
                           apart.setCodigo_cliente(Integer.parseInt(tfCodigoCliente.getText()));
                           apart.setMonto(Float.parseFloat(lbMontoTotal.getText()));
                           System.out.println("Monto Apartado: "+ apart.getMonto());
                           apart.setFecha(dpFecha.getValue().toString());
            
                           VentaBO ventBo = new VentaBO();
                           String strTipoVenta = cbTipoVenta.getSelectionModel().getSelectedItem().toString();
                           if (strTipoVenta.compareTo("EFECTIVO")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 0);
                                Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                                altMensaje.setContentText("Venta en Efectivo Registrada");
                                altMensaje.setTitle("Informacion-Venta");
                                altMensaje.show();
                            } // "Transferencia"
                           if (strTipoVenta.compareTo("VENTA A CREDITO")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                   ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 1);
                                   Alert altMensaje = new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta a Credito Registrada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            }            
                            if (strTipoVenta.compareTo("APARTADO")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                  ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 2);
                                  Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                  altMensaje.setContentText("Venta en Apartado Registrada");
                                  altMensaje.setTitle("Informacion-Venta");
                                  altMensaje.show();
                            } 
                            if (strTipoVenta.compareTo("TARJETA")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                  ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 3);
                                  Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                  altMensaje.setContentText("Venta Con Tarjeta de credito Registrada");
                                  altMensaje.setTitle("Informacion-Venta");
                                  altMensaje.show();
                            } 
                            if (strTipoVenta.compareTo("TRANSFERENCIA")==0){
                                    LocalDateTime ldtUserActividad = LocalDateTime.now();
                                    System.out.println("Usuario:"+usrActivo.getNombre_completo()+"Fecha Hora: "+ldtUserActividad.toString());
                                   ventBo.guardarVenta(vta, notaRem, cred, apart, detventa, 4);
                                   Alert altMensaje =new Alert(Alert.AlertType.INFORMATION);
                                   altMensaje.setContentText("Venta con Transferencia Registrada");
                                   altMensaje.setTitle("Informacion-Venta");
                                   altMensaje.show();
                            }
                            imprimirNotaRemision();
                            MontoTotal = 0.0f;
                            lbMontoTotal.setText(String.valueOf(MontoTotal));
                            removerVistas( vbAreaTrabajo);
                       }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Folio de Remision");
                        MensajeError.show();             
                       }
                    }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Tipo de Venta");
                        MensajeError.show();             
                    }
                }else{
                        Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                        MensajeError.setTitle("Error");
                        MensajeError.setContentText("Falta Seleccionar Producto");
                        MensajeError.show();             
                }
            }else {
                    Alert MensajeError = new Alert(Alert.AlertType.ERROR);
                    MensajeError.setTitle("Error");
                    MensajeError.setContentText("Falta Seleccionar Cliente");
                    MensajeError.show();
            }
        });
        
//        Button btnCrearNotaRemision = new Button("Generar Nota Remision");
//                btnCrearNotaRemision.setOnAction((event) -> {
//                  try{
//
//                   /* User home directory location */
//                   // String userHomeDirectory = System.getProperty("user.home");
//                    /* Output file location */
//
//                    File file;
//                    JasperReport jasperReport;
//                    file = new File("Reportes/Formatos/notaRemi.jasper");
//                    jasperReport = (JasperReport) JRLoader.loadObject(file);
//                    LocalDateTime ld = LocalDateTime.now();
//                    String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
//                    //String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
//                    String outputFile = "Reportes/NotasRemision/" + File.separatorChar + "NotaRemision"+fechaFile+".pdf";
//                   //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
//                   JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductosSelecc.getItems());
//                   System.out.println("Hay "+tvProductosSelecc.getItems().size());
//                   Map<String, Object> parameters = new HashMap<>();
//                   parameters.put("ItemsDataSource", itemsJRBean);
//                   parameters.put("ProductosDataSource", itemsJRBean);
//                   float total = Float.valueOf(lbMontoTotal.getText());
//                   parameters.put("total", total);
//                   parameters.put("cantDescuento", Descuento);
//                   parameters.put("descuento", tfdescuento.getText());
//                   parameters.put("folio", tfFolio.getText());
//                   parameters.put("nombreCliente", tfNombre.getText());
//                   parameters.put("domicilioFiscal", tfDomicilioFiscal.getText());
//                   parameters.put("cdSucursal", tfCdSucursal.getText());
//                   parameters.put("tipoVenta", cbTipoVenta.getValue().toString());
//                   
//                   //parameters.put("Fecha", LocalDate.now().toString());
//                   /* Generando el PDF */
//                    //C:\Users\dopcan\Documents\NetBeansProjects\ClasesConsultorio\src\gestionconsultorio
//                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//                    JRViewer jrViewer;
//                    JPanel jpanel;
//                    SwingNode swingNode;
//                    jpanel = new JPanel();
//                    swingNode = new SwingNode();
//                    jrViewer = new JRViewer(jasperPrint);
//                    jrViewer.setBounds(0, 0, 1200, 800);
//                    jpanel.setLayout(null);
//                    jpanel.add(jrViewer);
//                    jpanel.setSize(1200, 800);
//                    Pane panePreview = new Pane(); 
//                    panePreview.setPrefSize(1200, 800);
//                    panePreview.getChildren().add(swingNode);
//                    swingNode.setContent(jpanel);
//
//                    StackPane rootSelectClientes = new StackPane();
//                    rootSelectClientes.getChildren().addAll(swingNode);
//
//                    Scene scene = new Scene(rootSelectClientes,1200,800);
//                    Stage stgPpal = new Stage();
//                    stgPpal.setScene(scene);
//                    stgPpal.initModality(Modality.WINDOW_MODAL);
//                    stgPpal.show();  
//                } catch (JRException ex) {
//                    ex.printStackTrace();
//                }             
//                });
                


        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnGuardar);
        hbBotonesInferiores.setSpacing(10);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(vbClienteSeleccion, spVenta, gpBloqueVenta, spProducto, gpBloqueProducto, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabInventario, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead, hbBody);
        
        return vbVistaPpal; 
    }
    
    private void imprimirNotaRemision(){
        
            try{

             /* User home directory location */
             // String userHomeDirectory = System.getProperty("user.home");
              /* Output file location */

              File file;
              JasperReport jasperReport;
              file = new File("Reportes/Formatos/notaRemi.jasper");
              jasperReport = (JasperReport) JRLoader.loadObject(file);
              LocalDateTime ld = LocalDateTime.now();
              String fechaFile = String.valueOf(ld.getDayOfMonth())+String.valueOf(ld.getMonth())+String.valueOf(ld.getYear())+String.valueOf(ld.getHour())+String.valueOf(ld.getMinute())+String.valueOf(ld.getSecond());
              //String outputFile = userHomeDirectory + File.separatorChar + "ReporteInventario"+fechaFile+".pdf";
              String outputFile = "Reportes/NotasRemision/" + File.separatorChar + "NotaRemision"+fechaFile+".pdf";
             //JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductos.getItems().subList(0, tvProductos.getItems().size()-1));
             JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tvProductosSelecc.getItems());
             System.out.println("Hay "+tvProductosSelecc.getItems().size());
             Map<String, Object> parameters = new HashMap<>();
             parameters.put("ItemsDataSource", itemsJRBean);
             parameters.put("ProductosDataSource", itemsJRBean);
             float total = Float.valueOf(lbMontoTotal.getText());
             parameters.put("total", total);
             parameters.put("cantDescuento", Descuento);
             parameters.put("descuento", tfdescuento.getText());
             parameters.put("folio", tfFolio.getText());
             parameters.put("nombreCliente", tfNombre.getText());
             parameters.put("domicilioFiscal", tfDomicilioFiscal.getText());
             parameters.put("cdSucursal", tfCdSucursal.getText());
             parameters.put("tipoVenta", cbTipoVenta.getValue().toString());
             //JasperCompileManager.compileReportToFile("our_jasper_template.jrxml",
             //  "our_compiled_template.jasper");
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
        }

    private void ventanaSeleccionarClientes(){
           Stage stgPpal = new Stage();
           TableView tvClientes = new TableView();

            TableColumn<CLIENTE, String> codClientColumna = new TableColumn<>("Codigo Cliente");
            codClientColumna.setMinWidth(120);
            codClientColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));

            TableColumn<CLIENTE, String> nombreColumna = new TableColumn<>("Nombre");
            nombreColumna.setMinWidth(320);
            nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));

            TableColumn<CLIENTE, String> razonSocialColumna = new TableColumn<>("Razon Social");
            razonSocialColumna.setMinWidth(320);
            razonSocialColumna.setCellValueFactory(new PropertyValueFactory<>("razon_social"));

            TableColumn<CLIENTE, String> domFiscalColumna = new TableColumn<>("Domicilio Fiscal");
            domFiscalColumna.setMinWidth(320);
            domFiscalColumna.setCellValueFactory(new PropertyValueFactory<>("domicilio_fiscal"));

            TableColumn<CLIENTE, String> telefonoColumna = new TableColumn<>("Telefono");
            telefonoColumna.setMinWidth(120);
            telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));

            TableColumn<CLIENTE, String> rfcColumna = new TableColumn<>("RFC");
            rfcColumna.setMinWidth(120);
            rfcColumna.setCellValueFactory(new PropertyValueFactory<>("rfc"));

            TableColumn<CLIENTE, String> emailColumna = new TableColumn<>("E-Mail");
            emailColumna.setMinWidth(120);
            emailColumna.setCellValueFactory(new PropertyValueFactory<>("email"));

            tvClientes.getColumns().addAll(codClientColumna, nombreColumna, razonSocialColumna, domFiscalColumna,
                    telefonoColumna, rfcColumna, emailColumna);

             List<CLIENTE> lstInv = new ArrayList<>();
             List<String> lstWhere = new ArrayList<>();
             lstWhere.add("codigo_cliente is not null");
             lstInv=cliDAO.consultarClientes(lstWhere);
             ObservableList obList = FXCollections.observableArrayList(lstInv);
             tvClientes.setItems(obList);

           stgPpal.setTitle("Seleccion del Cliente");

           VBox vbPpal = new VBox();
           vbPpal.setSpacing(5);

           Button btnCancelar = new Button("Cancelar");
           btnCancelar.setOnAction((ActionEvent e)->{
               stgPpal.close();
           });
           Button btnSeleccionar = new Button("Seleccionar");
           btnSeleccionar.setOnAction((ActionEvent e)->{
               clIdent =(CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
               this.tfCodigoCliente.setText(String.valueOf(clIdent.getCodigo_cliente()));
               this.tfNombre.setText(clIdent.getNombre());
               this.tfDomicilioFiscal.setText(clIdent.getDomicilio_fiscal());
               this.tfEmail.setText(clIdent.getEmail());
               this.tfRFC.setText(clIdent.getRfc());
               this.tfRazonSocial.setText(clIdent.getRazon_social());
               this.tfTelefono.setText(clIdent.getTelefono());
               stgPpal.close();
           });

           tvClientes.setOnKeyPressed((event) -> {
               if (event.getCode()== KeyCode.ENTER){
                   clIdent =(CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
                   this.tfCodigoCliente.setText(String.valueOf(clIdent.getCodigo_cliente()));
                   this.tfNombre.setText(clIdent.getNombre());
                   this.tfDomicilioFiscal.setText(clIdent.getDomicilio_fiscal());
                   this.tfEmail.setText(clIdent.getEmail());
                   this.tfRFC.setText(clIdent.getRfc());
                   this.tfRazonSocial.setText(clIdent.getRazon_social());
                   this.tfTelefono.setText(clIdent.getTelefono());
                   stgPpal.close();
               }
           });
           HBox hbBotones = new HBox();
           hbBotones.setSpacing(5);
           hbBotones.getChildren().addAll(btnCancelar, btnSeleccionar);

           vbPpal.getChildren().addAll(tvClientes,hbBotones);

           StackPane rootSelectClientes = new StackPane();
           rootSelectClientes.getChildren().addAll(vbPpal);

           Scene scene = new Scene(rootSelectClientes,320,470);
           stgPpal.setScene(scene);
           stgPpal.initModality(Modality.WINDOW_MODAL);
           stgPpal.show();
        }   
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
}
