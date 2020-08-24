package com.example.proyekakhir_khoirulanam.Constructor;

import android.os.Parcel;
import android.os.Parcelable;

public class CekTransaksi implements Parcelable {


    private int id;
    private String nama_hadiah, nama,gambar,kodetransaksi;

    public CekTransaksi(int id, String nama_hadiah,  String nama, String gambar, String kodetransaksi) {
        this.id = id;
        this.nama = nama;
        this.nama_hadiah = nama_hadiah;
        this.gambar= gambar;
        this.kodetransaksi=kodetransaksi;

    }

    protected CekTransaksi(Parcel in) {
        id = in.readInt();
        nama_hadiah = in.readString();
        nama = in.readString();
        gambar = in.readString();
        kodetransaksi = in.readString();
    }

    public static final Creator<CekTransaksi> CREATOR = new Creator<CekTransaksi>() {
        @Override
        public CekTransaksi createFromParcel(Parcel in) {
            return new CekTransaksi(in);
        }

        @Override
        public CekTransaksi[] newArray(int size) {
            return new CekTransaksi[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNama_hadiah() {
        return nama_hadiah;
    }

    public String getNama() {
        return nama;
    }

    public String getGambar(){return gambar;}
    public String getKodetransaksi(){return  kodetransaksi;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_hadiah);
        dest.writeString((nama));
        dest.writeString(gambar);
        dest.writeString(kodetransaksi);
    }
}
