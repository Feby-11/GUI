import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Konverter Suhu (Celsius ke Fahrenheit) - GUI sederhana Java Swing.
 * Implements ActionListener (klik tombol) dan KeyListener (tekan Enter).
 */
public class SuhuApp extends JFrame implements ActionListener, KeyListener {

    // Atomic Component
    private JTextField txtCelsius;
    private JLabel lblHasil;
    private JButton btnKonversi, btnReset;

    public SuhuApp() {
        // Top Level Container: JFrame
        super("Konverter Suhu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Layout Manager 1

        // Atomic Component: judul
        JLabel lblJudul = new JLabel("Celsius ke Fahrenheit", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));

        // Intermediate Component: panel input (FlowLayout = Layout Manager 2)
        JPanel panelInput = new JPanel(new FlowLayout());
        txtCelsius = new JTextField(10);
        panelInput.add(new JLabel("Celsius:"));
        panelInput.add(txtCelsius);

        lblHasil = new JLabel("Hasil: -", SwingConstants.CENTER);
        lblHasil.setFont(new Font("Arial", Font.BOLD, 16));

        // Intermediate Component: panel tengah (GridLayout = Layout Manager 3)
        JPanel panelTengah = new JPanel(new GridLayout(2, 1));
        panelTengah.add(panelInput);
        panelTengah.add(lblHasil);

        // Intermediate Component: panel tombol
        btnKonversi = new JButton("Konversi");
        btnReset = new JButton("Reset");
        JPanel panelTombol = new JPanel(new FlowLayout());
        panelTombol.add(btnKonversi);
        panelTombol.add(btnReset);

        add(lblJudul, BorderLayout.NORTH);
        add(panelTengah, BorderLayout.CENTER);
        add(panelTombol, BorderLayout.SOUTH);

        // Event Handling: daftarkan listener
        btnKonversi.addActionListener(this); // ActionListener
        btnReset.addActionListener(this);
        txtCelsius.addKeyListener(this);     // KeyListener

        setSize(350, 220);
        setLocationRelativeTo(null);
    }

    private void konversi() {
        try {
            double c = Double.parseDouble(txtCelsius.getText().trim());
            double f = c * 9 / 5 + 32;
            lblHasil.setText(String.format("Hasil: %.1f °F", f));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ActionListener: dipanggil saat tombol diklik
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnKonversi) {
            konversi();
        } else if (e.getSource() == btnReset) {
            txtCelsius.setText("");
            lblHasil.setText("Hasil: -");
        }
    }

    // KeyListener: dipanggil saat tombol keyboard ditekan
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            konversi();
        }
    }

    @Override public void keyReleased(KeyEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SuhuApp().setVisible(true));
    }
}