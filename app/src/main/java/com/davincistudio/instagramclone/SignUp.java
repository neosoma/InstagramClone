package com.davincistudio.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    
    // Nos interesa que los elementos de la interfaz sean privadas
    private Button btnSaveBoxer, btnGetAllData;
    private EditText edtName, edtPunchSpeed, edtSpeedPower, edtKickPower;
    private TextView txtGetData;
    private String allBoxers;
    
    private Button btnTransition;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Inicializamos nuestros elementos en onCreate
        btnSaveBoxer = findViewById(R.id.btnSaveBoxer);
        // Onclick listener interface
        btnSaveBoxer.setOnClickListener(SignUp.this); // necesita implements View.OnclickListener. Esto implica crear el método onclick con @override para que no dé error
    
        // Es una buena práctica inicalizar los controles en el método onCreate
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtSpeedPower = findViewById(R.id.edtSpeedPower);
        edtKickPower = findViewById(R.id.edtKickPower);
        txtGetData = findViewById(R.id.txtGetData);
        
        btnGetAllData = findViewById(R.id.btnGetAllData);
        
        btnTransition = findViewById(R.id.btnNextActivity);
        
        txtGetData.setOnClickListener(new View.OnClickListener() {
    
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Boxer");
                parseQuery.getInBackground("72iULSJxMA", new GetCallback<ParseObject>() { // El método getInBackground devuelve un objeto
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object != null && e==null) {
                            txtGetData.setText(object.get("name") + " with kick power: " + object.get("kick_power")); // el +"" lo convierte en cadena de texto
                        }
                    }
                });
            }
        });
        
        btnGetAllData.setOnClickListener(new View.OnClickListener() {
    
            @Override
            public void onClick(View v) {
                
                allBoxers = ""; // Inicializamos la cadena de texto
                
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Boxer"); // equivale a un SELECT - selección de la clase
                //queryAll.whereGreaterThan("kick_power", 500); // Añadimos condición where
                queryAll.whereGreaterThanOrEqualTo("kick_power", 555);
                queryAll.setLimit(1); // Número máximo de registros
                
                queryAll.findInBackground(new FindCallback<ParseObject>() {  // el método findInBackground devuelve varios elementos
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e==null ) {
                            if (objects.size() > 0) { // Si recibimos al menos un elemento
                                
                                for (ParseObject boxer : objects) { // Recorremos todos los objetos de la lista
                                    allBoxers = allBoxers + "Boxer: " + boxer.get("name") + ". Kick power: " + boxer.get("kick_power") +"." + "\n";
                                }
                                FancyToast.makeText(SignUp.this, allBoxers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                
                            } else {
                                FancyToast.makeText(SignUp.this, "Something went wrong: " + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            }
        });
        
        btnTransition.setOnClickListener(new View.OnClickListener() {
    
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignUpLoginActivity.class);
                startActivity(intent);
            }
        });
    }
    
    @Override
    public void onClick(View v) {
        try {
            final ParseObject boxer = new ParseObject("Boxer"); // Especificamos objeto de una clase (se crea sobre la marcha)
            boxer.put("name", edtName.getText().toString()); // Atributo
            boxer.put("kick_power", Integer.parseInt(edtKickPower.getText().toString())); // Atributo
            boxer.put("speed_power", Integer.parseInt(edtSpeedPower.getText().toString())); // Atributo
            boxer.put("punch_speed", Integer.parseInt(edtPunchSpeed.getText().toString())); // Atributo
       
        /*
        boxer.put("name", "Pacus"); // Atributo
        boxer.put("kick_power", 12); // Atributo
        boxer.put("speed_power", 23); // Atributo
        boxer.put("punch_speed", 42); // Atributo
        */
    
    
            //boxer.save(); // This would block the application - blocks the main thread
            boxer.saveInBackground(new SaveCallback() { // El callback nos avisa cuando se ha realizado
                @Override
                public void done(ParseException e) { // Si algo va mal en el proceso de guardado
                    if (e == null) { // si no hay error -se ha guardado bien
                        //Toast.makeText(SignUp.this, "The boxer "+ boxer.get("name") + " is saved sucessfully", Toast.LENGTH_LONG).show();
                        FancyToast.makeText(SignUp.this, "The boxer " + boxer.get("name") + " is saved sucessfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        //Toast.makeText(SignUp.this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        FancyToast.makeText(SignUp.this, "Something went wrong: " + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
            
        } catch(Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }
    
}

