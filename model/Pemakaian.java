package model;

import exception.ValidationException;

/** MODUL TRANSAKSI: Pemakaian. Pemilik: Lisma Khairani (Ketua Tim). */
public class Pemakaian extends BaseEntity {

    private int ruangId;
    private int dosenId;
    private String tanggal;
    private String jamMulai;
    private String jamSelesai;
    private String keperluan;

    public Pemakaian() {
    }

    public Pemakaian(int ruangId, int dosenId, String tanggal,
                     String jamMulai, String jamSelesai, String keperluan) {
        this.ruangId = ruangId;
        this.dosenId = dosenId;
        this.tanggal = tanggal;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.keperluan = keperluan;
    }

    public int getRuangId() { return ruangId; }
    public void setRuangId(int ruangId) { this.ruangId = ruangId; }

    public int getDosenId() { return dosenId; }
    public void setDosenId(int dosenId) { this.dosenId = dosenId; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getJamMulai() { return jamMulai; }
    public void setJamMulai(String jamMulai) { this.jamMulai = jamMulai; }

    public String getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(String jamSelesai) { this.jamSelesai = jamSelesai; }

    public String getKeperluan() { return keperluan; }
    public void setKeperluan(String keperluan) { this.keperluan = keperluan; }

    @Override
    public void validasi() throws ValidationException {
        if (ruangId <= 0) {
            throw new ValidationException("ruang", "harus dipilih");
        }
        if (dosenId <= 0) {
            throw new ValidationException("dosen", "harus dipilih");
        }
        if (tanggal == null || tanggal.trim().isEmpty()) {
            throw new ValidationException("tanggal", "tidak boleh kosong");
        }
        if (!tanggal.trim().matches("\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])")) {
            throw new ValidationException("tanggal", "harus berformat tahun-bulan-tanggal, contoh 2026-11-10");
        }
        if (jamMulai == null || jamMulai.trim().isEmpty()) {
            throw new ValidationException("jam mulai", "tidak boleh kosong");
        }
        if (!jamMulai.trim().matches("([01]\\d|2[0-3]):[0-5]\\d")) {
            throw new ValidationException("jam mulai", "harus berformat jam:menit, contoh 08:00");
        }
        if (jamSelesai == null || jamSelesai.trim().isEmpty()) {
            throw new ValidationException("jam selesai", "tidak boleh kosong");
        }
        if (!jamSelesai.trim().matches("([01]\\d|2[0-3]):[0-5]\\d")) {
            throw new ValidationException("jam selesai", "harus berformat jam:menit, contoh 10:00");
        }
        if (jamSelesai.trim().compareTo(jamMulai.trim()) <= 0) {
            throw new ValidationException("jam selesai", "harus lebih akhir dari jam mulai");
        }
        if (keperluan == null || keperluan.trim().isEmpty()) {
            throw new ValidationException("keperluan", "tidak boleh kosong");
        }
    }

    @Override
    public String label() {
        return tanggal + " " + jamMulai + "-" + jamSelesai + " (ruang " + ruangId + ")";
    }
}