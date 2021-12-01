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

import com.example.doanquanlynhathuoc.Class.Thuoc;
import com.example.doanquanlynhathuoc.NVK_ThongTinThuoc;
import com.example.doanquanlynhathuoc.R;

import java.util.ArrayList;

public class Adapter_Thuoc extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Thuoc> arrThuoc;

    public Adapter_Thuoc(@NonNull Context context, int resource, ArrayList<Thuoc> arrThuoc) {
        super(context, resource, arrThuoc);
        this.context = context;
        this.resource = resource;
        this.arrThuoc = arrThuoc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView stt = convertView.findViewById(R.id.tvStt);
        TextView tenThuoc = convertView.findViewById(R.id.tvtenThuoc);
        TextView dvt = convertView.findViewById(R.id.tvDonViTinh);
        TextView giaBan = convertView.findViewById(R.id.tvGiaBan);
        Button btnChiTiet = convertView.findViewById(R.id.btnChiTiet);

        Thuoc t = arrThuoc.get(position);
        stt.setText(t.getStt()+"");
        tenThuoc.setText(t.getTenThuoc());
        dvt.setText(t.getDonViTinh()+"");
        giaBan.setText(t.getGiaBan()+"");

        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NVK_ThongTinThuoc.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ThongTinThuoc", t);
                intent.putExtras(bundle);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return arrThuoc.size();
    }

}
