package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AD_MainMenu extends AppCompatActivity {

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menuadmin,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
    Button btnDoiMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_main_menu);
        setControl();
        setEvent();

    }

    private void setEvent() {
        //nút đổi mật khẩu
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Chung_DoiMatKhau.class));
            }
        });
    }

    private void setControl() {
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhauAD);
    }
}