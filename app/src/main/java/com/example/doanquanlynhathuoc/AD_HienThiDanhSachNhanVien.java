package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Adapter.Adapter_KhachHang;
import com.example.doanquanlynhathuoc.Adapter.Adapter_NhanVien;
import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.NhanVien;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class AD_HienThiDanhSachNhanVien extends AppCompatActivity {
    ImageView imTroVe,imRefresh;
    EditText edTimKiem;
    ListView lvDSNV;
    TextView tvThemNhanVien;
    Adapter_NhanVien adapter_nhanVien;
    ArrayList<NhanVien> arrNhanVien = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_hien_thi_danh_sach_nhan_vien);
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
        //nút Refresh
        imRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrNhanVien.clear();
                layDanhSachNhanVien();
            }
        });
        //tìm kiếm nhân viên
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<NhanVien> kq = new ArrayList<>();
                String key = edTimKiem.getText().toString().toLowerCase();
                for (int i = 0; i < arrNhanVien.size(); i++) {
                    if (arrNhanVien.get(i).getSdt().toLowerCase().contains(key)
                            || arrNhanVien.get(i).getTenNV().toLowerCase().contains(key)) {
                        kq.add(arrNhanVien.get(i));
                    }
                }
                Adapter_NhanVien adapter_nhanVien = new Adapter_NhanVien(getApplicationContext(), R.layout.custom_nhanvien, kq);
                lvDSNV.setAdapter(adapter_nhanVien);
                adapter_nhanVien.notifyDataSetChanged();
            }
        });
        //hiển thị danh sách nhân viên
        layDanhSachNhanVien();
        //thêm nhân viên
        tvThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AD_ThemNhanVien.class));
            }
        });

    }

    private void layDanhSachNhanVien() {
        StaticConfig.mNhanVien.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                NhanVien nv = snapshot.getValue(NhanVien.class);
                arrNhanVien.add(nv);
                adapter_nhanVien.notifyDataSetChanged();
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
        imRefresh = findViewById(R.id.imRefreshNV);
        edTimKiem = findViewById(R.id.edTimKiemNV);
        lvDSNV = findViewById(R.id.lvDanhSachNhanVien);
        tvThemNhanVien = findViewById(R.id.tvThemNhanVien);

        adapter_nhanVien = new Adapter_NhanVien(getApplicationContext(), R.layout.custom_nhanvien, arrNhanVien);
        lvDSNV.setAdapter(adapter_nhanVien);

    }
}