package com.example.demo.controller;

import com.example.demo.Array.Paciente;
import com.example.demo.Service.PersonService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class appController {

    //Este controlador solamente sirve para hello-view.fxml
    //Tendremos que crear un controlador para el otro archivo
    @FXML private Label lblAlert;
    @FXML private Label lblActivos;
    @FXML private Label lblInactivos;
    @FXML private Label lblTotal;
    @FXML private TableView<Paciente> tableView;
    @FXML private TableColumn<Paciente, String> viewCurp;
    @FXML private TableColumn<Paciente, String> viewNombre;
    @FXML private TableColumn<Paciente, Integer> viewEdad;
    @FXML private TableColumn<Paciente, String> viewTelefono;
    @FXML private TableColumn<Paciente, String> viewAlergias;
    @FXML private TableColumn<Paciente, String> viewEstatus;

    private final PersonService service = new PersonService();

    //Esta parte es para incializar los datos en la TableView
    @FXML
    public void initialize() {
        // Cada columna de la tabla esta conectada con los datos del paciente.
        // lambda extrae directamente el valor de cada atributo (funciona, es lo importante)
        viewCurp.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurp()));
        viewNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        viewEdad.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEdad()));
        viewTelefono.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefono()));
        viewAlergias.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlergias()));
        viewEstatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstatus()));
        cargarDatos();
    }

    private void cargarDatos() {
        //Simples mensajes de si todo esta bien o exploto
        try {
            service.loadData();
            tableView.setItems(service.getPacientes());
            contadorActivos();
            tableView.setItems(service.getPacientes());
            lblAlert.setText("Datos cargados correctamente");
            lblAlert.setStyle("-fx-text-fill: green");
        } catch (IOException e) {
            lblAlert.setText("Error al cargar los datos");
            lblAlert.setStyle("-fx-text-fill: red");
        } catch (NumberFormatException e) {
            //Esto es pura comprobacion de datos, pero a lo mejor sobra
            //Pero funciona asi que no quiero mover nada
            lblAlert.setText("Hay una edad que no es Numerica");
            lblAlert.setStyle("-fx-text-fill: red");
        }
    }

    private void contadorActivos(){
        int total = service.contarTotales();
        int activos = service.contarActivos();
        int inactivos = service.contarInactivos();

        lblTotal.setText("Total: " + total);
        lblActivos.setText("Activos: " + activos);
        lblInactivos.setText("Inactivos: " + inactivos);
    }

    //Botones (Creo que los nombres de los metodos se explican por si mismos)
    //Solamente falta el boton de nuevo pq ese lleva a otra ventana
    @FXML
    private void onReload() {
        service.getPacientes().clear();
        try {
            //En caso de que todo salga bien mostramos el mensaje en un label
            service.loadData();
            lblAlert.setText("Datos cargados Correctamente");
            lblAlert.setStyle("-fx-text-fill: green");
        } catch (IOException e) {
            //Esto es para mostrar un error
            lblAlert.setText("Error al cargar los datos");
            lblAlert.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    private void onDelete() {
        // Obtenemos el paciente seleccionado en la tabla
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();
        //Comprobamos que si se seleccione un paciente
        //Tarde mas de lo que me gustaria admitir para encontrar pq fallaba
        if (seleccionado != null) {
            try {
                service.eliminarPaciente(seleccionado);
                tableView.refresh();
                lblAlert.setText("Registro eliminado");
                lblAlert.setStyle("-fx-text-fill: green");
            } catch (IOException e) {
                lblAlert.setText("Error al eliminar el registro");
                lblAlert.setStyle("-fx-text-fill: red");
            }
        } else {
            lblAlert.setText("Seleccione un registro");
            lblAlert.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    private void onStatus() {
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                service.cambiarEstatus(seleccionado);
                tableView.refresh();
                lblAlert.setText("Registro actualizado");
                lblAlert.setStyle("-fx-text-fill: green");
                contadorActivos();
            } catch (IOException e) {
                lblAlert.setText("Error al cargar el registro");
                lblAlert.setStyle("-fx-text-fill: red");
            }
        } else {
            lblAlert.setText("Seleccione un registro");
            lblAlert.setStyle("-fx-text-fill: red");
        }
    }
    @FXML
    private void handleNuevo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/nuevo.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la ventana: " + e.getMessage());
        }
    }
    @FXML
    private void handleEditar(ActionEvent event) {
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/nuevo.fxml"));
                Parent root = loader.load();
                FormularioController dc = loader.getController();
                dc.prepararEdicion(seleccionado);
                Stage stage = new Stage();
                stage.setTitle("Editar Paciente");
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                lblAlert.setText("Error al abrir el formulario de edición");
                e.printStackTrace();
            }
        } else {
            lblAlert.setText("Por favor, selecciona un paciente de la tabla");
            lblAlert.setStyle("-fx-text-fill: orange");
        }
    }
}
