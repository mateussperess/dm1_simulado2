package com.example.simulado2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText etNomeRio;
    private EditText etNumDecimalValorMinimo;
    private  EditText etNumDecimalValorMaximo;
    private Button btnSalvarConfig;

    public static String PREFS = "prefs";
    public static String CHAVE_NOME_RIO = "nome_rio";
    public static String CHAVE_VALOR_MINIMO = "valor_minimo";
    public static String CHAVE_VALOR_MAXIMO = "valor_maximo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent it2 = new Intent(this, MainActivity2.class);
        instanciarElementosInterface();

        etNomeRio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNomeRio.setText("");
            }
        });

        btnSalvarConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double alturaMinima = Double.parseDouble(etNumDecimalValorMinimo.getText().toString());
                    double alturaMaxima = Double.parseDouble(etNumDecimalValorMaximo.getText().toString());
                    String nomeRio = etNomeRio.getText().toString();
                     if(alturaMinima > alturaMaxima) {
                         throw new IllegalArgumentException("A altura mínima deve ser menor do que a altura máxima!");
                     }

                     if(nomeRio.isEmpty() || nomeRio.equals("")) {
                         throw new IllegalArgumentException("O nome do rio é inválido!");
                     }

                    salvarPreferencias();
                    startActivity(it2);

                } catch (IllegalArgumentException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void instanciarElementosInterface() {
        etNomeRio = findViewById(R.id.etNomeRio);
        etNumDecimalValorMinimo = findViewById(R.id.etNumDecimalValorMinimo);
        etNumDecimalValorMaximo = findViewById(R.id.etNumDecimalValorMaximo);
        btnSalvarConfig = findViewById(R.id.btnSalvarConfig);
        Log.d("SUCCESS", "Elementos de interface da MA1 instanciados!");
    }

    private void salvarPreferencias() {
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CHAVE_NOME_RIO, etNomeRio.getText().toString());
        editor.putString(CHAVE_VALOR_MINIMO, etNumDecimalValorMinimo.getText().toString());
        editor.putString(CHAVE_VALOR_MAXIMO, etNumDecimalValorMaximo.getText().toString());
        editor.commit();
        Toast.makeText(this, "Configuração inicial realizada!", Toast.LENGTH_SHORT).show();
    }
}