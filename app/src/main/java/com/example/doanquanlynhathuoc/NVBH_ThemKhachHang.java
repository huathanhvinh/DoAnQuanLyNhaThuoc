package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NVBH_ThemKhachHang extends AppCompatActivity {
    ImageButton imTrove, imLich;
    TextView tvMaKH, tvNgaySinh,tvThongBao;
    EditText edTenKH, edSDT, edDiaChi;
    Button btnThemKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvbh_them_khach_hang);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //nút trở về
        imTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Mã KH tự động phát sinh
        //Kiểm tra SDT khách hàng
        edSDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StaticConfig.mKhachHang.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds:snapshot.getChildren()) {
                            KhachHang kh = ds.getValue(KhachHang.class);
                            if(edSDT.getText().toString().equals(kh.getSdt()))
                            {
                                tvThongBao.setText("Số điện thoại đã tồn tại trong hệ thống");
                                btnThemKH.setEnabled(false);
                                tvMaKH.setText("KH100"+edSDT.getText().toString());
                                return;
                            }else
                            {
                                tvMaKH.setText("KH100"+edSDT.getText().toString());
                                tvThongBao.setText("");
                                btnThemKH.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //sự kiện chọn lịch và thêm ngày sinh vào textView lịch
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imButtonNgaySinh();
            }
        });
        //Sự kiện thêm khách hàng mới
        btnThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maKH = tvMaKH.getText().toString();
                String tenKH = edTenKH.getText().toString();
                String sdt = edSDT.getText().toString();
                String ngaySinh = tvNgaySinh.getText().toString();
                String diaChi = edDiaChi.getText().toString();

                if (tenKH.equals("") || sdt.equals("") || diaChi.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemKhachHang.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Thông tin không được bỏ trống !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    String key = StaticConfig.mKhachHang.push().getKey();
                    KhachHang kh = new KhachHang(key,0,0,maKH,tenKH,sdt,diaChi,ngaySinh);
                    StaticConfig.mKhachHang.child(key).setValue(kh);

                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemKhachHang.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Thêm thành công !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                    edTenKH.setText("");
                    edSDT.setText("");
                    tvNgaySinh.setText("01/01/2021");
                    edDiaChi.setText("");
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
                tvNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void setControl() {
        tvThongBao = findViewById(R.id.tvThongBao);
        imTrove = findViewById(R.id.imTrove);
        imLich = findViewById(R.id.imLich);
        tvMaKH = findViewById(R.id.tvMaKH);
        tvNgaySinh = findViewById(R.id.tvNamSinhKH);
        edTenKH = findViewById(R.id.edTenKH);
        edSDT = findViewById(R.id.edSDTKH);
        edDiaChi = findViewById(R.id.edDiaChiKH);
        btnThemKH = findViewById(R.id.btnThemKH);
    }
}