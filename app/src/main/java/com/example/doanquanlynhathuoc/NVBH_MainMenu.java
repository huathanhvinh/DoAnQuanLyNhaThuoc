package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Config.StaticConfig;

public class NVBH_MainMenu extends AppCompatActivity {
    TextView tvTaiKhoan;
    Button btnLapHoaDon,btnThemKH,btnTraCuuHD,btnXemDSHD,btnDanhSachKH,btnDoiMatKhau;
    Button btnDangXuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvbh_main_menu);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //lấy tài khoản
        tvTaiKhoan.setText("Xin chào: "+ StaticConfig.taiKhoan);
        //nút lập hóa đơn
        btnLapHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVBH_ThemHoaDon.class));
            }
        });
        //nút thêm khách hàng
        btnThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVBH_ThemKhachHang.class));
            }
        });
        //Tra cứu hóa đơn
        btnTraCuuHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVBH_TraCuuHoaDon.class));
            }
        });
        //nút xem danh sách hóa đơn
        btnXemDSHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVBH_DanhSachHoaDon.class));
            }
        });
        //danh sách khách hàng
        btnDanhSachKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVBH_DanhSachKhachHang.class));
            }
        });
        //nút đổi mật khẩu
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Chung_DoiMatKhau.class));
            }
        });
        //nút đăng xuất
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_MainMenu.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("bạn có muốn quay lại màn hình đăng nhập không ?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),Chung_DangNhapHeThong.class));
                    }
                });
                builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });


    }

    private void setControl() {
        tvTaiKhoan = findViewById(R.id.tvTaiKhoan);
        btnLapHoaDon = findViewById(R.id.btnLapHoaDon);
        btnThemKH = findViewById(R.id.btnThemKhachHang);
        btnTraCuuHD = findViewById(R.id.btnTraCuuHoaDon);
        btnXemDSHD = findViewById(R.id.btnDanhSachHoaDon);
        btnDanhSachKH = findViewById(R.id.btnDanhSachKhachHang);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = findViewById(R.id.btnDangXuat);
    }
}