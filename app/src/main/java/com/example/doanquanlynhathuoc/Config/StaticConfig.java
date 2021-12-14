package com.example.doanquanlynhathuoc.Config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaticConfig {
   public static FirebaseDatabase database = FirebaseDatabase.getInstance();
   //
   public static DatabaseReference mAccount = database.getReference("Account");
   public static DatabaseReference mThuoc = database.getReference("DanhSachThuoc");
   public static DatabaseReference mKhachHang = database.getReference("KhachHang");
   public static DatabaseReference mNhanVien = database.getReference("DanhSachNhanVien");
   public static DatabaseReference mItemMuaBanThuoc = database.getReference("DanhSachItemMuaBanThuoc");
   public static DatabaseReference mPhieuMuaThuoc = database.getReference("DanhSachPhieuMuaThuoc");
   public static DatabaseReference mPhieuHoaDon = database.getReference("DanhSachHoaDon");
   public static DatabaseReference mKhoThuoc = database.getReference("KhoThuoc");
   //
   public static String maFB,taiKhoan,trangThai,sdt="0933123003",ghiChu,matKhau;
   public static int role;


}
