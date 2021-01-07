package com.example.hotelmanagerv1;

import java.io.Serializable;

public class ServiciosClass implements Serializable {

    private int habitacion;
    private boolean limpieza;
    private boolean lavanderia;
    private boolean no_molestar;

    public ServiciosClass() {
    }

    public ServiciosClass(int habitacion, boolean limpieza, boolean lavanderia, boolean no_molestar) {
        this.habitacion = habitacion;
        this.limpieza = limpieza;
        this.lavanderia = lavanderia;
        this.no_molestar = no_molestar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public boolean isLimpieza() {
        return limpieza;
    }

    public void setLimpieza(boolean limpieza) {
        this.limpieza = limpieza;
    }

    public boolean isLavanderia() {
        return lavanderia;
    }

    public void setLavanderia(boolean lavanderia) {
        this.lavanderia = lavanderia;
    }

    public boolean isNo_molestar() {
        return no_molestar;
    }

    public void setNo_molestar(boolean no_molestar) {
        this.no_molestar = no_molestar;
    }
}
