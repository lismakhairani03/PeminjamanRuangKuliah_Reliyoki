package gui;

import model.Dosen;
import dao.DosenDao;
import exception.AppException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormDosen extends BaseForm {

    private final DosenDao dao = new DosenDao();

    private JTextField txtNip;
    private JTextField txtNama;
    private JTextField txtProgramStudi;

    private JTable tabel;
    private GenericTableModel<Dosen> model;

    private int idTerpilih = 0;

    public FormDosen() {
        super("Data Dosen", 800, 550);
        muatData();
    }

    @Override
    protected void susunKomponen() {

        // Input data dosen
        txtNip = new JTextField();
        txtNama = new JTextField();
        txtProgramStudi = new JTextField();

        JPanel panelInput = panelIsian(
                "Form Data Dosen",
                new String[]{
                        "NIP",
                        "Nama",
                        "Program Studi"
                },
                new JComponent[]{
                        txtNip,
                        txtNama,
                        txtProgramStudi
                }
        );

        // Tombol
        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersihkan = new JButton("Bersihkan");

        JPanel panelTombol = new JPanel();

        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersihkan);

        // Model tabel
        String[] kolom = {
                "ID",
                "NIP",
                "Nama",
                "Program Studi"
        };

        model = new GenericTableModel<>(
                kolom,
                d -> new Object[]{
                        d.getId(),
                        d.getNip(),
                        d.getNama(),
                        d.getProgramStudi()
                }
        );

        tabel = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tabel);

        // Panel atas
        JPanel panelAtas = new JPanel(new BorderLayout());

        panelAtas.add(panelInput, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));

        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Tombol simpan
        btnSimpan.addActionListener(e -> simpan());

        // Tombol ubah
        btnUbah.addActionListener(e -> ubah());

        // Tombol hapus
        btnHapus.addActionListener(e -> hapus());

        // Tombol bersihkan
        btnBersihkan.addActionListener(e -> bersihkanForm());

        // Memilih data dari tabel
        tabel.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int baris = tabel.getSelectedRow();

                if (baris >= 0) {

                    int indeks = tabel.convertRowIndexToModel(baris);

                    Dosen dosen = model.baris(indeks);

                    idTerpilih = dosen.getId();

                    txtNip.setText(dosen.getNip());
                    txtNama.setText(dosen.getNama());
                    txtProgramStudi.setText(
                            dosen.getProgramStudi()
                    );
                }
            }
        });
    }

    // Menampilkan data
    private void muatData() {

        try {

            List<Dosen> daftar = dao.ambilSemua();

            model.isiUlang(daftar);

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Menyimpan data
    private void simpan() {

        try {

            Dosen dosen = new Dosen(
                    txtNip.getText().trim(),
                    txtNama.getText().trim(),
                    txtProgramStudi.getText().trim()
            );

            dao.simpan(dosen);

            pesan("Data Dosen berhasil disimpan.");

            bersihkanForm();
            muatData();

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Mengubah data
    private void ubah() {

        if (idTerpilih == 0) {

            pesan("Pilih data yang ingin diubah.");

            return;
        }

        try {

            Dosen dosen = new Dosen(
                    txtNip.getText().trim(),
                    txtNama.getText().trim(),
                    txtProgramStudi.getText().trim()
            );

            dosen.setId(idTerpilih);

            dao.ubah(dosen);

            pesan("Data Dosen berhasil diubah.");

            bersihkanForm();
            muatData();

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Menghapus data
    private void hapus() {

        if (idTerpilih == 0) {

            pesan("Pilih data yang ingin dihapus.");

            return;
        }

        if (!konfirmasi("Yakin ingin menghapus data ini?")) {

            return;
        }

        try {

            dao.hapus(idTerpilih);

            pesan("Data Dosen berhasil dihapus.");

            bersihkanForm();
            muatData();

        } catch (AppException e) {

            tampilkanKesalahan(e);
        }
    }

    // Membersihkan form
    private void bersihkanForm() {

        txtNip.setText("");
        txtNama.setText("");
        txtProgramStudi.setText("");

        idTerpilih = 0;

        tabel.clearSelection();
    }

    // Menjalankan form
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            FormDosen form = new FormDosen();

            form.setVisible(true);
        });
    }
}
