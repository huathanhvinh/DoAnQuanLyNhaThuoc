package com.example.doanquanlynhathuoc.Class;

import java.io.Serializable;

public class NhanVien implements Serializable {
    int stt,role;
    String maFb, maNV, tenNV, sdt, DiaChi, NamSinh;

    public NhanVien() {
    }

    public NhanVien(String maFb, int stt, int role, String maNV, String tenNV, String sdt, String diaChi, String namSinh) {
        this.stt = stt;
        this.role = role;
        this.maFb = maFb;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.sdt = sdt;
        DiaChi = diaChi;
        NamSinh = namSinh;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getMaFb() {
        return maFb;
    }

    public void setMaFb(String maFb) {
        this.maFb = maFb;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
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