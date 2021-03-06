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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Adapter.Adapter_ItemMuaBanThuoc;
import com.example.doanquanlynhathuoc.Class.ItemMuaBanThuoc;
import com.example.doanquanlynhathuoc.Class.KhoThuoc;
import com.example.doanquanlynhathuoc.Class.PhieuHoaDon;
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

public class NVBH_ThongTinHoaDon extends AppCompatActivity {
    ImageButton imTroVe, imLich, imThem1, imTru1;
    TextView tvMaHD, tvNgayLap, tvMaThuoc, tvDonViTinh, tvSoluongCon, tvThanhTien, tvTongTien1, tvTongTien2, tvTenThuoc;
    TextView tvSDTKhachHang, tvTenKhachHang, tvNguoiLap;
    EditText edSoLuongThuoc;
    Button btnLuu, btnSuaThuoc, btnXoa;
    ListView lvDanhSachThuoc;

    Adapter_ItemMuaBanThuoc adapter_itemMuaBanThuoc;
    ArrayList<ItemMuaBanThuoc> arrThuoc = new ArrayList();
    int giaBan, viTri, soLuongCu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvbh_thong_tin_hoa_don);
        setControl();
        setEvent();
    }

    private void setEvent() {
        //n??t tr??? v???
        imTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //n??t l???ch
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imNgayLapPhieu();
            }
        });
        //n??t t??ng 1
        imThem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them1();
            }
        });
        //n??t gi???m 1
        imTru1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tru1();
            }
        });
        //l???y th??ng tin h??a ????n
        layThongTinHoaDon();
        //ki???m tra ???? ch???n thu???c hay ch??a
        if (tvTenThuoc.getText().toString().isEmpty()) {
            btnSuaThuoc.setEnabled(false);
        }
        //s??? ki???n click chu???t v??o LV
        lvDanhSachThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnSuaThuoc.setEnabled(true);
                tvMaThuoc.setText(arrThuoc.get(position).getMaThuoc());
                tvTenThuoc.setText(arrThuoc.get(position).getTenThuoc());
                tvDonViTinh.setText(arrThuoc.get(position).getDonViTinh());
                edSoLuongThuoc.setText(arrThuoc.get(position).getSoLuong() + "");
                tvThanhTien.setText(arrThuoc.get(position).getThanhTien() + "");
                viTri = position;
                giaBan = arrThuoc.get(position).getThanhTien() / arrThuoc.get(position).getSoLuong();
                //l???y s??? l?????ng thu???c c??n l???i trong kho
                LaySoLuongThuocConLai(arrThuoc.get(position).getMaThuoc());
                soLuongCu = arrThuoc.get(position).getSoLuong();

            }
        });
        //s??? ki???n thay ?????i th??nh ti???n khi t??ng ho???c gi???m s??? l?????ng
        edSoLuongThuoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edSoLuongThuoc.getText().toString().equals("")==false)
                {
                    int thanhTien = Integer.parseInt(edSoLuongThuoc.getText().toString()) * giaBan;
                    tvThanhTien.setText(thanhTien+"");
                }
            }
        });
        //n??t s???a
        btnSuaThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongCon = Integer.parseInt(tvSoluongCon.getText().toString());
                int soLuong = Integer.parseInt(edSoLuongThuoc.getText().toString());
                int soLuongTong = soLuongCu + soLuongCon;
                if(soLuong<1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThongTinHoaDon.this);
                    builder.setTitle("Th??ng B??o");
                    builder.setMessage("S??? l?????ng ph???i l???n h??n 1 !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
                else if(soLuong > soLuongTong)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThongTinHoaDon.this);
                    builder.setTitle("Th??ng B??o");
                    builder.setMessage("S??? l?????ng m???i ph???i nh??? h??n t???ng s??? thu???c ???? mua & kho hi???n c??!!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
                else{
                    arrThuoc.get(viTri).setSoLuong(Integer.parseInt(edSoLuongThuoc.getText().toString()));
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
            }
        });
        //l??u phi???u
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuHoaDon phieuMua = (PhieuHoaDon) getIntent().getSerializableExtra("ThongTinPhieuHoaDon");
                phieuMua.setTongTien(Integer.parseInt(tvTongTien1.getText().toString()));
                phieuMua.setNgayLap(tvNgayLap.getText().toString());
                //c???p nh???t bi???n ?????ng
                StaticConfig.mPhieuHoaDon.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        PhieuHoaDon mPhieuMua = snapshot.getValue(PhieuHoaDon.class);
                        if (mPhieuMua.getMaFB().equals(phieuMua.getMaFB())) {
                            for (int i = 0; i < phieuMua.getDanhSachThuoc().size(); i++) {
                                int soMoi = phieuMua.getDanhSachThuoc().get(i).getSoLuong();
                                int soCu = mPhieuMua.getDanhSachThuoc().get(i).getSoLuong();
                                phieuMua.getDanhSachThuoc().get(i).setBienDong(soMoi - soCu);
                            }
                        }
                        //s???a phi???u mua thu???c
                        StaticConfig.mPhieuHoaDon.child(phieuMua.getMaFB()).setValue(phieuMua);
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
                //c???p nh???t kho
                StaticConfig.mKhoThuoc.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        KhoThuoc kh = snapshot.getValue(KhoThuoc.class);
                        for (int i = 0; i < phieuMua.getDanhSachThuoc().size(); i++) {
                            if (kh.getMaThuoc().equals(phieuMua.getDanhSachThuoc().get(i).getMaThuoc())) {
                                kh.setSoLuong(kh.getSoLuong() - phieuMua.getDanhSachThuoc().get(i).getBienDong());
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
                //th??ng b??o
                AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThongTinHoaDon.this);
                builder.setTitle("Th??ng B??o");
                builder.setMessage("L??u th??nh c??ng, m???i b???n quay v??? m??n h??nh tr?????c !!!");
                builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            }
        });
        //X??a phi???u
        btnXoa.setOnClickListener(new View.OnClickListener() {
            PhieuHoaDon phieuMua = (PhieuHoaDon) getIntent().getSerializableExtra("ThongTinPhieuHoaDon");
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThongTinHoaDon.this);
                builder.setTitle("Th??ng B??o");
                builder.setMessage("B???n c?? mu???n x??a kh??ng ?");
                builder.setPositiveButton("c??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //C???p nh???t kho
                        StaticConfig.mKhoThuoc.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                KhoThuoc kh = snapshot.getValue(KhoThuoc.class);
                                for (int i = 0; i < phieuMua.getDanhSachThuoc().size(); i++) {
                                    if (kh.getMaThuoc().equals(phieuMua.getDanhSachThuoc().get(i).getMaThuoc())) {
                                        kh.setSoLuong(kh.getSoLuong() - phieuMua.getDanhSachThuoc().get(i).getSoLuong());
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
                        //x??a phi???u mua
                        StaticConfig.mPhieuHoaDon.child(phieuMua.getMaFB()).removeValue();
                        //th??ng b??o
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(NVBH_ThongTinHoaDon.this);
                        builder1.setTitle("Th??ng B??o");
                        builder1.setMessage("X??a th??nh c??ng ?");
                        builder1.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder1.show();
                    }
                });
                builder.setNegativeButton("kh??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private void tru1() {
        int soLuong = Integer.parseInt(edSoLuongThuoc.getText().toString());
        if (soLuong > 1) {
            soLuong--;
            edSoLuongThuoc.setText(soLuong + "");
        }

    }

    private void them1() {
        int soLuongCon = Integer.parseInt(tvSoluongCon.getText().toString());
        int soLuong = Integer.parseInt(edSoLuongThuoc.getText().toString());
        int soLuongTong = soLuongCu + soLuongCon;
        if (soLuong < soLuongTong) {
            soLuong++;
            edSoLuongThuoc.setText(soLuong + "");
        }

    }

    private void LaySoLuongThuocConLai(String maThuoc) {
        StaticConfig.mKhoThuoc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    KhoThuoc kh = ds.getValue(KhoThuoc.class);
                    if (kh.getMaThuoc().equals(maThuoc)) {
                        tvSoluongCon.setText(kh.getSoLuong() + "");
                        return;
                    } else
                        tvSoluongCon.setText("0");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void layThongTinHoaDon() {
        PhieuHoaDon phieuHD = (PhieuHoaDon) getIntent().getSerializableExtra("ThongTinPhieuHoaDon");
        tvMaHD.setText(phieuHD.getMaPhieu());
        tvNgayLap.setText(phieuHD.getNgayLap());
        tvNguoiLap.setText(phieuHD.getNhanVienLap());
        tvSDTKhachHang.setText(phieuHD.getSdtKhachHang());
        tvTenKhachHang.setText(phieuHD.getTenKhachHang());
        tvTongTien1.setText(phieuHD.getTongTien() + "");
        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
        tvTongTien2.setText(toTheFormat.format(phieuHD.getTongTien()));

        for (int i = 0; i < phieuHD.getDanhSachThuoc().size(); i++) {
            arrThuoc.add(phieuHD.getDanhSachThuoc().get(i));
            adapter_itemMuaBanThuoc.notifyDataSetChanged();
        }
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
        imThem1 = findViewById(R.id.imThem1);
        imTru1 = findViewById(R.id.imTru1);
        tvMaHD = findViewById(R.id.tvMaHoaDon);
        tvNguoiLap = findViewById(R.id.tvNguoiLap);
        tvTenThuoc = findViewById(R.id.tvTenThuoc);
        tvTongTien1 = findViewById(R.id.tvTongTien1);
        tvTongTien2 = findViewById(R.id.tvTongTien2);
        tvNgayLap = findViewById(R.id.tvNgayLapHoaDon);
        tvMaThuoc = findViewById(R.id.tvMaThuoc);
        tvDonViTinh = findViewById(R.id.tvDonViTinh);
        tvSoluongCon = findViewById(R.id.tvSoLuongConLai);
        tvThanhTien = findViewById(R.id.tvThanhTien);
        tvSDTKhachHang = findViewById(R.id.tvSdtKhachHang);
        tvTenKhachHang = findViewById(R.id.tvTenKhachHang);
        edSoLuongThuoc = findViewById(R.id.edSoluongThuoc);
        btnSuaThuoc = findViewById(R.id.btnThemThuoc);
        btnLuu = findViewById(R.id.btnLuuHoaDon);
        btnXoa = findViewById(R.id.btnXoaHoaDon);
        lvDanhSachThuoc = findViewById(R.id.lvDanhSachThuoc);

        adapter_itemMuaBanThuoc = new Adapter_ItemMuaBanThuoc(getApplicationContext(), R.layout.item_mua_ban_thuoc, arrThuoc);
        lvDanhSachThuoc.setAdapter(adapter_itemMuaBanThuoc);

    }
}