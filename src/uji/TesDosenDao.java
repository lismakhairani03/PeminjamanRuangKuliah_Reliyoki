package uji;

import dao.DosenDao;
import model.Dosen;
import exception.DataAccessException;

public class TesDosenDao {

    public static void main(String[] args) {

        DosenDao dosenDao = new DosenDao();

        try {

            Dosen dosen = dosenDao.cariDengan(
                    "nip", "123456789012345678"
            ).stream().findFirst().orElse(null);

            if (dosen != null) {

                System.out.println("Data dosen ditemukan!");
                System.out.println("ID            : " + dosen.getId());
                System.out.println("NIP           : " + dosen.getNip());
                System.out.println("Nama          : " + dosen.getNama());
                System.out.println("Program Studi : " + dosen.getProgramStudi());

            } else {

                System.out.println("Data dosen tidak ditemukan!");

            }

        } catch (DataAccessException e) {

            System.out.println("Terjadi kesalahan: " + e.getMessage());

        }
    }
}