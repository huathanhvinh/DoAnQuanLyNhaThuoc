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
<<<<<<< HEAD
import android.widget.Toast;
=======
>>>>>>> a3e79577e4e93d0867f3ba91e3889fa447058bd4

import com.example.doanquanlynhathuoc.Adapter.Adapter_KhachHang;
import com.example.doanquanlynhathuoc.Adapter.Adapter_Thuoc;
import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class NVBH_DanhSachKhachHang extends AppCompatActivity {
    ImageButton imTrove, imRefesh;
    EditText edTimkiem;
    ListView lvDanhSachKhachHang;
    TextView tvThemKH;

    Adapter_KhachHang adapter_khachHang;
    ArrayList<KhachHang> arrKH = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvbh_danh_sach_khach_hang);
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
        //nút tìm refresh dữ liệu
        imRefesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrKH.clear();
                layDuLieuKhachHang();
            }
        });
        //tìm kiếm theo tên hoặc sdt khách hàng
        edTimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<KhachHang> kq = new ArrayList<>();
                String key = edTimkiem.getText().toString().toLowerCase();
                for (int i = 0; i < arrKH.size(); i++) {
                    if (arrKH.get(i).getSdt().toLowerCase().contains(key)
                            || arrKH.get(i).getTenKH().toLowerCase().contains(key)) {
                        kq.add(arrKH.get(i));
                    }
                }
                Adapter_KhachHang adapter_khachHang = new Adapter_KhachHang(getApplicationContext(), R.layout.custom_khachhang, kq);
                lvDanhSachKhachHang.setAdapter(adapter_khachHang);
                adapter_khachHang.notifyDataSetChanged();
            }
        });
        //lấy dữ liệu khách hàng
        layDuLieuKhachHang();
        //Thêm khách hàng
        tvThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NVBH_ThemKhachHang.class));
            }
        });
    }

    private void layDuLieuKhachHang() {
        StaticConfig.mKhachHang.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                KhachHang kh = snapshot.getValue(KhachHang.class);
                arrKH.add(kh);
                adapter_khachHang.notifyDataSetChanged();
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
        imRefesh = findViewById(R.id.imRefreshKhachHang);
        edTimkiem = findViewById(R.id.edTimKiemKhachHang);
        lvDanhSachKhachHang = findViewById(R.id.lvDanhSachKhachHang);
        tvThemKH = findViewById(R.id.tvThemKhachHang);

        adapter_khachHang = new Adapter_KhachHang(getApplicationContext(), R.layout.custom_khachhang, arrKH);
        lvDanhSachKhachHang.setAdapter(adapter_khachHang);


    }
}