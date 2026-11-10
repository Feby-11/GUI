import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Kasir Sederhana - mencatat barang belanja dan menghitung total.
 * Implements ActionListener (klik tombol) dan KeyListener (tekan Enter).
 */
public class KasirApp extends JFrame implements ActionListener, KeyListener {

    // ===== Atomic Component =====
    private JTextField txtNama, txtHarga;
    private JSpinner spnJumlah;
    private JButton btnTambah, btnHapus, btnReset;
    private JLabel lblTotal;
    private JTable tabel;

    // ===== Data =====
    private DefaultTableModel model;                      // isi tabel
    private ArrayList<Barang> daftar = new ArrayList<>(); // daftar object Barang

    public KasirApp() {
        // ===== Top Level Container: JFrame =====
        super("Kasir Sederhana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Layout Manager 1: BorderLayout

        // ===== Intermediate Component: panel form (GridLayout = Layout Manager 2) =====
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 8, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        txtNama = new JTextField();
        txtHarga = new JTextField();
        spnJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // awal, min, max, step

        panelForm.add(new JLabel("Nama Barang"));
        panelForm.add(txtNama);
        panelForm.add(new JLabel("Harga (Rp)"));
        panelForm.add(txtHarga);
        panelForm.add(new JLabel("Jumlah"));
        panelForm.add(spnJumlah);

        // ===== Tabel dibungkus JScrollPane (Intermediate Component) =====
        model = new DefaultTableModel(new String[] { "Nama Barang", "Harga", "Jumlah", "Subtotal" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // sel tabel tidak bisa diedit langsung
            }
        };
        tabel = new JTable(model);
        JScrollPane scrollTabel = new JScrollPane(tabel);
        scrollTabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // ===== Panel bawah: total + tombol (FlowLayout = Layout Manager 3) =====
        lblTotal = new JLabel("Total: Rp 0", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));

        btnTambah = new JButton("Tambah");
        btnHapus = new JButton("Hapus Terpilih");
        btnReset = new JButton("Reset");

        JPanel panelTombol = new JPanel(new FlowLayout());
        panelTombol.add(btnTambah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnReset);

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.add(lblTotal, BorderLayout.NORTH);
        panelBawah.add(panelTombol, BorderLayout.CENTER);

        add(panelForm, BorderLayout.NORTH);
        add(scrollTabel, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);

        // ===== Event Handling =====
        // Interface 1: ActionListener -> klik tombol
        btnTambah.addActionListener(this);
        btnHapus.addActionListener(this);
        btnReset.addActionListener(this);

        // Interface 2: KeyListener -> tekan Enter di kolom harga = tambah barang
        txtHarga.addKeyListener(this);

        setSize(500, 450);
        setLocationRelativeTo(null);
    }

    // Menambah barang ke daftar dan tabel
    private void tambahBarang() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama barang tidak boleh kosong!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int harga = Integer.parseInt(txtHarga.getText().trim());
            int jumlah = (int) spnJumlah.getValue();
            if (harga <= 0) {
                throw new NumberFormatException();
            }

            Barang b = new Barang(nama, harga, jumlah);
            daftar.add(b);
            model.addRow(new Object[] { b.getNama(), b.getHarga(), b.getJumlah(), b.getSubtotal() });

            txtNama.setText("");
            txtHarga.setText("");
            spnJumlah.setValue(1);
            txtNama.requestFocus();
            updateTotal();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka lebih dari 0!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghapus baris yang dipilih di tabel
    private void hapusBarang() {
        int baris = tabel.getSelectedRow(); // -1 jika belum ada yang dipilih
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih barang di tabel dulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        daftar.remove(baris);
        model.removeRow(baris);
        updateTotal();
    }

    // Menghitung total dari semua subtotal
    private void updateTotal() {
        int total = 0;
        for (Barang b : daftar) {
            total += b.getSubtotal();
        }
        lblTotal.setText("Total: Rp " + total);
    }

    // ===== ActionListener: dipanggil saat tombol diklik =====
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTambah) {
            tambahBarang();
        } else if (e.getSource() == btnHapus) {
            hapusBarang();
        } else if (e.getSource() == btnReset) {
            daftar.clear();
            model.setRowCount(0);
            updateTotal();
        }
    }

    // ===== KeyListener: dipanggil saat tombol keyboard ditekan =====
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            tambahBarang();
        }
    }

    @Override public void keyReleased(KeyEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KasirApp().setVisible(true));
    }
}