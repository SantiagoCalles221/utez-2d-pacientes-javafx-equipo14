package com.example.demo;

public class Paciente {

    String curp;
    String nombre;
    String numero;
    int edad;
    String status;
    String alergia;

    public Paciente (String curp, String nombre, String numero, int edad, String status, String alergia){
        this.curp = curp;
        this.nombre = nombre;
        this.edad = edad;
        this.numero = numero;
        this.status = status;
        this.alergia = alergia;
    }
    public String getCurp() {
        return curp;
    }
    public String getNombre(){
        return nombre;
    }
    public int getEdad() {
        return edad;
    }
    public String getNumero() {
        return numero;
    }
    public String getStatus() {
        return status;
    }
    public String getAlergia() {return alergia;}
    public void setCurp(String curp) {
        this.curp = curp;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setNumero(String numero){
        this.numero = numero;
    }
    public void setEdad (int edad) {
        this.edad = edad;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setAlergia (String alergia) {this.alergia = alergia;}
}
