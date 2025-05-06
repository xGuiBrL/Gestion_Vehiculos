package com.example.gestionvehiculos.clases;

import java.io.Serializable;

public class Moto extends Vehiculo implements Serializable {
    public double cilindradaCc = 0.0;
    public String tipoManejo = "";
    public Moto (int id, String marca, String modelo, int anoFabricacion, double precioBase, double kilometraje,
                 int esperanzaVida, String potencia, String modificacion,
                 double cilindradaCc, String tipoManejo) {
        super(id, marca, modelo, anoFabricacion, precioBase, kilometraje, esperanzaVida, potencia, modificacion);
        this.cilindradaCc = cilindradaCc;
        this.tipoManejo = tipoManejo;
    }
    public double getCilindradaCc() { return cilindradaCc; }
    public String getTipoManejo() { return tipoManejo; }
}
