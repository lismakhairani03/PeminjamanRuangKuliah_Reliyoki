package uji;

import db.Koneksi;
import java.sql.Connection;
import java.sql.SQLException;

public class TesKoneksi {

    public static void main(String[] args) {

        try (Connection koneksi = Koneksi.buka()) {

            System.out.println("Koneksi database berhasil!");

        } catch (SQLException e) {

            System.out.println("Koneksi database gagal!");
            System.out.println("Penyebab: " + e.getMessage());

            e.printStackTrace();
        }
    }
}