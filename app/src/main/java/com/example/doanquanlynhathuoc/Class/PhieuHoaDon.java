package com.example.doanquanlynhathuoc.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class PhieuHoaDon implements Serializable {
    String maFB,maPhieu,ngayLap,nhanVienLap,sdtKhachHang,tenKhachHang;
    int TongTien;
    ArrayList<ItemMuaBanThuoc> danhSachThuoc;

    public PhieuHoaDon() {
    }

    public PhieuHoaDon(String maFB, String maPhieu, String ngayLap, String nhanVienLap, String sdtKhachHang, String tenKhachHang, int tongTien, ArrayList<ItemMuaBanThuoc> danhSachThuoc) {
        this.maFB = maFB;
        this.maPhieu = maPhieu;
        this.ngayLap = ngayLap;
        this.nhanVienLap = nhanVienLap;
        this.sdtKhachHang = sdtKhachHang;
        this.tenKhachHang = tenKhachHang;
        TongTien = tongTien;
        this.danhSachThuoc = danhSachThuoc;
    }

    public String getMaFB() {
        return maFB;
    }

    public void setMaFB(String maFB) {
        this.maFB = maFB;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getNhanVienLap() {
        return nhanVienLap;
    }

    public void setNhanVienLap(String nhanVienLap) {
        this.nhanVienLap = nhanVienLap;
    }

    public String getSdtKhachHang() {
        return sdtKhachHang;
    }

    public void setSdtKhachHang(String sdtKhachHang) {
        this.sdtKhachHang = sdtKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }

    public ArrayList<ItemMuaBanThuoc> getDanhSachThuoc() {
        return danhSachThuoc;
    }

    public void setDanhSachThuoc(ArrayList<ItemMuaBanThuoc> danhSachThuoc) {
        this.danhSachThuoc = danhSachThuoc;
    }
}
