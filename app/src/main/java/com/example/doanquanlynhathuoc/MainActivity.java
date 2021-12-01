package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.doanquanlynhathuoc.Class.Account;
import com.example.doanquanlynhathuoc.Config.StaticConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(getApplicationContext(), NVK_DanhSachThuoc.class));
    }
}