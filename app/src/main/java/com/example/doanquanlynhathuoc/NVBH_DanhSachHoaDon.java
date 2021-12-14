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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Adapter.Adapter_PhieuHoaDon;
import com.example.doanquanlynhathuoc.Adapter.Adapter_PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.Class.PhieuHoaDon;
import com.example.doanquanlynhathuoc.Class.PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class NVBH_DanhSachHoaDon extends AppCompatActivity {
    ImageButton imTroVe,imRefresh;
    EditText edTimKiem;
    ListView lvDanhSachHoaDon;
    TextView tvThemHoaDon;

    Adapter_PhieuHoaDon adapter_phieuHoaDon;
    ArrayList<PhieuHoaDon> arrPhieuHoaDon = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvbh_danh_sach_hoa_don);
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
        //nút refresh
        imRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrPhieuHoaDon.clear();
                adapter_phieuHoaDon.clear();
                LayDanhSachHoaDon();
            }
        });
        //tìm kiếm hóa đơn
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ArrayList<PhieuHoaDon> kq = new ArrayList<>();
                String key = edTimKiem.getText().toString().toLowerCase();
                for (int i = 0; i < arrPhieuHoaDon.size(); i++) {
                    if (arrPhieuHoaDon.get(i).getMaPhieu().toLowerCase().contains(key)) {
                        kq.add(arrPhieuHoaDon.get(i));
                    }
                }
                Adapter_PhieuHoaDon adapter_khachHang = new Adapter_PhieuHoaDon(getApplicationContext(), R.layout.custom_phieu_hoa_don, kq);
                lvDanhSachHoaDon.setAdapter(adapter_khachHang);
                adapter_khachHang.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //lấy danh sách hóa đơn
        LayDanhSachHoaDon();
        //thêm hóa đơn
        tvThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVBH_ThemHoaDon.class));
            }
        });
    }

    private void LayDanhSachHoaDon() {
        StaticConfig.mPhieuHoaDon.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PhieuHoaDon phieuHoaDon = snapshot.getValue(PhieuHoaDon.class);
                arrPhieuHoaDon.add(phieuHoaDon);
                adapter_phieuHoaDon.notifyDataSetChanged();
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
        imRefresh = findViewById(R.id.imRefresh);
        imTroVe = findViewById(R.id.imTrove);
        edTimKiem = findViewById(R.id.edTimHoaDon);
        lvDanhSachHoaDon = findViewById(R.id.lvDanhSachHoaDon);
        tvThemHoaDon = findViewById(R.id.tvThemHoaDon);

        adapter_phieuHoaDon = new Adapter_PhieuHoaDon(getApplicationContext(),R.layout.custom_phieu_hoa_don,arrPhieuHoaDon);
        lvDanhSachHoaDon.setAdapter(adapter_phieuHoaDon);
    }
}