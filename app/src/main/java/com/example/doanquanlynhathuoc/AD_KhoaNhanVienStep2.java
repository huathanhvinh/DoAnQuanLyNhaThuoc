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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.Account;
import com.example.doanquanlynhathuoc.Class.NhanVien;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class AD_KhoaNhanVienStep2 extends AppCompatActivity {
    ImageButton imTroVe;
    TextView tvTaiKhoan, tvSdt;
    RadioButton rdLyDo1, rdLyDo2, rdLyDo3, rdLyDo4;
    EditText edLydoKhac;
    Button btnKhoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_khoa_nhan_vien_step2);
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
        //nút khóa
        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanVien thongTinNV = (NhanVien) getIntent().getSerializableExtra("ThongTinNhanVien");
                if (btnKhoa.getText().toString().equals("Mở khóa")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_KhoaNhanVienStep2.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Mở khóa nhân viên ?");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StaticConfig.mAccount.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    Account ac = snapshot.getValue(Account.class);
                                    if (ac.getSdt().equals(thongTinNV.getSdt())) {
                                        ac.setTrangThai("dangSuDung");
                                        ac.setGhiChu("");
                                        StaticConfig.mAccount.child(ac.getMaFB()).setValue(ac);
                                        btnKhoa.setText("Khóa");
                                        edLydoKhac.setText("");
                                        rdLyDo1.setChecked(false);
                                        rdLyDo2.setChecked(false);
                                        rdLyDo3.setChecked(false);
                                        rdLyDo4.setChecked(false);
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
                    });
                    builder.show();
                } else if (rdLyDo1.isChecked() == false && rdLyDo2.isChecked() == false && rdLyDo3.isChecked() == false
                        && rdLyDo4.isChecked() == false && edLydoKhac.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_KhoaNhanVienStep2.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Vui lòng điền lý do !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    String lyDo = "";
                    if (rdLyDo1.isChecked())
                        lyDo = rdLyDo1.getText().toString();
                    if (rdLyDo2.isChecked())
                        lyDo = rdLyDo2.getText().toString();
                    if (rdLyDo3.isChecked())
                        lyDo = rdLyDo3.getText().toString();
                    if (rdLyDo4.isChecked())
                        lyDo = rdLyDo4.getText().toString();
                    if (lyDo.equals("") && edLydoKhac.getText().toString().equals("") == false)
                        lyDo = edLydoKhac.getText().toString();
                    else if (lyDo.equals("") == false && edLydoKhac.getText().toString().equals("")==false)
                        lyDo+=", "+edLydoKhac.getText().toString();
                        //cập nhật account
                        String finalLyDo = lyDo;
                    StaticConfig.mAccount.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Account ac = snapshot.getValue(Account.class);
                            if (ac.getSdt().equals(thongTinNV.getSdt())) {
                                ac.setTrangThai("khoa");
                                ac.setGhiChu(finalLyDo);
                                StaticConfig.mAccount.child(ac.getMaFB()).setValue(ac);
                                btnKhoa.setText("Mở khóa");
                                edLydoKhac.setText(ac.getGhiChu());
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_KhoaNhanVienStep2.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Khóa Thành Công !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            }
        });
        //lấy thông tin
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
                    if (ac.getTrangThai().equals("khoa")) {
                        edLydoKhac.setText(ac.getGhiChu());
                        btnKhoa.setText("Mở khóa");
                    }
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
        tvTaiKhoan = findViewById(R.id.tvTaiKhoan);
        tvSdt = findViewById(R.id.sdtNv);
        rdLyDo1 = findViewById(R.id.rdLyDo1);
        rdLyDo2 = findViewById(R.id.rdLyDo2);
        rdLyDo3 = findViewById(R.id.rdLyDo3);
        rdLyDo4 = findViewById(R.id.rdLyDo4);
        edLydoKhac = findViewById(R.id.edLydoKhac);
        btnKhoa = findViewById(R.id.btnKhoaNV);
    }
}