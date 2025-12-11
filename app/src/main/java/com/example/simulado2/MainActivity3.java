package com.example.simulado2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {
    private ImageView imageView;
    private TextView txtNomeRio;
    private TextView txtQtdMedicoes;
    private TextView txtMediaDeAltura;
    private TextView txtMaiorAltura;
    private TextView txtMenorAltura;
    private Button btnVoltar;
    private double alturaMedia;
    public static String PREFS = "prefs";
    public static String CHAVE_VALOR_MINIMO = "valor_minimo";
    public static String CHAVE_VALOR_MAXIMO = "valor_maximo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        instanciarElementosInterface();
        recuperarDados();
        definirAlerta(alturaMedia);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void definirAlerta(double mediaAltura) {
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        String alturaMinimaPreferences = preferences.getString(CHAVE_VALOR_MINIMO, "valor_minimo");
        String alturaMaximaPreferences = preferences.getString(CHAVE_VALOR_MAXIMO, "valor_maximo");

        double alturaMinima = Double.parseDouble(alturaMinimaPreferences);
        double alturaMaxima = Double.parseDouble(alturaMaximaPreferences);

        if(mediaAltura <= alturaMinima) {
            imageView.setImageResource(R.drawable.alertaverde);
        } else if (mediaAltura > alturaMinima && mediaAltura < alturaMaxima) {
            imageView.setImageResource(R.drawable.alertalaranja);
        } else if (mediaAltura >= alturaMaxima) {
            imageView.setImageResource(R.drawable.alertavermelho);
        }
    }

    private void mostrarDados(String nomeRio, int qtdMedicoes, double mediaAltura, double maiorAltura, double menorAltura) {
        txtNomeRio.setText("Nome do rio: " + nomeRio);
        txtQtdMedicoes.setText("Quantidade de medições: " + qtdMedicoes);
        txtMediaDeAltura.setText("Média de altura: " + Double.toString(mediaAltura));
        txtMaiorAltura.setText("Maior altura registrada: " + Double.toString(maiorAltura));
        txtMenorAltura.setText("Menor altura registrada: " + Double.toString(menorAltura));

        alturaMedia = mediaAltura;
    }

    private void recuperarDados() {
        Intent intent = getIntent();
        try {
            String nomeRioExtra = intent.getStringExtra("nome_rio");
            int quantidadeMedicoesExtra = intent.getIntExtra("qtd_medicoes", 0);
            double mediaAlturaExtra = intent.getDoubleExtra("media_altura", 0.0);
            double maiorAlturaExtra = intent.getDoubleExtra("maior_altura", 0.0);
            double menorAlturaExtra = intent.getDoubleExtra("menor_altura", 0.0);
            mostrarDados(nomeRioExtra, quantidadeMedicoesExtra, mediaAlturaExtra, maiorAlturaExtra, menorAlturaExtra);
        } catch (Exception e) {
            Log.e("ERROR", "Exception: " + e.getMessage());
        }
    }

    private void instanciarElementosInterface() {
        imageView = findViewById(R.id.imageView);
        txtNomeRio = findViewById(R.id.txtNomeRio);
        txtQtdMedicoes = findViewById(R.id.txtQtdMedicoes);
        txtMediaDeAltura = findViewById(R.id.txtMediaDeAltura);
        txtMaiorAltura = findViewById(R.id.txtMaiorAltura);
        txtMenorAltura = findViewById(R.id.txtMenorAltura);
        btnVoltar = findViewById(R.id.btnVoltar);
        Log.d("SUCCESS", "Elementos de interface da MA3 instanciados!");
    }
}