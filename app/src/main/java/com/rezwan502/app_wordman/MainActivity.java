package com.rezwan502.app_wordman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.rezwan502.app_wordman.adapter.PageAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem wordSearch, favWord;

    private PageAdapter pageAdapter;

    private static final int STORAGE_PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isNetworkAvailable()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Connection Alert")
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        }


        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);


        tabLayout = findViewById(R.id.tabLayout);
        wordSearch = findViewById(R.id.tab1);
        favWord= findViewById(R.id.tab2);

        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_star_24);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_search_24);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    pageAdapter.notifyDataSetChanged();
                }else if(tab.getPosition() == 1){
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);



        if((wificonn != null && wificonn.isConnected()) || mobileconn != null && mobileconn.isConnected()){
            return true;
        }else{
            return false;
      }
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
           // Toast.makeText(MainActivity.this,"Permission already granted",Toast.LENGTH_SHORT).show();
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);


        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        }
    }




}