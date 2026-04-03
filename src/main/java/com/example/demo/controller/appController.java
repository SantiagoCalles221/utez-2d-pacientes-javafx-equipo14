package com.example.demo.controller;

import com.example.demo.Repository.Paciente;
import com.example.demo.Service.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;

public class appController {

    //Este controlador solamente sirve para hello-view.fxml
    //Tendremos que crear un controlador para el otro archivo

    @FXML private Label lblAlert;
    @FXML private TableView<Paciente> tableView;
    @FXML private TableColumn<Paciente, String> viewCurp;
    @FXML private TableColumn<Paciente, String> viewNombre;
    @FXML private TableColumn<Paciente, Integer> viewEdad;
    @FXML private TableColumn<Paciente, String> viewTelefono;
    @FXML private TableColumn<Paciente, String> viewAlergias;
    @FXML private TableColumn<Paciente, String> viewEstatus;

    private final PersonService service = new PersonService();

    @FXML
    public void initialize() {
        viewCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        viewNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        viewEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        viewTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        viewAlergias.setCellValueFactory(new PropertyValueFactory<>("alergias"));
        viewEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        cargarDatos();
    }
    private void cargarDatos() {
        try {
            service.loadData();
            tableView.setItems(service.getPacientes());
            lblAlert.setText("Datos cargados Correctamente");
            lblAlert.setStyle("-fx-text-fill: green");
        } catch (IOException e) {
            lblAlert.setText("Error al cargar los datos");
            lblAlert.setStyle("-fx-text-fill: red");
        } catch (NumberFormatException e) {
            lblAlert.setText("Hay una edad que no es Numerica");
            lblAlert.setStyle("-fx-text-fill: red");
        }
    }

    //Botones
    //Solamente falta el boton de nuevo ya que ese es en otra ventana
    @FXML
    private void onReload() {
        service.getPacientes().clear();
        try {
            service.loadData();
            lblAlert.setText("Datos cargados Correctamente");
            lblAlert.setStyle("-fx-text-fill: green");
        } catch (IOException e) {
            lblAlert.setText("Error al cargar los datos");
            lblAlert.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    private void onDelete() {
        // Obtenemos el paciente seleccionado en la tabla
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();
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
            } catch (IOException e) {
                lblAlert.setText("Error al cargar el registro");
                lblAlert.setStyle("-fx-text-fill: red");
            }
        } else {
            lblAlert.setText("Seleccione un registro");
            lblAlert.setStyle("-fx-text-fill: red");
        }
    }
}
