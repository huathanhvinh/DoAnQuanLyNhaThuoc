package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.Account;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Chung_DangNhapHeThong extends AppCompatActivity {
    EditText edTaiKhoan, edMatKhau;
    Button btnDangNhap;
    TextView tvLienKetQuyDinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chung_dang_nhap_he_thong);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //Quy định nhà thuốc
        tvLienKetQuyDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Chung_QuyDinhNhaThuoc.class));
            }
        });
        //Nút đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra rỗng
                if (edTaiKhoan.getText().toString().equals("") || edMatKhau.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Chung_DangNhapHeThong.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Tài khoản hoặc mật khẩu không được để trống !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    StaticConfig.mAccount.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int check = 0;
                            String taiKhoan = edTaiKhoan.getText().toString();
                            String matKhau = edMatKhau.getText().toString();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Account ac = ds.getValue(Account.class);
                                if (taiKhoan.equals(ac.getTaiKhoan()) && matKhau.equals(ac.getMatKhau()) && ac.getTrangThai().equals("khoa")) {
                                    check = 1;
                                    StaticConfig.ghiChu = ac.getGhiChu();
                                    break;
                                } else if (taiKhoan.equals(ac.getTaiKhoan()) && matKhau.equals(ac.getMatKhau()) && ac.getTrangThai().equals("khoa") == false) {
                                    check = 2;
                                    StaticConfig.maFB = ac.getMaFB();
                                    StaticConfig.taiKhoan = ac.getTaiKhoan();
                                    StaticConfig.trangThai = ac.getTrangThai();
                                    StaticConfig.sdt = ac.getSdt();
                                    StaticConfig.role = ac.getRole();
                                    StaticConfig.matKhau = ac.getMatKhau();
                                    break;
                                } else {
                                    check = 3;
                                }

                            }
                            //Kiểm tra và cho đăng nhập
                            if (check == 3) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Chung_DangNhapHeThong.this);
                                builder.setTitle("Thông Báo");
                                builder.setMessage("Tài khoản hoặc mật khẩu không đúng !!!");
                                builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                builder.show();
                            } else if (check == 2) {
                                if (StaticConfig.role == 1) {
                                    startActivity(new Intent(getApplicationContext(), AD_MainMenu.class));
                                } else if (StaticConfig.role == 2) {
                                    startActivity(new Intent(getApplicationContext(), NVK_MainMenu.class));
                                }else
                                    startActivity(new Intent(getApplicationContext(), NVBH_MainMenu.class));
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Chung_DangNhapHeThong.this);
                                builder.setTitle("Thông Báo");
                                builder.setMessage("Tài khoản của bạn đã bị khóa !!!\n Lý do: " + StaticConfig.ghiChu + "");
                                builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                builder.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    private void setControl() {
        edTaiKhoan = findViewById(R.id.edTaiKhoan);
        edMatKhau = findViewById(R.id.edMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvLienKetQuyDinh = findViewById(R.id.tvQuyDinhNhaThuoc);
    }
}