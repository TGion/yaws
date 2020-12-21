package de.karrieretutor.springboot.domain;

import de.karrieretutor.springboot.Const;
import de.karrieretutor.springboot.enums.Zahlungsart;
import org.springframework.validation.BindingResult;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static de.karrieretutor.springboot.Const.CUSTOMER;
import static javax.persistence.CascadeType.ALL;

import java.math.BigInteger;


@Entity
public class Kunde {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{validation.adresse.vorname}")
    private String vorname;

    @NotBlank(message = "{validation.adresse.nachname}")
    private String nachname;

    @NotBlank(message = "{validation.adresse.strasse}")
    private String strasse;

    @NotBlank(message = "{validation.adresse.plz}")
    private String plz;

    @NotBlank(message = "{validation.adresse.ort}")
    private String ort;

    @NotNull(message = "{validation.zahlungsart.zahlungsart}")
    private Zahlungsart zahlungsart;

    private String iban;
    private String kreditkartenNr;

    @Email
    @NotBlank(message = "{validation.zahlungsart.email}")
    private String email;
    private String password;

    private String sprache = Locale.GERMAN.getLanguage();

    @OneToMany(mappedBy = CUSTOMER, cascade = ALL)
    private List<Bestellung> bestellungen = new ArrayList<>();

    public Kunde() {}

    public Kunde(String email, String passwort) {
        this.email = email;
        this.password = passwort;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    public String getNameFormatiert() {
        return vorname + " " + nachname;
    }

    public String getNachname() {
        return nachname;
    }
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStrasse() {
        return strasse;
    }
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }
    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }
    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Zahlungsart getZahlungsart() {
        return zahlungsart;
    }
    public void setZahlungsart(Zahlungsart zahlungsart) {
        this.zahlungsart = zahlungsart;
    }

    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getKreditkartenNr() {
        return kreditkartenNr;
    }
    public void setKreditkartenNr(String kreditkartenNr) {
        this.kreditkartenNr = kreditkartenNr;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSprache() {
        return sprache;
    }
    public void setSprache(String sprache) {
        this.sprache = sprache;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Bestellung> getBestellungen() {
        return bestellungen;
    }
    public void setBestellungen(List<Bestellung> bestellungen) {
        this.bestellungen = bestellungen;
    }


    @Transient
    public boolean validiereZahlungsart(BindingResult result) {
        

        return false;
    }

    // TODO: implementieren
    public boolean validiereIBAN(String accountNumber) {
        final int IBANNUMBER_MIN_SIZE = 15;
        final int IBANNUMBER_MAX_SIZE = 34;
        final BigInteger IBANNUMBER_MAGIC_NUMBER = new BigInteger("97");

        String newAccountNumber = accountNumber.trim();

        // Check that the total IBAN length is correct as per the country. If not, the IBAN is invalid.
        if (newAccountNumber.length() < IBANNUMBER_MIN_SIZE || newAccountNumber.length() > IBANNUMBER_MAX_SIZE) {
            return false;
        }

        // Move the four initial characters to the end of the string.
        newAccountNumber = newAccountNumber.substring(4) + newAccountNumber.substring(0, 4);

        // Replace each letter in the string with two digits, thereby expanding the string, where A = 10, B = 11, ..., Z = 35.
        StringBuilder numericAccountNumber = new StringBuilder();
        for (int i = 0;i < newAccountNumber.length();i++) {
            numericAccountNumber.append(Character.getNumericValue(newAccountNumber.charAt(i)));
        }

        // Interpret the string as a decimal integer and compute the remainder of that number on division by 97.
        BigInteger ibanNumber = new BigInteger(numericAccountNumber.toString());
        return ibanNumber.mod(IBANNUMBER_MAGIC_NUMBER).intValue() == 1;
    }

    // TODO: implementieren
    private boolean validiereKreditkartenNr() {
        return false;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", strasse='" + strasse + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                ", zahlungsart=" + zahlungsart +
                ", iban='" + iban + '\'' +
                ", kreditkartenNr='" + kreditkartenNr + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
