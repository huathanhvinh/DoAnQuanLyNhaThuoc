package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.content.DialogInterface;
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Config.StaticConfig;

public class NVK_MainMenu extends AppCompatActivity {
    TextView tvTaiKhoan;
    Button btnDoiMatKhau, btnLapPhieuNhapThuoc, btnTraCuuKho, btnTraCuuPhieuNhapThuoc, btnXemDanhSachThuoc, btnDangXuat, btnXemDanhSachPhieuNhapThuoc;

=======

public class NVK_MainMenu extends AppCompatActivity {
Button btnDoiMatKhau,btnLapPhieuNhapThuoc;
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_main_menu);
        setControl();
        setEvent();
    }

    private void setEvent() {
<<<<<<< HEAD
        //lấy tài khoản
        tvTaiKhoan.setText("Xin chào: "+StaticConfig.taiKhoan);
=======
        //nút đổi mật khẩu
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Chung_DoiMatKhau.class));
            }
        });
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
        //nút lập phiếu nhập thuốc
        btnLapPhieuNhapThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVK_ThemPhieuMuaThuoc.class));
            }
        });
<<<<<<< HEAD
        //nút tra cứu kho
        btnTraCuuKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVK_TraCuuKhoThuoc.class));
            }
        });
        //tra cứu phiếu nhập thuốc
        btnTraCuuPhieuNhapThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVK_TraCuuPhieuMuaThuoc.class));
            }
        });
        //xem danh sách thuốc
        btnXemDanhSachThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVK_DanhSachThuoc.class));
            }
        });
        //xem danh sách phiếu nhập thuốc
        btnXemDanhSachPhieuNhapThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVK_DanhSachPhieuMuaThuoc.class));
            }
        });
        //đổi mật khẩu
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
                AlertDialog.Builder builder = new AlertDialog.Builder(NVK_MainMenu.this);
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
        btnLapPhieuNhapThuoc = findViewById(R.id.btnLapPhieuNhapThuoc);
        btnTraCuuKho = findViewById(R.id.btnTraCuuKho);
        btnTraCuuPhieuNhapThuoc = findViewById(R.id.btnTraCuuPhieuNhapThuoc);
        btnXemDanhSachThuoc = findViewById(R.id.btnXemDanhSachThuoc);
        btnXemDanhSachPhieuNhapThuoc = findViewById(R.id.btnXemDanhSachPhieuNhapThuoc);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = findViewById(R.id.btnDangXuat);
=======
    }
    private void setControl() {
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhauNVK);
        btnLapPhieuNhapThuoc = findViewById(R.id.btnNVK_LapPhieuNhapThuoc);
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    }
}