package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NVK_ThemPhieuMuaThuoc extends AppCompatActivity {
    TextView tvMaPhieu, tvNgayLapPhieu, tvMaThuoc, tvDonViTinh, tvThanhTien;
    ImageView imTroVe, imLich, imThem1, imTru1;
    Spinner spDanhSachThuoc;
    EditText edTimTenThuoc, edSoLuong;
    Button btnThemThuoc, btnThemPhieuMua;
    ListView lvDanhSachThuoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_them_phieu_mua_thuoc);
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
        //lấy ra mã phiếu mua, ngày lập tự động.
        layMaPhieuMua();
        //nút lịch
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imNgayLapPhieu();
            }
        });
        //xử lý nút thêm 1
        imThem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them1();
            }
        });
        //xử lý nút giảm 1
        imTru1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tru1();
            }
        });
        //lấy danh sách thuốc đang có từ firebase
        layDanhSachThuocTruyenVaoSP();
        //nv tiếp
        //tiêu đề tổng cho listview
        //xử lý nút thêm vào listview
        //tính tổng tiền
        //thêm thuốc vào kho


    }

    private void layDanhSachThuocTruyenVaoSP() {
        ArrayList<Thuoc> arrThuoc = new ArrayList<>();
        ArrayList<String> arrTenThuoc = new ArrayList<>();
        StaticConfig.mThuoc.addValueEventListener(new ValueEventListener() {
            ArrayAdapter<String> adapter;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Thuoc t = ds.getValue(Thuoc.class);
                    arrThuoc.add(t);
                }
                for (int i = 0; i < arrThuoc.size(); i++) {
                    arrTenThuoc.add(arrThuoc.get(i).getTenThuoc());
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrTenThuoc);
                spDanhSachThuoc.setAdapter(adapter);
                //set mã, đơn vị tính,thành tiền
                spDanhSachThuoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        edSoLuong.setText("1");
                        tvMaThuoc.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getMaThuoc() + "");
                        tvDonViTinh.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getDonViTinh() + "");
                        //tính tiền lần 1, khi số lượng là 1
                        tvThanhTien.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getGiaBan() + "");
                        //tính tiền khi số lượng thay đổi
                        edSoLuong.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                int soLuong = Integer.parseInt(edSoLuong.getText().toString());
                                int giaBan = arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getGiaBan();
                                int thanhTien = soLuong * giaBan;
                                tvThanhTien.setText(thanhTien + "");
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //tìm kiếm tên thuốc
                edTimTenThuoc.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        for (int i = 0; i < spDanhSachThuoc.getCount(); i++) {
                            if (spDanhSachThuoc.getItemAtPosition(i).toString().toLowerCase().contains(edTimTenThuoc.getText().toString())) {
                                spDanhSachThuoc.setSelection(i);
                                return;
                            }
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void tru1() {
        int soLuong = Integer.parseInt(edSoLuong.getText().toString());
        if (soLuong > 1) {
            soLuong--;
            edSoLuong.setText(soLuong + "");
        }

    }

    private void them1() {
        int soLuong = Integer.parseInt(edSoLuong.getText().toString());
        soLuong++;
        edSoLuong.setText(soLuong + "");

    }

    private void imNgayLapPhieu() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tvNgayLapPhieu.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void layMaPhieuMua() {
        String maPhieuMua = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
        String ngayLap = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tvMaPhieu.setText("MT" + maPhieuMua);
        tvNgayLapPhieu.setText(ngayLap);
    }

    private void setControl() {
        tvMaPhieu = findViewById(R.id.tvMaPhieuMuaThuoc);
        tvNgayLapPhieu = findViewById(R.id.tvNgayPhieuMuaThuoc);
        imLich = findViewById(R.id.imLich);
        imTroVe = findViewById(R.id.imTrove);
        edTimTenThuoc = findViewById(R.id.edTimTenThuoc);
        spDanhSachThuoc = findViewById(R.id.spDanhSachThuoc);
        tvMaThuoc = findViewById(R.id.tvMaThuoc);
        tvDonViTinh = findViewById(R.id.tvDonViTinh);
        edSoLuong = findViewById(R.id.edSoluongThuoc);
        imThem1 = findViewById(R.id.imThem1);
        imTru1 = findViewById(R.id.imTru1);
        tvThanhTien = findViewById(R.id.tvThanhTien);
        btnThemThuoc = findViewById(R.id.btnThem);
        lvDanhSachThuoc = findViewById(R.id.dsThuocDaMua);
        btnThemPhieuMua = findViewById(R.id.btnThemPhieuMua);
    }
}