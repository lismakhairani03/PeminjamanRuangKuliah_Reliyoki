package gui;

import dao.GedungDao;
import exception.AppException;
import model.Gedung;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.util.List;

public class FormGedung extends BaseForm {

    private final GedungDao dao = new GedungDao();

    private JTextField txtKode;
    private JTextField txtNama;
    private JTextField txtLokasi;
    private JTextField txtCari;

    private GenericTableModel<Gedung> model;
    private JTable tabel;

    private int idTerpilih = 0;

    public FormGedung() {
        super("Data Gedung", 750, 500);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {

        txtKode = new JTextField(15);
        txtNama = new JTextField(15);
        txtLokasi = new JTextField(15);

        String[] label = {
                "Kode Gedung",
                "Nama Gedung",
                "Lokasi"
        };

        JComponent[] isian = {
                txtKode,
                txtNama,
                txtLokasi
        };

        String[] kolom = {
                "ID",
                "Kode",
                "Nama",
                "Lokasi"
        };

        model = new GenericTableModel<>(
                kolom,
                g -> new Object[]{
                        g.getId(),
                        g.getKode(),
                        g.getNama(),
                        g.getLokasi()
                }
        );

        tabel = new JTable(model);

        tabel.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabel.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()
                    && tabel.getSelectedRow() >= 0) {

                int baris = tabel.getSelectedRow();

                pilihBaris(model.baris(baris));
            }
        });

        JButton btnBaru = new JButton("Baru");
        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");

        btnBaru.addActionListener(e -> kosongkan());
        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());

        JPanel panelTombol = new JPanel();

        panelTombol.add(btnBaru);
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);

        JPanel panelAtas = new JPanel(new BorderLayout());

        panelAtas.add(
                panelIsian("Isian Data Gedung", label, isian),
                BorderLayout.CENTER
        );

        panelAtas.add(
                panelTombol,
                BorderLayout.SOUTH
        );

        txtCari = new JTextField(15);

        JButton btnCari = new JButton("Cari");

        JButton btnTampilkanSemua =
                new JButton("Tampilkan Semua");

        btnCari.addActionListener(e -> cari());

        btnTampilkanSemua.addActionListener(e -> {
            txtCari.setText("");
            muatTabel();
        });

        JPanel panelCari = new JPanel();

        panelCari.add(new JLabel("Cari Nama Gedung:"));
        panelCari.add(txtCari);
        panelCari.add(btnCari);
        panelCari.add(btnTampilkanSemua);

        JPanel panelTabel = new JPanel(new BorderLayout(5, 5));

        panelTabel.add(
                panelCari,
                BorderLayout.NORTH
        );

        panelTabel.add(
                new JScrollPane(tabel),
                BorderLayout.CENTER
        );

        setLayout(new BorderLayout(8, 8));

        add(panelAtas, BorderLayout.NORTH);
        add(panelTabel, BorderLayout.CENTER);
    }

    private void muatTabel() {

        try {

            List<Gedung> daftar = dao.ambilSemua();

            model.isiUlang(daftar);

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    private void cari() {

        String nama = txtCari.getText().trim();

        if (nama.isEmpty()) {

            pesan("Masukkan nama gedung yang ingin dicari.");

            return;
        }

        try {

            List<Gedung> daftar =
                    dao.cariDengan("nama", nama);

            model.isiUlang(daftar);

            if (daftar.isEmpty()) {

                pesan("Data gedung tidak ditemukan.");
            }

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {

        idTerpilih = 0;

        txtKode.setText("");
        txtNama.setText("");
        txtLokasi.setText("");

        tabel.clearSelection();

        txtKode.requestFocus();
    }

    private void pilihBaris(Gedung g) {

        idTerpilih = g.getId();

        txtKode.setText(g.getKode());
        txtNama.setText(g.getNama());
        txtLokasi.setText(g.getLokasi());
    }

    private Gedung dariIsian() {

        Gedung g = new Gedung(
                txtKode.getText().trim(),
                txtNama.getText().trim(),
                txtLokasi.getText().trim()
        );

        g.setId(idTerpilih);

        return g;
    }

    private void simpan() {

        try {

            Gedung g = dariIsian();

            g.setId(0);

            dao.simpan(g);

            pesan("Data gedung berhasil disimpan.");

            kosongkan();

            muatTabel();

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    private void ubah() {

        if (idTerpilih == 0) {

            pesan("Pilih data gedung terlebih dahulu.");

            return;
        }

        try {

            Gedung g = dariIsian();

            dao.ubah(g);

            pesan("Data gedung berhasil diubah.");

            kosongkan();

            muatTabel();

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    private void hapus() {

        if (idTerpilih == 0) {

            pesan("Pilih data gedung terlebih dahulu.");

            return;
        }

        if (!konfirmasi(
                "Apakah Anda yakin ingin menghapus data gedung ini?")) {

            return;
        }

        try {

            dao.hapus(idTerpilih);

            pesan("Data gedung berhasil dihapus.");

            kosongkan();

            muatTabel();

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            FormGedung form = new FormGedung();

            form.setVisible(true);
        });
    }
}