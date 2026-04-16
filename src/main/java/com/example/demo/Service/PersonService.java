package com.example.demo.Service;

import com.example.demo.Array.Paciente;
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
        try {
            loadData(); //Carga lo que ya existe en el csv al iniciar
        } catch (IOException e) {
            System.out.println("Error al cargar datos iniciales");
        }
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
    // Agregar nuevo paciente o actualizarlo
    public void addPaciente(Paciente nuevo, boolean esEdicion) throws IOException {
        if (esEdicion) {
            // LÓGICA PARA ACTUALIZAR
            for (int i = 0; i < pacientes.size(); i++) {//Buscamos al paciente a actualizar
                if (pacientes.get(i).getCurp().equalsIgnoreCase(nuevo.getCurp())) {
                    pacientes.set(i, nuevo);//Cuando lo encontremos lo remplazamos y terminamos de checar
                    break;
                }
            }
        } else {//Esto es para un registro nuevo
            boolean exist = false;
            for (Paciente paciente : pacientes) {
                if (paciente.getCurp().equalsIgnoreCase(nuevo.getCurp())) {//Busca si el curp ya existe
                    exist = true;
                    break;
                }
            }

            if (exist) {
                throw new IllegalArgumentException("El CURP ya está registrado.");
            } else {
                pacientes.add(nuevo);
            }
        }

        // Al final, guardamos la lista completa (ya sea con el cambio o el nuevo)
        repository.saveAll(pacientes);
    }

    // Eliminar un pasciente
    public void eliminarPaciente(Paciente paciente) throws IOException {
            pacientes.remove(paciente);
            repository.saveAll(pacientes);
    }

    //Cambiar el estatus de un paciente
    public void cambiarEstatus(Paciente paciente) throws IOException {
        if (paciente.getEstatus().equals("Activo")) {
            paciente.setEstatus("Inactivo");
        }
        else {
            paciente.setEstatus("Activo");
        }
        repository.saveAll(pacientes);
    }
    // Cuenta todos los pacientes de la lista
    public int contarTotales() {
        return pacientes.size();
    }

    // Cunta los pacientes Activos
    public int contarActivos() {
        int contador = 0;
        for (Paciente paciente : pacientes) {
            if (paciente.getEstatus().equalsIgnoreCase("Activo")) {
                contador++;
            }
        }
        return contador;
    }

    // Cunta los pacientes Inactivos
    public int contarInactivos() {
        int contador = 0;
        for (Paciente paciente : pacientes) {
            if (paciente.getEstatus().equalsIgnoreCase("Inactivo")) {
                contador++;
            }
        }
        return contador;
    }
}