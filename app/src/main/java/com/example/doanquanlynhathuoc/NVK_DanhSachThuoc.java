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

import com.example.doanquanlynhathuoc.Adapter.Adapter_Thuoc;
import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class NVK_DanhSachThuoc extends AppCompatActivity {
    ImageButton imTrove, imRefesh;
    EditText edTimkiem;
    ListView lvDanhSachThuoc;
    TextView tvThemThuoc;

    Adapter_Thuoc adapter_thuoc;
    ArrayList<Thuoc> arrThuoc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_danh_sach_thuoc);
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
        imRefesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrThuoc.clear();
                layThongTinThuoc();
            }
        });
        //tìm kiếm thuốc theo tên
        edTimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Thuoc> kq = new ArrayList<>();
                String key = edTimkiem.getText().toString().toLowerCase();
                for (int i = 0;i<arrThuoc.size();i++)
                {
                    if(arrThuoc.get(i).getTenThuoc().toLowerCase().contains(key)){
                        kq.add(arrThuoc.get(i));
                    }
                }
                Adapter_Thuoc adapter_thuoc = new Adapter_Thuoc(getApplicationContext(),R.layout.custom_thuoc, kq);
                lvDanhSachThuoc.setAdapter(adapter_thuoc);
                adapter_thuoc.notifyDataSetChanged();
            }
        });
        //Lấy dữ liệu thuốc từ FB
        layThongTinThuoc();
        //thêm mới thuốc
        tvThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NVK_ThemThuoc.class));
            }
        });
    }

    private void layThongTinThuoc() {
        StaticConfig.mThuoc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Thuoc t = snapshot.getValue(Thuoc.class);
                arrThuoc.add(t);
                adapter_thuoc.notifyDataSetChanged();
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
        imRefesh = findViewById(R.id.imRefreshThuoc);
        edTimkiem = findViewById(R.id.edTimKiemThuoc);
        lvDanhSachThuoc = findViewById(R.id.lvDanhSachThuoc);
        tvThemThuoc = findViewById(R.id.tvThemThuocMoi);

        adapter_thuoc = new Adapter_Thuoc(getApplicationContext(), R.layout.custom_thuoc, arrThuoc);
        lvDanhSachThuoc.setAdapter(adapter_thuoc);
    }
}