package com.example.proyekakhir_khoirulanam.Constructor;

import android.os.Parcel;
import android.os.Parcelable;


public class Animasi implements Parcelable {
    private int id;
    private String nama_konten,gambar,deskripsi;

    public Animasi(int id, String nama_konten, String deskripsi , String gambar) {
        this.id = id;
        this.gambar = gambar;
        this.nama_konten = nama_konten;
        this.deskripsi=deskripsi;
    }

    protected Animasi(Parcel in) {
        id = in.readInt();
        nama_konten = in.readString();
        gambar = in.readString();
        deskripsi=in.readString();
    }

    public static final Creator<Animasi> CREATOR = new Creator<Animasi>() {
        @Override
        public Animasi createFromParcel(Parcel in) {
            return new Animasi(in);
        }

        @Override
        public Animasi[] newArray(int size) {return new Animasi[size]; }
    };
    public int getId(){return id;}
    public String getGambar() {
        return gambar;
    }
    public String getNama_konten() {
        return nama_konten;
    }
    public String getDeskripsi(){
        return deskripsi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_konten);
        dest.writeString(gambar);
        dest.writeString(deskripsi);
    }
}
