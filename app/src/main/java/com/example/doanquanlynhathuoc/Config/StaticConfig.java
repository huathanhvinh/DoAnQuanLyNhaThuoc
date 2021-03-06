package com.example.doanquanlynhathuoc.Config;

import com.example.doanquanlynhathuoc.Class.KhachHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
   public static DatabaseReference mQuyDinh = database.getReference("QuyDinhNhaThuoc");
   //
   public static String maFB,taiKhoan,trangThai,sdt,ghiChu,matKhau;
   public static int role;

}
