package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NVK_MainMenu extends AppCompatActivity {
Button btnDoiMatKhau,btnLapPhieuNhapThuoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_main_menu);
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
        //nút lập phiếu nhập thuốc
        btnLapPhieuNhapThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVK_ThemPhieuMuaThuoc.class));
            }
        });
    }
    private void setControl() {
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnLapPhieuNhapThuoc = findViewById(R.id.btnLapPhieuNhapThuoc);
    }
}