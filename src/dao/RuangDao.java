package dao;

import db.Koneksi;
import exception.DataAccessException;
import model.Ruang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RuangDao extends BaseDao<Ruang> {

    @Override
    protected String namaTabel() {
        return "ruang";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{
                "kode",
                "nama",
                "kapasitas",
                "gedung_id"
        };
    }

    @Override
    protected void isiParameter(
            PreparedStatement ps, Ruang ruang
    ) throws SQLException {

        ps.setString(1, ruang.getKode());
        ps.setString(2, ruang.getNama());
        ps.setInt(3, ruang.getKapasitas());
        ps.setInt(4, ruang.getGedungId());
    }

    @Override
    protected Ruang dariBaris(ResultSet rs)
            throws SQLException {

        Ruang ruang = new Ruang();

        ruang.setId(rs.getInt("id"));
        ruang.setKode(rs.getString("kode"));
        ruang.setNama(rs.getString("nama"));
        ruang.setKapasitas(rs.getInt("kapasitas"));
        ruang.setGedungId(rs.getInt("gedung_id"));

        return ruang;
    }

    // Mencari ruang berdasarkan kode
    public Ruang cariKode(String kode)
            throws DataAccessException {

        String sql = "SELECT * FROM ruang WHERE kode = ?";

        try (Connection conn = Koneksi.buka();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kode);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return dariBaris(rs);
                }

                return null;
            }

        } catch (SQLException e) {

            throw new DataAccessException(
                    "Gagal mencari ruang berdasarkan kode", e
            );
        }
    }
}