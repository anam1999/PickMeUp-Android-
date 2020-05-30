package com.example.proyekakhir_khoirulanam.Constructor;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaksi implements Parcelable {


    private int id;
    private String nama_hadiah, harga_hadiah, sisapoin,gambar;

    public Transaksi(int id, String nama_hadiah,  String harga_hadiah, String sisapoin, String gambar) {
        this.id = id;
        this.harga_hadiah = harga_hadiah;
        this.sisapoin =sisapoin;
        this.nama_hadiah = nama_hadiah;
        this.gambar= gambar;

    }

    protected Transaksi(Parcel in) {
        id = in.readInt();
        nama_hadiah = in.readString();
        harga_hadiah = in.readString();
        sisapoin = in.readString();
        gambar = in.readString();
    }

    public static final Creator<Transaksi> CREATOR = new Creator<Transaksi>() {
        @Override
        public Transaksi createFromParcel(Parcel in) {
            return new Transaksi(in);
        }

        @Override
        public Transaksi[] newArray(int size) {
            return new Transaksi[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNama_hadiah() {
        return nama_hadiah;
    }

    public String getHarga_hadiah() {
        return harga_hadiah;
    }

    public String getSisapoin() {
        return sisapoin;
    }
    public String getGambar(){return gambar;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_hadiah);
        dest.writeString((harga_hadiah));
        dest.writeString(sisapoin);
        dest.writeString(gambar);
    }
}
