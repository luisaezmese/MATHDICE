package com.example.luisangel.proyecto_2_examen;



public class Jugador {
    private String nombre= "GUEST";
    private String nick= "GUEST";
    private int puntos = 0;

    public Jugador() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getNick() {
        return nick;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Nombre: "+this.nombre+"Nickname: "+this.nick;
    }

}
