package com.example.meriame.authenticationtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText emailResetPass;
    private Button ButtonReset;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        emailResetPass=(EditText)findViewById(R.id.EmailResetPass);
        ButtonReset=(Button)findViewById(R.id.ButtonResetPass);
        mAuth=FirebaseAuth.getInstance();

        ButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=emailResetPass.getText().toString().trim();
                if(userEmail.equals("")){
                    Toast.makeText(PasswordActivity.this, "Please enter your registred email", Toast.LENGTH_LONG).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this, "Password reset email sent !", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                            }
                            else {
                                Toast.makeText(PasswordActivity.this, "Error in sending password reset email", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, PasswordActivity.class));
                            }
                        }
                    });
                }

            }
        });
    }
}
