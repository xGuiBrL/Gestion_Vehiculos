package com.example.gestionvehiculos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionvehiculos.R;
import com.example.gestionvehiculos.adapters.RecyclerViewAdapter;
import com.example.gestionvehiculos.clases.AutoNuevo;
import com.example.gestionvehiculos.clases.Moto;
import com.example.gestionvehiculos.clases.MotoModificada;
import com.example.gestionvehiculos.clases.Vehiculo;
import com.example.gestionvehiculos.clases.VehiculoHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestorVehiculosActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemCrudListener {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RequestQueue requestQueue;
    private List<Vehiculo> vehiculosList;
    private FloatingActionButton btnAdd;
    private int editingPosition = -1;
    private final String BASE_URL = "https://raw.githubusercontent.com/adancondori/TareaAPI/refs/heads/main/api/vehiculos.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestor);

        recyclerView = findViewById(R.id.rvItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        vehiculosList = new ArrayList<>();

        adapter = new RecyclerViewAdapter(vehiculosList, this, this);
        recyclerView.setAdapter(adapter);

        cargarVehiculos();
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(GestorVehiculosActivity.this, FormularioActivity.class);
            launcherFormulario.launch(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onDeleteClick(Vehiculo vehiculo, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Vehículo")
                .setMessage("¿Estás seguro de que quieres eliminar este vehículo?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    vehiculosList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Vehículo eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onEditClick(Vehiculo vehiculo, int position) {
        Intent intent = new Intent(this, FormularioActivity.class);
        intent.putExtra("vehiculoEditar", vehiculo);
        intent.putExtra("posicion", position);
        launcherEditar.launch(intent);
    }

    private void cargarVehiculos() {
        vehiculosList.clear();
        adapter.notifyDataSetChanged();

        String url = BASE_URL;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("vehiculos");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject vehiculoData = results.getJSONObject(i);
                            Vehiculo vehiculo = null;
                            String tipo = vehiculoData.getString("tipo");
                            int id = vehiculoData.getInt("id");
                            String marca = vehiculoData.getString("marca");
                            String modelo = vehiculoData.getString("modelo");
                            int anioFabricacion = vehiculoData.getInt("anioFabricacion");
                            double precioBase = vehiculoData.getDouble("precioBase");
                            double kilometraje = vehiculoData.getDouble("kilometraje");
                            int esperanzaVida = 0;
                            if (vehiculoData.has("informacionAdicional") && vehiculoData.getJSONObject("informacionAdicional").has("esperanzaVida")) {
                                esperanzaVida = vehiculoData.getJSONObject("informacionAdicional").getInt("esperanzaVida");
                            }
                            JSONObject infoAdicional = vehiculoData.optJSONObject("informacionAdicional");
                            JSONArray datosAdicionales = infoAdicional != null ? infoAdicional.optJSONArray("datos") : null;
                            String dato1 = "";
                            String dato2 = "";
                            if (datosAdicionales != null) {
                                for (int j = 0; j < datosAdicionales.length(); j++) {
                                    JSONObject dato = datosAdicionales.getJSONObject(j);
                                    String nombreDato = dato.getString("nombreDato");
                                    String valor = dato.getString("valor");
                                    if (nombreDato.equals("Consumo combustible") || nombreDato.equals("Autonomía") || nombreDato.equals("Potencia")) {
                                        dato1 = valor;
                                    } else if (nombreDato.equals("Potencia motor") || nombreDato.equals("Peso") || nombreDato.equals("Marca escape") || nombreDato.equals("Modificación")) {
                                        dato2 = valor;
                                    }
                                }
                            }

                            if (tipo.equals("AutoNuevo")) {
                                int garantiaAnos = vehiculoData.getInt("garantiaAnios");
                                double descuentoPromocional = vehiculoData.getDouble("descuentoPromocional");
                                vehiculo = new AutoNuevo(id, marca, modelo, anioFabricacion, precioBase, kilometraje, esperanzaVida, dato1, dato2, garantiaAnos, descuentoPromocional);
                            } else if (tipo.equals("Moto")) {
                                double cilindradaCc = vehiculoData.getDouble("cilindradaCc");
                                String tipoManejo = vehiculoData.getString("tipoManejo");
                                vehiculo = new Moto(id, marca, modelo, anioFabricacion, precioBase, kilometraje, esperanzaVida, dato1, dato2, cilindradaCc, tipoManejo);
                            } else if (tipo.equals("MotoModificada")) {
                                double cilindradaCc = vehiculoData.getDouble("cilindradaCc");
                                String tipoManejo = vehiculoData.getString("tipoManejo");
                                String tipoModificacion = vehiculoData.getString("tipoModificacion");
                                vehiculo = new MotoModificada(id, marca, modelo, anioFabricacion, precioBase, kilometraje, esperanzaVida, dato1, dato2, cilindradaCc, tipoManejo, tipoModificacion);
                            }
                            if (vehiculo != null) {
                                vehiculosList.add(vehiculo);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        VehiculoHolder.setListaVehiculos(vehiculosList);
                    } catch (JSONException e) {
                        Toast.makeText(this, "Error al procesar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(request);
    }

    private final ActivityResultLauncher<Intent> launcherFormulario =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Vehiculo nuevo = (Vehiculo) result.getData().getSerializableExtra("vehiculoNuevo");
                            if (nuevo != null) {
                                nuevo.id = vehiculosList.size() + 1;
                                vehiculosList.add(nuevo);
                                adapter.notifyItemInserted(vehiculosList.size() - 1);
                                recyclerView.scrollToPosition(vehiculosList.size() - 1);
                                VehiculoHolder.setListaVehiculos(vehiculosList);
                            }
                        }
                    });

    private final ActivityResultLauncher<Intent> launcherEditar =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Vehiculo vehiculoEditado = (Vehiculo) result.getData().getSerializableExtra("vehiculoEditado");
                            int posicion = result.getData().getIntExtra("posicion", -1);
                            if (vehiculoEditado != null && posicion != -1) {
                                vehiculosList.set(posicion, vehiculoEditado);
                                adapter.notifyItemChanged(posicion);
                                VehiculoHolder.setListaVehiculos(vehiculosList);
                            }
                        }
                    });
}