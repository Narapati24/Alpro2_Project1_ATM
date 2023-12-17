package bank;

public class AtmLogData {
    private String nomorKartu;
    private String[] log, sum, date;
    AtmLogData(String nomorKartu, int jumlahLog){
        this.nomorKartu = nomorKartu;
        this.log = new String[jumlahLog];
        this.sum = new String[jumlahLog];
        this.date = new String[jumlahLog];
    }

    public void printLog(){
        for (int i = 0; i < this.log.length; i++)
            System.out.println(this.log[i] + ": " + this.sum[i] + " On Date: " + this.date[i]);
    }

    public void setDate(String date, int i){
        this.date[i] = date;
    }

    public void setLog(String log, int i){
        this.log[i] = log;
    }

    public void setSum(String sum, int i){
        this.sum[i] = sum;
    }

    public String getNomorKartu(){
        return this.nomorKartu;
    }
}
