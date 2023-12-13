package bank;

import java.io.*;
import java.util.Scanner;

public class AtmProgram {
    private Scanner scanner = new Scanner(System.in);
    private final AtmData[] dataATMArray;

    public AtmProgram() {
        String FILE_NAME = "src/data_atm.txt";
        dataATMArray = bacaDataATMDariFile(FILE_NAME);

        if (dataATMArray != null) {
            String tempNoKartu;
            boolean found = false;
            AtmData tempDataAtm;
            do {
                System.out.print("Masukan nomor kartu ATM: ");
                tempNoKartu = scanner.nextLine();
                tempDataAtm = cariDataATM(tempNoKartu);
                if (tempDataAtm != null) {
                    found = true;
                } else {
                    System.out.println("Nomor kartu yang dimasukan tidak ada!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (!found);
            boolean found2 = false;
            do {
                System.out.print("Masukan PIN Anda: ");
                int tempPin = scanner.nextInt();
                if (tempDataAtm.getPin() == tempPin){
                    found2 = true;
                } else {
                    System.out.println("Pin yang anda masukan salah!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            } while (!found2);


            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                tampilkanMenu();
                int pilihan = scanner.nextInt();

                switch (pilihan) {
                    case 1 -> cekSaldo(tempNoKartu);
                    case 2 -> transfer();
                    case 3 -> tarikTunai(tempNoKartu);
                    case 4 -> topupPulsa();
                    case 5 -> pembayaranListrik();
                    case 6 -> pembayaranPDAM();
                    case 7 -> {
                        System.out.println("Terima kasih telah menggunakan layanan ATM. Sampai jumpa!");
                        System.exit(0);
                    }
                    default -> System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
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

    private void cekSaldo(String nomorKartu) {
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

    private void tarikTunai(String nomorKartu) {
        AtmData dataATM = cariDataATM(nomorKartu);
        System.out.print("Masukan nominal untuk menarik tunai: ");
        double nominal = scanner.nextDouble();
        double tempSaldo = dataATM.getSaldo();
        if(nominal > tempSaldo){
            System.out.println("Maaf Saldo anda tidak mencukupi!");
        } else {
            dataATM.setSaldo(tempSaldo - nominal);
            System.out.println("Sisa saldo anda Rp. " + dataATM.getSaldo());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
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

    private void menulisDataATMKeFile(){

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
                String nomorKartu = data[0].split(": ")[1];
                int tempPin = Integer.parseInt(data[1].split(": ")[1]);
                double saldo = Double.parseDouble(data[2].split(": ")[1]);

//                Insert into AtmData
                int pin = Integer.parseInt(String.valueOf(tempPin));
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

