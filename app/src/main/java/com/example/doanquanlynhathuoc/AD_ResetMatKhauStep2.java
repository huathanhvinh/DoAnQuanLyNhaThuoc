package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Class.Account;
import com.example.doanquanlynhathuoc.Class.NhanVien;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class AD_ResetMatKhauStep2 extends AppCompatActivity {
    ImageButton imTroVe;
    TextView tvTaiKhoan, tvSdt;
    EditText edMatKhauMoi, edXacNhanMK;
    Button btnRS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_reset_mat_khau_step2);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //nút trở về
        imTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //nút reset
        btnRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMatKhauMoi.getText().toString().equals(edXacNhanMK.getText().toString()) == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_ResetMatKhauStep2.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Xác nhận mật khẩu không đúng !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    NhanVien thongTinNV = (NhanVien) getIntent().getSerializableExtra("ThongTinNhanVien");

                    StaticConfig.mAccount.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Account ac = snapshot.getValue(Account.class);
                            if(ac.getSdt().equals(thongTinNV.getSdt()))
                            {
                                ac.setMatKhau(edMatKhauMoi.getText().toString());
                                StaticConfig.mAccount.child(ac.getMaFB()).setValue(ac);
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_ResetMatKhauStep2.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Reset mật khẩu thành công !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();

                }
            }
        });
        //lấy thông tin nhân viên
        layThongTinNhanVien();
    }

    private void layThongTinNhanVien() {
        NhanVien thongTinNV = (NhanVien) getIntent().getSerializableExtra("ThongTinNhanVien");
        StaticConfig.mAccount.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Account ac = snapshot.getValue(Account.class);
                if (thongTinNV.getSdt().equals(ac.getSdt())) {
                    tvTaiKhoan.setText(ac.getTaiKhoan());
                    tvSdt.setText(ac.getSdt());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setControl() {
        imTroVe = findViewById(R.id.imTrove);
        tvTaiKhoan = findViewById(R.id.tvTaiKhoanNV);
        tvSdt = findViewById(R.id.sdtNv);
        edMatKhauMoi = findViewById(R.id.edMatKhauMoiRS);
        edXacNhanMK = findViewById(R.id.edXacNhanMatKhauMoiRS);
        btnRS = findViewById(R.id.btnRSMatKhau);
    }
}