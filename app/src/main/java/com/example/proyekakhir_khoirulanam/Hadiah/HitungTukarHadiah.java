package com.example.proyekakhir_khoirulanam.Hadiah;

public class HitungTukarHadiah {
    public String status = "";

    public double poinSementara;

    public String hadiah1(double poinsaya, double hadiah1) {
        if (poinsaya >= hadiah1) {
            poinSementara = poinsaya - hadiah1;
            status = "Hadiah berhasil ditukar";
            DetailHadiah2.poinsa = String.valueOf((int)poinSementara);

        } else {
            status = "Poin anda tidak cukup";

        }
        return (status);

    }
    public String kondisi(double poinsaya, double hadiah) {
        if (poinsaya >= hadiah) {
            poinSementara = poinsaya - hadiah;
            status = "Hadiah berhasil ditukar";
            DetailHadiah2.poinsa = String.valueOf((int)poinSementara);


        } else {
            status = "Poin anda tidak cukup";

        }
        return (status);
    }
}
