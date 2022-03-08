/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb;

import java.awt.Dimension;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scadb.IG.PantallaAgregarProducto;
import scadb.IG.PantallaEliminarCLientes;
import scadb.IG.PantallaEliminarCategoria;
import scadb.IG.PantallaEliminarDatosDeProvedor;
import scadb.IG.PantallaEliminarProducto;
import scadb.IG.PantallaGestionDeAparatados;
import scadb.IG.PantallaGestionDeCreditos;
import scadb.IG.PantallaImportarProducto;
import scadb.IG.PantallaModificarCLiente;
import scadb.IG.PantallaModificarCategoria;
import scadb.IG.PantallaModificarDatosDeProvedor;
import scadb.IG.PantallaModificarProducto;
import scadb.IG.PantallaNuevaCategoria;
import scadb.IG.PantallaNuevoCliente;
import scadb.IG.PantallaNuevoProvedor;
import scadb.IG.pantallaCancelarVenta;
import scadb.IG.pantallaConsultarVenta;
import scadb.IG.pantallaCrearVenta;
import scadb.IG.pantallaExportarDbVenta;
import scadb.IG.pantallaExportarXlsVenta;

/**
 *
 * @author dopcan
 */
public class SCADB extends Application {

    @Override
    public void start(Stage primaryStage) {
        // pantallas

        pantallaCrearVenta pCrearVenta = new pantallaCrearVenta();
        pantallaConsultarVenta pConsultarVenta = new pantallaConsultarVenta();
        pantallaCancelarVenta pCancelarVenta = new pantallaCancelarVenta();
        pantallaExportarXlsVenta pExportaXlsVenta = new pantallaExportarXlsVenta();
        pantallaExportarDbVenta pExportaDbVenta = new pantallaExportarDbVenta();

        PantallaAgregarProducto pAgregarProducto = new PantallaAgregarProducto();
        PantallaModificarProducto pModificarProducto = new PantallaModificarProducto();
        PantallaEliminarProducto pEliminarProdcto = new PantallaEliminarProducto();

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
         
         
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double h = screenSize.getHeight();
        double w = screenSize.getWidth();

        VBox vbPrincipal = new VBox();
        VBox vbAreTrabajo = new VBox();

        MenuItem miCrearVentas = new MenuItem("Crear");
        MenuItem miConsultarVentas = new MenuItem("Consultar");
        MenuItem miCancelarVentas = new MenuItem("Cancelar");

        Menu meVentas = new Menu("Ventas");
        meVentas.getItems().addAll(miCrearVentas, miConsultarVentas, miCancelarVentas);

        MenuItem miExportaXLS = new MenuItem("Exportar xls");
        MenuItem miExportaDB = new MenuItem("Exportar DB");

        Menu meHtasExport = new Menu("Herramientas de Exportacion");
        meHtasExport.getItems().addAll(miExportaXLS, miExportaDB);

        MenuItem miAgregarProducto = new MenuItem("Agregar Producto");
        MenuItem miEliminarProducto = new MenuItem("Eliminar Producto");
        MenuItem miModificarProducto = new MenuItem("Modificar Producto");
        MenuItem miImportarProducto = new MenuItem("Importar Productos");

        MenuItem miNuevaCategoria = new MenuItem("Nueva Categoria");
        MenuItem miModificarCategoria = new MenuItem("Modificar Categoria");
        MenuItem miEliminarCategoria = new MenuItem("Eliminar Categoria");
        Menu meCategorias = new Menu("Categorias");
        
        
        meCategorias.getItems().addAll(miNuevaCategoria, miModificarCategoria, miEliminarCategoria);

        Menu meListaProductos = new Menu("Inventario");
        meListaProductos.getItems().addAll(miAgregarProducto, miEliminarProducto, miModificarProducto, miImportarProducto, meCategorias);

        MenuItem miNuevoCliente = new MenuItem("Nuevo CLiente");
        MenuItem miModificarDatosDeCLiente = new MenuItem("Modificar Datos De Clientes");
        MenuItem miEliminarDatosDeCliente = new MenuItem("Eliminar Datos De Clientes");
        MenuItem miGestionDeCreditos = new MenuItem("Gestion De Creditos");
        MenuItem miGestionDeApartados = new MenuItem("Gestion De Aprtados");
        
        Menu mCreditos = new Menu("Creditos");
        mCreditos.getItems().addAll(miGestionDeCreditos);
        Menu mApartados = new Menu("Apartados");
        mApartados.getItems().addAll(miGestionDeApartados);
        Menu mClientes = new Menu("Clientes");
        mClientes.getItems().addAll(miNuevoCliente, miModificarDatosDeCLiente, miEliminarDatosDeCliente, mCreditos, mApartados);
        
        
        MenuItem miNuevoProveedor = new MenuItem("Nuevo Proveedor");
        MenuItem miModificarDatosDeProvedor = new MenuItem("Modificar Datos De Porvedor");
        MenuItem miEliminarDatosDeProvedor = new MenuItem("Eliminar Datos De Provedor");
        Menu mProveedores = new Menu("Proveedores");
        mProveedores.getItems().addAll(miNuevoProveedor, miModificarDatosDeProvedor,miEliminarDatosDeProvedor);
        
        MenuItem miGenerarReporte = new MenuItem("Generar Reporte");
        MenuItem miSubirReporte = new MenuItem("Subir Reporte");
        Menu mReporte = new Menu("Reporte");
        mReporte.getItems().addAll(miGenerarReporte, miSubirReporte);
        
        MenuBar mbPincipal = new MenuBar(meVentas, meHtasExport, meListaProductos, mClientes, mProveedores, mReporte);

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
            vbAreTrabajo.getChildren().add(pConsultarVenta.vistaConsultarVenta());
        });

        miCancelarVentas.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pCancelarVenta.vistaCancelarVenta());

        });

        miExportaXLS.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pExportaXlsVenta.vistaExportarXLS());

        });
        miExportaDB.setOnAction((event) -> {
            if (vbAreTrabajo.getChildren().size() > 0) {
                vbAreTrabajo.getChildren().clear();
            }
            vbAreTrabajo.getChildren().add(pExportaDbVenta.vistaExportarDB());

        });

        vbPrincipal.getChildren().addAll(mbPincipal, vbAreTrabajo);

        StackPane root = new StackPane();
        root.getChildren().add(vbPrincipal);

        Scene scene = new Scene(root, w, h);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
