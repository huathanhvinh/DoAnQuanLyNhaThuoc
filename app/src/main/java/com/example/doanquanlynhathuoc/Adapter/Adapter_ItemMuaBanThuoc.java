package com.example.doanquanlynhathuoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlynhathuoc.Class.ItemMuaBanThuoc;
import com.example.doanquanlynhathuoc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_ItemMuaBanThuoc extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ItemMuaBanThuoc> arrItem;
    public Adapter_ItemMuaBanThuoc(@NonNull Context context, int resource, ArrayList<ItemMuaBanThuoc> arrItem) {
        super(context, resource, arrItem);
        this.context = context;
        this.resource = resource;
        this.arrItem = arrItem;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView dVT = convertView.findViewById(R.id.tvDVT1);
        TextView tenThuoc = convertView.findViewById(R.id.tvTenThuoc1);
        TextView soLuong = convertView.findViewById(R.id.tvSoLuong1);
        TextView thanhTien = convertView.findViewById(R.id.tvThanhTien1);

        ItemMuaBanThuoc item =  arrItem.get(position);

        tenThuoc.setText(item.getTenThuoc());
        dVT.setText(item.getDonViTinh());
        soLuong.setText(item.getSoLuong()+"");
        thanhTien.setText(item.getThanhTien() + "");

        return convertView;
    }

    @Override
    public int getCount() {
        return arrItem.size();
    }
}
