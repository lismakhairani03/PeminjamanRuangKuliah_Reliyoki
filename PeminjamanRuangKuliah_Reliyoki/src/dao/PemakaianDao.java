package dao;

import model.Pemakaian;
import exception.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class PemakaianDao extends BaseDao<Pemakaian> {

    @Override
    protected String namaTabel() {
        return "pemakaian";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{
                "ruang_id",
                "dosen_id",
                "tanggal",
                "jam_mulai",
                "jam_selesai",
                "keperluan"
        };
    }

    @Override
    protected void isiParameter(
            PreparedStatement ps, Pemakaian objek
    ) throws SQLException {

        ps.setInt(1, objek.getRuangId());
        ps.setInt(2, objek.getDosenId());
        ps.setDate(3, Date.valueOf(objek.getTanggal()));
        ps.setTime(4, Time.valueOf(objek.getJamMulai() + ":00"));
        ps.setTime(5, Time.valueOf(objek.getJamSelesai() + ":00"));
        ps.setString(6, objek.getKeperluan());
    }

    @Override
    protected Pemakaian dariBaris(ResultSet r)
            throws SQLException {

        Pemakaian p = new Pemakaian();

        p.setId(r.getInt("id"));
        p.setRuangId(r.getInt("ruang_id"));
        p.setDosenId(r.getInt("dosen_id"));
        p.setTanggal(r.getDate("tanggal").toString());
        p.setJamMulai(
                r.getTime("jam_mulai").toString().substring(0, 5)
        );
        p.setJamSelesai(
                r.getTime("jam_selesai").toString().substring(0, 5)
        );
        p.setKeperluan(r.getString("keperluan"));

        return p;
    }

    public List<Pemakaian> cariKeperluan(String keperluan)
            throws DataAccessException {

        return cariDengan("keperluan", keperluan);
    }
}