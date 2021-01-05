package com.example.hotelmanagerv1;

public class PedidosClass {

    private int habitacion;
    private String objeto;
    private int cantidad;
    private boolean completado;
    private String Key;

    public PedidosClass(){

    }

    public PedidosClass(int habitacion, String objeto, int cantidad, boolean completado) {
        this.habitacion = habitacion;
        this.objeto = objeto;
        this.cantidad = cantidad;
        this.completado = completado;
    }

    public int getHabitacion() { return habitacion; }

    public void setHabitacion(int habitacion) { this.habitacion = habitacion; }

    public String getObjeto() { return objeto; }

    public void setObjeto(String objeto) { this.objeto = objeto; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public boolean isCompletado() { return completado; }

    public void setCompletado(boolean completado) { this.completado = completado; }

    public String getKey(String key) {
        return Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

}
