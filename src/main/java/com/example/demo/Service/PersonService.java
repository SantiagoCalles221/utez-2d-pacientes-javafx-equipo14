package com.example.demo.Service;

import com.example.demo.Repository.Paciente;
import com.example.demo.Repository.FileRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.List;

public class PersonService {
    private final FileRepository repository = new FileRepository();
    private final ObservableList<Paciente> pacientes;

    //Inicio la lista que esta conectada (La que esta en Data y se llama person.csv)
    public PersonService() {
        this.pacientes = FXCollections.observableArrayList();
    }

    //Cargo datos desde el archivo al iniciar
    public void loadData() throws IOException {
        List<Paciente> loaded = repository.loadPacientes();
        pacientes.setAll(loaded);
    }
    //Retornar la lista
    public ObservableList<Paciente> getPacientes() {
        return pacientes;
    }

    //Botones
    // Agregar nuevo paciente
    public void addPaciente(Paciente paciente) throws IOException {
        if (paciente.getCurp().equalsIgnoreCase(paciente.getCurp())) {
            throw new IllegalArgumentException("El CURP ya está registrado.");
        }else {
            pacientes.add(paciente);
            repository.saveAll(pacientes);
        }
    }

    // Eliminar un pasciente
    public void eliminarPaciente(Paciente paciente) throws IOException {
        if (paciente != null) {
            pacientes.remove(paciente);
            repository.saveAll(pacientes);
        }
    }

    //Cambiar el estatus de un paciente
    public void cambiarEstatus(Paciente paciente) throws IOException {
        if (paciente != null) {
            if (paciente.getEstatus().equals("ACTIVO")) {
                paciente.setEstatus("INACTIVO");
            }
            else {
                paciente.setEstatus("ACTIVO");
            }
            repository.saveAll(pacientes);
        }
    }
}