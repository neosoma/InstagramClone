package com.davincistudio.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class WelcomeActivity extends AppCompatActivity {
    
    private TextView txtWelcome, txtSessionSMS;
    private Button btnLogOut;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        txtWelcome = findViewById(R.id.txtWelcome);
        txtWelcome.setText("Welcome " + ParseUser.getCurrentUser().get("username") +"!"); // Así accedemos a un usuario recientemente registrado o logeado (mantiene el usuario)
        
        txtSessionSMS = findViewById(R.id.txtSessionSMS);
        txtSessionSMS.setText("You are in the secret zone\n of Espacio DaVinci!!");
        
        btnLogOut = findViewById(R.id.btnLogOut);
    
        btnLogOut.setOnClickListener(new View.OnClickListener() {
    
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        FancyToast.makeText(WelcomeActivity.this, "The user is logged out successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    
                        Intent intent = new Intent(WelcomeActivity.this, SignUp.class);
                        startActivity(intent);
                    }
                });
            }
        });
        
        // Accedemos al botón btnLogOut2 ahora sin crear un field Button
        findViewById(R.id.btnLogOut2).setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                finish(); // Cierra el activity actual (devolviéndonos al anterior) - Las activities se acumulan en una pila (Stack)
            }
        });
    }
}
