package com.example.proyekakhir_khoirulanam.Hadiah;

public class HitungTukarHadiah {
    public String status = "";

    public double poinSementara;
    public double stoksementara;

    public String hadiah1(double poinsaya, double hadiah1) {
        if (poinsaya >= hadiah1) {
            poinSementara = poinsaya - hadiah1;
            status = "Poin Anda Cukup";
            DetailHadiah2.poinsa = String.valueOf((int)poinSementara);

        } else {
            status = "Poin anda tidak cukup";

        }
        return (status);

    }
    public String jumlahhadiah(double pesanansaya, double jumlah) {
        if (pesanansaya >= jumlah) {
            poinSementara = pesanansaya - jumlah;
            status = "Jumlah  Hadiah ada";
            DetailHadiah2.stokpesan = String.valueOf((int)stoksementara);


        } else {
            status = "Jumlah Hadiah telah habis ";

        }
        return (status);
    }
}
