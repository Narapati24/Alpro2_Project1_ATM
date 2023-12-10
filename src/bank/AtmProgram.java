package bank;

import java.io.*;
import java.util.Scanner;

public class AtmProgram {
    private final String FILE_NAME = "src/data_atm.txt";
    private AtmData[] dataATMArray;

    public AtmProgram() {
        dataATMArray = bacaDataATMDariFile(FILE_NAME);

        if (dataATMArray != null) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                tampilkanMenu();
                int pilihan = scanner.nextInt();

                switch (pilihan) {
                    case 1:
                        cekSaldo();
                        break;
                    case 2:
                        transfer();
                        break;
                    case 3:
                        tarikTunai();
                        break;
                    case 4:
                        topupPulsa();
                        break;
                    case 5:
                        pembayaranListrik();
                        break;
                    case 6:
                        pembayaranPDAM();
                        break;
                    case 7:
                        System.out.println("Terima kasih telah menggunakan layanan ATM. Sampai jumpa!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
                }
            }
        } else {
            System.out.println("Gagal membaca data ATM dari file. Program akan keluar.");
        }
    }

    private void tampilkanMenu() {
        System.out.println("===== MENU ATM =====");
        System.out.println("1. Cek Saldo");
        System.out.println("2. Transfer");
        System.out.println("3. Tarik Tunai");
        System.out.println("4. Topup Pulsa");
        System.out.println("5. Pembayaran Listrik");
        System.out.println("6. Pembayaran PDAM");
        System.out.println("7. Keluar");
        System.out.print("Pilih menu (1-7): ");
    }

    private void cekSaldo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nomor kartu ATM: ");
        String nomorKartu = scanner.nextLine();

        AtmData dataATM = cariDataATM(nomorKartu);

        if (dataATM != null) {
            System.out.println("Saldo saat ini: Rp." + dataATM.saldo);
        } else {
            System.out.println("Nomor kartu ATM tidak ditemukan.");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void transfer() {
        System.out.println("Fitur transfer belum diimplementasikan.");
    }

    private void tarikTunai() {
        System.out.println("Fitur tarik tunai belum diimplementasikan.");
    }

    private void topupPulsa() {
        System.out.println("Fitur topup pulsa belum diimplementasikan.");
    }

    private void pembayaranListrik() {
        System.out.println("Fitur pembayaran listrik belum diimplementasikan.");
    }

    private void pembayaranPDAM() {
        System.out.println("Fitur pembayaran PDAM belum diimplementasikan.");
    }

    private AtmData[] bacaDataATMDariFile(String namaFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(namaFile))) {
            String line;
            int jumlahData = 0;

//            count sum of data for length array
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    jumlahData++;
                }
            }

            AtmData[] dataATMArray = new AtmData[jumlahData];
            br.close();

//            read real data
            BufferedReader newBr = new BufferedReader(new FileReader(namaFile));  // Open a new BufferedReader
            int index = 0;

            while ((line = newBr.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(", ");

//                 Extract values from the split data
                String tempNomorKartu = data[0].split(": ")[1];
                int tempPin = Integer.parseInt(data[1].split(": ")[1]);
                double tempSaldo = Double.parseDouble(data[2].split(": ")[1]);

//                Insert into AtmData
                String nomorKartu = tempNomorKartu;
                int pin = Integer.parseInt(String.valueOf(tempPin));
                double saldo = tempSaldo;
                dataATMArray[index] = new AtmData(nomorKartu, pin, saldo);
                index++;

            }

            newBr.close();

            return dataATMArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private AtmData cariDataATM(String nomorKartu) {
        for (AtmData atmData : dataATMArray) {
            if (atmData != null && atmData.getNomorKartu().equals(nomorKartu)) {
                return atmData;
            }
        }
        return null;
    }
}

