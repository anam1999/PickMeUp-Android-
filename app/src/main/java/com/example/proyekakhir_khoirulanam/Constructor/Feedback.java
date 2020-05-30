package com.example.proyekakhir_khoirulanam.Constructor;
import android.os.Parcel;
import android.os.Parcelable;

public class Feedback implements Parcelable {
    private int id;
    private String nama,komentar,gambar,username;

    public Feedback(int id, String nama, String komentar , String gambar,String username ) {
        this.id = id;
        this.gambar = gambar;
        this.nama = nama;
        this.komentar=komentar;
        this.username = username;
    }

    protected Feedback(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        gambar = in.readString();
        komentar=in.readString();
        username=in.readString();
    }

    public static final Creator<Feedback> CREATOR = new Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);}

        @Override
        public Feedback[] newArray(int size) {return new Feedback[size]; }
    };

    public String getGambar() {
        return gambar;
    }
    public String getNama() {
        return nama;
    }
    public String getKomentar(){
        return komentar;
    }
    public String getUsername(){
        return username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeString(gambar);
        dest.writeString(komentar);
        dest.writeString(username);
    }
}
