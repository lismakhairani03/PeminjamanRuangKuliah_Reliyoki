package model;

import exception.ValidationException;

// MODUL MASTER 2
public class Dosen extends BaseEntity {

    private String nip;
    private String nama;
    private String programStudi;

    public Dosen() {
    }

    public Dosen(String nip, String nama, String programStudi) {
        this.nip = nip;
        this.nama = nama;
        this.programStudi = programStudi;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProgramStudi() {
        return programStudi;
    }

    public void setProgramStudi(String programStudi) {
        this.programStudi = programStudi;
    }

    @Override
    public void validasi() throws ValidationException {

        if (nip == null || nip.trim().isEmpty()) {
            throw new ValidationException("NIP", "tidak boleh kosong");
        }

        if (!nip.matches("\\d{18}")) {
            throw new ValidationException("NIP", "harus 18 angka");
        }

        if (nama == null || nama.trim().isEmpty()) {
            throw new ValidationException("Nama", "tidak boleh kosong");
        }

        if (programStudi == null || programStudi.trim().isEmpty()) {
            throw new ValidationException(
                    "Program studi", "tidak boleh kosong"
            );
        }
    }

    @Override
    public String label() {
        return nama + " (" + nip + ")";
    }
}
