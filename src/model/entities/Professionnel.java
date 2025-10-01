package model.entities;

import model.enums.SecteurActivite;

import java.time.LocalDate;

public class Professionnel extends Person {
    private double revenu;
    private String immatriculationFiscale;
    private SecteurActivite secteurActivite;
    private String activite;
    private int idPersonne;

    public Professionnel(String nom, String prenom, LocalDate dateDeNaissance, String ville,
                         int nombreEnfants, double investissement, double placement,
                         String situationFamiliale, LocalDate createdAt, double score,
                         double revenu, String immatriculationFiscale,
                         SecteurActivite secteurActivite, String activite) {
        super(nom, prenom, dateDeNaissance, ville, nombreEnfants, investissement,
                placement, situationFamiliale, createdAt, score);
        this.revenu = revenu;
        this.immatriculationFiscale = immatriculationFiscale;
        this.secteurActivite = secteurActivite;
        this.activite = activite;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public double getRevenu() {

        return revenu;
    }

    public void setRevenu(double revenu) {
        this.revenu = revenu;
    }

    public String getImmatriculationFiscale() {
        return immatriculationFiscale;
    }

    public void setImmatriculationFiscale(String immatriculationFiscale) {
        this.immatriculationFiscale = immatriculationFiscale;
    }

    public SecteurActivite getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(SecteurActivite secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
    @Override
    public void calculerScore() {

    }
}
