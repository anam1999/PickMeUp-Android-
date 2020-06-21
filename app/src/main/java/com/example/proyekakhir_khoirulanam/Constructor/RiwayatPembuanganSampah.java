package com.example.proyekakhir_khoirulanam.Constructor;

import android.os.Parcel;
import android.os.Parcelable;

public class RiwayatPembuanganSampah implements Parcelable {


    private int id;
    private String nama_lokasi, kode_reward, nilai,status;

    public RiwayatPembuanganSampah (int id, String nama_lokasi,  String kode_reward, String nilai, String status) {
        this.id = id;
        this.nama_lokasi = nama_lokasi;
        this.kode_reward = kode_reward;
        this.nilai = nilai;
        this.status= status;

    }

    protected RiwayatPembuanganSampah(Parcel in) {
        id = in.readInt();
        nama_lokasi = in.readString();
        kode_reward = in.readString();
        nilai = in.readString();
        status = in.readString();
    }

    public static final Creator<RiwayatPembuanganSampah> CREATOR = new Creator<RiwayatPembuanganSampah>() {
        @Override
        public RiwayatPembuanganSampah createFromParcel(Parcel in) {
            return new RiwayatPembuanganSampah(in);
        }

        @Override
        public RiwayatPembuanganSampah[] newArray(int size) {
            return new RiwayatPembuanganSampah[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNama_lokasi() {
        return nama_lokasi;
    }

    public String getKode_reward() {
        return kode_reward;
    }

    public String getNilai() {
        return nilai;
    }
    public String getStatus(){return status;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_lokasi);
        dest.writeString((kode_reward));
        dest.writeString(nilai);
        dest.writeString(status);
    }
}
