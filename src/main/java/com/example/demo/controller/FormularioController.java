package com.example.demo.controller;

import com.example.demo.Array.Paciente;
import com.example.demo.Service.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class FormularioController {

    private final PersonService service = new PersonService();

    @FXML
    private TextField txtCurp;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private  TextField txtTelefono;
    @FXML
    private TextField txtAlergias;
    @FXML
    private Label lblAlert;

    private boolean esEdicion = false;
    private Paciente pacienteAEditar;

    public void prepararEdicion(Paciente seleccionado) {
        this.esEdicion = true;
        this.pacienteAEditar = seleccionado;
        txtCurp.setText(seleccionado.getCurp());
        txtNombre.setText(seleccionado.getNombre());
        txtEdad.setText(String.valueOf(seleccionado.getEdad()));
        txtTelefono.setText(seleccionado.getTelefono());
        txtAlergias.setText(seleccionado.getAlergias());
        txtCurp.setEditable(false);
    }

    @FXML
    private void onSave(ActionEvent event) {
        if (validarDatos()) { // Este metodo comprueba los datos
            String curp = txtCurp.getText().trim();
            String nombre = txtNombre.getText().trim();
            String edad = txtEdad.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String alergias = txtAlergias.getText().trim();
            Paciente nuevo = new Paciente(curp,nombre,Integer.parseInt(edad),telefono,alergias,"Activo");
            try {
                service.addPaciente(nuevo, esEdicion);
                lblAlert.setText("Paciente registrado con éxito");
                lblAlert.setStyle("-fx-text-fill: green");
                clear();
            } catch (IllegalArgumentException e) {
                // Este es el mensaje de Curp duplicado (Ya esta establecido)
                lblAlert.setText(e.getMessage());
                lblAlert.setStyle("-fx-text-fill: red");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private void handleCancelar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/hello-view.fxml"));
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

    private boolean validarDatos() {
        String curp = txtCurp.getText().trim();
        String nombre = txtNombre.getText().trim();
        String edad = txtEdad.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String alergias = txtAlergias.getText().trim();
        // Solamante esta verificando campos vacios
        if (curp.isEmpty() || nombre.isEmpty() || edad.isEmpty() || telefono.isEmpty()) {
            lblAlert.setText("Rellene los campos vacios");
            lblAlert.setStyle("-fx-text-fill: red");
            return false;
        }
        // Validamos la longitud del curp
        if (curp.length() != 18) {
            lblAlert.setText("El CURP debe tener exactamente 18 caracteres.");
            lblAlert.setStyle("-fx-text-fill: red");
            return false;
        }
        // Comprobamos que la edad sea valida
        try {
            int edadNum = Integer.parseInt(edad);
            if (edadNum < 0 || edadNum > 120) {
                lblAlert.setText("La edad debe estar entre 0 y 120 años");
                lblAlert.setStyle("-fx-text-fill: red");
                return false;
            }
            //Esto es por si mete algo que no sea numero
        } catch (NumberFormatException e) {
            lblAlert.setText("La edad debe ser numerica");
            lblAlert.setStyle("-fx-text-fill: red");
            return false;
        }
        try {
            //Trato de comprobar que el telefono si es numerico
            Long.parseLong(telefono);
            if (telefono.length() != 10) {//Compruebo que tiene la longitud deseada
                lblAlert.setText("El telefono debe tener exactamente 10 digitos");
                lblAlert.setStyle("-fx-text-fill: red");
                return false;
            }
        } catch (NumberFormatException e) {
            lblAlert.setText("El telefono debe contener solo numeros");
            lblAlert.setStyle("-fx-text-fill: red");
            return false;
        }
        if (alergias.isEmpty()) {
            //No se me ocurrio como mas meter esto para despues guardarlo (Animo amigo tu puedes)
            txtAlergias.setText("Ninguna");
        }
        return true;
    }

    @FXML
    private void clear(){
        txtCurp.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        txtAlergias.setText("");
    }
}


