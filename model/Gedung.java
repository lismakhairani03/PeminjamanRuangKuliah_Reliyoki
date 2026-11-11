package Model;

import exception.ValidationException;

// MODUL MASTER 3
public class Gedung extends BaseEntity {

    private String kode;
    private String nama;

    public Gedung() {
    }

    public Gedung(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    @Override
    public void validasi() throws ValidationException {
        if (kode == null || kode.trim().isEmpty()) {
            throw new ValidationException("Kode", "tidak boleh kosong");
        }
        if (nama == null || nama.trim().isEmpty()) {
            throw new ValidationException("Nama", "tidak boleh kosong");
        }
    }

    @Override
    public String label() {
        return nama + " (" + kode + ")";
    }
}
