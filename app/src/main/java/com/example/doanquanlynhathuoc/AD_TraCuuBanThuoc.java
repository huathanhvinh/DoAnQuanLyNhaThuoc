package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Adapter.Adapter_TraCuuHoaDon;
import com.example.doanquanlynhathuoc.Class.PhieuHoaDon;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class AD_TraCuuBanThuoc extends AppCompatActivity {
    ImageButton imTroVe, imLichBD, imLichKT, imTimKiem, imLocTheoTien, imLocTheoNgay, imRefresh;
    TextView tvNgayBatDau, tvNgayKetThuc, tvTongPhieu;
    ListView lvDanhSach;

    Adapter_TraCuuHoaDon adapter_traCuuHoaDon;
    ArrayList<PhieuHoaDon> arrDanhSach = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_tra_cuu_ban_thuoc);
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
        //lấy ra ngày bất đầu và kết thúc theo tháng
        layRaThangHienTaiVaThangTiepTheo();
        //nút lịch ngày bất đầu
        imLichBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imButtonLichNgayBatDau();
            }
        });
        //nút lịch ngày kết thúc
        imLichKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imButtonLichNgayKetThuc();
            }
        });
        //lấy thông tin các phiếu hóa đơn từ firebase
        layThongTinPhieuHoaDon();
        //nút tìm
        imTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkNgay = 0;
                Date ngayBatDau;
                Date ngayKetThuc;
                //kiểm tra thời gian bất đầu với thời gian kết thúc
                try {
                    ngayBatDau = new SimpleDateFormat("dd/MM/yyyy").parse(tvNgayBatDau.getText().toString());
                    ngayKetThuc = new SimpleDateFormat("dd/MM/yyyy").parse(tvNgayKetThuc.getText().toString());
                    if (ngayBatDau.compareTo(ngayKetThuc) > 0)
                        checkNgay = 1;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (checkNgay != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AD_TraCuuBanThuoc.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Thời gian tìm kiếm không hợp lệ !!!");
                    builder.setPositiveButton("oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                } else {
                    arrDanhSach.clear();
                    adapter_traCuuHoaDon.clear();
                    tvTongPhieu.setText("0 VNĐ");
                    StaticConfig.mPhieuHoaDon.addChildEventListener(new ChildEventListener() {
                        int tongtien = 0;
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            PhieuHoaDon phieuMua = snapshot.getValue(PhieuHoaDon.class);
                            try {
                                Date ngayLap = new SimpleDateFormat("dd/MM/yyyy").parse(phieuMua.getNgayLap());
                                Date ngayBatDau1 = new SimpleDateFormat("dd/MM/yyyy").parse(tvNgayBatDau.getText().toString());
                                Date ngayKetThuc1 = new SimpleDateFormat("dd/MM/yyyy").parse(tvNgayKetThuc.getText().toString());
                                if (ngayLap.compareTo(ngayBatDau1) >= 0 && ngayLap.compareTo(ngayKetThuc1) <= 0) {
                                    arrDanhSach.add(phieuMua);
                                    adapter_traCuuHoaDon.notifyDataSetChanged();
                                    tongtien+=phieuMua.getTongTien();
                                    DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
                                    tvTongPhieu.setText(toTheFormat.format(tongtien) + " VNĐ");

                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
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

                }
            }
        });
        //nút lọc theo tiền
        imLocTheoTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(arrDanhSach, new Comparator<PhieuHoaDon>() {
                    @Override
                    public int compare(PhieuHoaDon o1, PhieuHoaDon o2) {
                        if (o1.getTongTien() < o2.getTongTien())
                            return -1;
                        else if (o1.getTongTien() == o2.getTongTien())
                            return 0;
                        else
                            return 1;
                    }
                });
                adapter_traCuuHoaDon.notifyDataSetChanged();
            }
        });
        //nút lọc theo ngày
        imLocTheoNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(arrDanhSach, new Comparator<PhieuHoaDon>() {
                    @Override
                    public int compare(PhieuHoaDon o1, PhieuHoaDon o2) {
                        try {
                            Date ngay1 = new SimpleDateFormat("dd/MM/yyyy").parse(o1.getNgayLap());
                            Date ngay2 = new SimpleDateFormat("dd/MM/yyyy").parse(o2.getNgayLap());
                            if (ngay1.compareTo(ngay2) < 0)
                                return -1;
                            else if (ngay1.compareTo(ngay2) == 0)
                                return 0;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 1;
                    }
                });
                adapter_traCuuHoaDon.notifyDataSetChanged();
            }
        });
        //nút refresh
        imRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrDanhSach.clear();
                adapter_traCuuHoaDon.clear();
                layRaThangHienTaiVaThangTiepTheo();
                layThongTinPhieuHoaDon();
            }
        });
    }

    private void layThongTinPhieuHoaDon() {
        StaticConfig.mPhieuHoaDon.addChildEventListener(new ChildEventListener() {
            int tongtien = 0;
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PhieuHoaDon phieuMua = snapshot.getValue(PhieuHoaDon.class);
                try {
                    Date ngayLap = new SimpleDateFormat("dd/MM/yyyy").parse(phieuMua.getNgayLap());
                    Date ngayBatDau1 = new SimpleDateFormat("dd/MM/yyyy").parse(tvNgayBatDau.getText().toString());
                    Date ngayKetThuc1 = new SimpleDateFormat("dd/MM/yyyy").parse(tvNgayKetThuc.getText().toString());
                    if (ngayLap.compareTo(ngayBatDau1) >= 0 && ngayLap.compareTo(ngayKetThuc1) <= 0) {
                        arrDanhSach.add(phieuMua);
                        adapter_traCuuHoaDon.notifyDataSetChanged();
                        tongtien+=phieuMua.getTongTien();
                        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
                        tvTongPhieu.setText(toTheFormat.format(tongtien) + " VNĐ");

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
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
    }

    private void imButtonLichNgayKetThuc() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tvNgayKetThuc.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void imButtonLichNgayBatDau() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tvNgayBatDau.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void layRaThangHienTaiVaThangTiepTheo() {
        String namHienTai = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String namTiepTheo = namHienTai;
        String thangHienTai = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        String thangTiepTheo = (Integer.parseInt(thangHienTai) + 1) + "";
        if (thangTiepTheo.equals("13")) {
            thangTiepTheo = "1";
            namTiepTheo = (Integer.parseInt(namHienTai) + 1) + "";
        }
        if (Integer.parseInt(thangTiepTheo) < 10) {
            thangTiepTheo = "0" + thangTiepTheo;
        }
        //thêm tháng mặc địch vào ô tìm kiếm
        tvNgayBatDau.setText("01" + "/" + thangHienTai + "/" + namHienTai);
        tvNgayKetThuc.setText("01" + "/" + thangTiepTheo + "/" + namTiepTheo);
    }

    private void setControl() {
        imTroVe = findViewById(R.id.imTrove);
        imRefresh = findViewById(R.id.imRefresh);
        imLichBD = findViewById(R.id.imLichBD);
        imLichKT = findViewById(R.id.imLichKT);
        imTimKiem = findViewById(R.id.imTimKiem);
        imLocTheoTien = findViewById(R.id.imLocTheoTien);
        imLocTheoNgay = findViewById(R.id.imLocTheoNgay);
        tvNgayBatDau = findViewById(R.id.tvNgayBatDau);
        tvNgayKetThuc = findViewById(R.id.tvNgayKetThuc);
        tvTongPhieu = findViewById(R.id.tvTongHoaDon);
        lvDanhSach = findViewById(R.id.lvDanhSachHoaDon);

        adapter_traCuuHoaDon = new Adapter_TraCuuHoaDon(getApplicationContext(), R.layout.custom_tra_cuu_hoa_don, arrDanhSach);
        lvDanhSach.setAdapter(adapter_traCuuHoaDon);
    }
}