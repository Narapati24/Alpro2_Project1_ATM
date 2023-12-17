package bank;

public class AtmLogData {
    private String nomorKartu;
    private String[] log;
    AtmLogData(String nomorKartu, int jumlahLog){
        this.nomorKartu = nomorKartu;
        this.log = new String[jumlahLog];
    }
}
