package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.Class.KhoThuoc;
import com.example.doanquanlynhathuoc.Class.NhanVien;
import com.example.doanquanlynhathuoc.Class.PhieuHoaDon;
import com.example.doanquanlynhathuoc.Class.PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NVBH_ThemHoaDon extends AppCompatActivity {
    ImageButton imTroVe, imLich, imThem1, imTru1;
    Spinner spDanhSachThuoc;
    TextView tvMaHD, tvNgayLap, tvMaThuoc, tvDonViTinh, tvSoluongCon, tvThanhTien, tvTongTien1, tvTongTien2;
    EditText edSDTKhachHang, edTenKhachHang, edDiaChi, edTimKiemTenThuoc, edSoLuongThuoc;
    Button btnThemHoaDon, btnThemThuoc;
    ListView lvDanhSachThuoc;

    Adapter_ItemMuaBanThuoc adapter_itemMuaBanThuoc;
    ArrayList<ItemMuaBanThuoc> arrItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvbh_them_hoa_don);
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
        //l???y ra m?? h??a ????n v?? ng??y l???p t??? ?????ng
        layMaPhieuMua();
        //n??t l???ch
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imNgayLapPhieu();
            }
        });
        //x??? l?? khi nh???p sdt kh??ch h??ng
        edSDTKhachHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sdtKH = edSDTKhachHang.getText().toString();
                StaticConfig.mKhachHang.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            KhachHang kh = ds.getValue(KhachHang.class);
                            if (kh.getSdt().equals(sdtKH)) {
                                edTenKhachHang.setText(kh.getTenKH());
                                edDiaChi.setText(kh.getDiaChi());
                                return;
                            } else {
                                edTenKhachHang.setText("");
                                edDiaChi.setText("");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //n??t th??m 1
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
        //l???y danh s??ch thu???c t??? firebase truy???n v??o t??n thu???c
        layDanhSachThuocTruyenVaoSP();
        //l???y s??? l?????ng thu???c ??ang c?? trong kho - xong (layDanhSachThuocTruyenVaoSP();)
        //n??t th??m
        btnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMua = 0;
                int soLuongCon = 0;
                if (edSoLuongThuoc.getText().toString().equals("") == false) {
                    soLuongMua = Integer.parseInt(edSoLuongThuoc.getText().toString());
                    soLuongCon = Integer.parseInt(tvSoluongCon.getText().toString());
                }
                if (edSDTKhachHang.getText().toString().isEmpty() || edTenKhachHang.getText().toString().isEmpty() || edDiaChi.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemHoaDon.this);
                    builder.setTitle("Th??ng B??o");
                    builder.setMessage("Th??ng tin kh??ch h??ng kh??ng ???????c ????? tr???ng !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else if (edSoLuongThuoc.getText().toString().equals("") || edSoLuongThuoc.getText().toString().equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemHoaDon.this);
                    builder.setTitle("Th??ng B??o");
                    builder.setMessage("M???i nh???p s??? l?????ng c???n mua !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else if (soLuongMua > soLuongCon) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemHoaDon.this);
                    builder.setTitle("Th??ng B??o");
                    builder.setMessage("S??? l?????ng mua ph???i nh??? h??n s??? l?????ng thu???c c??n l???i !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    int check = 0;
                    String key = StaticConfig.mItemMuaBanThuoc.push().getKey();
                    int thanhTien = Integer.parseInt(tvThanhTien.getText().toString());
                    int soLuong = Integer.parseInt(edSoLuongThuoc.getText().toString());
                    ItemMuaBanThuoc item = new ItemMuaBanThuoc(key, tvMaThuoc.getText().toString(), spDanhSachThuoc.getSelectedItem().toString(), tvDonViTinh.getText().toString(), soLuong, thanhTien, 0);
                    for (int i = 0; i < arrItem.size(); i++) {
                        if (tvMaThuoc.getText().toString().equals(arrItem.get(i).getMaThuoc())) {
                            check = 1;
                            AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemHoaDon.this);
                            builder.setTitle("Th??ng B??o");
                            builder.setMessage("???? c?? thu???c n??y trong danh s??ch !!!");
                            builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                            builder.show();
                        }
                    }
                    if (check == 0) {
                        arrItem.add(item);
                        adapter_itemMuaBanThuoc.notifyDataSetChanged();
                        int tongTien = Integer.parseInt(tvTongTien1.getText().toString());
                        tongTien += item.getThanhTien();
                        tvTongTien1.setText(tongTien + "");
                        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
                        tvTongTien2.setText(toTheFormat.format(tongTien));
                    }
                }

            }
        });
        //x??a thu???c n???u mua sai d???a v??o longclick tr??n listview
        lvDanhSachThuoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemHoaDon.this);
                builder.setTitle("Th??ng B??o");
                builder.setMessage("B???n c?? mu???n x??a kh??ng");
                builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int tongTien = Integer.parseInt(tvTongTien1.getText().toString());
                        int tienBitru = arrItem.get(position).getThanhTien();
                        tongTien = tongTien - tienBitru;
                        tvTongTien1.setText(tongTien + "");
                        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
                        tvTongTien2.setText(toTheFormat.format(tongTien));
                        arrItem.remove(position);
                        adapter_itemMuaBanThuoc.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("kh??ng", null);
                builder.show();
                return true;
            }
        });
        //th??m h??a ????n
        btnThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrItem.size() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemHoaDon.this);
                    builder.setTitle("Th??ng B??o");
                    builder.setMessage("Th??m th???t b???i, b???n ch??a mua thu???c g?? !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    StaticConfig.mNhanVien.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String tenNhanVien = "";
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                NhanVien nv = ds.getValue(NhanVien.class);
                                if (nv.getSdt().equals(StaticConfig.sdt))
                                    tenNhanVien = nv.getTenNV();
                            }
                            ArrayList<ItemMuaBanThuoc> dsThuoc = new ArrayList<>();
                            for (int i = 0; i < arrItem.size(); i++) {
                                dsThuoc.add(arrItem.get(i));
                            }
                            int tongTien = Integer.parseInt(tvTongTien1.getText().toString());
                            //Th??m h??a ????n
                            String key = StaticConfig.mPhieuHoaDon.push().getKey();
                            PhieuHoaDon hd = new PhieuHoaDon(key, tvMaHD.getText().toString(), tvNgayLap.getText().toString(), tenNhanVien,
                                    edSDTKhachHang.getText().toString(), edTenKhachHang.getText().toString(), tongTien, dsThuoc);
                            StaticConfig.mPhieuHoaDon.child(key).setValue(hd);
                            //c???p nh???t kho thu???c
                            StaticConfig.mKhoThuoc.addValueEventListener(new ValueEventListener() {
                                int min = 0;
                                int max = dsThuoc.size();
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (min < max)
                                    {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            KhoThuoc kh = ds.getValue(KhoThuoc.class);
                                            if (dsThuoc.get(min).getMaThuoc().equals(kh.getMaThuoc())) {
                                                int tonThuoc = kh.getSoLuong();
                                                tonThuoc = tonThuoc - dsThuoc.get(min).getSoLuong();
                                                kh.setSoLuong(tonThuoc);
                                                StaticConfig.mKhoThuoc.child(kh.getMaFB()).setValue(kh);
                                                min++;
                                                break;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            //th???ng b??o
                            AlertDialog.Builder builder = new AlertDialog.Builder(NVBH_ThemHoaDon.this);
                            builder.setTitle("Th??ng B??o");
                            builder.setMessage("Th??m Th??nh C??ng !!!");
                            //set d??? li???u r???ng
                            arrItem.clear();
                            adapter_itemMuaBanThuoc.clear();
                            edTimKiemTenThuoc.setText("");
                            edSDTKhachHang.setText("");
                            tvTongTien1.setText("0");
                            tvTongTien2.setText("0");
                            builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            builder.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
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

    private void layDanhSachThuocTruyenVaoSP() {
        ArrayList<Thuoc> arrThuoc = new ArrayList<>();
        ArrayList<String> arrTenThuoc = new ArrayList<>();
        StaticConfig.mThuoc.addValueEventListener(new ValueEventListener() {
            ArrayAdapter<String> adapter;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Thuoc t = ds.getValue(Thuoc.class);
                    arrThuoc.add(t);
                }
                for (int i = 0; i < arrThuoc.size(); i++) {
                    arrTenThuoc.add(arrThuoc.get(i).getTenThuoc());
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrTenThuoc);
                spDanhSachThuoc.setAdapter(adapter);
                //set m??, ????n v??? t??nh,th??nh ti???n
                spDanhSachThuoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        edSoLuongThuoc.setText("1");
                        tvMaThuoc.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getMaThuoc() + "");
                        tvDonViTinh.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getDonViTinh() + "");
                        //c???p nh???t s??? l?????ng thu???c c??n l???i
                        LaySoLuongThuocConLai(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getMaThuoc());
                        //t??nh ti???n l???n 1, khi s??? l?????ng l?? 1
                        tvThanhTien.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getGiaBan() + "");
                        //t??nh ti???n khi s??? l?????ng thay ?????i
                        edSoLuongThuoc.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (edSoLuongThuoc.getText().toString().equals("") == false) {
                                    int soLuong = Integer.parseInt(edSoLuongThuoc.getText().toString());
                                    int giaBan = arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getGiaBan();
                                    int thanhTien = soLuong * giaBan;
                                    tvThanhTien.setText((thanhTien) + "");
                                }
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //t??m ki???m t??n thu???c
                edTimKiemTenThuoc.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        for (int i = 0; i < spDanhSachThuoc.getCount(); i++) {
                            if (spDanhSachThuoc.getItemAtPosition(i).toString().toLowerCase().contains(edTimKiemTenThuoc.getText().toString())) {
                                spDanhSachThuoc.setSelection(i);
                                return;
                            }
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        if (soLuong < soLuongCon) {
            soLuong++;
            edSoLuongThuoc.setText(soLuong + "");
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

    private void layMaPhieuMua() {
        String maPhieuMua = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
        String ngayLap = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tvMaHD.setText("HD" + maPhieuMua);
        tvNgayLap.setText(ngayLap);
    }

    private void setControl() {
        imTroVe = findViewById(R.id.imTrove);
        spDanhSachThuoc = findViewById(R.id.spDanhSachThuoc);
        imLich = findViewById(R.id.imLich);
        imThem1 = findViewById(R.id.imThem1);
        imTru1 = findViewById(R.id.imTru1);
        tvMaHD = findViewById(R.id.tvMaHoaDon);
        tvTongTien1 = findViewById(R.id.tvTongTien1);
        tvTongTien2 = findViewById(R.id.tvTongTien2);
        tvNgayLap = findViewById(R.id.tvNgayLapHoaDon);
        tvMaThuoc = findViewById(R.id.tvMaThuoc);
        tvDonViTinh = findViewById(R.id.tvDonViTinh);
        tvSoluongCon = findViewById(R.id.tvSoLuongConLai);
        tvThanhTien = findViewById(R.id.tvThanhTien);
        edSDTKhachHang = findViewById(R.id.edSdtKhachHang);
        edTenKhachHang = findViewById(R.id.edTenKhachHang);
        edDiaChi = findViewById(R.id.edDiaChiKhachHang);
        edTimKiemTenThuoc = findViewById(R.id.edTimTenThuoc);
        edSoLuongThuoc = findViewById(R.id.edSoluongThuoc);
        btnThemThuoc = findViewById(R.id.btnThemThuoc);
        btnThemHoaDon = findViewById(R.id.btnThemHoaDon);
        lvDanhSachThuoc = findViewById(R.id.lvDanhSachThuoc);

        adapter_itemMuaBanThuoc = new Adapter_ItemMuaBanThuoc(getApplicationContext(), R.layout.item_mua_ban_thuoc, arrItem);
        lvDanhSachThuoc.setAdapter(adapter_itemMuaBanThuoc);

    }
}