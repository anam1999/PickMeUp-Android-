package com.example.proyekakhir_khoirulanam.Constructor;

import android.os.Parcel;
import android.os.Parcelable;

public class NotifikasiSampah implements Parcelable {
    private int id;
    private String nama_lokasi, gambar, lat,lng,keterangan;

    public NotifikasiSampah(int id, String nama_lokasi, String keterangan, String gambar, String lat,String lng) {
        this.id = id;
        this.gambar = gambar;
        this.nama_lokasi = nama_lokasi;
        this.keterangan = keterangan;
    }

    protected NotifikasiSampah(Parcel in) {
        id = in.readInt();
        nama_lokasi = in.readString();
        gambar = in.readString();
        keterangan = in.readString();
        lat = in.readString();
        lng=in.readString();
    }

    public static final Creator<NotifikasiSampah> CREATOR = new Creator<NotifikasiSampah>() {
        @Override
        public NotifikasiSampah createFromParcel(Parcel in) {
            return new NotifikasiSampah(in);
        }

        @Override
        public NotifikasiSampah[] newArray(int size) {
            return new NotifikasiSampah[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getGambar() {
        return gambar;
    }

    public String getNama_lokasi() {
        return nama_lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }
    public String getLat() {
        return lat;
    }
    public String getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_lokasi);
        dest.writeString(gambar);
        dest.writeString(keterangan);
        dest.writeString(lat);
        dest.writeString(lng);
    }
}
