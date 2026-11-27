package uji;

import dao.RuangDao;
import model.Ruang;
import exception.DataAccessException;

public class TesRuangDao {

    public static void main(String[] args) {

        RuangDao ruangDao = new RuangDao();

        try {

            Ruang ruang = ruangDao.cariKode("R001");

            if (ruang != null) {

                System.out.println("Data ruang ditemukan!");
                System.out.println("ID        : " + ruang.getId());
                System.out.println("Kode      : " + ruang.getKode());
                System.out.println("Nama      : " + ruang.getNama());
                System.out.println("Kapasitas : " + ruang.getKapasitas());
                System.out.println("Gedung ID : " + ruang.getGedungId());

            } else {

                System.out.println("Data ruang tidak ditemukan!");

            }

        } catch (DataAccessException e) {

            System.out.println("Terjadi kesalahan: " + e.getMessage());

        }
    }
}