package dao;

import model.Gedung;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GedungDao extends BaseDao<Gedung> {

    @Override
    protected String namaTabel() {
        return "gedung";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{"kode", "nama", "lokasi"};
    }

    @Override
    protected void isiParameter(
            PreparedStatement ps, Gedung gedung
    ) throws SQLException {

        ps.setString(1, gedung.getKode());
        ps.setString(2, gedung.getNama());
        ps.setString(3, gedung.getLokasi());
    }

    @Override
    protected Gedung dariBaris(ResultSet r)
            throws SQLException {

        Gedung gedung = new Gedung();

        gedung.setId(r.getInt("id"));
        gedung.setKode(r.getString("kode"));
        gedung.setNama(r.getString("nama"));
        gedung.setLokasi(r.getString("lokasi"));

        return gedung;
    }
}