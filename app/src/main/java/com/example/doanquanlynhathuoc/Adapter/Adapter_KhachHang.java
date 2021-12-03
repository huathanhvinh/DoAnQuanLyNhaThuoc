package com.example.doanquanlynhathuoc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.example.doanquanlynhathuoc.NVBH_ThongTinKhachHang;
import com.example.doanquanlynhathuoc.NVK_ThongTinThuoc;
import com.example.doanquanlynhathuoc.R;

import java.util.ArrayList;

public class Adapter_KhachHang extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<KhachHang> arrKhachHang;

    public Adapter_KhachHang(@NonNull Context context, int resource,ArrayList<KhachHang> arrKhachHang) {
        super(context, resource,arrKhachHang);
        this.context = context;
        this.resource = resource;
        this.arrKhachHang = arrKhachHang;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView stt = convertView.findViewById(R.id.tvStt);
        TextView tenKh = convertView.findViewById(R.id.tvtenKhachHang);
        TextView giamGia = convertView.findViewById(R.id.tvGiamGia);
        TextView sdt = convertView.findViewById(R.id.tvSDT);
        Button btnChitiet = convertView.findViewById(R.id.btnChiTiet);

        KhachHang kh = arrKhachHang.get(position);
        stt.setText(kh.getStt()+"");
        tenKh.setText(kh.getTenKH());
        giamGia.setText(kh.getGiamGia()+" %");
        sdt.setText(kh.getSdt());

        btnChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NVBH_ThongTinKhachHang.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ThongTinKhachHang", kh);
                intent.putExtras(bundle);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return arrKhachHang.size();
    }
}
