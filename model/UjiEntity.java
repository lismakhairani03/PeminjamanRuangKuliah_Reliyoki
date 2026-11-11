package uji;

import exception.ValidationException;
import Model.Dosen;

public class UjiEntity {
    public static void main(String[] args) {

        // Membuat objek Dosen
        Dosen dosen = new Dosen(
                "198765432109876543",
                "Andi",
                "Rekayasa Perangkat Lunak"
        );

        // Menguji data dosen
        try {
            dosen.validasi();

            System.out.println("=== DATA DOSEN ===");
            System.out.println("NIP           : " + dosen.getNip());
            System.out.println("Nama          : " + dosen.getNama());
            System.out.println("Program Studi : " + dosen.getProgramStudi());
            System.out.println("Label         : " + dosen.label());
            System.out.println("Status        : Data dosen valid");

        } catch (ValidationException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}
