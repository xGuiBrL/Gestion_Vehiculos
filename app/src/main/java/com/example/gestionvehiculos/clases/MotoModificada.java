package com.example.gestionvehiculos.clases;

import java.io.Serializable;

public class MotoModificada extends Moto implements Serializable {
    public String tipoModificacion = "";

    public MotoModificada(int id, String marca, String modelo, int anoFabricacion, double precioBase, double kilometraje,
                          int esperanzaVida, String potencia, String modificacion,
                          double cilindradaCc, String tipoManejo, String tipoModificacion) {
        super(id, marca, modelo, anoFabricacion, precioBase, kilometraje, esperanzaVida, potencia, modificacion, cilindradaCc, tipoManejo);
        this.tipoModificacion = tipoModificacion;
    }

    public String getTipoModificacion() { return tipoModificacion; }
}
