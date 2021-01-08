package com.example.hotelmanagerv1;

public class ReporteClass {

    private int habitacion;
    private String reporte;
    private String key;

    public ReporteClass() {
    }

    public ReporteClass(int habitacion, String reporte) {
        this.habitacion = habitacion;
        this.reporte = reporte;

    }

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
