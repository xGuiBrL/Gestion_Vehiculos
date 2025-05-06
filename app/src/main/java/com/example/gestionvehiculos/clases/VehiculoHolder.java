package com.example.gestionvehiculos.clases;

import java.util.List;

public class VehiculoHolder {
    private static List<Vehiculo> listaVehiculos;

    public static void setListaVehiculos(List<Vehiculo> lista) {
        listaVehiculos = lista;
    }
    public static List<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }
}
