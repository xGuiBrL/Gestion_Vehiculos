package com.example.gestionvehiculos.clases;

import java.io.Serializable;

public class Vehiculo implements Serializable {
    public int id = 0;
    public String marca = "";
    public String modelo = "";
    public int anoFabricacion = 0;
    public double precioBase = 0.0;
    public double kilometraje = 0.0;
    public int esperanzaVida = 0;
    public String potencia = "";
    public String modificacion = "";

    public Vehiculo (int id, String marca, String modelo, int anoFabricacion, double precioBase, double kilometraje,
                     int esperanzaVida, String potencia, String modificacion) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacion = anoFabricacion;
        this.precioBase = precioBase;
        this.kilometraje = kilometraje;
        this.esperanzaVida = esperanzaVida;
        this.potencia = potencia;
        this.modificacion = modificacion;
    }
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnoFabricacion() { return anoFabricacion; }
    public double getPrecioBase() { return precioBase; }
    public double getKilometraje() { return kilometraje; }
    public int getEsperanzaVida() { return esperanzaVida; }
    public String getPotencia() { return potencia; }
    public String getModificacion() { return modificacion; }
}
