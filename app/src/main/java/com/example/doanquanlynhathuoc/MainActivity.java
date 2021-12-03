package com.example.doanquanlynhathuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.doanquanlynhathuoc.Class.Account;
import com.example.doanquanlynhathuoc.Class.NhanVien;
import com.example.doanquanlynhathuoc.Config.StaticConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
////Account
//        String key = StaticConfig.mAccount.push().getKey();
//        Account ac = new Account(key, "thanhvinh", "123456", "0933123001", "dangSuDung", 1, "");
//        StaticConfig.mAccount.child(key).setValue(ac);
//
//        String key1 = StaticConfig.mAccount.push().getKey();
//        Account ac1 = new Account(key1, "duyhuy", "123456", "0933123002", "dangSuDung", 2, "");
//        StaticConfig.mAccount.child(key1).setValue(ac1);
//
//        String key2 = StaticConfig.mAccount.push().getKey();
//        Account ac2 = new Account(key2, "thaiquoc", "123456", "0933123003", "dangSuDung", 3, "");
//        StaticConfig.mAccount.child(key2).setValue(ac2);
//
//        String key3 = StaticConfig.mAccount.push().getKey();
//        Account ac3 = new Account(key3, "dinhtho", "123456", "0933123004", "khoa", 3, "Đã nghỉ việc");
//        StaticConfig.mAccount.child(key3).setValue(ac3);
//
////Nhanvien
//        String key5 = StaticConfig.mNhanVien.push().getKey();
//        NhanVien ac5 = new NhanVien(key5,1,1,"1","1","0933123001","1","1");
//        StaticConfig.mNhanVien.child(key5).setValue(ac5);
//
//        String key6 = StaticConfig.mNhanVien.push().getKey();
//        NhanVien ac6 = new NhanVien(key6,2,2,"2","2","0933123002","2","2");
//        StaticConfig.mNhanVien.child(key6).setValue(ac6);
//
//        String key7 = StaticConfig.mNhanVien.push().getKey();
//        NhanVien ac7 = new NhanVien(key7,3,3,"3","3","0933123003","3","3");
//        StaticConfig.mNhanVien.child(key7).setValue(ac7);
//
//        String key8 = StaticConfig.mNhanVien.push().getKey();
//        NhanVien ac8 = new NhanVien(key8,4,3,"4","4","0933123004","4","4");
//        StaticConfig.mNhanVien.child(key8).setValue(ac8);

        startActivity(new Intent(getApplicationContext(), AD_ThemNhanVien.class));
    }
}