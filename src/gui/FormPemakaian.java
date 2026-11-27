package gui;

import dao.PemakaianDao;
import exception.AppException;
import model.Pemakaian;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormPemakaian extends BaseForm {

    private final PemakaianDao dao = new PemakaianDao();

    private JTextField txtRuangId;
    private JTextField txtDosenId;
    private JTextField txtTanggal;
    private JTextField txtJamMulai;
    private JTextField txtJamSelesai;
    private JTextField txtKeperluan;
    private JTextField txtCari;

    private GenericTableModel<Pemakaian> model;
    private JTable tabel;

    private int idTerpilih = 0;

    public FormPemakaian() {
        super("Data Pemakaian Ruang", 900, 600);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {

        // Membuat input
        txtRuangId = new JTextField();
        txtDosenId = new JTextField();
        txtTanggal = new JTextField();
        txtJamMulai = new JTextField();
        txtJamSelesai = new JTextField();
        txtKeperluan = new JTextField();

        String[] label = {
                "ID Ruang",
                "ID Dosen",
                "Tanggal (YYYY-MM-DD)",
                "Jam Mulai (HH:MM)",
                "Jam Selesai (HH:MM)",
                "Keperluan"
        };

        JComponent[] isian = {
                txtRuangId,
                txtDosenId,
                txtTanggal,
                txtJamMulai,
                txtJamSelesai,
                txtKeperluan
        };

        JPanel panelInput = panelIsian(
                "Form Pemakaian Ruang",
                label,
                isian
        );

        // Membuat tombol
        JButton btnBaru = new JButton("Baru");
        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");

        JPanel panelTombol = new JPanel();

        panelTombol.add(btnBaru);
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);

        // Membuat tabel
        String[] kolom = {
                "ID",
                "ID Ruang",
                "ID Dosen",
                "Tanggal",
                "Jam Mulai",
                "Jam Selesai",
                "Keperluan"
        };

        model = new GenericTableModel<>(
                kolom,
                p -> new Object[]{
                        p.getId(),
                        p.getRuangId(),
                        p.getDosenId(),
                        p.getTanggal(),
                        p.getJamMulai(),
                        p.getJamSelesai(),
                        p.getKeperluan()
                }
        );

        tabel = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tabel);

        // Memilih data pada tabel
        tabel.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int baris = tabel.getSelectedRow();

                if (baris >= 0) {
                    pilihBaris(model.baris(
                            tabel.convertRowIndexToModel(baris)
                    ));
                }
            }
        });

        // Panel pencarian
        txtCari = new JTextField(20);

        JButton btnCari = new JButton("Cari");
        JButton btnSemua = new JButton("Tampilkan Semua");

        JPanel panelCari = new JPanel();

        panelCari.add(new JLabel("Cari Keperluan:"));
        panelCari.add(txtCari);
        panelCari.add(btnCari);
        panelCari.add(btnSemua);

        // Panel atas
        JPanel panelAtas = new JPanel(new BorderLayout());

        panelAtas.add(panelInput, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        // Panel utama
        setLayout(new BorderLayout(10, 10));

        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelCari, BorderLayout.SOUTH);

        // Aksi tombol
        btnBaru.addActionListener(e -> kosongkan());

        btnSimpan.addActionListener(e -> simpan());

        btnUbah.addActionListener(e -> ubah());

        btnHapus.addActionListener(e -> hapus());

        btnCari.addActionListener(e -> cari());

        btnSemua.addActionListener(e -> {
            kosongkan();
            muatTabel();
        });
    }

    // Menampilkan seluruh data
    private void muatTabel() {

        try {

            List<Pemakaian> daftar = dao.ambilSemua();

            model.isiUlang(daftar);

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Mencari data berdasarkan keperluan
    private void cari() {

        String keperluan = txtCari.getText().trim();

        if (keperluan.isEmpty()) {
            pesan("Masukkan keperluan yang ingin dicari.");
            return;
        }

        try {

            List<Pemakaian> daftar =
                    dao.cariKeperluan(keperluan);

            model.isiUlang(daftar);

            if (daftar.isEmpty()) {
                pesan("Data tidak ditemukan.");
            }

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Mengosongkan form
    private void kosongkan() {

        txtRuangId.setText("");
        txtDosenId.setText("");
        txtTanggal.setText("");
        txtJamMulai.setText("");
        txtJamSelesai.setText("");
        txtKeperluan.setText("");
        txtCari.setText("");

        idTerpilih = 0;

        tabel.clearSelection();

        txtRuangId.requestFocus();
    }

    // Mengambil data dari tabel
    private void pilihBaris(Pemakaian p) {

        idTerpilih = p.getId();

        txtRuangId.setText(
                String.valueOf(p.getRuangId())
        );

        txtDosenId.setText(
                String.valueOf(p.getDosenId())
        );

        txtTanggal.setText(p.getTanggal());

        txtJamMulai.setText(p.getJamMulai());

        txtJamSelesai.setText(p.getJamSelesai());

        txtKeperluan.setText(p.getKeperluan());
    }

    // Mengambil data dari input
    private Pemakaian dariIsian() {

        Pemakaian p = new Pemakaian(

                Integer.parseInt(txtRuangId.getText().trim()),

                Integer.parseInt(txtDosenId.getText().trim()),

                txtTanggal.getText().trim(),

                txtJamMulai.getText().trim(),

                txtJamSelesai.getText().trim(),

                txtKeperluan.getText().trim()
        );

        p.setId(idTerpilih);

        return p;
    }

    // Menyimpan data
    private void simpan() {

        try {

            Pemakaian p = dariIsian();

            p.setId(0);

            dao.simpan(p);

            pesan("Data pemakaian berhasil disimpan.");

            kosongkan();

            muatTabel();

        } catch (AppException e) {

            tampilkanKesalahan(e);

        } catch (NumberFormatException e) {

            pesan("ID ruang dan ID dosen harus berupa angka.");
        }
    }

    // Mengubah data
    private void ubah() {

        if (idTerpilih == 0) {

            pesan("Pilih terlebih dahulu data pada tabel.");

            return;
        }

        try {

            Pemakaian p = dariIsian();

            dao.ubah(p);

            pesan("Data pemakaian berhasil diubah.");

            kosongkan();

            muatTabel();

        } catch (AppException e) {

            tampilkanKesalahan(e);

        } catch (NumberFormatException e) {

            pesan("ID ruang dan ID dosen harus berupa angka.");
        }
    }

    // Menghapus data
    private void hapus() {

        if (idTerpilih == 0) {

            pesan("Pilih terlebih dahulu data pada tabel.");

            return;
        }

        if (!konfirmasi(
                "Apakah kamu yakin ingin menghapus data ini?"
        )) {

            return;
        }

        try {

            dao.hapus(idTerpilih);

            pesan("Data pemakaian berhasil dihapus.");

            kosongkan();

            muatTabel();

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Menjalankan form
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            FormPemakaian form = new FormPemakaian();

            form.setVisible(true);
        });
    }
}