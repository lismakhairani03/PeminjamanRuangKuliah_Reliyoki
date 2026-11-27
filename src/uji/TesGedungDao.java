package uji;

import dao.GedungDao;
import model.Gedung;
import exception.DataAccessException;

import java.util.List;

public class TesGedungDao {

    public static void main(String[] args) {

        GedungDao gedungDao = new GedungDao();

        try {

            List<Gedung> daftarGedung = gedungDao.ambilSemua();

            if (!daftarGedung.isEmpty()) {

                System.out.println("Data gedung berhasil ditemukan!");

                for (Gedung gedung : daftarGedung) {

                    System.out.println("----------------------");
                    System.out.println("ID     : " + gedung.getId());
                    System.out.println("Kode   : " + gedung.getKode());
                    System.out.println("Nama   : " + gedung.getNama());
                    System.out.println("Lokasi : " + gedung.getLokasi());

                }

            } else {

                System.out.println("Data gedung tidak ditemukan!");

            }

        } catch (DataAccessException e) {

            System.out.println("Terjadi kesalahan: " + e.getMessage());

        }
    }
}