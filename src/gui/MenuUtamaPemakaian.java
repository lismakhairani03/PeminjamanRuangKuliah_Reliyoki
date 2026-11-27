package gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class MenuUtamaPemakaian extends BaseForm {

    public MenuUtamaPemakaian() {
        super("Menu Utama Pemakaian", 400, 280);
    }

    @Override
    protected void susunKomponen() {

        JButton btnPemakaian = new JButton("Data Pemakaian");
        JButton btnKeluar = new JButton("Keluar");

        // Membuka Form Pemakaian
        btnPemakaian.addActionListener(e ->
                new FormPemakaian().setVisible(true)
        );

        // Keluar dari menu
        btnKeluar.addActionListener(e -> dispose());

        JPanel isi = new JPanel(new GridLayout(2, 1, 8, 8));

        isi.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        isi.add(btnPemakaian);
        isi.add(btnKeluar);

        JLabel judul = new JLabel(
                "Menu Utama Modul Pemakaian",
                JLabel.CENTER
        );

        judul.setBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 0)
        );

        setLayout(new BorderLayout());

        add(judul, BorderLayout.NORTH);
        add(isi, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new MenuUtamaPemakaian().setVisible(true)
        );
    }
}