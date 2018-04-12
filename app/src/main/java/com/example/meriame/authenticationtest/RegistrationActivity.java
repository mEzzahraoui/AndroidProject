package com.example.meriame.authenticationtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    public FirebaseAuth mAuth;
    private String email, password, password2, userName;
    private Button submit;
    private ImageView image;

    private Uri imagePath;

    private static final int PICK_IMG=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

        storageReference=firebaseStorage.getReference();
       // StorageReference myRef1=storageReference.child(mAuth.getCurrentUser().getUid());

        submit=(Button)findViewById(R.id.validate);
        image=(ImageView)findViewById(R.id.imageView2);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"),PICK_IMG );
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate()){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        sendEmailVerification();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegistrationActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                else {
                    Toast.makeText(RegistrationActivity.this, "Verify the input",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMG && resultCode==RESULT_OK && data.getData()!=null){
            imagePath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Boolean Validate(){
        email=((EditText)findViewById(R.id.EmailRegistration)).getText().toString().trim();
        password=((EditText)findViewById(R.id.PasswordRegistration)).getText().toString();
        password2=((EditText)findViewById(R.id.Password2)).getText().toString();
        userName=((EditText)findViewById(R.id.NameRegistration)).getText().toString();
        if(!email.equals("") && !password.equals("") && !password2.equals("") && !userName.equals("") && imagePath!=null){
            if(password.equals(password2)){
                return true;
            }
            else
                return false;
        }

        return  false;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Verification mail hasn't been sent!", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("users").child(mAuth.getCurrentUser().getUid());
        StorageReference imgReference=storageReference.child("users").child(mAuth.getCurrentUser().getUid()).child("Profile pic");
        UploadTask uploadTask=imgReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Upload failed", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
            }
        });
        User user=new User(userName, email);
        myRef.setValue(user);

    }

    public void OnClickLogin(View view) {
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
        finish();
    }
}
