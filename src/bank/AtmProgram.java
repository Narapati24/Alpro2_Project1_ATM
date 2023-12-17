package bank;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class AtmProgram {
    private static final String FILE_ATM = "src/data_atm.txt";
    private final Scanner scanner = new Scanner(System.in);
    private final AtmData[] dataATMArray;

    public AtmProgram() {
        bacaDataLogATMDariFile();
        dataATMArray = bacaDataATMDariFile(FILE_ATM);

        if (dataATMArray != null) {
            AtmData tempDataAtm = login();
            memilihMenuUtama(tempDataAtm);

        } else {
            System.out.println("Gagal membaca data ATM dari file. Program akan keluar.");
        }
    }

    private void tampilkanMenu() {
        System.out.println("===== MENU ATM =====");
        System.out.println("1. Cek Saldo");
        System.out.println("2. Transfer");
        System.out.println("3. Tarik Tunai");
        System.out.println("4. Pembayaran");
        System.out.println("5. Keluar");
        System.out.print("Pilih menu (1-5): ");
    }

    private void tampilkanMenuPembayaran(){
        System.out.println("===== MENU PEMBAYARAN =====");
        System.out.println("1. Topup Pulsa");
        System.out.println("2. Pembayaran Listrik");
        System.out.println("3. Pembayaran PDAM");
        System.out.println("4. Kembali");
        System.out.print("Pilih menu (1-4): ");
    }

    private void tampilkanMenuTopUp() {
        System.out.println("===== MENU TOPUP PULSA =====");
        System.out.println("1. Rp. 5.000");
        System.out.println("2. Rp. 10.000");
        System.out.println("3. Rp. 25.000");
        System.out.println("4. Rp. 50.000");
        System.out.println("5. Isi Sendiri");
        System.out.println("6. Cancel");
        System.out.print("Pilih menu (1-6): ");
    }

    private void tampilkanMenuPembayaranListrik(){
        System.out.println("===== MENU PEMBAYARAN LISTRIK =====");
        System.out.println("1. Rp. 50.000");
        System.out.println("2. Rp. 100.000");
        System.out.println("3. Rp. 250.000");
        System.out.println("4. Rp. 500.000");
        System.out.println("5. Cancel");
        System.out.print("Pilih menu (1-5): ");
    }

    private void tampilkanMenuPembayaranPDAM(){
        System.out.println("===== MENU PEMBAYARAN PDAM =====");
        System.out.println("1. Rp. 50.000");
        System.out.println("2. Rp. 100.000");
        System.out.println("3. Rp. 250.000");
        System.out.println("4. Rp. 500.000");
        System.out.println("5. Cancel");
        System.out.print("Pilih menu (1-5): ");
    }

    private void memilihMenuUtama(AtmData tempDataAtm){
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            tampilkanMenu();
            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1 -> cekSaldo(tempDataAtm);
                case 2 -> transfer(tempDataAtm);
                case 3 -> tarikTunai(tempDataAtm);
                case 4 -> memilihMenuPembayaran(tempDataAtm);
                case 5 -> {
                    menulisDataATMKeFile(FILE_ATM);
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
            tampilkanMenuPembayaran();
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
            targetNoKartu = cariDataATM(inputTargetNoKartu);
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

    private void topupPulsa(AtmData dataAtm) {
        System.out.print("Isikan Nomor Tujuan Top Up");
        scanner.next();
        int number;
        double saldoIni = dataAtm.getSaldo();
        boolean filled = false;
        do {
            tampilkanMenuTopUp();
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
        tampilkanMenuPembayaranListrik();
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
        tampilkanMenuPembayaranPDAM();
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

    private void menulisDataATMKeFile(String namaFile){
        StringBuilder data = new StringBuilder();
        for (AtmData atmData : dataATMArray) {
            data.append("Nomor Kartu: ").append(atmData.getNomorKartu()).append(", PIN: ").append(atmData.getPin()).append(", Saldo: ").append(atmData.getSaldo()).append("\n");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            writer.write(data.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private AtmData login(){
        AtmData tempDataAtm;
        boolean found = false;
        do {
            System.out.print("Masukan nomor kartu ATM: ");
            String tempNoKartu = scanner.nextLine();
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
        return tempDataAtm;
    }
    
    private AtmLogData[] bacaDataLogATMDariFile() {
    	try (BufferedReader br = new BufferedReader(new FileReader("src/data_log.txt"))){
            String line;
            int jumlahData = 0;
            while ((line = br.readLine()) != null){
                if (!line.isEmpty()){
                    jumlahData++;
                }
            }

            AtmLogData[] dataLogATMArray = new AtmLogData[jumlahData];
            br.close();

            BufferedReader newBr = new BufferedReader(new FileReader("src/data_log.txt"));
            int index = 0;

            while ((line = newBr.readLine()) != null){
                if (line.isEmpty()){
                    continue;
                }

                String[] data = line.split(", ");
                String[] dataLog1 = data[1].split("Log: ");
                String[] dataLog2 = dataLog1[1].split("],");
                String[] dataEachLog = Arrays.copyOf(dataLog2, dataLog2.length-1);

                String nomorKartu = data[0].split(": ")[1];
                int jumlahLog = dataEachLog.length;

                dataLogATMArray[index] = new AtmLogData(nomorKartu, jumlahLog);
                for (int i = 0; i < dataEachLog.length; i++){
                    String[] dataEachLog2 = dataEachLog[i].split(",Da");
                    for (int u = 0; u < dataEachLog2.length; u++){
                        String[] dataSmall = dataEachLog2[u].split(": ");
                        for (int o = 0; o < dataSmall.length; o++){
                            if (u % 2 == 0){
                                dataLogATMArray[index].setLog(dataSmall[0], o);
                                dataLogATMArray[index].setSum(dataSmall[1], o);
                            } else {
                                dataLogATMArray[index].setDate(dataSmall[1], o);
                            }
                            System.out.println(dataSmall[o] + o);
                        }
                    }
                }
                for (int i = 0; i < dataLogATMArray.length; i++){
                    System.out.println(dataLogATMArray[i].getNomorKartu());
                    dataLogATMArray[i].printLog();
                }
                index++;
            }
            return dataLogATMArray;
    	} catch (IOException e) {
    		e.printStackTrace();
            return null;
    	}
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

