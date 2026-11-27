package model;

import exception.ValidationException;

public class Gedung extends BaseEntity {

    private String kode;
    private String nama;
    private String lokasi;

    public Gedung() {
    }

    public Gedung(String kode, String nama, String lokasi) {
        this.kode = kode;
        this.nama = nama;
        this.lokasi = lokasi;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    @Override
    public void validasi() throws ValidationException {

        if (kode == null || kode.trim().isEmpty()) {
            throw new ValidationException(
                    "Kode gedung", "tidak boleh kosong");
        }

        if (nama == null || nama.trim().isEmpty()) {
            throw new ValidationException(
                    "Nama gedung", "tidak boleh kosong");
        }

        if (lokasi == null || lokasi.trim().isEmpty()) {
            throw new ValidationException(
                    "Lokasi", "tidak boleh kosong");
        }
    }

    @Override
    public String label() {
        return nama + " (" + kode + ")";
    }
}