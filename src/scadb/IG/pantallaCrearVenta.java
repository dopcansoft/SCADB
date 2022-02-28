/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author dopcan
 */
public class pantallaCrearVenta {
    
   TextField tfCodigoCliente = new TextField();
   TextField tfNombre = new TextField();
   TextField tfRazonSocial = new TextField();
   TextField tfDomicilioFiscal= new TextField();
   TextField tfTelefono = new TextField();
   TextField tfRFC = new TextField();
   TextField tfEmail = new TextField();
   TextField tfdescuento = new TextField();
   Label lbDescuento = new Label();
   Button btntDescuento = new Button();
  
    float MontoTotal = 0.0f;
    float Descuento = 10.0f;
   float MontoOriginal = 0.0f;
    
    public VBox vistaCrearVenta(){
        
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
        
        //Etiquetas/datos de la Remisión
        Label lbFolio = new Label("Folio de Nota: ");
        Label lbFecha = new Label("Fecha: ");
        
        TextField tfFolio = new TextField();
        DatePicker dpFecha = new DatePicker(LocalDate.now());

        //Etiquetas/Datos de la Venta
        Label lbEtiquetaMonto = new Label("TOTAL: $ ");
        Label lbMontoTotal = new Label("100.0");
        MontoOriginal = Float.parseFloat(lbMontoTotal.getText());
        lbEtiquetaMonto.setFont(fuente);
        lbMontoTotal.setFont(fuente);
        
        btntDescuento.setOnAction((event) -> {
        MontoTotal=MontoOriginal;
        Descuento = MontoTotal / 100 * Float.parseFloat(tfdescuento.getText());
        MontoTotal = MontoOriginal - Descuento;
        lbMontoTotal.setText(String.valueOf(MontoTotal));
        });
        
	Label lbTipo_venta = new Label("Tipo de Venta: ");
        Label lbCodigoFactura = new Label("Codigo Factura");
        
	Label lbCantidad  = new Label("Cantidad");
	Label lbCodigoProd  = new Label("Codigo Producto: ");
        Label lbDescProducto = new Label ("Descripción Producto: ");
	Label lbPrecioMenudeo  = new Label("Precio Menudeo: ");
	Label lbPrecioMayoreo  = new Label("Precrio Mayoreo: ");
        Label lbPrecioVenta = new Label("Precio Venta");
        
        ObservableList<String> lstOpcionesTipoVenta = FXCollections.observableArrayList("EFECTIVO","VENTA A CREDITO", "APARTADO", "TARJETA", "TRANSFERENCIA");
        ComboBox cbTipoVenta = new ComboBox(lstOpcionesTipoVenta);
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
        
        TableColumn<String, Integer> claveProdColumna = new TableColumn<>("Codigo Producto");
        claveProdColumna.setMinWidth(120);
        claveProdColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));

        TableColumn<String, Integer> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(120);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        
        TableColumn<String, Integer> idUbicacionColumna = new TableColumn<>("Id Ubicación");
        idUbicacionColumna.setMinWidth(120);
        idUbicacionColumna.setCellValueFactory(new PropertyValueFactory<>("id_ubicacion"));
        
        TableColumn<String, Float> pMenudeoColumna = new TableColumn<>("Precio Menudeo");
        pMenudeoColumna.setMinWidth(120);
        pMenudeoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_menudeo"));        

        TableColumn<String, Float> pMayoreoColumna = new TableColumn<>("Precio Mayoreo");
        pMayoreoColumna.setMinWidth(120);
        pMayoreoColumna.setCellValueFactory(new PropertyValueFactory<>("precio_mayoreo"));
        
        TableColumn<String, String> descripcionColumna = new TableColumn<>("Descripción");
        descripcionColumna.setMinWidth(120);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        TableColumn<String, String> uMedidaColumna = new TableColumn<>("Unidad Medidad");
        uMedidaColumna.setMinWidth(120);
        uMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("unidad_medida"));

        TableColumn<String, Float> cCompraColumna = new TableColumn<>("Costo Compra");
        cCompraColumna.setMinWidth(120);
        cCompraColumna.setCellValueFactory(new PropertyValueFactory<>("costo_compra"));
        
        TableColumn<String, Integer> codProvColumna = new TableColumn<>("Codigo Proveedor");
        codProvColumna.setMinWidth(120);
        codProvColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prov"));
        
        tvInventario.getColumns().addAll(claveProdColumna, descripcionColumna, existenciaColumna,
                pMenudeoColumna, pMayoreoColumna, uMedidaColumna, idUbicacionColumna, 
                codProvColumna, cCompraColumna);
        
        tvInventario.setOnMouseClicked((event) -> {
            tfCodigoProducto.setText("");
            tfDescrProd.setText("");
            tfPrecioMenudeo.setText("");
            tfPrecioMayor.setText("");
            rbPrecioMenudeo.setSelected(true);
            tfPrecioVenta.setText(tfPrecioMenudeo.getText());
        });
        
        tvInventario.setOnKeyPressed((event) ->{ 
            if(event.getCode()==KeyCode.ENTER){
            }
        });
        MenuItem miEliminarDetVenta = new MenuItem("Eliminar");
        ContextMenu cmTabVentas = new ContextMenu();
        cmTabVentas.getItems().add(miEliminarDetVenta);
        
        Label lbProducto = new Label("Tabla Producto Seleccionados: ");
        TableView tvProductosSelecc = new TableView();
        tvProductosSelecc.setPrefHeight(350);
        tvProductosSelecc.setPrefWidth(550);
        //id_detalle_venta clave_prod cantidad codigo_nota_venta 
        //codigo_prod precio_menudeo precio_mayoreo descrprod 
        
        TableColumn<String, Integer> idDetalleVentaColumna = new TableColumn<>("Id Detalle Venta");
        idDetalleVentaColumna.setMinWidth(120);
        idDetalleVentaColumna.setCellValueFactory(new PropertyValueFactory<>("id_detalle_venta"));
        
        TableColumn<String, Integer> codigoProductColumna = new TableColumn<>("Codigo Producto");
        codigoProductColumna.setMinWidth(80);
        codigoProductColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<String, String> descrProductColumna = new TableColumn<>("Descripción Producto");
        descrProductColumna.setMinWidth(220);
        descrProductColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<String, Integer> cantidadProductColumna = new TableColumn<>("Cantidad");
        cantidadProductColumna.setMinWidth(80);
        cantidadProductColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<String, Integer> codigoNotaVentaColumna = new TableColumn<>("Codigo Nota Venta");
        codigoNotaVentaColumna.setMinWidth(120);
        codigoNotaVentaColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_nota_venta"));

        TableColumn<String, Integer> precioVentaColumna = new TableColumn<>("Precio Venta");
        precioVentaColumna.setMinWidth(120);
        precioVentaColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<String, Integer> subtotalColumna = new TableColumn<>("Sub-total");
        subtotalColumna.setMinWidth(120);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductosSelecc.getColumns().addAll(codigoProductColumna, descrProductColumna, cantidadProductColumna, precioVentaColumna, subtotalColumna);
        tvProductosSelecc.setContextMenu(cmTabVentas);
        miEliminarDetVenta.setOnAction((event) -> {
            tvProductosSelecc.getItems().remove(
                    tvProductosSelecc.getSelectionModel().getSelectedIndex());
            
            lbMontoTotal.setText("0.0");

        });       

        Button btnAgregarProducto = new Button("Agregar Producto");
        btnAgregarProducto.setOnAction((ActionEvent e)->{
            
        });
        
        
        Button btnSeleccionarCliente = new Button("Seleccionar Cliente..");
        btnSeleccionarCliente.setOnAction((ActionEvent e)->{
           
        });
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbCodigo_cliente, 0, 0);
        gpClienteSeleccionado.add(tfCodigoCliente, 1, 0);
        gpClienteSeleccionado.add(lbNombre, 2, 0);
        gpClienteSeleccionado.add(tfNombre, 3, 0);
         
        GridPane gpDesscuento = new GridPane();
        gpDesscuento.add(lbDescuento, 0,0);
        gpDesscuento.add(tfdescuento, 1,0);
        gpDesscuento.add(btntDescuento, 1,1);
        gpDesscuento.setHgap(10);
        gpDesscuento.setVgap(10);
        
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
        btnBuscarProductos.setOnAction((ActionEvent e)->{
         
          if (rbTodos.isSelected()){
           //String titulo = "MODIFICAR PRODUCTOS ("+String.valueOf(tvInventario.getItems().size())+" Seleccionados)";
         } 
         
         if (rbCodigo.isSelected()){
           //lstInv=invent.consultarInventario(lstWhere);
           //ObservableList obList = FXCollections.observableArrayList(lstInv);
         }
         if (rbDescripcion.isSelected()){
             
           //lstInv=invent.consultarInventario(lstWhere);
           //ObservableList obList = FXCollections.observableArrayList(lstInv);
         }
         
         if (rbCategoria.isSelected()){
           
         }
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
            }
        });
        
        Button btnGuardar = new Button("Registrar Venta");
        btnGuardar.setOnAction((event) -> {
           
        });
        
        HBox hbBotonesInferiores = new HBox();
        hbBotonesInferiores.setAlignment(Pos.CENTER_RIGHT);
        hbBotonesInferiores.getChildren().addAll(btnCancelar, btnGuardar);
        
        VBox vbTabProducto = new VBox();
        vbTabProducto.setSpacing(5);
        vbTabProducto.getChildren().addAll(vbClienteSeleccion, spVenta, gpBloqueVenta, spProducto, gpBloqueProducto, hbBotonesInferiores);
        
        
        HBox hbBody = new HBox();
        hbBody.getChildren().addAll(vbTabInventario, vbTabProducto);
        
        vbVistaPpal.getChildren().addAll(lbTituloVista, vbHead, hbBody);
        
        return vbVistaPpal; 
    }
    
}
