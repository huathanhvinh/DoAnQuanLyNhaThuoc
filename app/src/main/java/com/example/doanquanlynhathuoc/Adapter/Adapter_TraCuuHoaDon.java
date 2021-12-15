package com.example.doanquanlynhathuoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlynhathuoc.Class.PhieuHoaDon;
import com.example.doanquanlynhathuoc.Class.PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_TraCuuHoaDon extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<PhieuHoaDon> arrPhieuHoaDon;
    public Adapter_TraCuuHoaDon(@NonNull Context context, int resource, ArrayList<PhieuHoaDon> arrPhieuHoaDon) {
        super(context, resource, arrPhieuHoaDon);
        this.context = context;
        this.resource = resource;
        this.arrPhieuHoaDon = arrPhieuHoaDon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView maPhieu = convertView.findViewById(R.id.tvMaPhieu3);
        TextView ngayLap = convertView.findViewById(R.id.tvNgayLap3);
        TextView tenNhanVien = convertView.findViewById(R.id.tvNhanVienLap3);
        TextView tongTien = convertView.findViewById(R.id.tvTongTien3);

        PhieuHoaDon phieuMua = arrPhieuHoaDon.get(position);

        maPhieu.setText("Mã hóa đơn: "+phieuMua.getMaPhieu());
        ngayLap.setText("Ngày lập: "+phieuMua.getNgayLap());
        tenNhanVien.setText("Nhân viên lập: "+phieuMua.getNhanVienLap());
        int tongTienChuaFM = phieuMua.getTongTien();
        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
        tongTien.setText("Tổng tiền: "+toTheFormat.format(tongTienChuaFM));

        return convertView;
    }

    @Override
    public int getCount() {
        return arrPhieuHoaDon.size();
    }
}
