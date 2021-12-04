package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class NVK_ThemThuoc extends AppCompatActivity {
    EditText edMaThuoc, edTenThuoc, edGiaBan, edHsd, edThongTinThuoc;
    Spinner spDonViTinh;
    Button btnThemThuoc;
    ImageButton imTrove;
    TextView tvThongBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_them_thuoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //quay lại màn hình trước
        imTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //kiểm tra mã thuốc đã tồn tại hay chưa
        edMaThuoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StaticConfig.mThuoc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Thuoc t = ds.getValue(Thuoc.class);
                            if (edMaThuoc.getText().toString().equals(t.getMaThuoc())) {
                                tvThongBao.setText("Mã thuốc đã tồn tại trong hệ thống");
                                btnThemThuoc.setEnabled(false);
                                return;
                            } else {
                                tvThongBao.setText("");
                                btnThemThuoc.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //nút thêm thuốc
        btnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maThuoc = edMaThuoc.getText().toString();
                String tenThuoc = edTenThuoc.getText().toString();
                String dvt = spDonViTinh.getSelectedItem().toString();
                String thongTin = edThongTinThuoc.getText().toString();
                int giaBan;
                int hsd;
                if (maThuoc.equals("") || tenThuoc.equals("") || edGiaBan.getText().toString().equals("") ||
                        edHsd.getText().toString().equals("") || thongTin.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Thông tin không được bỏ trống !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    giaBan = Integer.parseInt(edGiaBan.getText().toString());
                    hsd = Integer.parseInt(edHsd.getText().toString());
                    String key = StaticConfig.mThuoc.push().getKey();
                    Thuoc t = new Thuoc(giaBan, hsd, key, maThuoc, tenThuoc, dvt, thongTin);
                    StaticConfig.mThuoc.child(key).setValue(t);

                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Thêm thành công !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                    //set dữ liệu rỗng
                    edMaThuoc.setText("");
                    edTenThuoc.setText("");
                    edGiaBan.setText("");
                    edHsd.setText("");
                    edThongTinThuoc.setText("");
                    spDonViTinh.setSelection(0);
                }
            }
        });
    }

    private void setControl() {
        tvThongBao = findViewById(R.id.tvThongBao);
        edMaThuoc = findViewById(R.id.edThemMaThuoc);
        edTenThuoc = findViewById(R.id.edThemTenThuoc);
        edGiaBan = findViewById(R.id.edThemGiaBanThuoc);
        edHsd = findViewById(R.id.edThemHSDTHuoc);
        edThongTinThuoc = findViewById(R.id.edThemThongTinThuoc);
        spDonViTinh = findViewById(R.id.spThemDonViTinh);
        btnThemThuoc = findViewById(R.id.btnThemThuoc);
        imTrove = findViewById(R.id.imTrove);
    }
}