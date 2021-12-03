package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class NVK_ThongTinThuoc extends AppCompatActivity {
    ImageButton imTrove;
    TextView tvMaThuoc;
    EditText edTenThuoc, edGiaBan, edHanDung, edThongTin;
    Spinner spDVT;
    Button btnCapNhat, btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_thong_tin_thuoc);
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
        //Gán thông tin vào các trường
        LayThongTinThuoc();
        //nút cập nhật
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTenThuoc.getText().toString().equals("") || edGiaBan.getText().toString().equals("")||
                edHanDung.getText().toString().equals("")||edThongTin.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Không được để trống thông tin !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }else
                {
                    Thuoc thongTinThuoc = (Thuoc) getIntent().getSerializableExtra("ThongTinThuoc");
                    thongTinThuoc.setTenThuoc(edTenThuoc.getText().toString());
                    thongTinThuoc.setGiaBan(Integer.parseInt(edGiaBan.getText().toString()));
                    thongTinThuoc.setHsd(Integer.parseInt(edHanDung.getText().toString()));
                    thongTinThuoc.setDonViTinh(spDVT.getSelectedItem().toString());
                    thongTinThuoc.setNoiDung(edThongTin.getText().toString());

                    StaticConfig.mThuoc.child(thongTinThuoc.getMaFB()).setValue(thongTinThuoc);

                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Cập nhật thành công !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            }
        });
        //nút xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            Thuoc thongTinThuoc = (Thuoc) getIntent().getSerializableExtra("ThongTinThuoc");
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                builder.setTitle("Xóa Nhân viên");
                builder.setMessage("Bạn có muốn xóa không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StaticConfig.mThuoc.child(thongTinThuoc.getMaFB()).removeValue();
                        AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinThuoc.this);
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Xóa thành công !!!");
                        builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private void LayThongTinThuoc() {
        Thuoc thongTinThuoc = (Thuoc) getIntent().getSerializableExtra("ThongTinThuoc");
        tvMaThuoc.setText(thongTinThuoc.getMaThuoc());
        edTenThuoc.setText(thongTinThuoc.getTenThuoc());
        edGiaBan.setText(thongTinThuoc.getGiaBan() + "");
        edHanDung.setText(thongTinThuoc.getHsd() + "");
        String dvt = thongTinThuoc.getDonViTinh();
        if (dvt.equals("Chai"))
            spDVT.setSelection(0);
        else if (dvt.equals("Gói"))
            spDVT.setSelection(1);
        else if (dvt.equals("Hộp"))
            spDVT.setSelection(2);
        else if (dvt.equals("Lọ"))
            spDVT.setSelection(3);
        else if (dvt.equals("Vỉ"))
            spDVT.setSelection(4);
        else
            spDVT.setSelection(5);
        edThongTin.setText(thongTinThuoc.getNoiDung());


    }

    private void setControl() {
        imTrove = findViewById(R.id.imTrove);
        tvMaThuoc = findViewById(R.id.tvMaThuocTT);
        edTenThuoc = findViewById(R.id.edTenThuocTT);
        edGiaBan = findViewById(R.id.edGiaThuocTT);
        edHanDung = findViewById(R.id.edHanSuDungTT);
        edThongTin = findViewById(R.id.edThongTinThuocTT);
        spDVT = findViewById(R.id.spDonViTinhTT);
        btnCapNhat = findViewById(R.id.btnCapNhatTT);
        btnXoa = findViewById(R.id.btnXoaThuocTT);
    }
}