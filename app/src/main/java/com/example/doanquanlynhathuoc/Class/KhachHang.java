package com.example.doanquanlynhathuoc.Class;

import java.io.Serializable;

public class KhachHang implements Serializable {
    int tongTienDaMua, giamGia;
    String maFb, maKH, tenKH, sdt, DiaChi, NamSinh;

    public KhachHang() {
    }

    public KhachHang(String maFb, int tongTienDaMua, int giamGia, String maKH, String tenKH, String sdt, String diaChi, String namSinh) {
        this.tongTienDaMua = tongTienDaMua;
        this.giamGia = giamGia;
        this.maFb = maFb;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
        DiaChi = diaChi;
        NamSinh = namSinh;
    }
    public int getTongTienDaMua() {
        return tongTienDaMua;
    }

    public void setTongTienDaMua(int tongTienDaMua) {
        this.tongTienDaMua = tongTienDaMua;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public String getMaFb() {
        return maFb;
    }

    public void setMaFb(String maFb) {
        this.maFb = maFb;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String namSinh) {
        NamSinh = namSinh;
    }
}
