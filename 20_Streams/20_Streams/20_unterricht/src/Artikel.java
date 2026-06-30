public class Artikel {
    // Instanzvariablen, Atrribute
    private final int artikelnummer;
    private final String bezeichnung;
    private final double preis;
    private final String hersteller;

    public Artikel(int artikelnummer, String bezeichnung, double preis, String hersteller) {
        this.artikelnummer = artikelnummer;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.hersteller = hersteller;
    }

    public int getArtikelnummer() {
        return artikelnummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public double getPreis() {
        return preis;
    }

    public String getHersteller() {
        return hersteller;
    }

    @Override
    public String toString() {
        return "Artikel{" +
                "artikelnummer=" + artikelnummer +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", preis=" + preis +
                ", hersteller='" + hersteller + '\'' +
                '}';
    }
}
