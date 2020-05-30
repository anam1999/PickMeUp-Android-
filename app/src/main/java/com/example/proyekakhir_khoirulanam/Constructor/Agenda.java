package com.example.proyekakhir_khoirulanam.Constructor;
import android.os.Parcel;
import android.os.Parcelable;

public class Agenda  implements Parcelable {
    private int id;
    private String nama_agenda,gambar,keterangan;

    public Agenda(int id, String nama_agenda, String keterangan , String gambar) {
        this.id = id;
        this.gambar = gambar;
        this.nama_agenda = nama_agenda;
        this.keterangan=keterangan;
    }

    protected Agenda(Parcel in) {
        id = in.readInt();
        nama_agenda = in.readString();
        gambar = in.readString();
        keterangan=in.readString();
    }

    public static final Creator<Agenda> CREATOR = new Creator<Agenda>() {
        @Override
        public Agenda createFromParcel(Parcel in) {
            return new Agenda(in);
        }

        @Override
        public Agenda[] newArray(int size) {return new Agenda[size]; }
    };
    public int getId(){return id;}
    public String getGambar() {
        return gambar;
    }
    public String getNama_agenda() {
        return nama_agenda;
    }
    public String getKeterangan(){
        return keterangan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama_agenda);
        dest.writeString(gambar);
        dest.writeString(keterangan);
    }
}