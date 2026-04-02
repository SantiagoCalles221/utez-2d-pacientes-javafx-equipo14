package com.example.demo.Service;

import com.example.demo.Repository.FileRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private final FileRepository repo = new FileRepository();

    //Estamos cargando la lista
    public List<String> loadDataForList() throws IOException {
        List<String> lines = repo.readAllLines();
        List<String> result = new ArrayList<>();
        for (String line : lines){
            if (line==null || line.isBlank()) continue; //Ignora las lineas nulas

            String[] parts = line.split(",", -1);
            String curp = parts[0].trim(); // Obtiee el curp
            String name = parts[1].trim(); //Obtiene el nombre
            String edad = parts[2].trim(); //Obtiene la edad
            String telefono = parts[3].trim(); //Obtiene el telefono
            String alergia = parts[4].trim(); //Obtiene las alergias
            String estatus = parts[5].trim(); // Obtiene el estatus
            result.add(curp+"-"+name+"-"+edad+"-"+telefono+"-"+alergia+"-"+estatus);//Se agrega a la lista de resultados con el formato
        }
        return result;
    }

    //Clase que añade a los pacientes nuevos
    public void agregarPaciente(String curp, String nombre, String edad, String telefono, String alergia, String estatus) throws IOException {
        validarDatos(curp, nombre, edad, telefono, alergia, estatus);
        String curpNoComan = curp.replace(",", "");
        String nameNoComan = nombre.replace(",", "");
        String edadNoComan = edad.replace(",", "");
        String telefonoNoComa = telefono.replace(",", "");
        String alergiaNoComa = alergia.replace(",", "");

        repo.appendNewLine(curpNoComan+","+nameNoComan+","+edadNoComan+","+telefonoNoComa+","+alergiaNoComa+","+estatus);
    }

    //Actualiza el registro de un paciente
    public void actualizarPaciente(int index, String curp, String nombre, String edad, String telefono, String alergia, String estatus) throws IOException {
        List<String> lines = getAllCleanLines();
        if (index == -1){
            throw new IllegalArgumentException("El indice recibido es invalido");
        }
        lines.set(index,curp+","+nombre+","+edad+","+telefono+","+alergia+","+estatus);
        repo.appendAllLines(lines);
    }

    //Borra el registro de un paciente
    public void borrarPaciente(int index) throws IOException {
        List<String> lines = getAllCleanLines();
        lines.remove(index);
        repo.appendAllLines(lines);
    }

    //Limpia las lineas donde estan almacenados los pacientes
    private List<String> getAllCleanLines() throws IOException {
        List<String> lines = repo.readAllLines();
        List<String> cleanLines = new ArrayList<>();
        for(String line : lines){
            if (line != null && !line.isBlank()){
                cleanLines.add(line);
            }
        }
        return cleanLines;
    }

    //Valida los datos de los pacientes
    public void validarDatos(String curp, String nombre, String edad, String telefono, String alergia, String estatus){
        if (curp.isBlank() || curp.isEmpty() || curp.length() < 13){
            throw new IllegalArgumentException("El curp no es valido");
        }
        if (nombre.isBlank() || nombre.isEmpty() || nombre.length()<3 ) {
            throw new IllegalArgumentException("El nombre no cumple con los estandares");
        }
        try {
            int numEdad = Integer.parseInt(edad.trim());
            if (!(numEdad >=18)){
                throw new IllegalArgumentException("Solo se aceptan mayores de 18");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser numerica");
        }
        try {
            int numTelefono = Integer.parseInt(telefono.trim());
            if (!(numTelefono == 10)){
                throw new IllegalArgumentException("La longitud del telefono debe ser de 10");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El telefono debe ser numerico");
        }
    }
}
