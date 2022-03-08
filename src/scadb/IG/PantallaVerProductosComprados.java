/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.detalle_venta;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scadb.DAO.detalle_ventaDAO;

public class PantallaVerProductosComprados {
    
    detalle_ventaDAO detventaDAO = new detalle_ventaDAO();
    
   ObservableList<detalle_venta> detventa = FXCollections.observableArrayList();

    public void ventanaVerProductosComprados(int codigoNotaVenta){
       Stage stgPpal = new Stage();
       TableView tvProductos = new TableView();
       
        TableColumn<detalle_venta, Integer> codproColumna = new TableColumn<>("Codigo");
        codproColumna.setMinWidth(90);
        codproColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, String> descripColumna = new TableColumn<>("Descripcion");
        descripColumna.setMinWidth(320);
        descripColumna.setCellValueFactory(new PropertyValueFactory<>("descrprod"));
        
        TableColumn<detalle_venta, Integer> cantidadColumna = new TableColumn<>("Cantidad");
        cantidadColumna.setMinWidth(100);
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        
        TableColumn<detalle_venta, String> costoUnitColumna = new TableColumn<>("Costo Unitario");
        costoUnitColumna.setMinWidth(100);
        costoUnitColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
        
        TableColumn<detalle_venta, String> subtotalColumna = new TableColumn<>("Sub-Total");
        subtotalColumna.setMinWidth(100);
        subtotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        
        tvProductos.getColumns().addAll(codproColumna, descripColumna, cantidadColumna, costoUnitColumna,
                subtotalColumna);
        
         List<String> lstWhere = new ArrayList<>();
         lstWhere.clear();
         lstWhere.add("codigo_nota_venta = "+codigoNotaVenta);
         detventa.clear();
         detventa = FXCollections.observableArrayList(detventaDAO.consultaDetVenta(lstWhere));
         for (detalle_venta detVentaT: detventa){
             detVentaT.setSubTotal(detVentaT.getCantidad()*detVentaT.getPrecio_venta());
         }
         tvProductos.setItems(detventa);
         
       stgPpal.setTitle("Productos Comprados");
       
       VBox vbPpal = new VBox();
       vbPpal.setSpacing(5);
       
       Button btnCancelar = new Button("Salir");
       btnCancelar.setOnAction((ActionEvent e)->{
           stgPpal.close();
       });
       HBox hbBotones = new HBox();
       hbBotones.setSpacing(5);
       hbBotones.setAlignment(Pos.CENTER_RIGHT);
       hbBotones.getChildren().addAll(btnCancelar);
       
       vbPpal.getChildren().addAll(tvProductos, hbBotones);
       
       StackPane rootSelectClientes = new StackPane();
       rootSelectClientes.getChildren().addAll(vbPpal);
       
       Scene scene = new Scene(rootSelectClientes,720,470);
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
