package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NVBH_MainMenu extends AppCompatActivity {
Button btnDoiMatKhau,btnThemHoaDon;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvbh_main_menu);
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
        //nút thêm hóa đơn
        btnThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVBH_ThemHoaDon.class));
            }
        });
    }

    private void setControl() {
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhauNVBH);
        btnThemHoaDon = findViewById(R.id.btnThemHoaDon);
    }
}