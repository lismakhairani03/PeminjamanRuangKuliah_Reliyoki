package dao;

import db.Koneksi;
import exception.DataAccessException;
import exception.ValidationException;
import model.BaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T extends BaseEntity> {

    protected abstract String namaTabel();

    protected abstract String[] kolomData();

    protected abstract void isiParameter(
            PreparedStatement ps, T objek
    ) throws SQLException;

    protected abstract T dariBaris(ResultSet r)
            throws SQLException;

    // Menyimpan data baru
    public void simpan(T objek)
            throws DataAccessException, ValidationException {

        objek.validasi();

        String[] kolom = kolomData();

        StringBuilder sql = new StringBuilder(
                "INSERT INTO " + namaTabel() + " ("
        );

        sql.append(String.join(", ", kolom));
        sql.append(") VALUES (");

        for (int i = 0; i < kolom.length; i++) {
            sql.append("?");
            if (i < kolom.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(")");

        try (Connection koneksi = Koneksi.buka();
             PreparedStatement ps = koneksi.prepareStatement(
                     sql.toString(),
                     Statement.RETURN_GENERATED_KEYS
             )) {

            isiParameter(ps, objek);
            ps.executeUpdate();

            try (ResultSet hasil = ps.getGeneratedKeys()) {
                if (hasil.next()) {
                    objek.setId(hasil.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Gagal menyimpan data", e
            );
        }
    }

    // Mengubah data
    public void ubah(T objek)
            throws DataAccessException, ValidationException {

        objek.validasi();

        String[] kolom = kolomData();

        StringBuilder sql = new StringBuilder(
                "UPDATE " + namaTabel() + " SET "
        );

        for (int i = 0; i < kolom.length; i++) {
            sql.append(kolom[i]).append(" = ?");

            if (i < kolom.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(" WHERE id = ?");

        try (Connection koneksi = Koneksi.buka();
             PreparedStatement ps = koneksi.prepareStatement(
                     sql.toString()
             )) {

            isiParameter(ps, objek);
            ps.setInt(kolom.length + 1, objek.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Gagal mengubah data", e
            );
        }
    }

    // Menghapus data
    public void hapus(int id)
            throws DataAccessException {

        String sql = "DELETE FROM " + namaTabel()
                + " WHERE id = ?";

        try (Connection koneksi = Koneksi.buka();
             PreparedStatement ps = koneksi.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Gagal menghapus data", e
            );
        }
    }

    // Mengambil semua data
    public List<T> ambilSemua()
            throws DataAccessException {

        List<T> daftar = new ArrayList<>();

        String sql = "SELECT * FROM " + namaTabel();

        try (Connection koneksi = Koneksi.buka();
             PreparedStatement ps = koneksi.prepareStatement(sql);
             ResultSet hasil = ps.executeQuery()) {

            while (hasil.next()) {
                daftar.add(dariBaris(hasil));
            }

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Gagal mengambil data", e
            );
        }

        return daftar;
    }

    // Mencari data berdasarkan ID
    public T cariId(int id)
            throws DataAccessException {

        String sql = "SELECT * FROM " + namaTabel()
                + " WHERE id = ?";

        try (Connection koneksi = Koneksi.buka();
             PreparedStatement ps = koneksi.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet hasil = ps.executeQuery()) {
                if (hasil.next()) {
                    return dariBaris(hasil);
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Gagal mencari data", e
            );
        }

        return null;
    }

    // Mencari data berdasarkan kolom tertentu
    public List<T> cariDengan(String kolom, Object nilai)
            throws DataAccessException {

        List<T> daftar = new ArrayList<>();

        String sql = "SELECT * FROM " + namaTabel()
                + " WHERE " + kolom + " = ?";

        try (Connection koneksi = Koneksi.buka();
             PreparedStatement ps = koneksi.prepareStatement(sql)) {

            ps.setObject(1, nilai);

            try (ResultSet hasil = ps.executeQuery()) {
                while (hasil.next()) {
                    daftar.add(dariBaris(hasil));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Gagal mencari data", e
            );
        }

        return daftar;
    }
}