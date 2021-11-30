package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Config.StaticConfig;

public class Chung_DoiMatKhau extends AppCompatActivity {
    TextView tvTaiKhoan;
    EditText mkHienTai, mkMoi, mkXacNhan;
    Button btnDoiMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chung_doi_mat_khau);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //Gán giá trị tài khoản hiện tại
        tvTaiKhoan.setText(StaticConfig.taiKhoan);
        //nút đổi mật khẩu
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if()
            }
        });
    }
    private void setControl() {
        tvTaiKhoan = findViewById(R.id.tvTaiKhoan);
        mkHienTai = findViewById(R.id.edMatKhauHienTai);
        mkMoi = findViewById(R.id.edMatKhauMoi);
        mkXacNhan = findViewById(R.id.edXacNhanMatKhauMoi);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
    }
}