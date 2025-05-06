package com.example.gestionvehiculos.clases;

import java.io.Serializable;

public class AutoNuevo extends Vehiculo implements Serializable {
    public int garantiaAnos = 0;
    public double descuentoPromocional = 0.0;
    public AutoNuevo (int id, String marca, String modelo, int anoFabricacion, double precioBase, double kilometraje,
                      int esperanzaVida, String potencia, String modificacion,
                      int garantiaAnos, double descuentoPromocional) {
        super(id, marca, modelo, anoFabricacion, precioBase, kilometraje, esperanzaVida, potencia, modificacion);
        this.garantiaAnos = garantiaAnos;
        this.descuentoPromocional = descuentoPromocional;
    }

    public int getGarantiaAnos() { return garantiaAnos; }
    public double getDescuentoPromocional() { return descuentoPromocional; }
}
