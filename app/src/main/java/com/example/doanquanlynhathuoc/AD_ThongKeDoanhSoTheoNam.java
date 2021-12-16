package com.example.doanquanlynhathuoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanquanlynhathuoc.Class.PhieuHoaDon;
import com.example.doanquanlynhathuoc.Config.StaticConfig;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;

public class AD_ThongKeDoanhSoTheoNam extends AppCompatActivity {
    BarChart barChart;
    PieChart pieChare;
    TextView tvTongHoaDon;
    TextView tvNamHienTai,tvNamHienTai1;
    ImageView imTroVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_thong_ke_doanh_so_theo_nam);
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
        //lấy ra năm hiện tại
        layNamHienTai();
        //biểu đồ cột
        bieuDoCot();
        //biểu đồ tròn
        bieuDoTron();
    }

    private void layNamHienTai() {
        String namHienTai = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        tvNamHienTai.setText(namHienTai);
        tvNamHienTai1.setText(namHienTai);
    }

    private void bieuDoTron() {
        StaticConfig.mPhieuHoaDon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float tongTienQuy1 = 0;
                float tongTienQuy2 = 0;
                float tongTienQuy3 = 0;
                float tongTienQuy4 = 0;
                float tongDoanhThu = 0;
                String namHienTai = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
                String namTiepTheo = (Integer.parseInt(namHienTai) + 1) + "";
                for (DataSnapshot ds : snapshot.getChildren()) {
                    PhieuHoaDon hd = ds.getValue(PhieuHoaDon.class);
                    try {
                        Date ngayLap = new SimpleDateFormat("dd/MM/yyyy").parse(hd.getNgayLap());
                        Date ngayBatDauQ1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + namHienTai);
                        Date ngayKetThucQ1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/04/" + namHienTai);
                        Date ngayBatDauQ2 = new SimpleDateFormat("dd/MM/yyyy").parse("01/04/" + namHienTai);
                        Date ngayKetThucQ2 = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/" + namHienTai);
                        Date ngayBatDauQ3 = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/" + namHienTai);
                        Date ngayKetThucQ3 = new SimpleDateFormat("dd/MM/yyyy").parse("01/10/" + namHienTai);
                        Date ngayBatDauQ4 = new SimpleDateFormat("dd/MM/yyyy").parse("01/10/" + namHienTai);
                        Date ngayKetThucQ4 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + namTiepTheo);

                        if (ngayLap.compareTo(ngayBatDauQ1) >= 0 && ngayLap.compareTo(ngayKetThucQ1) < 0) {
                            tongTienQuy1 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauQ2) >= 0 && ngayLap.compareTo(ngayKetThucQ2) < 0) {
                            tongTienQuy2 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauQ3) >= 0 && ngayLap.compareTo(ngayKetThucQ3) < 0) {
                            tongTienQuy3 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauQ4) >= 0 && ngayLap.compareTo(ngayKetThucQ4) < 0) {
                            tongTienQuy4 += hd.getTongTien();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //////////////////////////////////////////////
                tongDoanhThu = tongTienQuy1 + tongTienQuy2 + tongTienQuy3 + tongTienQuy4;
                DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
                String tongDoanhThuFM = toTheFormat.format(tongDoanhThu) + " VNĐ";
                tvTongHoaDon.setText(toTheFormat.format(tongDoanhThu) + " VNĐ");

                pieChare.setDrawHoleEnabled(true);
                pieChare.setUsePercentValues(true);
                pieChare.setEntryLabelTextSize(12);
                pieChare.setEntryLabelColor(Color.BLACK);
                pieChare.setCenterText("Tổng Doanh Thu\n" + tongDoanhThuFM);
                pieChare.setCenterTextSize(18);
                pieChare.getDescription().setEnabled(false);

                Legend l = pieChare.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(false);
                l.setEnabled(true);
                //////////////////////////////
                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(tongTienQuy1, "Quý 1"));
                entries.add(new PieEntry(tongTienQuy2, "Quý 2"));
                entries.add(new PieEntry(tongTienQuy3, "Quý 3"));
                entries.add(new PieEntry(tongTienQuy4, "Quý 4"));

                ArrayList<Integer> colors = new ArrayList<>();
                for (int color : ColorTemplate.MATERIAL_COLORS) {
                    colors.add(color);
                }

                for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                    colors.add(color);
                }

                PieDataSet dataSet = new PieDataSet(entries, "Thống kê năm " + namHienTai);
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                data.setDrawValues(true);
                data.setValueFormatter(new PercentFormatter(pieChare));
                data.setValueTextSize(12f);
                data.setValueTextColor(Color.BLACK);

                pieChare.setData(data);
                pieChare.invalidate();

                pieChare.animateY(1400, Easing.EaseInOutQuad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void bieuDoCot() {
        StaticConfig.mPhieuHoaDon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float tongTienThang1 = 0;
                float tongTienThang2 = 0;
                float tongTienThang3 = 0;
                float tongTienThang4 = 0;
                float tongTienThang5 = 0;
                float tongTienThang6 = 0;
                float tongTienThang7 = 0;
                float tongTienThang8 = 0;
                float tongTienThang9 = 0;
                float tongTienThang10 = 0;
                float tongTienThang11 = 0;
                float tongTienThang12 = 0;
                float tongDoanhThu = 0;
                String namHienTai = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
                String namTiepTheo = (Integer.parseInt(namHienTai) + 1) + "";
                for (DataSnapshot ds : snapshot.getChildren()) {
                    PhieuHoaDon hd = ds.getValue(PhieuHoaDon.class);
                    try {
                        Date ngayLap = new SimpleDateFormat("dd/MM/yyyy").parse(hd.getNgayLap());
                        Date ngayBatDauT1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + namHienTai);
                        Date ngayKetThucT1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/02/" + namHienTai);
                        Date ngayBatDauT2 = new SimpleDateFormat("dd/MM/yyyy").parse("01/02/" + namHienTai);
                        Date ngayKetThucT2 = new SimpleDateFormat("dd/MM/yyyy").parse("01/03/" + namHienTai);
                        Date ngayBatDauT3 = new SimpleDateFormat("dd/MM/yyyy").parse("01/03/" + namHienTai);
                        Date ngayKetThucT3 = new SimpleDateFormat("dd/MM/yyyy").parse("01/04/" + namHienTai);
                        Date ngayBatDauT4 = new SimpleDateFormat("dd/MM/yyyy").parse("01/04/" + namHienTai);
                        Date ngayKetThucT4 = new SimpleDateFormat("dd/MM/yyyy").parse("01/05/" + namHienTai);
                        Date ngayBatDauT5 = new SimpleDateFormat("dd/MM/yyyy").parse("01/05/" + namHienTai);
                        Date ngayKetThucT5 = new SimpleDateFormat("dd/MM/yyyy").parse("01/06/" + namHienTai);
                        Date ngayBatDauT6 = new SimpleDateFormat("dd/MM/yyyy").parse("01/06/" + namHienTai);
                        Date ngayKetThucT6 = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/" + namHienTai);
                        Date ngayBatDauT7 = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/" + namHienTai);
                        Date ngayKetThucT7 = new SimpleDateFormat("dd/MM/yyyy").parse("01/08/" + namHienTai);
                        Date ngayBatDauT8 = new SimpleDateFormat("dd/MM/yyyy").parse("01/08/" + namHienTai);
                        Date ngayKetThucT8 = new SimpleDateFormat("dd/MM/yyyy").parse("01/09/" + namHienTai);
                        Date ngayBatDauT9 = new SimpleDateFormat("dd/MM/yyyy").parse("01/09/" + namHienTai);
                        Date ngayKetThucT9 = new SimpleDateFormat("dd/MM/yyyy").parse("01/10/" + namHienTai);
                        Date ngayBatDauT10 = new SimpleDateFormat("dd/MM/yyyy").parse("01/10/" + namHienTai);
                        Date ngayKetThucT10 = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/" + namHienTai);
                        Date ngayBatDauT11 = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/" + namHienTai);
                        Date ngayKetThucT11 = new SimpleDateFormat("dd/MM/yyyy").parse("01/12/" + namHienTai);
                        Date ngayBatDauT12 = new SimpleDateFormat("dd/MM/yyyy").parse("01/12/" + namHienTai);
                        Date ngayKetThucT12 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + namTiepTheo);

                        if (ngayLap.compareTo(ngayBatDauT1) >= 0 && ngayLap.compareTo(ngayKetThucT1) < 0) {
                            tongTienThang1 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT2) >= 0 && ngayLap.compareTo(ngayKetThucT2) < 0) {
                            tongTienThang2 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT3) >= 0 && ngayLap.compareTo(ngayKetThucT3) < 0) {
                            tongTienThang3 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT4) >= 0 && ngayLap.compareTo(ngayKetThucT4) < 0) {
                            tongTienThang4 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT5) >= 0 && ngayLap.compareTo(ngayKetThucT5) < 0) {
                            tongTienThang5 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT6) >= 0 && ngayLap.compareTo(ngayKetThucT6) < 0) {
                            tongTienThang6 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT7) >= 0 && ngayLap.compareTo(ngayKetThucT7) < 0) {
                            tongTienThang7 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT8) >= 0 && ngayLap.compareTo(ngayKetThucT8) < 0) {
                            tongTienThang8 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT9) >= 0 && ngayLap.compareTo(ngayKetThucT9) < 0) {
                            tongTienThang9 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT10) >= 0 && ngayLap.compareTo(ngayKetThucT10) < 0) {
                            tongTienThang10 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT11) >= 0 && ngayLap.compareTo(ngayKetThucT11) < 0) {
                            tongTienThang11 += hd.getTongTien();
                        }
                        if (ngayLap.compareTo(ngayBatDauT12) >= 0 && ngayLap.compareTo(ngayKetThucT12) < 0) {
                            tongTienThang12 += hd.getTongTien();
                        }
                        ///////////////////////
//                        tongDoanhThu = tongTienThang1 + tongTienThang2 + tongTienThang3 + tongTienThang4 + tongTienThang5 + tongTienThang6+tongTienThang7+tongTienThang8+tongTienThang9
//                        +tongTienThang10+tongTienThang11+tongTienThang12;
//                        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
//                        tvTongHoaDon.setText(toTheFormat.format(tongDoanhThu)+" VNĐ");
                        ////////////
                        ArrayList<BarEntry> barEntries = new ArrayList<>();
                        barEntries.add(new BarEntry(1,tongTienThang1));
                        barEntries.add(new BarEntry(2,tongTienThang2));
                        barEntries.add(new BarEntry(3,tongTienThang3));
                        barEntries.add(new BarEntry(4,tongTienThang4));
                        barEntries.add(new BarEntry(5,tongTienThang5));
                        barEntries.add(new BarEntry(6,tongTienThang6));
                        barEntries.add(new BarEntry(7,tongTienThang7));
                        barEntries.add(new BarEntry(8,tongTienThang8));
                        barEntries.add(new BarEntry(9,tongTienThang9));
                        barEntries.add(new BarEntry(10,tongTienThang10));
                        barEntries.add(new BarEntry(11,tongTienThang11));
                        barEntries.add(new BarEntry(12,tongTienThang12));
                        BarDataSet barDataSet = new BarDataSet(barEntries,"Mẫu 1");
                        BarData barData = new BarData(barDataSet);
                        barChart.setData(barData);
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(12);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        imTroVe = findViewById(R.id.imTrove);
        barChart = findViewById(R.id.barChart);
        pieChare = findViewById(R.id.pieChare);
        tvTongHoaDon = findViewById(R.id.tvTongHoaDon);
        tvNamHienTai = findViewById(R.id.tvNamHienTai);
        tvNamHienTai1 = findViewById(R.id.tvNamHienTai1);
    }
}