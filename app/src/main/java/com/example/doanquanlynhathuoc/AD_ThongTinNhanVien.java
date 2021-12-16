package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.Account;
import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.NhanVien;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AD_ThongTinNhanVien extends AppCompatActivity {
    ImageView imTrove, imLich;
    TextView tvMaNv, tvThongBao;
    EditText edTenNV, edSdtNV, edNamSinh, edDiaChi;
    Spinner spLoaiNv;
    Button btnLuu, btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_thong_tin_nhan_vien);
        setControl();
        SetEvent();
    }

    private void SetEvent() {
        //nút trở về
        imTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //nút lịch
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imButtonNgaySinh();
            }
        });
        //kiểm tra sdt trong danh sách nhân viên
        edSdtNV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                NhanVien thongTinNV = (NhanVien) getIntent().getSerializableExtra("ThongTinNhanVien");
                StaticConfig.mNhanVien.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            NhanVien nv = ds.getValue(NhanVien.class);
                            if (edSdtNV.getText().toString().equals(nv.getSdt()) && nv.getMaFb().equals(thongTinNV.getMaFb()) == false) {
                                tvThongBao.setText("Số điện thoại đã tồn tại trong hệ thống");
                                btnLuu.setEnabled(false);
                                return;
                            } else {
                                tvThongBao.setText("");
                                btnLuu.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //lấy thông tin nhân viên
        layThongTinNhanVien();
        //nút lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTenNV.getText().toString().isEmpty() || edSdtNV.getText().toString().isEmpty() || edNamSinh.getText().toString().isEmpty() || edDiaChi.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_ThongTinNhanVien.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Không được để trống thông tin !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    int role = 0;
                    if (spLoaiNv.getSelectedItem().toString().equals("Quản lý"))
                        role = 1;
                    else if (spLoaiNv.getSelectedItem().toString().equals("NV kho"))
                        role = 2;
                    else
                        role = 3;
                    int finalRole = role;
                    String finalSdt = edSdtNV.getText().toString();
                    NhanVien thongTinNV = (NhanVien) getIntent().getSerializableExtra("ThongTinNhanVien");
                    String sdt = thongTinNV.getSdt();
                    //cập nhật role,sdt account
                    StaticConfig.mAccount.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Account ac = snapshot.getValue(Account.class);
                            if (ac.getSdt().equals(sdt)) {
                                ac.setSdt(finalSdt);
                                ac.setRole(finalRole);
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
                    //cập nhật nhân viên
                    thongTinNV.setTenNV(edTenNV.getText().toString());
                    thongTinNV.setSdt(edSdtNV.getText().toString());
                    thongTinNV.setNamSinh(edNamSinh.getText().toString());
                    thongTinNV.setRole(role);
                    thongTinNV.setDiaChi(edDiaChi.getText().toString());

                    StaticConfig.mNhanVien.child(thongTinNV.getMaFb()).setValue(thongTinNV);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(AD_ThongTinNhanVien.this);
                    builder1.setTitle("Thông Báo");
                    builder1.setMessage("Cập nhật thành công, \n" +
<<<<<<< HEAD
                            "Vui lòng cập nhật lại thông tin tại màn hình danh sách nhân viên !!!");
=======
                            "Vui lòng cập nhật lại thông tin tại màn hình danh sách khách hàng !!!");
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
                    builder1.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AD_ThongTinNhanVien.this);
                            builder.setTitle("Thông Báo");
<<<<<<< HEAD
                            builder.setMessage("Mời bạn quay lại màn hình danh sách nhân viên !!!");
=======
                            builder.setMessage("Mời bạn quay lại màn hình danh sách !!!");
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4
                            builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            builder.show();
                        }
                    });
                    builder1.show();
                }
            }
        });
        //nút xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AD_ThongTinNhanVien.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn xóa khách hàng không ?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //xóa account
                        NhanVien thongTinNV = (NhanVien) getIntent().getSerializableExtra("ThongTinNhanVien");
                        String sdt = thongTinNV.getSdt();
                        StaticConfig.mAccount.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                Account ac = snapshot.getValue(Account.class);
                                if (ac.getSdt().equals(sdt)) {
                                    StaticConfig.mAccount.child(ac.getMaFB()).removeValue();
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
                        //xóa nhân viên
                        StaticConfig.mNhanVien.child(thongTinNV.getMaFb()).removeValue();
                        //thông báo
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AD_ThongTinNhanVien.this);
                        builder1.setTitle("Thông Báo");
                        builder1.setMessage("Xóa thành công ?");
                        builder1.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder1.show();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private void layThongTinNhanVien() {
        NhanVien thongTinNV = (NhanVien) getIntent().getSerializableExtra("ThongTinNhanVien");
        tvMaNv.setText(thongTinNV.getMaNV());
        edTenNV.setText(thongTinNV.getTenNV());
        edSdtNV.setText(thongTinNV.getSdt());
        edNamSinh.setText(thongTinNV.getNamSinh());
        //loại nv
        if (thongTinNV.getRole() == 1)
            spLoaiNv.setSelection(2);
        else if (thongTinNV.getRole() == 2)
            spLoaiNv.setSelection(0);
        else
            spLoaiNv.setSelection(1);
        //
        edDiaChi.setText(thongTinNV.getDiaChi());
    }

    private void imButtonNgaySinh() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edNamSinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void setControl() {
        imTrove = findViewById(R.id.imTrove);
        imLich = findViewById(R.id.imLich);
        tvMaNv = findViewById(R.id.tvMaNVTT);
        tvThongBao = findViewById(R.id.tvThongBao);
        edTenNV = findViewById(R.id.edTenNVTT);
        edSdtNV = findViewById(R.id.edSDTNVTT);
        edNamSinh = findViewById(R.id.edNamSinhNVTT);
        edDiaChi = findViewById(R.id.edDiaChiNVTT);
        spLoaiNv = findViewById(R.id.spChuVuTT);
        btnLuu = findViewById(R.id.btnLuuNVTT);
        btnXoa = findViewById(R.id.btnXoaNVTT);
    }
}