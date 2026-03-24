import java.util.Date;

/** * Implementasi Sistem ATM Sederhana
 * Menggunakan Konsep 4 Pilar OOP: Abstraksi, Enkapsulasi, Pewarisan, Polimorfisme.
 */

// --- ABSTRAKSI ---
// Kelas dasar untuk semua jenis transaksi di ATM
abstract class TransaksiBank {
    private String idTransaksi;
    protected double nominal; 

    public TransaksiBank(String id, double nominal) {
        this.idTransaksi = id;
        this.nominal = nominal;
    }

    // Method abstrak yang akan diimplementasikan berbeda oleh tiap jenis transaksi (Polimorfisme)
    public abstract boolean proses(AkunNasabah akun);
}

// --- PEWARISAN (INHERITANCE) ---
// Subclass khusus untuk menangani penarikan uang
class TarikTunai extends TransaksiBank {
    public TarikTunai(String id, double jumlah) {
        super(id, jumlah);
    }

    @Override
    public boolean proses(AkunNasabah akun) {
        if (akun.getSaldo() >= nominal) {
            akun.setSaldo(akun.getSaldo() - nominal);
            System.out.println("[INFO] Berhasil tarik tunai sebesar: " + nominal);
            return true;
        }
        System.out.println("[PERINGATAN] Saldo Anda tidak cukup untuk melakukan penarikan.");
        return false;
    }
}

// Subclass khusus untuk setor uang
class SetorTunai extends TransaksiBank {
    public SetorTunai(String id, double jumlah) {
        super(id, jumlah);
    }

    @Override
    public boolean proses(AkunNasabah akun) {
        akun.setSaldo(akun.getSaldo() + nominal);
        System.out.println("[INFO] Setoran masuk sebesar: " + nominal);
        return true;
    }
}

// --- ENKAPSULASI ---
class AkunNasabah {
    private String noRekening;
    private double saldo;

    public AkunNasabah(String noRek, double saldoAwal) {
        this.noRekening = noRek;
        this.saldo = saldoAwal;
    }

    // Getter untuk akses saldo secara aman
    public double getSaldo() {
        return saldo;
    }

    // Setter sederhana untuk update nilai saldo
    public void setSaldo(double saldoBaru) {
        this.saldo = saldoBaru;
    }
}

class KartuATM {
    private String idKartu;
    private Date masaBerlaku;

    public KartuATM(String idKartu) {
        this.idKartu = idKartu;
        this.masaBerlaku = new Date(); 
    }

    public boolean cekValidasi() {
        System.out.println("Sistem sedang memverifikasi kartu: " + idKartu);
        return true; 
    }
}

// --- CLASS UTAMA ---
public class ATM {
    
    public void cetakHeader() {
        System.out.println("==================================");
        System.out.println("       BANK DIGITAL MANDIRI       ");
        System.out.println("==================================");
    }

    public void eksekusi(AkunNasabah akun, TransaksiBank aksi) {
        aksi.proses(akun);
        System.out.println("Update Saldo Terakhir: Rp" + akun.getSaldo());
    }

    public static void main(String[] args) {
        ATM mesin = new ATM();
        KartuATM kartuKu = new KartuATM("5441-xxxx-9901");
        AkunNasabah akunKu = new AkunNasabah("2024001", 1000000.0); // Saldo awal 1 juta

        mesin.cetakHeader();
        
        if (kartuKu.cekValidasi()) {
            // Contoh Polimorfisme: Menggunakan objek TransaksiBank untuk aksi yang berbeda
            TransaksiBank aksi1 = new TarikTunai("TX-001", 250000.0);
            mesin.eksekusi(akunKu, aksi1);

            System.out.println("----------------------------------");

            TransaksiBank aksi2 = new SetorTunai("TX-002", 500000.0);
            mesin.eksekusi(akunKu, aksi2);
        }

        System.out.println("\nTransaksi Selesai. Jangan lupa ambil kartu!");
        System.out.println("==================================");
    }
}