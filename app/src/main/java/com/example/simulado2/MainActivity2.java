package com.example.simulado2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private EditText etDateDataLeitura;
    private EditText etTimeHoraLeitura;
    private EditText etNumberDecimalAlturaLeitura;
    private Button btnSalvar;
    private TextView txtNomeRio;
    private TextView txtLimiteMinimo;
    private TextView txtLimiteMaximo;
    private Button btnVoltar;

    private String nomeRio;
    private double valorMinimo;
    private double valorMaximo;

    public static String PREFS = "prefs";
    public static String CHAVE_NOME_RIO = "nome_rio";
    public static String CHAVE_VALOR_MINIMO = "valor_minimo";
    public static String CHAVE_VALOR_MAXIMO = "valor_maximo";

    private int qtdMedicoes = 0;
    private double somaAlturasRegistradas = 0;
    private double mediaDeAlturaDasMedicoes = 0;
    private double maiorAlturaRegistrada = 0;
    private double menorAlturaRegistrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        instanciarElementosInterface();
        lerPreferencias();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtNomeRio.setText("Rio em observação: " + nomeRio);
        txtLimiteMinimo.setText("Limite Mínimo: " + valorMinimo + " metros.");
        txtLimiteMaximo.setText("Limite Máximo: " + valorMaximo + " metros.");

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String dataLeitura = etDateDataLeitura.getText().toString();
                    String horaLeitura = etTimeHoraLeitura.getText().toString();
                    double alturaLeitura = Double.parseDouble(etNumberDecimalAlturaLeitura.getText().toString());

                    if(dataLeitura.isEmpty() || dataLeitura.equals("")) {
                        throw new IllegalArgumentException("A data informada não foi informada!");
                    }

                    if(horaLeitura.isEmpty() || horaLeitura.equals("")) {
                        throw new IllegalArgumentException("A hora informada não foi informada!");
                    }

                    if(alturaLeitura <= 0) {
                        throw new IllegalArgumentException("A altura informa não está correta!");
                    }

                    if(alturaLeitura > maiorAlturaRegistrada) {
                        maiorAlturaRegistrada = alturaLeitura;
                    }

                    if(qtdMedicoes < 1) {
                        menorAlturaRegistrada = alturaLeitura;
                    }

                    if(alturaLeitura < menorAlturaRegistrada) {
                        menorAlturaRegistrada = alturaLeitura;
                    }

                    somaAlturasRegistradas += alturaLeitura;
                    qtdMedicoes++;
                    mediaDeAlturaDasMedicoes = calcularMediaDeAltura();

//                    Log.d("DATA", "Soma das alturas registradas: " + Double.toString(somaAlturasRegistradas));
//                    Log.d("DATA", "Quantidade de medicoes: " + qtdMedicoes);
//                    Log.d("DATA", "Media de altura: " + Double.toString(mediaDeAlturaDasMedicoes));
//                    Log.d("DATA", "Maior altura registrada: " + Double.toString(maiorAlturaRegistrada));
//                    Log.d("DATA", "Menor altura registrada: " + Double.toString(menorAlturaRegistrada));

                    Intent it3 = new Intent(MainActivity2.this, MainActivity3.class);
                    it3.putExtra("nome_rio", nomeRio);
                    it3.putExtra("qtd_medicoes", qtdMedicoes);
                    it3.putExtra("media_altura", mediaDeAlturaDasMedicoes);
                    it3.putExtra("maior_altura", maiorAlturaRegistrada);
                    it3.putExtra("menor_altura", menorAlturaRegistrada);
                    etTimeHoraLeitura.setText("");
                    etNumberDecimalAlturaLeitura.setText("");
                    startActivity(it3);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void instanciarElementosInterface() {
        etDateDataLeitura = findViewById(R.id.etDateDataLeitura);
        etTimeHoraLeitura = findViewById(R.id.etTimeHoraLeitura);
        etNumberDecimalAlturaLeitura = findViewById(R.id.etNumberDecimalAlturaLeitura);
        btnSalvar = findViewById(R.id.btnSalvar);
        txtNomeRio = findViewById(R.id.txtNomeRio);
        txtLimiteMinimo = findViewById(R.id.txtLimiteMinimo);
        txtLimiteMaximo = findViewById(R.id.txtLimiteMaximo);
        btnVoltar = findViewById(R.id.btnVoltar);
        Log.d("SUCCESS", "Elementos de interface da MA2 instanciados!");
    }

    private void lerPreferencias() {
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        String nomeRioPreferences = preferences.getString(CHAVE_NOME_RIO, "nome_rio");
        String valorMinimoPreferences = preferences.getString(CHAVE_VALOR_MINIMO, "valor_minimo");
        String valorMaximoPreferences = preferences.getString(CHAVE_VALOR_MAXIMO, "valor_maximo");
        nomeRio = nomeRioPreferences;
        valorMinimo = Double.parseDouble(valorMinimoPreferences);
        valorMaximo = Double.parseDouble(valorMaximoPreferences);
    }

    private double calcularMediaDeAltura() {
        return somaAlturasRegistradas / qtdMedicoes;
    }
}