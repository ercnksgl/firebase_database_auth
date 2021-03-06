package com.agriculture.ek.agriculture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private String token;
    TextView name_surname;
    private String program_jobs = " ";
    private ImageView header_pic;
    private TextView header_title;
    private String userName=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name_surname = findViewById(R.id.home_title_text);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            token = firebaseAuth.getCurrentUser().getUid();
        } else {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Services");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Users").child(token).child("name").getValue(String.class);
                String surname = dataSnapshot.child("Users").child(token).child("surname").getValue(String.class);
                String program_job = dataSnapshot.child("Users").child(token).child("program_job").getValue(String.class);
                String city = dataSnapshot.child("Users").child(token).child("city").getValue(String.class);
                String email = dataSnapshot.child("Users").child(token).child("email").getValue(String.class);
                program_jobs = program_job;
                userName=name+" "+surname;
                String field_name = dataSnapshot.child("Fields").child(token).child("field_name").getValue(String.class);

                String irrigation_system = dataSnapshot.child("Profile").child(token).child("irrigation_system").getValue(String.class);


                name_surname.setText("Adın:" + name + "\n\nSoyad:" + surname + "\n\n" +
                        "Şehir:" + city + "\n\n" +
                        "Email:" + email + "\n\nMeslek:" + program_job + "\n\n" +
                        "Tarla Adı:" + field_name + "\n\n" + "Sulama yöntemi:" + irrigation_system);

                showMenuItems();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_job_list).setVisible(false);
        nav_Menu.findItem(R.id.nav_give_job_ad).setVisible(false);

        navigationView.removeHeaderView(null);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_home, null);
        navigationView.addHeaderView(headerView);
        header_pic = headerView.findViewById(R.id.header_profile_pic);
        header_title = headerView.findViewById(R.id.header_nav_profile_name_txt);


    }

    private void showMenuItems() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

        header_title.setText(program_jobs +"\n"+ userName );

        if (program_jobs.equalsIgnoreCase("Öğrenci")) {
            nav_Menu.findItem(R.id.nav_job_list).setVisible(true);
            nav_Menu.findItem(R.id.nav_give_job_ad).setVisible(false);
            header_pic.setImageResource(R.drawable.ic_student_icon);
        } else if (program_jobs.equalsIgnoreCase("Firma Yetkilisi")) {
            nav_Menu.findItem(R.id.nav_give_job_ad).setVisible(true);
            nav_Menu.findItem(R.id.nav_job_list).setVisible(false);
            header_pic.setImageResource(R.drawable.boss_icon);
        } else {
            nav_Menu.findItem(R.id.nav_job_list).setVisible(false);
            nav_Menu.findItem(R.id.nav_give_job_ad).setVisible(false);
            header_pic.setImageResource(R.drawable.ic_farmer_icon);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            finish();

        } else if (id == R.id.nav_give_job_ad) {
            startActivity(new Intent(HomeActivity.this, GiveBussinesAdActivity.class));
            finish();
            Toast.makeText(this, "İlan Ver", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_job_list) {
            startActivity(new Intent(HomeActivity.this,AdvertisementsActivity.class));
            finish();

        } else if (id == R.id.nav_premium_packages) {
            Toast.makeText(this, "Premium Paketler", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_analyst) {
            Toast.makeText(this, "Analistler", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Ayarlar", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Paylaş", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_exit) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, SplashActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
