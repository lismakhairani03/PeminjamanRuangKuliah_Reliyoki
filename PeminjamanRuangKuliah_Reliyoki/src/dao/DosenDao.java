package dao;

import model.Dosen;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DosenDao extends BaseDao<Dosen> {

    @Override
    protected String namaTabel() {
        return "dosen";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{
                "nip",
                "nama",
                "program_studi"
        };
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Dosen objek)
            throws SQLException {

        ps.setString(1, objek.getNip());
        ps.setString(2, objek.getNama());
        ps.setString(3, objek.getProgramStudi());
    }

    @Override
    protected Dosen dariBaris(ResultSet r) throws SQLException {

        Dosen dosen = new Dosen();

        dosen.setId(r.getInt("id"));
        dosen.setNip(r.getString("nip"));
        dosen.setNama(r.getString("nama"));
        dosen.setProgramStudi(r.getString("program_studi"));

        return dosen;
    }
}