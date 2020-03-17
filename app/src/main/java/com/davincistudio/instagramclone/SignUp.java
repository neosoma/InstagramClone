package com.davincistudio.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    
    // Nos interesa que los elementos de la interfaz sean privadas
    private Button btnSaveBoxer;
    private EditText edtName, edtPunchSpeed, edtSpeedPower, edtKickPower;
    
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

