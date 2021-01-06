package com.example.hotelmanagerv1;

public class PedidosClass {

    private int habitacion;
    private int almohadas=0;
    private int toallas=0;
    private int papel=0;
    private int jabon=0;
    private boolean completado;
    private String Key;

    public PedidosClass(){

    }


    public PedidosClass(int habitacion, int almohadas, int toallas, int papel, int jabon, boolean completado) {
        this.habitacion = habitacion;
        this.almohadas = almohadas;
        this.toallas = toallas;
        this.papel = papel;
        this.jabon = jabon;
        this.completado = completado;
    }

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public int getAlmohadas() {
        return almohadas;
    }

    public void setAlmohadas(int almohadas) {
        this.almohadas = almohadas;
    }

    public int getToallas() {
        return toallas;
    }

    public void setToallas(int toallas) {
        this.toallas = toallas;
    }

    public int getPapel() {
        return papel;
    }

    public void setPapel(int papel) {
        this.papel = papel;
    }

    public int getJabon() {
        return jabon;
    }

    public void setJabon(int jabon) {
        this.jabon = jabon;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
