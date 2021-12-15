package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Class.QuyDinhNhaThuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Chung_QuyDinhNhaThuoc extends AppCompatActivity {
Button btnTrove;
TextView tvQD1,tvQD2,tvQD3,tvQD4,tvQD5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chung_quy_dinh_nha_thuoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //nút trở về
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //lấy thông tin quy định
        layThongTinQuyDinh();
    }

    private void layThongTinQuyDinh() {
        StaticConfig.mQuyDinh.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    QuyDinhNhaThuoc qd = ds.getValue(QuyDinhNhaThuoc.class);
                    if(qd.getStt()==1)
                    {
                        tvQD1.setText("1. "+qd.getNoidung());
                    }
                    if(qd.getStt()==2)
                    {
                        tvQD2.setText("2. "+qd.getNoidung());
                    }
                    if(qd.getStt()==3)
                    {
                        tvQD3.setText("3. "+qd.getNoidung());
                    }
                    if(qd.getStt()==4)
                    {
                        tvQD4.setText("4. "+qd.getNoidung());
                    }
                    if(qd.getStt()==5)
                    {
                        tvQD5.setText("5. "+qd.getNoidung());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl()
    {
        btnTrove = findViewById(R.id.btnTrove);
        tvQD1 = findViewById(R.id.tvQuyDinh1);
        tvQD2 = findViewById(R.id.tvQuyDinh2);
        tvQD3 = findViewById(R.id.tvQuyDinh3);
        tvQD4 = findViewById(R.id.tvQuyDinh4);
        tvQD5 = findViewById(R.id.tvQuyDinh5);
    }
}