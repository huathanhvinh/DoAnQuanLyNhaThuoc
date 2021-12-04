package com.example.doanquanlynhathuoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlynhathuoc.Class.NhanVien;
import com.example.doanquanlynhathuoc.R;

import java.util.ArrayList;

public class Adapter_NhanVien extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<NhanVien> arrNhanVien;

    public Adapter_NhanVien(@NonNull Context context, int resource, ArrayList<NhanVien> arrNhanVien) {
        super(context, resource, arrNhanVien);
        this.context = context;
        this.resource = resource;
        this.arrNhanVien = arrNhanVien;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView maNV = convertView.findViewById(R.id.tvMaNV);
        TextView tvChucVu = convertView.findViewById(R.id.tvChucVu);
        TextView tenNV = convertView.findViewById(R.id.tvtenNhanVien);
        TextView tvSDT = convertView.findViewById(R.id.tvSDTNV);
        Button btnChiTiet = convertView.findViewById(R.id.btnChiTiet);

        NhanVien nv = arrNhanVien.get(position);

        maNV.setText(nv.getMaNV());
        tenNV.setText(nv.getTenNV());
        tvSDT.setText(nv.getSdt());

        if(nv.getRole() == 1)
            tvChucVu.setText("Quản lý");
        else if(nv.getRole() == 2)
            tvChucVu.setText("NV kho");
        else
            tvChucVu.setText("NV bán hàng");

        return convertView;

    }

    @Override
    public int getCount() {
        return arrNhanVien.size();
    }
}
