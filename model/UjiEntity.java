package ujii;

import exception.ValidationException;
import Model.Gedung;

public class UjiEntity {

    public static void main(String[] args) {
        cobaGedung("lengkap dan benar", new Gedung("GDG-A", "Gedung A"));
        cobaGedung("kode kosong", new Gedung("", "Gedung A"));
        cobaGedung("nama kosong", new Gedung("GDG-A", " "));
        cobaGedung("kode null", new Gedung(null, "Gedung A"));
        cobaGedung("nama null", new Gedung("GDG-A", null));
    }

    private static void cobaGedung(String keterangan, Gedung g) {
        try {
            g.validasi();
            System.out.println("DITERIMA : " + keterangan + " -> " + g.label());
        } catch (ValidationException e) {
            System.out.println("DITOLAK : " + keterangan + " -> " + e.pesanPengguna());
        }
    }
}
