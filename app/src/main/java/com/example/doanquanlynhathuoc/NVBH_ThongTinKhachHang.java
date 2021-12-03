package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NVBH_ThongTinKhachHang extends AppCompatActivity {
    ImageButton imTrove, imLich;
    TextView tvMaKH, tvNgaySinh;
    EditText edTenKH, edSDT, edDiaChi;
    Button btnLuuKH, btnXoaKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvbh_thong_tin_khach_hang);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //lấy thông tin khách hàng
        layThongTinKhachHang();
        //nút trở về
        imTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //nút lịch
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imButtonNgaySinh();
            }
        });
        //nút lưu khách hàng
        btnLuuKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTenKH.getText().toString().equals("") || edSDT.getText().toString().equals("")
                        || edDiaChi.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThongTinKhachHang.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Không được để trống thông tin !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
                else {
                    KhachHang thongTinKH = (KhachHang) getIntent().getSerializableExtra("ThongTinKhachHang");
                    String tenKH = edTenKH.getText().toString();
                    final String sdt = edSDT.getText().toString();
                    String ngaySinh = tvNgaySinh.getText().toString();
                    String diaChi = edDiaChi.getText().toString();

                    thongTinKH.setTenKH(tenKH);
                    thongTinKH.setSdt(sdt);
                    thongTinKH.setNamSinh(ngaySinh);
                    thongTinKH.setDiaChi(diaChi);

                    StaticConfig.mKhachHang.child(thongTinKH.getMaFb()).setValue(thongTinKH);

                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThongTinKhachHang.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Cập nhật thành công, \n" +
                            "Vui lòng cập nhật lại thông tin tại màn hình danh sách khách hàng !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();

                        }
                    });
                    builder.show();
                }
            }
        });
        //nút xóa khách hàng
        btnXoaKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThongTinKhachHang.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn xóa khách hàng không ?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        KhachHang thongTinKH = (KhachHang) getIntent().getSerializableExtra("ThongTinKhachHang");
                        StaticConfig.mKhachHang.child(thongTinKH.getMaFb()).removeValue();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(NVBH_ThongTinKhachHang.this);
                        builder1.setTitle("Thông Báo");
                        builder1.setMessage("Xóa thành công ?");
                        builder1.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder1.show();
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

    private void layThongTinKhachHang() {
        KhachHang thongTinKH = (KhachHang) getIntent().getSerializableExtra("ThongTinKhachHang");
        tvMaKH.setText(thongTinKH.getMaKH());
        edTenKH.setText(thongTinKH.getTenKH());
        edSDT.setText(thongTinKH.getSdt());
        tvNgaySinh.setText(thongTinKH.getNamSinh());
        edDiaChi.setText(thongTinKH.getDiaChi());

    }

    private void imButtonNgaySinh() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tvNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    Boolean checkSDT(String maFB) {
        return false;
    }

    private void setControl() {
        imTrove = findViewById(R.id.imTrove);
        imLich = findViewById(R.id.imLich);
        tvMaKH = findViewById(R.id.tvMaKHTT);
        tvNgaySinh = findViewById(R.id.tvNamSinhKHTT);
        edTenKH = findViewById(R.id.edTenKHTT);
        edSDT = findViewById(R.id.edSDTKHTT);
        edDiaChi = findViewById(R.id.edDiaChiKHTT);
        btnLuuKH = findViewById(R.id.btnLuuKHTT);
        btnXoaKH = findViewById(R.id.btnXoaKHTT);
    }
}