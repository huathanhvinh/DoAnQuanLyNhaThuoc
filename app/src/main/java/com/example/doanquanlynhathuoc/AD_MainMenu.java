package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.content.DialogInterface;
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Config.StaticConfig;
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4

public class AD_MainMenu extends AppCompatActivity {
    TextView tvTaiKhoan;
    Button btnThemNV, btnRSMatKhauNV, btnKhoaNV, btnThongKe;
    Button btnDSNV, btnTraCuuNhapThuoc, btnTraCuuBanThuoc, btnSuaQuyDinh, btnDoiMK, btnDangXuat;

<<<<<<< HEAD
=======
    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menuadmin,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
    Button btnDoiMatKhau;

>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_main_menu);
        setControl();
        setEvent();

    }

    private void setEvent() {
<<<<<<< HEAD
        //lấy tài khoản
        tvTaiKhoan.setText("Xin chào: "+ StaticConfig.taiKhoan);
        //nút thêm nhân viên
        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_ThemNhanVien.class));
            }
        });
        //rs mật khẩu nv
        btnRSMatKhauNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_ResetMatKhauStep1.class));
            }
        });
        //khóa mật khẩu nv
        btnKhoaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_KhoaNhanVienStep1.class));
            }
        });
        //thống kê
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_ThongKeDoanhSoTheoNam.class));
            }
        });
        //danh sách nhân viên
        btnDSNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_HienThiDanhSachNhanVien.class));
            }
        });
        //tra cứu nhập thuốc
        btnTraCuuNhapThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_TraCuuNhapThuoc.class));
            }
        });
        //tra cứu bán thuốc
        btnTraCuuBanThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_TraCuuBanThuoc.class));
            }
        });
        //sửa đổi quy định nhà thuốc
        btnSuaQuyDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_SuaDoiQuyDinhNhaThuoc.class));
            }
        });
        //đổi mật khẩu
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
=======
        //nút đổi mật khẩu
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Chung_DoiMatKhau.class));
            }
        });
<<<<<<< HEAD
        //đăng xuất
        //nút đăng xuất
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AD_MainMenu.this);
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
        btnThemNV = findViewById(R.id.btnThemNhanVien);
        btnRSMatKhauNV = findViewById(R.id.btnRSMatKhau);
        btnKhoaNV = findViewById(R.id.btnKhoaNhanVien);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnDSNV = findViewById(R.id.btnXemDanhSachNhanVien);
        btnTraCuuNhapThuoc = findViewById(R.id.btnTraCuuNhapThuoc);
        btnTraCuuBanThuoc = findViewById(R.id.btnTraCuuBanThuoc);
        btnSuaQuyDinh = findViewById(R.id.btnSuaDoiQuyDinhNhaThuoc);
        btnDoiMK = findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = findViewById(R.id.btnDangXuat);
=======
    }

    private void setControl() {
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhauAD);
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    }
}