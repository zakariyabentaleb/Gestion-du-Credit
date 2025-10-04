package model.entities;

import model.enums.DecisionCredit;

import java.time.LocalDate;

public class Credit {
    private int idCredit;
    private LocalDate dateCredit;
    private double montantDemande;
    private double montantOctroye;
    private double tauxInteret;
    private int dureeEnMois;
    private String typeCredit;
    private DecisionCredit decision;


    public Credit(LocalDate dateCredit, double montantDemande, String typeCredit, int dureeEnMois) {
        this.dateCredit = dateCredit;
        this.montantDemande = montantDemande;
        this.typeCredit = typeCredit;
        this.dureeEnMois = dureeEnMois;
    }

    public Credit() {

    }


    public int getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(int idCredit) {
        this.idCredit = idCredit;
    }

    public LocalDate getDateCredit() {
        return dateCredit;
    }

    public void setDateCredit(LocalDate dateCredit) {
        this.dateCredit = dateCredit;
    }

    public double getMontantDemande() {
        return montantDemande;
    }

    public void setMontantDemande(double montantDemande) {
        this.montantDemande = montantDemande;
    }

    public double getMontantOctroye() {
        return montantOctroye;
    }

    public void setMontantOctroye(double montantOctroye) {
        this.montantOctroye = montantOctroye;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public int getDureeEnMois() {
        return dureeEnMois;
    }

    public void setDureeEnMois(int dureeEnMois) {
        this.dureeEnMois = dureeEnMois;
    }

    public String getTypeCredit() {
        return typeCredit;
    }

    public void setTypeCredit(String typeCredit) {
        this.typeCredit = typeCredit;
    }

    public DecisionCredit getDecision() {
        return decision;
    }

    public void setDecision(DecisionCredit decision) {
        this.decision = decision;
    }

    public void setDateDeCredit(LocalDate now) {
    }
}


