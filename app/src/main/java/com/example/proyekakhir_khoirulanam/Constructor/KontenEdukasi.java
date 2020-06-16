package com.example.proyekakhir_khoirulanam.Constructor;

import android.os.Parcel;
import android.os.Parcelable;


public class KontenEdukasi implements Parcelable {
    private int id;
    private String nama_konten,gambar,deskripsi;

    public KontenEdukasi(int id, String nama_konten, String deskripsi , String gambar) {
        this.id = id;
        this.gambar = gambar;
        this.nama_konten = nama_konten;
        this.deskripsi=deskripsi;
    }

    protected KontenEdukasi(Parcel in) {
        id = in.readInt();
        nama_konten = in.readString();
        gambar = in.readString();
        deskripsi=in.readString();
    }

    public static final Creator<KontenEdukasi> CREATOR = new Creator<KontenEdukasi>() {
        @Override
        public KontenEdukasi createFromParcel(Parcel in) {
            return new KontenEdukasi(in);
        }

        @Override
        public KontenEdukasi[] newArray(int size) {return new KontenEdukasi[size]; }
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
