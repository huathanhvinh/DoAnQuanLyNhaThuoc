package com.example.doanquanlynhathuoc.Class;

import java.io.Serializable;

public class Thuoc implements Serializable {
    int giaBan,hsd;
    String maFB,maThuoc,tenThuoc,donViTinh,noiDung;

    public Thuoc() {
    }

    public Thuoc(int giaBan, int hsd, String maFB, String maThuoc, String tenThuoc, String donViTinh, String noiDung) {
        this.giaBan = giaBan;
        this.hsd = hsd;
        this.maFB = maFB;
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.noiDung = noiDung;
    }
    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getHsd() {
        return hsd;
    }

    public void setHsd(int hsd) {
        this.hsd = hsd;
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

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
