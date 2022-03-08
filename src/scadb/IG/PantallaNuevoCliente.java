/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import scadb.DAO.clienteDAO;

public class PantallaNuevoCliente {

  public VBox vistaNuevoCliente(VBox vbAreaTrabajo){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("AGREGAR CLIENTE");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);
        
        //Etiquetas/Datos de Cliente;
	Label lbNombre  = new Label("Nombre: ");
	Label lbRazon_social  = new Label("Razon Social");
	Label lbDomicilio_fiscal  = new Label("Dom. Fiscal");
	Label lbTelefono  = new Label("Telefono.");
	Label lbRFC  = new Label("RFC");
	Label lbEmail  = new Label("Email:");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(320);
        TextField tfRazonSocial = new TextField();
        tfRazonSocial.setPrefWidth(320);
        TextField tfDomicilioFiscal = new TextField();
        tfDomicilioFiscal.setMaxWidth(320);
        TextField tfTelefono = new TextField();
        tfTelefono.setMaxWidth(200);
        TextField tfRFC = new TextField();
        tfRFC.setMaxWidth(120);
        TextField tfEmail = new TextField();
        tfEmail.setMaxWidth(320);
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction((ActionEvent e)->{
            removerVistas(vbAreaTrabajo);
        });
        
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((ActionEvent e)->{
           clienteDAO cliDAO = new clienteDAO();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              cliDAO.insertarCliente(tfNombre.getText(), tfRazonSocial.getText(), tfDomicilioFiscal.getText(), 
                      tfTelefono.getText(), tfRFC.getText(), tfEmail.getText());
              removerVistas(vbAreaTrabajo);
           } else {
              removerVistas(vbAreaTrabajo);
           }

        });
        
        HBox hbBotones = new HBox();
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnCancelar, btnGuardar);
        
        GridPane gpClienteSeleccionado = new GridPane();
        gpClienteSeleccionado.setPadding(new Insets(5, 5, 5, 5));
        gpClienteSeleccionado.setVgap(10);
        gpClienteSeleccionado.setHgap(10);
        
        gpClienteSeleccionado.add(lbNombre, 0, 0);
        gpClienteSeleccionado.add(tfNombre, 1, 0);
        gpClienteSeleccionado.add(lbRazon_social, 2, 0);
        gpClienteSeleccionado.add(tfRazonSocial, 3, 0);
        
        gpClienteSeleccionado.add(lbDomicilio_fiscal, 0, 1);
        gpClienteSeleccionado.add(tfDomicilioFiscal, 1, 1);
        gpClienteSeleccionado.add(lbTelefono, 2, 1);
        gpClienteSeleccionado.add(tfTelefono, 3, 1);
        
        gpClienteSeleccionado.add(lbRFC, 0, 2);
        gpClienteSeleccionado.add(tfRFC, 1, 2);
        gpClienteSeleccionado.add(lbEmail, 2, 2);
        gpClienteSeleccionado.add(tfEmail, 3, 2);
        
        gpClienteSeleccionado.add(hbBotones, 3, 3);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpClienteSeleccionado);
        
        return vbPpal;
    }

    private void removerVistas(VBox vbAreaTrabajo) {
        if (vbAreaTrabajo.getChildren().size() > 0) {
            vbAreaTrabajo.getChildren().remove(0);
        }
    }
}
