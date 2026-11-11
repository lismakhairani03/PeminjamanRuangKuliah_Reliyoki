package model;

import exception.ValidationException;

public class Penumpang extends BaseEntity {

    private String NIK;
    private String nama;
    private String telepon;
    private String alamat;

    public Penumpang() {
    }

    public Penumpang(String nik, String nama, String telepon, String alamat) {
        this.NIK = nik;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
    }

    public String getNik() { return NIK; }
    public void setNik(String nik) { this.NIK = nik; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    @Override
    public void validasi() throws ValidationException {
        // Percobaan 3 (urutan lama):
        // - NIK langsung di-trim tanpa dicek null
        // - NIK kosong tidak dicek terpisah, hanya dicek panjangnya
        // - baris di bawah ini sengaja berada di baris 37
        if (NIK.trim().length() != 16) {
            throw new ValidationException("NIK tidak sah", "Isian NIK tidak sah: harus 16 angka");
        }
        if (nama == null || nama.trim().isEmpty()) {
            throw new ValidationException("Nama kosong", "Isian nama tidak sah: tidak boleh kosong");
        }
    }

    @Override
    public String label() {
        return nama + " (" + NIK + ")";
    }
}