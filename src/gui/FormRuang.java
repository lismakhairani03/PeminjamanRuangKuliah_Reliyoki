package gui;

import dao.RuangDao;
import exception.AppException;
import model.Ruang;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.util.List;

public class FormRuang extends BaseForm {

    private RuangDao dao;

    private JTextField txtKode;
    private JTextField txtNama;
    private JTextField txtKapasitas;
    private JTextField txtGedungId;

    private GenericTableModel<Ruang> model;
    private JTable tabel;

    private int idTerpilih = 0;

    public FormRuang() {
        super("Data Ruang Kuliah", 750, 450);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {

        // Membuat objek DAO
        dao = new RuangDao();

        // Membuat input
        txtKode = new JTextField();
        txtNama = new JTextField();
        txtKapasitas = new JTextField();
        txtGedungId = new JTextField();

        // Label input
        String[] label = {
                "Kode Ruang",
                "Nama Ruang",
                "Kapasitas",
                "ID Gedung"
        };

        JComponent[] isian = {
                txtKode,
                txtNama,
                txtKapasitas,
                txtGedungId
        };

        // Kolom tabel
        String[] kolom = {
                "ID",
                "Kode Ruang",
                "Nama Ruang",
                "Kapasitas",
                "ID Gedung"
        };

        // Model tabel
        model = new GenericTableModel<>(
                kolom,
                r -> new Object[]{
                        r.getId(),
                        r.getKode(),
                        r.getNama(),
                        r.getKapasitas(),
                        r.getGedungId()
                }
        );

        tabel = new JTable(model);

        tabel.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        // Tombol
        JButton btnBaru = new JButton("Baru");
        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");

        JPanel panelTombol = new JPanel();

        panelTombol.add(btnBaru);
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);

        // Panel input
        JPanel panelAtas = new JPanel(new BorderLayout());

        panelAtas.add(
                panelIsian("Isian Data Ruang", label, isian),
                BorderLayout.CENTER
        );

        panelAtas.add(
                panelTombol,
                BorderLayout.SOUTH
        );

        // Mengatur tampilan form
        setLayout(new BorderLayout(8, 8));

        add(panelAtas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);

        // Aksi tombol
        btnBaru.addActionListener(e -> kosongkan());

        btnSimpan.addActionListener(e -> simpan());

        btnUbah.addActionListener(e -> ubah());

        btnHapus.addActionListener(e -> hapus());

        // Memilih data dari tabel
        tabel.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int baris = tabel.getSelectedRow();

                if (baris >= 0) {

                    Ruang ruang = model.baris(baris);

                    pilihBaris(ruang);
                }
            }
        });
    }

    // Mengambil data dari database
    private void muatTabel() {

        try {

            List<Ruang> daftar = dao.ambilSemua();

            model.isiUlang(daftar);

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Mengosongkan form
    private void kosongkan() {

        idTerpilih = 0;

        txtKode.setText("");
        txtNama.setText("");
        txtKapasitas.setText("");
        txtGedungId.setText("");

        tabel.clearSelection();

        txtKode.requestFocus();
    }

    // Mengisi form dari tabel
    private void pilihBaris(Ruang ruang) {

        idTerpilih = ruang.getId();

        txtKode.setText(ruang.getKode());

        txtNama.setText(ruang.getNama());

        txtKapasitas.setText(
                String.valueOf(ruang.getKapasitas())
        );

        txtGedungId.setText(
                String.valueOf(ruang.getGedungId())
        );
    }

    // Mengubah input menjadi objek Ruang
    private Ruang dariIsian() {

        Ruang ruang = new Ruang();

        ruang.setId(idTerpilih);

        ruang.setKode(
                txtKode.getText().trim()
        );

        ruang.setNama(
                txtNama.getText().trim()
        );

        ruang.setKapasitas(
                Integer.parseInt(txtKapasitas.getText().trim())
        );

        ruang.setGedungId(
                Integer.parseInt(txtGedungId.getText().trim())
        );

        return ruang;
    }

    // Menyimpan data ruang
    private void simpan() {

        try {

            Ruang ruang = dariIsian();

            ruang.setId(0);

            dao.simpan(ruang);

            pesan("Data ruang berhasil disimpan.");

            kosongkan();

            muatTabel();

        } catch (NumberFormatException e) {

            pesan("Kapasitas dan ID Gedung harus berupa angka.");

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Mengubah data ruang
    private void ubah() {

        if (idTerpilih == 0) {

            pesan("Pilih dahulu data ruang pada tabel.");

            return;
        }

        try {

            Ruang ruang = dariIsian();

            dao.ubah(ruang);

            pesan("Data ruang berhasil diubah.");

            kosongkan();

            muatTabel();

        } catch (NumberFormatException e) {

            pesan("Kapasitas dan ID Gedung harus berupa angka.");

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Menghapus data ruang
    private void hapus() {

        if (idTerpilih == 0) {

            pesan("Pilih dahulu data ruang pada tabel.");

            return;
        }

        if (!konfirmasi("Yakin ingin menghapus data ruang ini?")) {

            return;
        }

        try {

            dao.hapus(idTerpilih);

            pesan("Data ruang berhasil dihapus.");

            kosongkan();

            muatTabel();

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Menjalankan form
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            FormRuang form = new FormRuang();

            form.setVisible(true);
        });
    }
}