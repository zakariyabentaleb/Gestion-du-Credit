package model.entities;

import java.time.LocalDate;

public abstract class Person {
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    private String ville;
    private int nombreEnfants;
    private double investissement;   // montant investi
    private double placement;        // montant placé
    private String situationFamiliale; // ex: Marié, Célibataire
    private LocalDate createdAt;     // date de création du profil
    private double score;

    public Person (String nom, String prenom, LocalDate dateDeNaissance, String ville, int nombreEnfants, double investissement, double placement, String situationFamiliale, LocalDate createdAt, double score){
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.ville = ville;
        this.nombreEnfants = nombreEnfants;
        this.investissement = investissement;
        this.placement = placement;
        this.situationFamiliale = situationFamiliale;
        this.createdAt = createdAt;
        this.score = score;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public double getInvestissement() {
        return investissement;
    }

    public void setInvestissement(double investissement) {
        this.investissement = investissement;
    }

    public double getPlacement() {
        return placement;
    }

    public void setPlacement(double placement) {
        this.placement = placement;
    }

    public String getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(String situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    public abstract void calculerScore();

}
