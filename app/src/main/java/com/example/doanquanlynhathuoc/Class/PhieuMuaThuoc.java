package com.example.doanquanlynhathuoc.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class PhieuMuaThuoc implements Serializable {
    String maFB,maPhieu,ngayLap,nhanVienLap;
    int TongTien;
    ArrayList<ItemMuaBanThuoc> danhSachThuoc;

    public PhieuMuaThuoc() {
    }

    public PhieuMuaThuoc(String maFB, String maPhieu, String ngayLap, String nhanVienLap, int tongTien, ArrayList<ItemMuaBanThuoc> danhSachThuoc) {
        this.maFB = maFB;
        this.maPhieu = maPhieu;
        this.ngayLap = ngayLap;
        this.nhanVienLap = nhanVienLap;
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
