/**
 * Class model: menyimpan data satu barang belanja.
 * File ini tidak berisi GUI, hanya data dan perhitungan subtotal.
 */
public class Barang {

    private String nama;
    private int harga;
    private int jumlah;

    public Barang(String nama, int harga, int jumlah) {
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    // Subtotal = harga x jumlah
    public int getSubtotal() {
        return harga * jumlah;
    }
}