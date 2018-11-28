package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5; // Aloitustaulukon koko
    public final static int OLETUSKASVATUS = 5; // Taulukon oletuskasvatuskoko
    private int kasvatuskoko; // Uusi taulukko on tämän verran vanhaa suurempi
    private int[] luvut; // Joukon luvut säilytetään taulukossa, jonka täyttö alkaa alkupäästä
    private int alkioidenLkm; // Tyhjässä joukossa alkioiden määrä on nolla

    public IntJoukko() {
        luvut = new int[KAPASITEETTI];
        alustaJoukko();
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        luvut = new int[kapasiteetti];
        alustaJoukko();
        this.kasvatuskoko = OLETUSKASVATUS;

    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetin on oltava positiivinen");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kasvatuskoon on oltava positiivinen");
        }
        luvut = new int[kapasiteetti];
        alustaJoukko();
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (alkioidenLkm == 0 || !kuuluu(luku)) {
            luvut[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (alkioidenLkm % luvut.length == 0) {
                kasvataTaulukkoa();
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == luvut[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int kohta = luvunIndeksi(luku);
        if (kohta != -1) {
            luvut[kohta] = 0;
            for (int i = kohta; i < alkioidenLkm - 1; i++) {
                int apu = luvut[i];
                luvut[i] = luvut[i + 1];
                luvut[i + 1] = apu;
            }
            alkioidenLkm--;
            return true;
        }
        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }
    }

    public int alkioidenLukumaara() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else {
            String merkkijono = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                merkkijono += luvut[i];
                merkkijono += ", ";
            }
            merkkijono += luvut[alkioidenLkm - 1];
            merkkijono += "}";
            return merkkijono;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = luvut[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        x.lisaaTaulukonLuvutJoukkoon(aTaulu);
        x.lisaaTaulukonLuvutJoukkoon(bTaulu);
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        z.lisaaTaulukonLuvutJoukkoon(aTaulu);
        z.lisaaTaulukonLuvutJoukkoon(bTaulu);
        return z;
    }

    private void alustaJoukko() {
        for (int i = 0; i < luvut.length; i++) {
            luvut[i] = 0;
        }
        alkioidenLkm = 0;
    }

    private int luvunIndeksi(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == luvut[i]) {
                return i;
            }
        }
        return -1;
    }

    private void kasvataTaulukkoa() {
        int[] vanhaTaulukko = luvut;
        luvut = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(vanhaTaulukko, luvut);
    }

    public void lisaaTaulukonLuvutJoukkoon(int[] taulukko) {
        for (int i = 0; i < taulukko.length; i++) {
            this.lisaa(taulukko[i]);
        }
    }
}
