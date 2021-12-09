package com.example.doanquanlynhathuoc.Class;

import java.io.Serializable;

public class ItemMuaBanThuoc implements Serializable {
    String maFB,maThuoc,tenThuoc,donViTinh;
    int soLuong,thanhTien, bienDong;

    public ItemMuaBanThuoc() {
    }

    public ItemMuaBanThuoc(String maFB, String maThuoc, String tenThuoc, String donViTinh, int soLuong, int thanhTien,int bienDong) {
        this.maFB = maFB;
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.bienDong = bienDong;
    }

    public int getBienDong() {
        return bienDong;
    }

    public void setBienDong(int bienDong) {
        this.bienDong = bienDong;
    }

    public String getMaFB() {
        return maFB;
    }

    public void setMaFB(String maFB) {
        this.maFB = maFB;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

}
