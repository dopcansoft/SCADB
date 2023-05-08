/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb;

import DTO.usuario;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scadb.DAO.usuarioDAO;
import scadb.IG.PantallaAgregarGastos;
import scadb.IG.PantallaAgregarProducto;
import scadb.IG.PantallaConsultarGastos;
import scadb.IG.PantallaEliminarCLientes;
import scadb.IG.PantallaEliminarCategoria;
import scadb.IG.PantallaEliminarDatosDeProvedor;
import scadb.IG.PantallaEliminarGastos;
import scadb.IG.PantallaEliminarProducto;
import scadb.IG.PantallaGestionDeAparatados;
import scadb.IG.PantallaGestionDeCreditos;
import scadb.IG.PantallaImportarProducto;
import scadb.IG.PantallaModificarCLiente;
import scadb.IG.PantallaModificarCategoria;
import scadb.IG.PantallaModificarDatosDeProvedor;
import scadb.IG.PantallaModificarGastos;
import scadb.IG.PantallaModificarProducto;
import scadb.IG.PantallaNuevaCategoria;
import scadb.IG.PantallaNuevoCliente;
import scadb.IG.PantallaNuevoProvedor;
import scadb.IG.pantallaCancelarVenta;
import scadb.IG.pantallaConsultarVenta;
import scadb.IG.pantallaConsultarVentasCanceladas;
import scadb.IG.pantallaCrearVenta;
import scadb.IG.pantallaExportarXLSGasto;
import scadb.IG.pantallaExportarXlsVenta;
import scadb.IG.pantallaExportarXLSApartado;
import scadb.IG.pantallaExportarXLSCredito;
import scadb.IG.pantallaReporteInventario;

/**
 *
 * @author dopcan
 */
public class SCADB extends Application {
    Stage primarioStage;
    String UsuarioActivo;
    usuario usrActivo;
    HBox hbBarraEstado = new HBox();
    List<String> lstWhereConcepto = new ArrayList<>();
    usuarioDAO userDAO = new usuarioDAO();
    
    MenuItem miCrearVentas = new MenuItem("Crear");
    MenuItem miConsultarVentas = new MenuItem("Consultar");
    MenuItem miConsultarVentasCanceladas = new MenuItem("Consultar Ventas Canceladas");
    MenuItem miCancelarVentas = new MenuItem("Cancelar");
    
    MenuItem miExportaVentasXLS = new MenuItem("Exportar  Ventas a xls");
    MenuItem miExportaGastosXLS = new MenuItem("Exportar Gastos a xls");
    MenuItem miExportaCreditosXLS = new MenuItem("Exportar Creditos a xls");
    MenuItem miExportaApartadosXLS = new MenuItem("Exportar Apartados a xls");

    MenuItem miAgregarProducto = new MenuItem("Agregar Producto");
    MenuItem miEliminarProducto = new MenuItem("Eliminar Producto");
    MenuItem miModificarProducto = new MenuItem("Modificar Producto");
    MenuItem miImportarProducto = new MenuItem("Importar Productos");
    MenuItem miReportesInventario = new MenuItem("Reportes de Inventario");

    MenuItem miNuevaCategoria = new MenuItem("Nueva Categoria");
    MenuItem miModificarCategoria = new MenuItem("Modificar Categoria");
    MenuItem miEliminarCategoria = new MenuItem("Eliminar Categoria");

    MenuItem miNuevoCliente = new MenuItem("Nuevo CLiente");
    MenuItem miModificarDatosDeCLiente = new MenuItem("Modificar Datos De Clientes");
    MenuItem miEliminarDatosDeCliente = new MenuItem("Eliminar Datos De Clientes");
    MenuItem miGestionDeCreditos = new MenuItem("Gestion De Creditos");
    MenuItem miGestionDeApartados = new MenuItem("Gestion De Apartados");

    MenuItem miNuevoProveedor = new MenuItem("Nuevo Proveedor");
    MenuItem miModificarDatosDeProvedor = new MenuItem("Modificar Datos De Porvedor");
    MenuItem miEliminarDatosDeProvedor = new MenuItem("Eliminar Datos De Provedor");

    MenuItem miAgregarGastos = new MenuItem("Agregar Gastos");
    MenuItem miConsultarGastos = new MenuItem("Consulta Gastos");
    MenuItem miModificarGastos = new MenuItem("Modificar Gastos");
    MenuItem miEliminarGastos = new MenuItem("Eliminar Gastos");
    
    @Override
    public void start(Stage primaryStage) {
        primarioStage = primaryStage;
        // pantallas
        pantallaCrearVenta pCrearVenta = new pantallaCrearVenta();
        pantallaConsultarVenta pConsultarVenta = new pantallaConsultarVenta();
        pantallaConsultarVentasCanceladas pConsultarVentaCanceladas = new pantallaConsultarVentasCanceladas();
        pantallaCancelarVenta pCancelarVenta = new pantallaCancelarVenta();
        pantallaExportarXlsVenta pExportaXlsVenta = new pantallaExportarXlsVenta();
        pantallaExportarXLSGasto pExportaGasto = new pantallaExportarXLSGasto();
        pantallaExportarXLSApartado pExportaApartado = new pantallaExportarXLSApartado();
        pantallaExportarXLSCredito pExportaCredito = new pantallaExportarXLSCredito();

        PantallaAgregarProducto pAgregarProducto = new PantallaAgregarProducto();
        PantallaModificarProducto pModificarProducto = new PantallaModificarProducto();
        PantallaEliminarProducto pEliminarProdcto = new PantallaEliminarProducto();
        pantallaReporteInventario pReporteInventario = new pantallaReporteInventario();

        PantallaNuevaCategoria pNuevaCategoria = new PantallaNuevaCategoria();
        PantallaModificarCategoria pModificarCategoria = new PantallaModificarCategoria();
        PantallaEliminarCategoria pEliminarCategoria = new PantallaEliminarCategoria();

        PantallaNuevoCliente pNuevoCLiente = new PantallaNuevoCliente();
        PantallaModificarCLiente pModificarCliente = new PantallaModificarCLiente();
        PantallaEliminarCLientes pEliminaraDatosDelCliente = new PantallaEliminarCLientes();
        PantallaGestionDeCreditos pGestionDeCreditos = new PantallaGestionDeCreditos();
        PantallaGestionDeAparatados pGestionDeApartados = new PantallaGestionDeAparatados();
         
        PantallaNuevoProvedor pNuevoProvedo = new PantallaNuevoProvedor();
        PantallaModificarDatosDeProvedor pModificarDatosDeProvedor = new PantallaModificarDatosDeProvedor();
        PantallaEliminarDatosDeProvedor pEliminarDatosDeProvedor = new PantallaEliminarDatosDeProvedor();
         
        PantallaAgregarGastos pAgregarGasto = new PantallaAgregarGastos();
        PantallaModificarGastos pModificarGastos = new PantallaModificarGastos();
        PantallaEliminarGastos pEliminarGastos = new PantallaEliminarGastos();
        PantallaConsultarGastos pConsultarGastos =  new PantallaConsultarGastos();
        
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double h = screenSize.getHeight();
        double w = screenSize.getWidth();

        VBox vbPrincipal = new VBox();
        VBox vbAreTrabajo = new VBox();

        Menu meVentas = new Menu("Ventas");
        meVentas.getItems().addAll(miCrearVentas, miConsultarVentas, miCancelarVentas, miConsultarVentasCanceladas);
        Menu meHtasExport = new Menu("Exportacion");
        meHtasExport.getItems().addAll(miExportaVentasXLS, miExportaGastosXLS, miExportaCreditosXLS, miExportaApartadosXLS);
        Menu meCategorias = new Menu("Categorias");
        meCategorias.getItems().addAll(miNuevaCategoria, miModificarCategoria, miEliminarCategoria);
        Menu meListaProductos = new Menu("Inventario");
        meListaProductos.getItems().addAll(miAgregarProducto, miEliminarProducto, miModificarProducto, miImportarProducto, meCategorias, miReportesInventario);
        Menu mCreditos = new Menu("Creditos");
        mCreditos.getItems().addAll(miGestionDeCreditos);
        Menu mApartados = new Menu("Apartados");
        mApartados.getItems().addAll(miGestionDeApartados);
        Menu mClientes = new Menu("Clientes");
        mClientes.getItems().addAll(miNuevoCliente, miModificarDatosDeCLiente, miEliminarDatosDeCliente, mCreditos, mApartados);
        Menu mProveedores = new Menu("Proveedores");
        mProveedores.getItems().addAll(miNuevoProveedor, miModificarDatosDeProvedor,miEliminarDatosDeProvedor);
        Menu mGastos = new Menu("Gastos");    
        mGastos.getItems().addAll(miAgregarGastos, miConsultarGastos, miModificarGastos, miEliminarGastos);
    
//        MenuItem miGenerarReporte = new MenuItem("Generar Reporte");
//        MenuItem miSubirReporte = new MenuItem("Subir Reporte");
//        Menu mReporte = new Menu("Reporte");
//        mReporte.getItems().addAll(miGenerarReporte, miSubirReporte);
        
        MenuBar mbPincipal = new MenuBar(meVentas, meHtasExport, meListaProductos, mClientes, mProveedores, mGastos);

        //Eventos del Menu
        miEliminarDatosDeProvedor.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pEliminarDatosDeProvedor.vistaEliminarProveedor(vbAreTrabajo));
        });
        
        miModificarDatosDeProvedor.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pModificarDatosDeProvedor.vistaModificarProveedor(vbAreTrabajo));
        });
        
        miNuevoProveedor.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pNuevoProvedo.vistaNuevoProveedor(vbAreTrabajo));
        });
        
        miGestionDeApartados.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pGestionDeApartados.vistaGestionApartadosCliente(vbAreTrabajo));
        });
        
        miGestionDeCreditos.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pGestionDeCreditos.vistaGestionCreditosCliente(vbAreTrabajo));
        });
        
        miEliminarDatosDeCliente.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pEliminaraDatosDelCliente.vistaEliminarCliente(vbAreTrabajo));
        });
        
        miModificarDatosDeCLiente.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pModificarCliente.vistaModificarCliente(vbAreTrabajo));
        });
        
        miNuevoCliente.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pNuevoCLiente.vistaNuevoCliente(vbAreTrabajo));
        });
        
        miCrearVentas.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pCrearVenta.vistaCrearVenta(vbAreTrabajo));
        });
        miEliminarProducto.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pEliminarProdcto.vistaEliminarProducto(vbAreTrabajo));
        });

        miImportarProducto.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(PantallaImportarProducto.VistaImportarProducto());
        });

        miAgregarProducto.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pAgregarProducto.vistaNuevoProducto(vbAreTrabajo));
        });

        miModificarProducto.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pModificarProducto.vistaModificarProducto(vbAreTrabajo));
        });

        miReportesInventario.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pReporteInventario.vistaReporteInventario(vbAreTrabajo));
        });
           
        miNuevaCategoria.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pNuevaCategoria.vistaNuevaCategoria(vbAreTrabajo));
        });
        
        miModificarCategoria.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pModificarCategoria.vistaModificarCategoria(vbAreTrabajo));
        });
        
        miEliminarCategoria.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pEliminarCategoria.vistaEliminarCategoria(vbAreTrabajo));
        });

        miConsultarVentas.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pConsultarVenta.vistaConsultarVenta(vbAreTrabajo));
        });
        
        miConsultarVentasCanceladas.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pConsultarVentaCanceladas.vistaConsultarVentaCancelada(vbAreTrabajo));
        });

        miCancelarVentas.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pCancelarVenta.vistaCancelarVenta(vbAreTrabajo));

        });

        miExportaVentasXLS.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pExportaXlsVenta.vistaExportarXLS(vbAreTrabajo));

        });
        miExportaGastosXLS.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pExportaGasto.vistaExportarGasto(vbAreTrabajo));

        });
        
        miExportaCreditosXLS.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pExportaCredito.vistaExportarCredito(vbAreTrabajo));

        });
        
        miExportaApartadosXLS.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pExportaApartado.vistaExportarApartado(vbAreTrabajo));

        });
        
        miAgregarGastos.setOnAction((event) -> {
              if (vbAreTrabajo.getChildren().size() > 0){ 
                  vbAreTrabajo.getChildren().clear();
              }
               vbAreTrabajo.getChildren().addAll(pAgregarGasto.vistaAgregarGasto(vbAreTrabajo));
        });
        
        miModificarGastos.setOnAction((event) -> {
              if (vbAreTrabajo.getChildren().size() > 0){ 
               vbAreTrabajo.getChildren().clear();
              }
               vbAreTrabajo.getChildren().addAll(pModificarGastos.vistaModificarGasto(vbAreTrabajo));
        });
        
        miEliminarGastos.setOnAction((event) -> {
              if (vbAreTrabajo.getChildren().size() > 0){ 
               vbAreTrabajo.getChildren().clear();
              }
               vbAreTrabajo.getChildren().addAll(pEliminarGastos.vistaEliminarGasto(vbAreTrabajo));
        });
        
        miConsultarGastos.setOnAction((event) -> {
              if (vbAreTrabajo.getChildren().size() > 0){ 
               vbAreTrabajo.getChildren().clear();
              }
               vbAreTrabajo.getChildren().addAll(pConsultarGastos.vistaConsultarGasto(vbAreTrabajo));
        });

        vbPrincipal.getChildren().addAll(mbPincipal, vbAreTrabajo);

        StackPane root = new StackPane();
        root.getChildren().add(vbPrincipal);

        Scene scene = new Scene(root, w, h);

        primaryStage.setTitle("scadb!");
        primaryStage.setScene(scene);
        loginEmpresa();
        //primaryStage.show();
    }
    
    public void loginEmpresa(){
        Stage loginStage = new Stage();
        loginStage.setTitle("TIENDAS PLAMAR LOGIN");
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        
        Label lbTituloVista = new Label("ACCESO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        Label lbUsuario = new Label("Usuario:");
        Label lbContrasena = new Label("Contraseña:");        
        TextField tfUsuario = new TextField();
        tfUsuario.setText("");
        tfUsuario.setPrefWidth(180);
        tfUsuario.setAlignment(Pos.CENTER);
        PasswordField tfContrasena = new PasswordField();
        tfContrasena.setPrefWidth(180);
        tfContrasena.setAlignment(Pos.CENTER);        
        tfContrasena.setPromptText("********");
        tfContrasena.setText("");
        
        GridPane gpUsuario = new GridPane();
        gpUsuario.setPadding(new Insets(5, 5, 5, 5));
        gpUsuario.setVgap(10);
        gpUsuario.setHgap(10);
        gpUsuario.setAlignment(Pos.CENTER);
        
        gpUsuario.add(lbUsuario,0,1);
        gpUsuario.add(tfUsuario,1,1);
        gpUsuario.add(lbContrasena, 0, 2);
        gpUsuario.add(tfContrasena, 1, 2);
        
        Button btnIngresar = new Button("Ingresar");
        btnIngresar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            Alert aviso = new Alert(Alert.AlertType.WARNING);
            if (tfUsuario.getText().isEmpty() || tfContrasena.getText().isEmpty()){
                aviso.setContentText("Campo(s) vacío(s)");
                Optional<ButtonType> action = aviso.showAndWait();
                Boolean vUsuarioCorrecto = false;
            }
            else{
                usuario uslogin = new usuario();
                lstWhereConcepto.clear();
                lstWhereConcepto.add("usuario = '"+tfUsuario.getText()+"' ");
                uslogin = userDAO.consultaUsuario(lstWhereConcepto).get(0);
                
                if (tfContrasena.getText().equals(uslogin.getClave())){
                        aviso.setContentText("Acceso Permitido");
                        //Optional<ButtonType> action = aviso.showAndWait();
                        if (uslogin.getTipo().toString().equals("VENTA")){
                            //miCancelarVentas.setDisable(true);
                            //miModificarProducto.setDisable(true);
                            //miImportarProducto.setDisable(true);
                            //miEliminarProducto.setDisable(true);
                            //miEliminarDatosDeCliente.setDisable(true);
                            //miNuevaCategoria.setDisable(true);
                            //miModificarCategoria.setDisable(true);
                            //miEliminarCategoria.setDisable(true);
                            //miReportesInventario.setDisable(true);
                            //miNuevoCliente.setDisable(true);
                            //miModificarDatosDeCLiente.setDisable(true);
                            //miEliminarDatosDeCliente.setDisable(true);
                            //miNuevoProveedor.setDisable(true);
                            //miModificarDatosDeProvedor.setDisable(true);
                            //miEliminarDatosDeProvedor.setDisable(true);
                            
                        
                        }else if (uslogin.getTipo().toString().equals("INVENTARIO")){
                            //mAdmin.setDisable(true);
                        }
                        UsuarioActivo = uslogin.getNombre_completo();
                        usrActivo = uslogin;
                        Label lbUsuarioActivo = new Label(UsuarioActivo);
                        hbBarraEstado.setAlignment(Pos.CENTER_RIGHT);
                        hbBarraEstado.getChildren().add(lbUsuarioActivo);
                        loginStage.close();
                        primarioStage.show();
                }
                else{
                    aviso.setContentText("Usuario y/o Contraseña Incorrectos");
                    Optional<ButtonType> action = aviso.showAndWait();
                }
            }
            }
        });
        Button btnSalir = new Button("   Salir   ");
        btnSalir.setOnAction((ActionEvent e)->{
            loginStage.close();
        });
        
        HBox hbBotones = new HBox();
        hbBotones.setSpacing(10);
        hbBotones.getChildren().addAll(btnIngresar, btnSalir);
        hbBotones.setAlignment(Pos.CENTER);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpUsuario, hbBotones);
        
        StackPane rootSelectClientes = new StackPane();
        rootSelectClientes.getChildren().addAll(vbPpal);
        Scene scene = new Scene(rootSelectClientes,300,200);
        loginStage.setScene(scene);
        loginStage.setIconified(false);
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
