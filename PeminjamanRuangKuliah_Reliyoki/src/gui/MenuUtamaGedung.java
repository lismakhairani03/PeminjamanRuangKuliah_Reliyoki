package gui;

import javax.swing.*;
import java.awt.*;

public class MenuUtamaGedung extends JFrame {

    private JButton btnDataGedung;
    private JButton btnKeluar;

    public MenuUtamaGedung() {

        setTitle("Menu Utama Modul Gedung");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblJudul = new JLabel(
                "SISTEM PEMINJAMAN RUANG KULIAH",
                SwingConstants.CENTER
        );

        lblJudul.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblMenu = new JLabel(
                "MENU UTAMA GEDUNG",
                SwingConstants.CENTER
        );

        lblMenu.setFont(new Font("Arial", Font.BOLD, 16));

        btnDataGedung = new JButton("Data Gedung");
        btnKeluar = new JButton("Keluar");

        JPanel panelJudul = new JPanel(new GridLayout(2, 1, 10, 10));
        panelJudul.add(lblJudul);
        panelJudul.add(lblMenu);

        JPanel panelTombol = new JPanel(new GridLayout(2, 1, 10, 10));
        panelTombol.add(btnDataGedung);
        panelTombol.add(btnKeluar);

        JPanel panelUtama = new JPanel(new BorderLayout(20, 30));

        panelUtama.setBorder(
                BorderFactory.createEmptyBorder(60, 70, 60, 70)
        );

        panelUtama.add(panelJudul, BorderLayout.NORTH);
        panelUtama.add(panelTombol, BorderLayout.CENTER);

        add(panelUtama);

        // Membuka Form Gedung
        btnDataGedung.addActionListener(e -> {
            FormGedung form = new FormGedung();
            form.setVisible(true);
        });

        // Tombol Keluar
        btnKeluar.addActionListener(e -> {

            int pilihan = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah Anda yakin ingin keluar?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (pilihan == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MenuUtamaGedung menu = new MenuUtamaGedung();
            menu.setVisible(true);
        });
    }
}