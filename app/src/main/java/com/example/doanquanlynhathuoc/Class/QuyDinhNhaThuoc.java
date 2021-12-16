package com.example.doanquanlynhathuoc.Class;

import java.io.Serializable;

public class QuyDinhNhaThuoc implements Serializable {
    int stt;
    String maFB,noidung;

    public QuyDinhNhaThuoc() {
    }

    public QuyDinhNhaThuoc(String maFB,int stt, String noidung) {
        this.stt = stt;
        this.noidung = noidung;
        this.maFB = maFB;
    }

    public String getMaFB() {
        return maFB;
    }

    public void setMaFB(String maFB) {
        this.maFB = maFB;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
