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
<<<<<<< HEAD
import android.widget.ImageButton;
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Class.Account;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class Chung_DoiMatKhau extends AppCompatActivity {
    TextView tvTaiKhoan;
    EditText edMkHienTai, edMkMoi, edMkXacNhan;
    Button btnDoiMatKhau;
<<<<<<< HEAD
    ImageButton imTroVe;
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chung_doi_mat_khau);
        setControl();
        setEvent();
    }

    private void setEvent() {
<<<<<<< HEAD
        //nút trở về
        imTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
        //Gán giá trị tài khoản hiện tại
        tvTaiKhoan.setText(StaticConfig.taiKhoan);
        //nút đổi mật khẩu
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mk = edMkHienTai.getText().toString();
                String mkmoi = edMkMoi.getText().toString();
                String mkXN = edMkXacNhan.getText().toString();
                if (mk.equals("") || mkmoi.equals("") || mkXN.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Chung_DoiMatKhau.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn chưa điền đủ thông tin !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else if (mk.equals(StaticConfig.matKhau) == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Chung_DoiMatKhau.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Mật khẩu hiện tại không đúng !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }else if(mkmoi.equals(mkXN)==false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Chung_DoiMatKhau.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Mật khẩu xác nhận không đúng !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }else
                {
                    StaticConfig.mAccount.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            if (snapshot.child("maFB").getValue().toString().equals(StaticConfig.maFB))
                            {
                                Account ac = snapshot.getValue(Account.class);
                                ac.setMatKhau(edMkMoi.getText().toString());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Chung_DoiMatKhau.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Đổi mật khẩu thành công !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private void setControl() {
        tvTaiKhoan = findViewById(R.id.tvTaiKhoan);
        edMkHienTai = findViewById(R.id.edMatKhauHienTai);
        edMkMoi = findViewById(R.id.edMatKhauMoi);
        edMkXacNhan = findViewById(R.id.edXacNhanMatKhauMoi);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
<<<<<<< HEAD
        imTroVe = findViewById(R.id.imTrove);
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
    }
}