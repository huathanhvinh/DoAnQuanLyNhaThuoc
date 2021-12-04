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
import java.util.ArrayList;
import java.util.Calendar;

public class AD_ThemNhanVien extends AppCompatActivity {
    ImageView imTrove, imLich;
    EditText edTenNV, edSdtNV, edDiaChi;
    TextView tvNamSinh,tvThongBao;
    Spinner spChucVu;
    Button btnThemNV;
    ArrayList<NhanVien> arrNhanVien = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_them_nhan_vien);
        SetControl();
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
        //kiểm tra sdt đã tồn tại hay chưa
        edSdtNV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StaticConfig.mNhanVien.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds:snapshot.getChildren()) {
                            NhanVien nv = ds.getValue(NhanVien.class);
                            if(edSdtNV.getText().toString().equals(nv.getSdt()))
                            {
                                tvThongBao.setText("Số điện thoại đã tồn tại trong hệ thống");
                                btnThemNV.setEnabled(false);
                                return;
                            }else
                            {
                                tvThongBao.setText("");
                                btnThemNV.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //nút lịch & thêm năm sinh vào textview
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imButtonNgaySinh();
            }
        });
        //nút thêm nhân viên
        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = edTenNV.getText().toString();
                String sdt = edSdtNV.getText().toString();
                String namSinh = tvNamSinh.getText().toString();
                String chucVu = spChucVu.getSelectedItem().toString();
                String diaChi = edDiaChi.getText().toString();
                if (hoTen.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_ThemNhanVien.this);
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
                    if (chucVu.equals("NV kho"))
                        role = 2;
                    else if (chucVu.equals("NV bán hàng"))
                        role = 3;
                    else
                        role = 1;
                    int finalRole = role;
                    String maNV = "NV100"+sdt;
                    String taiKhoan = "abc"+sdt;
                    String matKhau = "123456";
                    //Thêm mới nhân viên
                    String key = StaticConfig.mNhanVien.push().getKey();
                    NhanVien ac = new NhanVien(key,finalRole,maNV,hoTen,sdt,diaChi,namSinh);
                    StaticConfig.mNhanVien.child(key).setValue(ac);
                    //Thêm mới Account
                    String key1 = StaticConfig.mAccount.push().getKey();
                    Account ac1 = new Account(key1,taiKhoan,matKhau,sdt,"dangSuDung",finalRole,"");
                    StaticConfig.mAccount.child(key1).setValue(ac1);
                    //Thông báo
                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_ThemNhanVien.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Thêm nhân viên thành công !!!\n" +
                            "Tài khoản: "+taiKhoan+"\n"+
                            "Mật Khẩu: "+matKhau);
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
                tvNamSinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void SetControl() {
        tvThongBao = findViewById(R.id.tvThongBao);
        imTrove = findViewById(R.id.imTrove);
        imLich = findViewById(R.id.imLich);
        edTenNV = findViewById(R.id.edHotenNV);
        edSdtNV = findViewById(R.id.edSdtNV);
        edDiaChi = findViewById(R.id.edDiaChiNV);
        tvNamSinh = findViewById(R.id.tvNamSinhNV);
        spChucVu = findViewById(R.id.spChucVu);
        edDiaChi = findViewById(R.id.edDiaChiNV);
        btnThemNV = findViewById(R.id.btnThemNV);
    }
}