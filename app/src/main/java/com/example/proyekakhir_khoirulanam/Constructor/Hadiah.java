package com.example.proyekakhir_khoirulanam.Constructor;

import android.os.Parcel;
import android.os.Parcelable;

public class Hadiah implements Parcelable {


    private int id;
    private String nama_hadiah, deskripsi, gambar, poin;

    public Hadiah(int id, String nama_hadiah, String gambar, String deskripsi, String poin) {
        this.id = id;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.poin = poin;
        this.nama_hadiah = nama_hadiah;

    }

    protected Hadiah(Parcel in) {
        id = in.readInt();
        nama_hadiah = in.readString();
        deskripsi = in.readString();
        gambar = in.readString();
        poin = in.readString();
    }

    public static final Creator<Hadiah> CREATOR = new Creator<Hadiah>() {
        @Override
        public Hadiah createFromParcel(Parcel in) {
            return new Hadiah(in);
        }

        @Override
        public Hadiah[] newArray(int size) {
            return new Hadiah[size];
        }
    };

    public int getId(){return id;}
    public String getGambar() { return gambar; }
    public String getNama_hadiah() {
        return nama_hadiah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getPoin() {return poin; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_hadiah);
        dest.writeString((deskripsi));
        dest.writeString(gambar);
        dest.writeString(poin);
    }
}
