package com.example.gestionvehiculos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionvehiculos.R;
import com.example.gestionvehiculos.clases.AutoNuevo;
import com.example.gestionvehiculos.clases.Moto;
import com.example.gestionvehiculos.clases.MotoModificada;
import com.example.gestionvehiculos.clases.Vehiculo;
import com.example.gestionvehiculos.clases.VehiculoHolder;

import java.util.List;

public class FormularioActivity extends AppCompatActivity {
    private LinearLayout layoutAutoNuevo, layoutMoto, layoutMotoModificada;
    private Button btnSave, btnClear;
    private List<Vehiculo> vehiculos;
    private VehiculoHolder vehiculoHolder;
    private EditText etId, etModelo, etMarca, etAnioFabricacion, etPrecioBase, etKilometraje, etEsperanzaVida;
    private EditText etConsumoCombustible, etPotenciaMotor, etGarantia, etDescuento;
    private EditText etAutonomia, etPeso, etCilindrada, etTipoManejo;
    private EditText etPotenciaMotoModificada, etMarcaEscape, etCilindrada2, etTipoManejo2, etTipoModificacion;
    private Spinner spinnerTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario);

        vehiculoHolder = new VehiculoHolder();
        vehiculos = vehiculoHolder.getListaVehiculos();
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);

        // Inicializar EditTexts
        etId = findViewById(R.id.etId);
        etModelo = findViewById(R.id.etModelo);
        etMarca = findViewById(R.id.etMarca);
        etAnioFabricacion = findViewById(R.id.etAnioFabricacion);
        etPrecioBase = findViewById(R.id.etPrecioBase);
        etKilometraje = findViewById(R.id.etKilometraje);
        etEsperanzaVida = findViewById(R.id.etEsperanzaVida);

        etConsumoCombustible = findViewById(R.id.etConsumoCombustible);
        etPotenciaMotor = findViewById(R.id.etPotenciaMotor);
        etGarantia = findViewById(R.id.etGarantia);
        etDescuento = findViewById(R.id.etDescuento);

        etAutonomia = findViewById(R.id.etAutonomia);
        etPeso = findViewById(R.id.etPeso);
        etCilindrada = findViewById(R.id.etCilindrada);
        etTipoManejo = findViewById(R.id.etTipoManejo);

        etPotenciaMotoModificada = findViewById(R.id.etPotencia);
        etMarcaEscape = findViewById(R.id.etMarcaEscape);
        etCilindrada2 = findViewById(R.id.etCilindrada2);
        etTipoManejo2 = findViewById(R.id.etTipoManejo2);
        etTipoModificacion = findViewById(R.id.etTipoModificacion);

        //CONFIGURACIÓN DEL SPINNER
        spinnerTipo = findViewById(R.id.spinnerTipo);
        layoutAutoNuevo = findViewById(R.id.layoutAutoNuevo);
        layoutMoto = findViewById(R.id.layoutMoto);
        layoutMotoModificada = findViewById(R.id.layoutMotoModificada);

        String[] opciones = {"Auto Nuevo", "Moto", "Moto Modificada"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                layoutAutoNuevo.setVisibility(View.GONE);
                layoutMoto.setVisibility(View.GONE);
                layoutMotoModificada.setVisibility(View.GONE);

                switch (position) {
                    case 0:
                        layoutAutoNuevo.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        layoutMoto.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        layoutMotoModificada.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // HASTA AQUÍ LA CONFIGURACIÓN DEL SPINNER

        // ESTO ES PARA EL AUTORELLENADO DE LOS CAMPOS
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("vehiculoEditar")) {
            Vehiculo vehiculoEditar = (Vehiculo) intent.getSerializableExtra("vehiculoEditar");
            etId.setVisibility(View.VISIBLE);
            etId.setEnabled(false);

            if (vehiculoEditar instanceof AutoNuevo) {
                spinnerTipo.setSelection(0);
                layoutAutoNuevo.setVisibility(View.VISIBLE);
                AutoNuevo auto = (AutoNuevo) vehiculoEditar;

                etId.setText(String.valueOf(auto.getId()));
                etMarca.setText(auto.getMarca());
                etModelo.setText(auto.getModelo());
                etAnioFabricacion.setText(String.valueOf(auto.getAnoFabricacion()));
                etPrecioBase.setText(String.valueOf(auto.getPrecioBase()));
                etKilometraje.setText(String.valueOf(auto.getKilometraje()));
                etEsperanzaVida.setText(String.valueOf(auto.getEsperanzaVida()));
                etConsumoCombustible.setText(auto.getPotencia());
                etPotenciaMotor.setText(auto.getModificacion());
                etGarantia.setText(String.valueOf(auto.getGarantiaAnos()));
                etDescuento.setText(String.valueOf(auto.getDescuentoPromocional()));

            } else if (vehiculoEditar instanceof MotoModificada) {
                spinnerTipo.setSelection(2);
                layoutMotoModificada.setVisibility(View.VISIBLE);
                MotoModificada mm = (MotoModificada) vehiculoEditar;

                etId.setText(String.valueOf(mm.getId()));
                etMarca.setText(mm.getMarca());
                etModelo.setText(mm.getModelo());
                etAnioFabricacion.setText(String.valueOf(mm.getAnoFabricacion()));
                etPrecioBase.setText(String.valueOf(mm.getPrecioBase()));
                etKilometraje.setText(String.valueOf(mm.getKilometraje()));
                etEsperanzaVida.setText(String.valueOf(mm.getEsperanzaVida()));
                etPotenciaMotoModificada.setText(mm.getPotencia());
                etMarcaEscape.setText(mm.getModificacion());
                etCilindrada2.setText(String.valueOf(mm.getCilindradaCc()));
                etTipoManejo2.setText(mm.getTipoManejo());
                etTipoModificacion.setText(mm.getTipoModificacion());
            } else if (vehiculoEditar instanceof Moto) {
                spinnerTipo.setSelection(1);
                layoutMoto.setVisibility(View.VISIBLE);
                Moto moto = (Moto) vehiculoEditar;

                etId.setText(String.valueOf(moto.getId()));
                etMarca.setText(moto.getMarca());
                etModelo.setText(moto.getModelo());
                etAnioFabricacion.setText(String.valueOf(moto.getAnoFabricacion()));
                etPrecioBase.setText(String.valueOf(moto.getPrecioBase()));
                etKilometraje.setText(String.valueOf(moto.getKilometraje()));
                etEsperanzaVida.setText(String.valueOf(moto.getEsperanzaVida()));
                etAutonomia.setText(moto.getPotencia());
                etPeso.setText(moto.getModificacion());
                etCilindrada.setText(String.valueOf(moto.getCilindradaCc()));
                etTipoManejo.setText(moto.getTipoManejo());
            }
        }

        btnSave.setOnClickListener(v -> {
            Vehiculo vehiculo = null;
            int seleccion = spinnerTipo.getSelectedItemPosition();
            int id = -1;

            if (getIntent().hasExtra("vehiculoEditar")) {
                id = Integer.parseInt(etId.getText().toString());
            }

            String marca = etMarca.getText().toString();
            String modelo = etModelo.getText().toString();
            int anioFabricacion = Integer.parseInt(etAnioFabricacion.getText().toString());
            double precioBase = Double.parseDouble(etPrecioBase.getText().toString());
            double km = Double.parseDouble(etKilometraje.getText().toString());
            int vida = Integer.parseInt(etEsperanzaVida.getText().toString());

            if (seleccion == 0) {
                String consumoCombustible = etConsumoCombustible.getText().toString();
                String potenciaMotor = etPotenciaMotor.getText().toString();
                int garantia = Integer.parseInt(etGarantia.getText().toString());
                double descuento = Double.parseDouble(etDescuento.getText().toString());

                vehiculo = new AutoNuevo(id, marca, modelo, anioFabricacion, precioBase, km, vida, consumoCombustible,
                        potenciaMotor, garantia, descuento);

            } else if (seleccion == 1) {
                String autonomia = etAutonomia.getText().toString();
                String peso = etPeso.getText().toString();
                double cilindradaMoto = Double.parseDouble(etCilindrada.getText().toString());
                String tipoManejoMoto = etTipoManejo.getText().toString();

                vehiculo = new Moto(id, marca, modelo, anioFabricacion, precioBase, km, vida, autonomia,
                        peso, cilindradaMoto, tipoManejoMoto);
            } else if (seleccion == 2) {
                String potenciaMotoModificada = etPotenciaMotoModificada.getText().toString();
                String marcaEscape = etMarcaEscape.getText().toString();
                double cilindradaMotoModificada = Double.parseDouble(etCilindrada2.getText().toString());
                String tipoManejoMotoModificada = etTipoManejo2.getText().toString();
                String tipoModificacionMotoModificada = etTipoModificacion.getText().toString();

                vehiculo = new MotoModificada(id, marca, modelo, anioFabricacion, precioBase, km, vida, potenciaMotoModificada,
                        marcaEscape, cilindradaMotoModificada, tipoManejoMotoModificada, tipoModificacionMotoModificada);
            }

            if (vehiculo != null) {
                Toast.makeText(FormularioActivity.this, "Vehículo " + (getIntent().hasExtra("vehiculoEditar") ? "editado" : "agregado") + " correctamente", Toast.LENGTH_SHORT).show();
                Intent intentResultado = new Intent();
                int posicion = getIntent().getIntExtra("posicion", -1);

                if (getIntent().hasExtra("vehiculoEditar")) {
                    intentResultado.putExtra("vehiculoEditado", vehiculo);
                    intentResultado.putExtra("posicion", posicion);
                } else {
                    intentResultado.putExtra("vehiculoNuevo", vehiculo);
                }

                setResult(RESULT_OK, intentResultado);
                finish();
            }
        });

        btnClear.setOnClickListener(v -> {
            etModelo.setText("");
            etMarca.setText("");
            etAnioFabricacion.setText("");
            etPrecioBase.setText("");
            etKilometraje.setText("");
            etEsperanzaVida.setText("");
            etConsumoCombustible.setText("");
            etPotenciaMotor.setText("");
            etGarantia.setText("");
            etDescuento.setText("");
            etAutonomia.setText("");
            etPeso.setText("");
            etCilindrada.setText("");
            etTipoManejo.setText("");
            etPotenciaMotoModificada.setText("");
            etMarcaEscape.setText("");
            etCilindrada2.setText("");
            etTipoManejo2.setText("");
            etTipoModificacion.setText("");
            spinnerTipo.setSelection(0);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}