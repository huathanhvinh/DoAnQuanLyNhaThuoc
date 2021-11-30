package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Chung_QuyDinhNhaThuoc extends AppCompatActivity {
Button btnTrove;
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
    }
    private void setControl()
    {
        btnTrove = findViewById(R.id.btnTrove);
    }
}