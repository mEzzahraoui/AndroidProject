package com.example.meriame.authenticationtest;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class navigationBarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseUser user;
    private ImageView imageView;
    private StorageReference storageReference;
    private NotificationCompat.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("places");
        builder = new NotificationCompat.Builder(this);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("Firebase Push Notification");
                builder.setContentText("Hello this is a test Firebase notification, a new database child has been added");
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new FMaps(), "mMap").commit();


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        user = FirebaseAuth.getInstance().getCurrentUser();
        imageView=(ImageView)findViewById(R.id.profilePicture);
        Uri photoUrl = user.getPhotoUrl();
        TextView username=(TextView)findViewById(R.id.userName);
        TextView useremail=(TextView)findViewById(R.id.UserEmail);
        username.setText(user.getDisplayName());
        useremail.setText(user.getEmail());
        //Picasso.with(this).load(photoUrl).into(imageView);
        Picasso.get().load(photoUrl).resize(200, 200).centerCrop().into(imageView);
        getMenuInflater().inflate(R.menu.navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        FMaps f = (FMaps)getSupportFragmentManager().findFragmentByTag("mMap");

        if(f!=null){
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_none) {
                f.mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            }
            else if (id == R.id.action_satellite) {
                f.mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            }
            else if (id == R.id.action_normal) {
                f.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
      //  android.support.v4.app.FragmentManager sfm=getSupportFragmentManager();

        if (id == R.id.nav_map) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new FMaps(), "mMap").commit();
        } else if (id == R.id.nav_add_place) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            Intent addPlace=new Intent(navigationBarActivity.this, AddPlace.class);
            startActivity(addPlace);

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            Intent second=new Intent(navigationBarActivity.this, MainActivity.class);
            startActivity(second);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
