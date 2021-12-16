package com.example.doanquanlynhathuoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlynhathuoc.Class.PhieuMuaThuoc;
import com.example.doanquanlynhathuoc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_TraCuuPhieuMuaThuoc extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<PhieuMuaThuoc> arrPhieuMuaThuoc;
    public Adapter_TraCuuPhieuMuaThuoc(@NonNull Context context, int resource, ArrayList<PhieuMuaThuoc> arrPhieuMuaThuoc) {
        super(context, resource, arrPhieuMuaThuoc);
        this.context = context;
        this.resource = resource;
        this.arrPhieuMuaThuoc = arrPhieuMuaThuoc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView maPhieu = convertView.findViewById(R.id.tvMaPhieu2);
        TextView ngayLap = convertView.findViewById(R.id.tvNgayLap2);
        TextView tenNhanVien = convertView.findViewById(R.id.tvNhanVienLap2);
        TextView tongTien = convertView.findViewById(R.id.tvTongTien2);

        PhieuMuaThuoc phieuMua = arrPhieuMuaThuoc.get(position);

        maPhieu.setText("Mã phiếu: "+phieuMua.getMaPhieu());
        ngayLap.setText("Ngày lập: "+phieuMua.getNgayLap());
        tenNhanVien.setText("Nhân viên lập: "+phieuMua.getNhanVienLap());
        int tongTienChuaFM = phieuMua.getTongTien();
        DecimalFormat toTheFormat = new DecimalFormat("###,###,###.#");
        tongTien.setText("Tổng tiền: "+toTheFormat.format(tongTienChuaFM));

        return convertView;
    }

    @Override
    public int getCount() {
        return arrPhieuMuaThuoc.size();
    }
}
