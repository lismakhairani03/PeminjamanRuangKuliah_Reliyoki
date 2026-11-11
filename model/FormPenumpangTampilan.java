package latihan;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class FormPenumpangTampilan extends JFrame {
    private JTextField fNik;
    private JTextField fNama;
    private JTextField fTelepon;
    private JTextField fAlamat;
    public FormPenumpangTampilan() {
        super("Data Penumpang");
        setSize(760, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        susunKomponen();
    }
    private void susunKomponen() {
        setLayout(new BorderLayout(8, 8));
        JPanel panelAtas = new JPanel();
        panelAtas.add(new JLabel("Nama:"));
        panelAtas.add(new JTextField(20));
        panelAtas.add(new JButton("Cari"));
        add(panelAtas, BorderLayout.NORTH);
        String[] judulKolom = {"NIK", "Nama", "Telepon", "Alamat"};
        Object[][] dataSementara = {
                {"1403010101010001", "Rudi Sanjaya", "081234567801", "Bengkalis"},
                {"1403010101010002", "Siti Aminah", "081234567802", "Bantan"}
        };
        JTable tabel = new JTable(dataSementara, judulKolom);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
        fNik = new JTextField(8);
        fNama = new JTextField(8);
        fTelepon = new JTextField(8);
        fAlamat = new JTextField(8);
        JTextField[] isian = {fNik, fNama, fTelepon, fAlamat};
        add(buatPanelIsian(new String[]{"Nomor Induk Kependudukan", "Nama", "Telepon", "Alamat"}, isian),
                BorderLayout.EAST);
        JPanel panelBawah = new JPanel();
        panelBawah.add(new JButton("Baru"));
        panelBawah.add(new JButton("Simpan"));
        panelBawah.add(new JButton("Hapus"));
        add(panelBawah, BorderLayout.SOUTH);
    }
    private JPanel buatPanelIsian(String[] judul, JTextField[] isian) {
        JPanel kolomLabel = new JPanel(new GridLayout(judul.length, 1, 6, 6));
        JPanel kolomIsian = new JPanel(new GridLayout(isian.length, 1, 6, 6));
        for (int i = 0; i < judul.length; i++) {
            kolomLabel.add(new JLabel(judul[i]));
            kolomIsian.add(isian[i]);
        }
        JPanel panel = new JPanel(new BorderLayout(6, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Isian"));
        panel.add(kolomLabel, BorderLayout.WEST);
        panel.add(kolomIsian, BorderLayout.CENTER);
        return panel;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new
                FormPenumpangTampilan().setVisible(true));
    }
}