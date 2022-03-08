/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadb.IG;

import DTO.CLIENTE;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import scadb.DAO.clienteDAO;

public class PantallaModificarCLiente {

        clienteDAO cliDAO = new clienteDAO();

    
    public VBox vistaModificarCliente(VBox vbAreaTrabajo){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("MODIFICAR CLIENTES");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        
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
         String titulo = lbTituloVista.getText()+" ("+String.valueOf(obList.size())+")";
         lbTituloVista.setText(titulo);

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
            vbAreaTrabajo.getChildren().remove(0);
        });
        
        tvClientes.setOnMouseClicked((event) -> {
            CLIENTE cli= (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
            tfNombre.setText(cli.getNombre());
            tfDomicilioFiscal.setText(cli.getDomicilio_fiscal());
            tfRazonSocial.setText(cli.getRazon_social());
            tfRFC.setText(cli.getRfc());
            tfTelefono.setText(cli.getTelefono());
            tfEmail.setText(cli.getEmail());
        });
        
        Button btnGuardar = new Button("Modificar");
        btnGuardar.setOnAction((ActionEvent e)->{
           CLIENTE cli= (CLIENTE) tvClientes.getSelectionModel().getSelectedItem();
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setHeaderText(null);
           alert.setTitle("Confirmación");
           alert.setContentText("¿Estas seguro de confirmar la acción?");
           Optional<ButtonType> action = alert.showAndWait(); 
           if (action.get() == ButtonType.OK) {
              cliDAO.modificarCliente(cli.getCodigo_cliente(), 
                      tfNombre.getText(), tfRazonSocial.getText(), tfDomicilioFiscal.getText(), 
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
        
        vbPpal.getChildren().addAll(lbTituloVista, tvClientes, gpClienteSeleccionado);
        
        return vbPpal;
    }
    
    private void removerVistas(VBox vbAreaTrabajo){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
}
