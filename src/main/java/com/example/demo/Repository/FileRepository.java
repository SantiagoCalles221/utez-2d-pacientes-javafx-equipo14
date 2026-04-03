package com.example.demo.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {
    private final Path filePath = Paths.get("Data", "person.csv");

    private void ensureFileExist() throws IOException {
        if(Files.notExists(filePath)){
            Files.createFile(filePath);
        }
    }
    public List<String> readAllLines() throws IOException {
        ensureFileExist();
        return Files.readAllLines(filePath, StandardCharsets.UTF_8);
    }

    // Lee el archivo y devuelve una lista de Pacientes
    // Funciona solo porque dios asi lo quizo
    public List<Paciente> loadPacientes() throws IOException {
        List<String> lines = readAllLines();
        List<Paciente> lista = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",", -1);
            if (parts.length >= 6) {
                Paciente p = new Paciente(
                        parts[0].trim(), // curp
                        parts[1].trim(), // nombre
                        Integer.parseInt(parts[2].trim()), // edad
                        parts[3].trim(), // telefono
                        parts[4].trim(), // alergias
                        parts[5].trim()  // estatus
                );
                lista.add(p);
            }
        }
        return lista;
    }
    // Guarda toda la lista de objetos de vuelta al CSV
    public void saveAll(List<Paciente> pacientes) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            lines.add(String.format("%s,%s,%d,%s,%s,%s",
                    paciente.getCurp(), paciente.getNombre(), paciente.getEdad(), paciente.getTelefono(), paciente.getAlergias(), paciente.getEstatus()));
        }
        Files.write(filePath, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
//LLamen a mi mami