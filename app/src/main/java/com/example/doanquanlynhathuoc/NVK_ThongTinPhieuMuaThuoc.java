package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Adapter.Adapter_ItemMuaBanThuoc;
import com.example.doanquanlynhathuoc.Adapter.Adapter_PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.Class.ItemMuaBanThuoc;
import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.KhoThuoc;
import com.example.doanquanlynhathuoc.Class.PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

public class NVK_ThongTinPhieuMuaThuoc extends AppCompatActivity {
    ImageButton imTroVe, imLich, imTang1, imGiam1;
    TextView tvMaPhieu, tvNgayLap, tvNguoiLap, tvTenThuoc, tvMaThuoc;
    TextView tvTongTien1, tvTongTien2, tvDonViTinh, tvThanhTien;
    ListView lvDanhSachThuoc;
    EditText edSoLuong;
    Button btnSua, btnLuu, btnXoa;
    Adapter_ItemMuaBanThuoc adapter_itemMuaBanThuoc;
    ArrayList<ItemMuaBanThuoc> arrThuoc = new ArrayList();
    int giaBan, viTri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_thong_tin_phieu_mua_thuoc);
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
        //nút chọn lịch
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imNgayLapPhieu();
            }
        });
        //nút tăng 1
        imTang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them1();
            }
        });
        //nút giảm 1
        imGiam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tru1();
            }
        });
        //lấy thông tin phiếu mua thuốc
        layThongTinPhieuMuaThuoc();
        //kiểm tra đã chọn thuốc hay chưa
        if (tvTenThuoc.getText().toString().isEmpty()) {
            btnSua.setEnabled(false);
        }
        //sự kiện click chuột vào LV
        lvDanhSachThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnSua.setEnabled(true);
                tvMaThuoc.setText(arrThuoc.get(position).getMaThuoc());
                tvTenThuoc.setText(arrThuoc.get(position).getTenThuoc());
                tvDonViTinh.setText(arrThuoc.get(position).getDonViTinh());
                edSoLuong.setText(arrThuoc.get(position).getSoLuong() + "");
                tvThanhTien.setText(arrThuoc.get(position).getThanhTien() + "");
                viTri = position;
                giaBan = arrThuoc.get(position).getThanhTien() / arrThuoc.get(position).getSoLuong();
            }
        });
        //sự kiện thay đổi thành tiền khi tăng hoặc giảm số lượng
        edSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int thanhTien = Integer.parseInt(edSoLuong.getText().toString()) * giaBan;
                tvThanhTien.setText(thanhTien+"");
            }
        });
        //sự kiện khi nhấn nút sửa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrThuoc.get(viTri).setSoLuong(Integer.parseInt(edSoLuong.getText().toString()));
                arrThuoc.get(viTri).setThanhTien(Integer.parseInt(tvThanhTien.getText().toString()));
                adapter_itemMuaBanThuoc.notifyDataSetChanged();
                int tongTien=0;
                for (int i = 0;i<arrThuoc.size();i++)
                {
                    tongTien+= arrThuoc.get(i).getThanhTien();
                }
                tvTongTien1.setText(tongTien+"");
                DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
                tvTongTien2.setText(toTheFormat.format(tongTien));
            }
        });
        //Lưu phiếu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuaThuoc phieuMua = (PhieuMuaThuoc) getIntent().getSerializableExtra("ThongTinPhieuMua");
                phieuMua.setTongTien(Integer.parseInt(tvTongTien1.getText().toString()));
                phieuMua.setNgayLap(tvNgayLap.getText().toString());
                //cập nhật biến động
                StaticConfig.mPhieuMuaThuoc.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        PhieuMuaThuoc mPhieuMua = snapshot.getValue(PhieuMuaThuoc.class);
                        if(mPhieuMua.getMaFB().equals(phieuMua.getMaFB()))
                        {
                            for (int i = 0;i<phieuMua.getDanhSachThuoc().size();i++)
                            {
                                //Toast.makeText(getApplicationContext(), mPhieuMua.getDanhSachThuoc().get(i).getSoLuong()+"", Toast.LENGTH_SHORT).show();
                                int soMoi = phieuMua.getDanhSachThuoc().get(i).getSoLuong();
                                int soCu = mPhieuMua.getDanhSachThuoc().get(i).getSoLuong();
                                phieuMua.getDanhSachThuoc().get(i).setBienDong(soMoi-soCu);
                            }
                        }
                        //sửa phiếu mua thuốc
                        StaticConfig.mPhieuMuaThuoc.child(phieuMua.getMaFB()).setValue(phieuMua);
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
                //Cập nhật kho
                StaticConfig.mKhoThuoc.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        KhoThuoc kh = snapshot.getValue(KhoThuoc.class);
                        for (int i = 0;i<phieuMua.getDanhSachThuoc().size();i++)
                        {
                            if (kh.getMaThuoc().equals(phieuMua.getDanhSachThuoc().get(i).getMaThuoc()))
                            {
                                kh.setSoLuong(kh.getSoLuong()+phieuMua.getDanhSachThuoc().get(i).getBienDong());
                                StaticConfig.mKhoThuoc.child(kh.getMaFB()).setValue(kh);
                            }
                        }
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
                AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinPhieuMuaThuoc.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Lưu thành công, mời bạn quay về màn hình trước !!!");
                builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            }
        });
        //nút xóa phiếu mua thuốc
        btnXoa.setOnClickListener(new View.OnClickListener() {
            PhieuMuaThuoc phieuMua = (PhieuMuaThuoc) getIntent().getSerializableExtra("ThongTinPhieuMua");
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThongTinPhieuMuaThuoc.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn xóa không ?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cập nhật kho
                        StaticConfig.mKhoThuoc.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                KhoThuoc kh = snapshot.getValue(KhoThuoc.class);
                                for (int i = 0;i<phieuMua.getDanhSachThuoc().size();i++)
                                {
                                    if (kh.getMaThuoc().equals(phieuMua.getDanhSachThuoc().get(i).getMaThuoc()))
                                    {
                                        kh.setSoLuong(kh.getSoLuong()- phieuMua.getDanhSachThuoc().get(i).getSoLuong());
                                        StaticConfig.mKhoThuoc.child(kh.getMaFB()).setValue(kh);
                                    }
                                }
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
                        //xóa phiếu mua
                        StaticConfig.mPhieuMuaThuoc.child(phieuMua.getMaFB()).removeValue();
                        //thông báo
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(NVK_ThongTinPhieuMuaThuoc.this);
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
                builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private void layThongTinPhieuMuaThuoc() {
        PhieuMuaThuoc phieuMua = (PhieuMuaThuoc) getIntent().getSerializableExtra("ThongTinPhieuMua");
        tvMaPhieu.setText(phieuMua.getMaPhieu());
        tvNgayLap.setText(phieuMua.getNgayLap());
        tvNguoiLap.setText(phieuMua.getNhanVienLap());
        tvTongTien1.setText(phieuMua.getTongTien() + "");
        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
        tvTongTien2.setText(toTheFormat.format(phieuMua.getTongTien()));

        for (int i = 0; i < phieuMua.getDanhSachThuoc().size(); i++) {
            arrThuoc.add(phieuMua.getDanhSachThuoc().get(i));
            adapter_itemMuaBanThuoc.notifyDataSetChanged();
        }
    }

    private void tru1() {
        int soLuong = Integer.parseInt(edSoLuong.getText().toString());
        if (soLuong > 1) {
            soLuong--;
            edSoLuong.setText(soLuong + "");
        }

    }

    private void them1() {
        int soLuong = Integer.parseInt(edSoLuong.getText().toString());
        soLuong++;
        edSoLuong.setText(soLuong + "");
    }

    private void imNgayLapPhieu() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tvNgayLap.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void setControl() {
        imTroVe = findViewById(R.id.imTrove);
        imLich = findViewById(R.id.imLich);
        imTang1 = findViewById(R.id.imThem1);
        imGiam1 = findViewById(R.id.imTru1);
        tvNgayLap = findViewById(R.id.tvNgayLapPhieuTT);
        tvMaPhieu = findViewById(R.id.tvMaPMTTT);
        tvNguoiLap = findViewById(R.id.tvNguoiLapPMTTT);
        tvTenThuoc = findViewById(R.id.tvTenThuocTT);
        tvMaThuoc = findViewById(R.id.tvMaThuocTT1);
        tvTongTien1 = findViewById(R.id.tvTongTien1);
        tvTongTien2 = findViewById(R.id.tvTongTien11);
        tvDonViTinh = findViewById(R.id.tvDonViTinhTT);
        tvThanhTien = findViewById(R.id.tvThanhTienTT);
        lvDanhSachThuoc = findViewById(R.id.dsThuocDaMua);
        btnSua = findViewById(R.id.btnSuaTTPMT);
        btnXoa = findViewById(R.id.btnXoaPMT);
        btnLuu = findViewById(R.id.btnLuuPMTTT);
        edSoLuong = findViewById(R.id.edSoluongThuocTT);

        adapter_itemMuaBanThuoc = new Adapter_ItemMuaBanThuoc(getApplicationContext(), R.layout.item_mua_ban_thuoc, arrThuoc);
        lvDanhSachThuoc.setAdapter(adapter_itemMuaBanThuoc);
    }
}