package com.example.meriame.authenticationtest;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Vector;

public class AddPlace extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    //DECLARE THE FIELDS
    EditText userNameEditText, userStautsEditText,userAddsEditText;
    ImageView userImageProfileView;
    Button saveProfileBtn;
    Spinner spinner;
    String names[]={"hospital","restaurant","school","cafe","shop"};
    ArrayAdapter <String> adapter;
    String type;


    //FIREBASE AUTHENTICATION FIELDS
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    //FIREBASE DATABASE FIELDS
    DatabaseReference mUserDatabse;
    StorageReference mStorageRef;

    //IMAGE HOLD URI
    Uri imageHoldUri = null;

    //PROGRESS DIALOG
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplace);

        //ASSIGN ID'S
        userNameEditText = (EditText) findViewById(R.id.placeName);
        userStautsEditText = (EditText) findViewById(R.id.placeStatus);
        saveProfileBtn =(Button)findViewById(R.id.ButtonSavePlace);
        userImageProfileView = (ImageView) findViewById(R.id.placeImageView);
        userAddsEditText=(EditText) findViewById(R.id.placeAdd);

        spinner=(Spinner)findViewById(R.id.spinner);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position){
                case 0:
                    type="hospital";
                    break;
                case 1:
                    type="restaurant";
                    break;
                case 2:
                    type="school";
                    break;
                case 3:
                    type="cafe";
                    break;
                case 4:
                    type="shop";
                    break;
            }
        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


        //ASSIGN INSTANCE TO FIREBASE AUTH
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //LOGIC CHECK USER
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    finish();
                    Intent moveToHome = new Intent(AddPlace.this, MainActivity.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome);

                }

            }
        };

        //PROGRESS DIALOG
        mProgress = new ProgressDialog(this);
        //FIREBASE DATABASE INSTANCE
        mUserDatabse = FirebaseDatabase.getInstance().getReference().child("Places");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //ONCLICK SAVE PROFILE BTN
        saveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LOGIC FOR SAVING USER PROFILE
                saveUserProfile();

            }
        });

        //USER IMAGEVIEW ONCLICK LISTENER
        userImageProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //LOGIC FOR PROFILE PICTURE
                profilePicSelection();

            }
        });


    }

    private void saveUserProfile() {

        final String username, userStatus,userAdd;

        username = userNameEditText.getText().toString().trim();
        userStatus = userStautsEditText.getText().toString().trim();
        userAdd=userAddsEditText.getText().toString().trim();

        if( !TextUtils.isEmpty(username) && !TextUtils.isEmpty(userStatus))
        {

            if( imageHoldUri != null )
            {

                mProgress.setTitle("Saving Place");
                mProgress.setMessage("Please wait....");
                mProgress.show();

                StorageReference mChildStorage = mStorageRef.child("Pic_Places").child(imageHoldUri.getLastPathSegment());
                String profilePicUrl = imageHoldUri.getLastPathSegment();

                mChildStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Uri imageUrl = taskSnapshot.getDownloadUrl();
                        Place place=new Place();
                        place.setName(username);
                        place.setStatus(userStatus);
                        place.setType(type);
                        place.setNote(5);
                        place.setAddedBy(mAuth.getCurrentUser().getUid());
                        place.setUri(imageUrl.toString());
                        place.setLatitude(FMaps.latitude);
                        place.setLongitude(FMaps.longitude);
                        place.setAddress(userAdd);
                        Comment comment=new Comment();
                        comment.setUserName(mAuth.getCurrentUser().getDisplayName());
                        comment.setUserComment(userStatus);
                        ArrayList<Comment> comments=new ArrayList<Comment>();
                        comments.add(comment);
                        place.setComments(comments);
                        sendPlaceData(place);
                        mProgress.dismiss();
                        finish();
                        Intent moveToHome = new Intent(AddPlace.this, MainActivity.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(moveToHome);


                    }
                });
            }else
            {
                Toast.makeText(AddPlace.this, "Please select the pic", Toast.LENGTH_LONG).show();
            }

        }else
        {

            Toast.makeText(AddPlace.this, "Please enter name and status", Toast.LENGTH_LONG).show();

        }

    }
    private void sendPlaceData(Place place2){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("Places").child(place2.getName());
        Place place=place2;
        myRef.setValue(place);
    }

    private void profilePicSelection() {


        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPlace.this);
        builder.setTitle("Add Photo!");

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void cameraIntent() {

        //CHOOSE CAMERA
        Log.d("gola", "entered here");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {

        //CHOOSE IMAGE FROM GALLERY
        Log.d("gola", "entered here");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //SAVE URI FROM GALLERY
        if(requestCode == SELECT_FILE && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }else if ( requestCode == REQUEST_CAMERA && resultCode == RESULT_OK ){
            //SAVE URI FROM CAMERA

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }


        //image crop library code
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageHoldUri = result.getUri();

                userImageProfileView.setImageURI(imageHoldUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

}
