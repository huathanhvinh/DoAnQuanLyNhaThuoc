package com.example.doanquanlynhathuoc.Config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaticConfig {
   public static FirebaseDatabase database = FirebaseDatabase.getInstance();
   //
   public static DatabaseReference mAccount = database.getReference("Account");
   public static DatabaseReference mThuoc = database.getReference("DanhSachThuoc");
   //
   public static String maFB,taiKhoan,trangThai,sdt,ghiChu,matKhau;
   public static int role;


}
