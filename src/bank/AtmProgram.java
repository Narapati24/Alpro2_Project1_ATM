package bank;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class AtmProgram {
    private final Scanner scanner = new Scanner(System.in);
    private final AtmView view = new AtmView();
    private AtmFileController fileController = new AtmFileController();

    public AtmProgram() {


        if (fileController.getDataATMArray() != null) {
            AtmData tempDataAtm = login();
            memilihMenuUtama(tempDataAtm);

        } else {
            System.out.println("Gagal membaca data ATM dari file. Program akan keluar.");
        }
    }

    private void memilihMenuUtama(AtmData tempDataAtm){
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            view.menu();
            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1 -> cekSaldo(tempDataAtm);
                case 2 -> transfer(tempDataAtm);
                case 3 -> tarikTunai(tempDataAtm);
                case 4 -> setorTunai(tempDataAtm);
                case 5 -> memilihMenuPembayaran(tempDataAtm);
                case 6 -> historyAtm(tempDataAtm);
                case 7 -> {
                    fileController.writeDataATM();
                    System.out.println("Terima kasih telah menggunakan layanan ATM. Sampai jumpa!");
                    System.exit(0);
                }
                default -> System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
            }
        }
    }

    private void memilihMenuPembayaran(AtmData tempDataAtm) {
        boolean filled;
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            view.menuPembayaran();
            int pilihan = scanner.nextInt();

            filled = false;
            switch (pilihan) {
                case (1) -> topupPulsa(tempDataAtm);
                case (2) -> pembayaranListrik(tempDataAtm);
                case (3) -> pembayaranPDAM(tempDataAtm);
                case (4) -> {
                    filled = true;
                }
                default -> System.out.println("Pilihan Tidak Valid!");
            }
        } while (!filled);
    }

    private void cekSaldo(AtmData dataATM) {
            System.out.println("Saldo saat ini: Rp." + dataATM.getSaldo());
    }

    private void transfer(AtmData dataATM) {
        AtmData targetNoKartu;
        scanner.nextLine();
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("Masukan nomor yang dituju: ");
            String inputTargetNoKartu = scanner.nextLine();
            targetNoKartu = fileController.findDataATM(inputTargetNoKartu);
            if (targetNoKartu == null) {
                System.out.println("Nomor yang dituju tidak terdaftar");
            }
        } while (targetNoKartu == null);
        double transfer;
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("Masukan nominal yang akan di Transfer: ");
            transfer = scanner.nextDouble();
            if (transfer > dataATM.getSaldo()) {
                System.out.println("Nominal yang anda masukan tidak cukup");
            } else {
                dataATM.setSaldo(dataATM.getSaldo() - transfer);
                targetNoKartu.setSaldo(targetNoKartu.getSaldo() + transfer);
                System.out.println("Rp. " + transfer + " berhasil di transfer \n" +
                        "Sisa saldo anda sebesar Rp. " + dataATM.getSaldo());
            }
        } while (transfer > dataATM.getSaldo());
    }

    private void tarikTunai(AtmData dataATM) {
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

    private void setorTunai(AtmData noKartu){
        boolean loop = true;
        while (loop){
            System.out.println("Hanya Menerima Saldo Kelipatan 50.000 dan 100.000");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.print("Silahkan masukan uang yang akan di setorkan: ");
            double setor = scanner.nextDouble();
            if (setor % 50000 == 0){
                noKartu.setSaldo(noKartu.getSaldo() + setor);
                loop = false;
            }
        }
    }

    private void historyAtm(AtmData noKartu){
        AtmLogData log = fileController.findDataLogATM(noKartu.getNomorKartu());
        if (log == null) {
            System.out.println("Anda Belum Melakukan Transaksi Apapun");
        } else {
            log.printLog();
        }
    }

    private void topupPulsa(AtmData dataAtm) {
        System.out.print("Isikan Nomor Tujuan Top Up");
        scanner.next();
        int number;
        double saldoIni = dataAtm.getSaldo();
        boolean filled = false;
        do {
            view.menuTopUp();
            number = scanner.nextInt();
            switch (number) {
                case (1) -> {
                    if (saldoIni > 5500){
                        dataAtm.setSaldo(saldoIni - 5500);
                        filled = true;
                    }
                }
                case (2) -> {
                    if (saldoIni > 11000){
                        dataAtm.setSaldo(saldoIni - 11000);
                        filled = true;
                    }
                }
                case (3) -> {
                    if (saldoIni > 27500){
                        dataAtm.setSaldo(saldoIni - 27500);
                        filled = true;
                    }
                }
                case (4) -> {
                    if (saldoIni > 55000){
                        dataAtm.setSaldo(saldoIni - 55000);
                        filled = true;
                    }
                }
                case (5) -> {
                    System.out.print("Jumlah yang ingin di top up Rp. ");
                    double jumlah = scanner.nextDouble();
                    if (saldoIni > jumlah){
                        dataAtm.setSaldo(saldoIni - jumlah);
                        filled = true;
                    }
                }
                case (6) -> {
                    filled = true;
                }
                default -> System.out.println("Pilihan Tidak Valid");
            }
            if (number >= 1 && number <= 5 && !filled){
                System.out.println("Saldo anda tidak mencukupi");
            }
        } while (!filled);
        if (number != 6){
            System.out.println("Top Up Berhasil");
        }
    }

    private void pembayaranListrik(AtmData dataAtm) {
        double saldo = dataAtm.getSaldo();
        view.menuPembayaranListrik();
        int number = scanner.nextInt();
        boolean filled = false;
        do {
            switch (number){
                case (1) ->{
                    if (saldo > 55000) {
                        dataAtm.setSaldo(saldo - 55000);
                        filled = true;
                    }
                }
                case (2) ->{
                    if (saldo > 110000) {
                        dataAtm.setSaldo(saldo - 110000);
                        filled = true;
                    }
                }
                case (3) ->{
                    if (saldo > 275000) {
                        dataAtm.setSaldo(saldo - 275000);
                        filled = true;
                    }
                }
                case (4) ->{
                    if (saldo > 550000) {
                        dataAtm.setSaldo(saldo - 550000);
                        filled = true;
                    }
                }
                case (5) ->{
                    filled = true;
                }
                default -> System.out.println("Pilihan tidak valid");
            }
        } while (!filled);
        if (number != 5){
            System.out.println("Anda berhasil Membayar listrik! \n" +
                    "Sisa Saldo anda sebesar: Rp." + dataAtm.getSaldo());
        }
    }

    private void pembayaranPDAM(AtmData dataAtm) {
        double saldo = dataAtm.getSaldo();
        view.menuPembayaranPDAM();
        int number = scanner.nextInt();
        boolean filled = false;
        do {
            switch (number){
                case (1) ->{
                    if (saldo > 55000) {
                        dataAtm.setSaldo(saldo - 55000);
                        filled = true;
                    }
                }
                case (2) ->{
                    if (saldo > 110000) {
                        dataAtm.setSaldo(saldo - 110000);
                        filled = true;
                    }
                }
                case (3) ->{
                    if (saldo > 275000) {
                        dataAtm.setSaldo(saldo - 275000);
                        filled = true;
                    }
                }
                case (4) ->{
                    if (saldo > 550000) {
                        dataAtm.setSaldo(saldo - 550000);
                        filled = true;
                    }
                }
                case (5) ->{
                    filled = true;
                }
                default -> System.out.println("Pilihan tidak valid");
            }
        } while (!filled);
        if (number != 5){
            System.out.println("Anda berhasil Membayar PDAM! \n" +
                    "Sisa Saldo anda sebesar: Rp." + dataAtm.getSaldo());
        }
    }

    private AtmData login(){
        AtmData tempDataAtm;
        boolean found = false;
        do {
            System.out.print("Masukan nomor kartu ATM: ");
            String tempNoKartu = scanner.nextLine();
            tempDataAtm = fileController.findDataATM(tempNoKartu);
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
        return tempDataAtm;
    }
}

