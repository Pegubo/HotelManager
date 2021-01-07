package com.example.hotelmanagerv1;

import java.io.Serializable;
import java.util.Date;

public class HabitacionesClass implements Serializable {
    private int numero;
    private String contra;
    private String Huesped;
    private String fInicio;
    private String fSalida;
    private String mKey;
    private Boolean Reservada;
    private String correo;

    public HabitacionesClass() {

    }

    public HabitacionesClass(int numero, String contra, String huesped, String fInicio, String fSalida, Boolean Reservada) {
        this.setNumero(numero);
        this.setContra(contra);
        setHuesped(huesped);
        this.setfInicio(fInicio);
        this.setfSalida(fSalida);
        this.setReservada(Reservada);
    }


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getHuesped() {
        return Huesped;
    }

    public void setHuesped(String huesped) {
        Huesped = huesped;
    }



    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public Boolean getReservada() {
        return Reservada;
    }

    public void setReservada(Boolean reservada) {
        Reservada = reservada;
    }

    public String getfInicio() {
        return fInicio;
    }

    public void setfInicio(String fInicio) {
        this.fInicio = fInicio;
    }

    public String getfSalida() {
        return fSalida;
    }

    public void setfSalida(String fSalida) {
        this.fSalida = fSalida;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
