package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class FormularioController implements Initializable {


    @FXML
    private TextField txtNombre, txtNumero, txtEdad, txtCurp, txtAlergia;

    @FXML
    private ToggleButton btnActivo, btnInactivo;

    @FXML

    void handleAplicar (ActionEvent event){
        String nombre =
                txtNombre.getText().trim();
        String edad =
                txtEdad.getText().trim();
        String numero =
                txtNumero.getText().trim();
        String curp =
                txtCurp.getText().trim();
        boolean esActivo = btnActivo.isSelected();

        if (curp.length()!= 18) {
            //alerta
            return;
        }
        //if de la alerta
        if (nombre.length() <= 2) {
            //alerta
            return;
        }
        try { int edadNum = Integer.parseInt(edad);
            if (edadNum > 120 || edadNum < 0) {
                //alerta
                return;
            }
        } catch (NumberFormatException e) {
            //alerta num//
            return;
        }
        try { long numValidado = Long.parseLong(numero);
        }catch (NumberFormatException e){
            //alerta num
            return;
        }
        Persona p = new PersonaService(edadNum, nombre, curp, numero);
    }

    @FXML

    void handleCancelar (ActionEvent event){
        javafx.stage.Stage ventana = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        ventana.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup grupoEstatus = new ToggleGroup();

        btnActivo.setToggleGroup(grupoEstatus);
        btnInactivo.setToggleGroup(grupoEstatus);
        btnActivo.setSelected(true);
    }
}

