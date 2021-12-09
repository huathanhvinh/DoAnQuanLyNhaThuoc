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
import com.example.doanquanlynhathuoc.Adapter.Adapter_PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class NVK_DanhSachPhieuMuaThuoc extends AppCompatActivity {
    ImageView imTrove,imRefersh;
    EditText edTimKiem;
    ListView lvDanhSachPhieuMua;
    TextView tvThemPMT;

    Adapter_PhieuMuaThuoc adapter_phieuMuaThuoc;
    ArrayList<PhieuMuaThuoc> arrPhieuMuaThuoc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_danh_sach_phieu_mua_thuoc);
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
        //nút refresh
        imRefersh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrPhieuMuaThuoc.clear();
                adapter_phieuMuaThuoc.clear();
                layDanhSachPhieuMuaThuoc();
            }
        });
        //tìm kiếm theo mã phiếu hoặc ngày lập
        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<PhieuMuaThuoc> kq = new ArrayList<>();
                String key = edTimKiem.getText().toString().toLowerCase();
                for (int i = 0; i < arrPhieuMuaThuoc.size(); i++) {
                    if (arrPhieuMuaThuoc.get(i).getMaPhieu().toLowerCase().contains(key)) {
                        kq.add(arrPhieuMuaThuoc.get(i));
                    }
                }
                Adapter_PhieuMuaThuoc adapter_khachHang = new Adapter_PhieuMuaThuoc(getApplicationContext(), R.layout.custom_phieu_mua_thuoc, kq);
                lvDanhSachPhieuMua.setAdapter(adapter_khachHang);
                adapter_phieuMuaThuoc.notifyDataSetChanged();
            }
        });
        //lấy danh sách phiếu mua thuốc
        layDanhSachPhieuMuaThuoc();
        //nút thêm phiếu mua thuốc mới
        tvThemPMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NVK_ThemPhieuMuaThuoc.class));
            }
        });
    }

    private void layDanhSachPhieuMuaThuoc() {
        StaticConfig.mPhieuMuaThuoc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PhieuMuaThuoc phieuMua = snapshot.getValue(PhieuMuaThuoc.class);
                arrPhieuMuaThuoc.add(phieuMua);
                adapter_phieuMuaThuoc.notifyDataSetChanged();
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
        imTrove = findViewById(R.id.imTrove);
        imRefersh = findViewById(R.id.imRefreshPMT);
        edTimKiem = findViewById(R.id.edTimKiemPMT);
        lvDanhSachPhieuMua = findViewById(R.id.lvDanhSachPMT);
        tvThemPMT = findViewById(R.id.tvThemPhieuMuaThuoc);

        adapter_phieuMuaThuoc = new Adapter_PhieuMuaThuoc(getApplicationContext(),R.layout.custom_phieu_mua_thuoc,arrPhieuMuaThuoc);
        lvDanhSachPhieuMua.setAdapter(adapter_phieuMuaThuoc);
    }
}