package com.example.doanquanlynhathuoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlynhathuoc.Class.ItemKhoThuoc;
import com.example.doanquanlynhathuoc.Class.NhanVien;
import com.example.doanquanlynhathuoc.R;

import java.util.ArrayList;

public class Adapter_KhoThuoc extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ItemKhoThuoc> arrThuoc;
    public Adapter_KhoThuoc(@NonNull Context context, int resource, ArrayList<ItemKhoThuoc> arrThuoc) {
        super(context, resource,arrThuoc);
        this.context = context;
        this.resource = resource;
        this.arrThuoc = arrThuoc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView maThuoc = convertView.findViewById(R.id.tvMaThuoc);
        TextView tenThuoc = convertView.findViewById(R.id.tvTenThuoc);
        TextView soLuong = convertView.findViewById(R.id.tvSoLuong);

        ItemKhoThuoc item = arrThuoc.get(position);

        maThuoc.setText(item.getMaThuoc());
        tenThuoc.setText(item.getTenThuoc());
        soLuong.setText(item.getSoLuong()+"");

        return convertView;
    }

    @Override
    public int getCount() {
        return arrThuoc.size();
    }
}
