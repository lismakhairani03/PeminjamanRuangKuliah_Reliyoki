package gui;

import exception.AppException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * Induk seluruh form.
 * Pemilik: Koordinator Antarmuka.
 */
public abstract class BaseForm extends JFrame {

    protected BaseForm(String judul, int lebar, int tinggi) {

        super(judul);

        setSize(lebar, tinggi);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        susunKomponen();
    }

    /**
     * Wajib diisi setiap modul.
     */
    protected abstract void susunKomponen();

    /**
     * Membuat panel isian.
     */
    protected JPanel panelIsian(
            String judulBingkai,
            String[] label,
            JComponent[] isian) {

        int baris = label.length;

        JPanel kolomLabel =
                new JPanel(new GridLayout(baris, 1, 6, 6));

        JPanel kolomIsian =
                new JPanel(new GridLayout(baris, 1, 6, 6));

        for (int i = 0; i < baris; i++) {

            kolomLabel.add(new JLabel(label[i]));
            kolomIsian.add(isian[i]);
        }

        JPanel panel = new JPanel(new BorderLayout(6, 0));

        panel.setBorder(
                BorderFactory.createTitledBorder(judulBingkai)
        );

        panel.add(kolomLabel, BorderLayout.WEST);
        panel.add(kolomIsian, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Menampilkan pesan kesalahan.
     */
    protected void tampilkanKesalahan(AppException e) {

        JOptionPane.showMessageDialog(
                this,
                e.pesanPengguna(),
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Konfirmasi tindakan.
     */
    protected boolean konfirmasi(String pertanyaan) {

        return JOptionPane.showConfirmDialog(
                this,
                pertanyaan,
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }

    /**
     * Menampilkan pesan.
     */
    protected void pesan(String teks) {

        JOptionPane.showMessageDialog(this, teks);
    }
}