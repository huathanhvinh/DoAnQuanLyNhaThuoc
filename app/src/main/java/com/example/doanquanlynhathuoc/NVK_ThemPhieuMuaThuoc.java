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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanquanlynhathuoc.Adapter.Adapter_ItemMuaBanThuoc;
import com.example.doanquanlynhathuoc.Class.ItemMuaBanThuoc;
import com.example.doanquanlynhathuoc.Class.KhoThuoc;
import com.example.doanquanlynhathuoc.Class.NhanVien;
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

public class NVK_ThemPhieuMuaThuoc extends AppCompatActivity {
    TextView tvMaPhieu, tvNgayLapPhieu, tvMaThuoc, tvDonViTinh, tvThanhTien, tvTongTien1, tvTongTien11;
    ImageView imTroVe, imLich, imThem1, imTru1;
    Spinner spDanhSachThuoc;
    EditText edTimTenThuoc, edSoLuong;
    Button btnThemThuoc, btnThemPhieuMua;
    ListView lvDanhSachThuoc;

    Adapter_ItemMuaBanThuoc adapter_itemMuaBanThuoc;
    ArrayList<ItemMuaBanThuoc> arrItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nvk_them_phieu_mua_thuoc);
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
        //lấy ra mã phiếu mua, ngày lập tự động.
        layMaPhieuMua();
        //nút lịch
        imLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imNgayLapPhieu();
            }
        });
        //xử lý nút thêm 1
        imThem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them1();
            }
        });
        //xử lý nút giảm 1
        imTru1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tru1();
            }
        });
        //lấy danh sách thuốc đang có từ firebase
        layDanhSachThuocTruyenVaoSP();
        //xử lý nút thêm vào listview
        btnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = StaticConfig.mItemMuaBanThuoc.push().getKey();
                int thanhTien = Integer.parseInt(tvThanhTien.getText().toString());
                int soLuong = Integer.parseInt(edSoLuong.getText().toString());
                ItemMuaBanThuoc item = new ItemMuaBanThuoc(key, tvMaThuoc.getText().toString(), spDanhSachThuoc.getSelectedItem().toString(), tvDonViTinh.getText().toString(), soLuong, thanhTien,0);
                arrItem.add(item);
                adapter_itemMuaBanThuoc.notifyDataSetChanged();
                int tongTien = Integer.parseInt(tvTongTien1.getText().toString());
                tongTien += item.getThanhTien();
                tvTongTien1.setText(tongTien + "");
                DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
                tvTongTien11.setText(toTheFormat.format(tongTien));
            }
        });
        //xóa subItem nếu thêm sai thuốc
        lvDanhSachThuoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemPhieuMuaThuoc.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int tongTien = Integer.parseInt(tvTongTien1.getText().toString());
                        int tienBitru = arrItem.get(position).getThanhTien();
                        tongTien = tongTien - tienBitru;
                        tvTongTien1.setText(tongTien + "");
                        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
                        tvTongTien11.setText(toTheFormat.format(tongTien));
                        arrItem.remove(position);
                        adapter_itemMuaBanThuoc.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("không", null);
                builder.show();
                return true;
            }
        });
        //tính tổng tiền - xong
        //thêm phiếu mua thuốc vào firebase
        btnThemPhieuMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvTongTien11.getText().toString().equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemPhieuMuaThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn chưa nhập thuốc nào !!!");
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
                            //thêm phiếu mua
                            String key = StaticConfig.mPhieuMuaThuoc.push().getKey();
                            PhieuMuaThuoc pm = new PhieuMuaThuoc(key, tvMaPhieu.getText().toString(), tvNgayLapPhieu.getText().toString(), tenNhanVien, tongTien, dsThuoc);
                            StaticConfig.mPhieuMuaThuoc.child(key).setValue(pm);
                            //cập nhật kho thuốc
                            StaticConfig.mKhoThuoc.addValueEventListener(new ValueEventListener() {
                                int min = 0;
                                int max = dsThuoc.size();
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (min < max) {
                                        int check = 0;
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            KhoThuoc kh = ds.getValue(KhoThuoc.class);
                                            if (dsThuoc.get(min).getMaThuoc().equals(kh.getMaThuoc())) {
                                                int tonThuoc = kh.getSoLuong();
                                                tonThuoc = tonThuoc + dsThuoc.get(min).getSoLuong();
                                                kh.setSoLuong(tonThuoc);
                                                StaticConfig.mKhoThuoc.child(kh.getMaFB()).setValue(kh);
                                                min++;
                                                check = 1;
                                                break;
                                            }
                                        }
                                        if (check == 0) {
                                            String key = StaticConfig.mKhoThuoc.push().getKey();
                                            KhoThuoc kh1 = new KhoThuoc(key, dsThuoc.get(min).getMaThuoc(), dsThuoc.get(min).getTenThuoc(), dsThuoc.get(min).getSoLuong());
                                            StaticConfig.mKhoThuoc.child(key).setValue(kh1);
                                            min++;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            //thống báo
                            AlertDialog.Builder builder = new AlertDialog.Builder(NVK_ThemPhieuMuaThuoc.this);
                            builder.setTitle("Thông Báo");
                            builder.setMessage("Thêm Thành Công !!!");
                            //set dữ liệu rỗng
                            arrItem.clear();
                            adapter_itemMuaBanThuoc.clear();
                            tvTongTien1.setText("0");
                            tvTongTien11.setText("0");
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
                //set mã, đơn vị tính,thành tiền
                spDanhSachThuoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        edSoLuong.setText("1");
                        tvMaThuoc.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getMaThuoc() + "");
                        tvDonViTinh.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getDonViTinh() + "");
                        //tính tiền lần 1, khi số lượng là 1
                        tvThanhTien.setText(arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getGiaBan() + "");
                        //tính tiền khi số lượng thay đổi
                        edSoLuong.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                int soLuong = Integer.parseInt(edSoLuong.getText().toString());
                                int giaBan = arrThuoc.get(spDanhSachThuoc.getSelectedItemPosition()).getGiaBan();
                                int thanhTien = soLuong * giaBan;
                                tvThanhTien.setText((thanhTien) + "");
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //tìm kiếm tên thuốc
                edTimTenThuoc.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        for (int i = 0; i < spDanhSachThuoc.getCount(); i++) {
                            if (spDanhSachThuoc.getItemAtPosition(i).toString().toLowerCase().contains(edTimTenThuoc.getText().toString())) {
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
                tvNgayLapPhieu.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void layMaPhieuMua() {
        String maPhieuMua = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
        String ngayLap = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tvMaPhieu.setText("MT" + maPhieuMua);
        tvNgayLapPhieu.setText(ngayLap);
    }

    private void setControl() {
        tvMaPhieu = findViewById(R.id.tvMaPhieuMuaThuoc);
        tvTongTien1 = findViewById(R.id.tvTongTien1);
        tvTongTien11 = findViewById(R.id.tvTongTien11);
        tvNgayLapPhieu = findViewById(R.id.tvNgayPhieuMuaThuoc);
        imLich = findViewById(R.id.imLich);
        imTroVe = findViewById(R.id.imTrove);
        edTimTenThuoc = findViewById(R.id.edTimTenThuoc);
        spDanhSachThuoc = findViewById(R.id.spDanhSachThuoc);
        tvMaThuoc = findViewById(R.id.tvMaThuoc);
        tvDonViTinh = findViewById(R.id.tvDonViTinh);
        edSoLuong = findViewById(R.id.edSoluongThuoc);
        imThem1 = findViewById(R.id.imThem1);
        imTru1 = findViewById(R.id.imTru1);
        tvThanhTien = findViewById(R.id.tvThanhTien);
        btnThemThuoc = findViewById(R.id.btnThem);
        lvDanhSachThuoc = findViewById(R.id.dsThuocDaMua);
        btnThemPhieuMua = findViewById(R.id.btnThemPhieuMua);

        adapter_itemMuaBanThuoc = new Adapter_ItemMuaBanThuoc(getApplicationContext(), R.layout.item_mua_ban_thuoc, arrItem);
        lvDanhSachThuoc.setAdapter(adapter_itemMuaBanThuoc);
    }
}