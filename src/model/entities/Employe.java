package model.entities;

import model.enums.SecteurEmploi;

import java.time.LocalDate;

public class Employe extends Person {
    private double salaire;
    private int anciennete;           // en années
    private String poste;
    private String typeContrat;       // CDI, CDD, etc.
    private SecteurEmploi secteur;
    private int idPersonne;

    public Employe(String nom, String prenom, LocalDate dateDeNaissance, String ville,
                   int nombreEnfants, double investissement, double placement,
                   String situationFamiliale, LocalDate createdAt, double score,
                   double salaire, int anciennete, String poste, String typeContrat,
                   SecteurEmploi secteur) {
        super(nom, prenom, dateDeNaissance, ville, nombreEnfants, investissement, placement, situationFamiliale, createdAt, score);
        this.salaire = salaire;
        this.anciennete = anciennete;
        this.poste = poste;
        this.typeContrat = typeContrat;
        this.secteur = secteur;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public int getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(int anciennete) {
        this.anciennete = anciennete;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public SecteurEmploi getSecteur() {
        return secteur;
    }

    public void setSecteur(SecteurEmploi secteur) {
        this.secteur = secteur;
    }

    @Override
    public String toString() {
        return String.format("Employe[nom=%s, prenom=%s, salaire=%.2f, poste=%s, secteur=%s, score=%.2f]",
                getNom(), getPrenom(), salaire, poste, secteur, getScore());
    }
    @Override
    public void calculerScore() {

    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }
}
