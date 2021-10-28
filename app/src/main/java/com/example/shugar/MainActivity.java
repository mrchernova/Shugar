package com.example.shugar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etCurrentGlucose;
    private EditText etXE;
    private EditText etFinalGlucose;
    private EditText etTotalAL;
    private EditText etKGlucose;
    private EditText etKXE;

    private Button btnOk;
    private Button btnUpdate;
    private Button btnClose;
    private Button btnSave;


    private TextView APIDRA;

    SharedPreferences sPref; //for save
    final String SAVED_TEXT = "saved_text";
    final String SAVED_TEXT_FG = "saved_text_fg";
    final String SAVED_TEXT_TAL = "saved_text_tal";
    final String SAVED_TEXT_KG = "saved_text_kg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        etCurrentGlucose = findViewById(R.id.etCurrentGlucose);
        etXE = findViewById(R.id.etXE);
        etFinalGlucose = findViewById(R.id.etFinalGlucose);
        etTotalAL = findViewById(R.id.etTotalAL);
        etKGlucose = findViewById(R.id.etKGlucose);
        etKXE = findViewById(R.id.etKXE);

        btnOk = findViewById(R.id.btnOk);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnClose = findViewById(R.id.btnClose);
        btnSave = findViewById(R.id.btnSave);

        APIDRA = findViewById(R.id.APIDRA);


        //set focus
        etCurrentGlucose.requestFocus();


// вычислить kXE BEGIN
        double tal, kxe;

        String str_tal = etTotalAL.getText().toString();
        tal = Double.parseDouble(str_tal);

        kxe = 12 / (500 / tal);
        kxe = Math.round(kxe * 100.0) / 100.0;
        String str_kxe = Double.toString(kxe);

        etKXE.setText(str_kxe);
// вычислить kXE END



// выводит сохрааненные значения при запуске
        sPref = getSharedPreferences("savedData", MODE_PRIVATE);
        String savedTextFG = sPref.getString(SAVED_TEXT_FG, "");
        etFinalGlucose.setText(savedTextFG);
        String savedTextTAL = sPref.getString(SAVED_TEXT_TAL, "");
        etTotalAL.setText(savedTextTAL);
        String savedTextKG = sPref.getString(SAVED_TEXT_KG, "");
        etKGlucose.setText(savedTextKG);
     //   etKXE.setText(savedText);
        Toast.makeText(MainActivity.this, "Text loaded", Toast.LENGTH_SHORT).show();



// btnOK BEGIN
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCurrentGlucose.getText().toString().trim().equals(""))
                    Toast.makeText(MainActivity.this, R.string.no_input_glucose, Toast.LENGTH_SHORT).show();
                else {

                    double a, fg, cg, xe, tal, kg, kxe;

                    // читает что написано в EditText
                    String str_cg = etCurrentGlucose.getText().toString();
                    String str_xe = etXE.getText().toString();
                    String str_fg = etFinalGlucose.getText().toString();
                    String str_tal = etTotalAL.getText().toString();
                    String str_kg = etKGlucose.getText().toString();
                    String str_kxe = etKXE.getText().toString();              // заполнится автоматически расчет формулой при запуске


                    //Преобразуем текстовые переменные в числовые значения
                    cg = Double.parseDouble(str_cg);
                    xe = Double.parseDouble(str_xe);
                    fg = Double.parseDouble(str_fg);
                  //  tal = Double.parseDouble(str_tal);
                    kg = Double.parseDouble(str_kg);
                    kxe = Double.parseDouble(str_kxe);

                    // вычисление количества Апидры
                    a = (kxe * xe) + (cg - fg) / kg;
                    a = Math.round(a * 100.0) / 100.0;

                    String str_apidra = Double.toString(a);
                    APIDRA.setText(str_apidra);
                }
            }
        });
// btnOK END


// btnUpdate BEGIN
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double tal, kxe;

                String str_tal = etTotalAL.getText().toString();
                tal = Double.parseDouble(str_tal);

                kxe = 12 / (500 / tal);
                kxe = Math.round(kxe * 100.0) / 100.0;
                String str_kxe = Double.toString(kxe);

                etKXE.setText(str_kxe);
            }
        });
// btnUpdate END

// btnSave BEGIN
btnSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        sPref = getSharedPreferences("savedData", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT_FG, etFinalGlucose.getText().toString());
        ed.putString(SAVED_TEXT_TAL, etTotalAL.getText().toString());
        ed.putString(SAVED_TEXT_KG, etKGlucose.getText().toString());
       // ed.putString(SAVED_TEXT, etKXE.getText().toString());
        ed.commit();
        Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
    }
});
// btnSave END


// btnClose BEGIN
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
// btnClose END



    }
}