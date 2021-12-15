package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Adapter.Adapter_KhoThuoc;
import com.example.doanquanlynhathuoc.Class.ItemKhoThuoc;
import com.example.doanquanlynhathuoc.Class.PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NVK_TraCuuKhoThuoc extends AppCompatActivity {
    ImageButton imTroVe, imLocTang, imLocGiam, imLocTen;
    ListView lvDanhSachThuoc;
    TextView tvTongThuoc;

    Adapter_KhoThuoc adapter_khoThuoc;
    ArrayList<ItemKhoThuoc> arrThuoc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_tra_cuu_kho_thuoc);
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
        //lấy danh sách kho thuốc
        layDanhSachThuoc();
        //lọc theo số lượng tăng
        imLocTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(arrThuoc, new Comparator<ItemKhoThuoc>() {
                    @Override
                    public int compare(ItemKhoThuoc o1, ItemKhoThuoc o2) {
                        if(o1.getSoLuong() < o2.getSoLuong())
                            return -1;
                        else if (o1.getSoLuong() == o2.getSoLuong())
                            return 0;
                        else
                            return 1;
                    }
                });
                adapter_khoThuoc.notifyDataSetChanged();
            }
        });
        //lọc theo số lượng giảm
        imLocGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(arrThuoc, new Comparator<ItemKhoThuoc>() {
                    @Override
                    public int compare(ItemKhoThuoc o1, ItemKhoThuoc o2) {
                        if(o1.getSoLuong() < o2.getSoLuong())
                            return 1;
                        else if (o1.getSoLuong() == o2.getSoLuong())
                            return 0;
                        else
                            return -1;
                    }
                });
                adapter_khoThuoc.notifyDataSetChanged();
            }
        });
        //lọc theo tên
        imLocTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(arrThuoc, new Comparator<ItemKhoThuoc>() {
                    @Override
                    public int compare(ItemKhoThuoc o1, ItemKhoThuoc o2) {
                        if(o1.getTenThuoc().compareTo(o2.getTenThuoc())<0)
                            return -1;
                        else if (o1.getSoLuong() == o2.getSoLuong())
                            return 0;
                        else
                            return 1;
                    }
                });
                adapter_khoThuoc.notifyDataSetChanged();
            }
        });
    }

    private void layDanhSachThuoc() {
        StaticConfig.mKhoThuoc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ItemKhoThuoc item = snapshot.getValue(ItemKhoThuoc.class);
                arrThuoc.add(item);
                tvTongThuoc.setText(arrThuoc.size()+"");
                adapter_khoThuoc.notifyDataSetChanged();
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
        imLocTang = findViewById(R.id.imLocTheoSoLuongTang);
        imLocGiam = findViewById(R.id.imLocTheoSoLuongGiam);
        imLocTen = findViewById(R.id.imLocTheoTenThuoc);
        lvDanhSachThuoc = findViewById(R.id.lvDanhSachKhoThuoc);
        tvTongThuoc = findViewById(R.id.tvTongSoThuoc);

        adapter_khoThuoc = new Adapter_KhoThuoc(getApplicationContext(),R.layout.custom_kho_thuoc,arrThuoc);
        lvDanhSachThuoc.setAdapter(adapter_khoThuoc);
    }
}