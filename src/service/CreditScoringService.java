package service;

import model.entities.Credit;
import model.entities.Employe;
import model.enums.DecisionCredit;

public class CreditScoringService {


    public static double calculerScore(Employe e, boolean clientExistant, int incidents, int retards, boolean impayeRegle, boolean impayeNonRegle) {
        double score = 0;

        // ===============================
        // 1. STABILITÉ PROFESSIONNELLE (30 pts)
        // ===============================
        switch (e.getSecteur()) {
            case PUBLIC: // CDI secteur public
                score += 25;
                break;
            case GRANDE_ENTREPRISE: // CDI secteur privé grande entreprise
                score += 15;
                break;
            case PME: // CDI secteur privé PME
                score += 12;
                break;

        }

        // Ancienneté
        if (e.getAnciennete() >= 60) score += 5; // >= 5 ans
        else if (e.getAnciennete() >= 24) score += 3; // 2-5 ans
        else if (e.getAnciennete() >= 12) score += 1; // 1-2 ans
        // < 1 an => 0 bonus

        // ===============================
        // 2. CAPACITÉ FINANCIÈRE (30 pts)
        // ===============================
        double salaire = e.getSalaire();
        if (salaire >= 10000) score += 30;
        else if (salaire >= 8000) score += 25;
        else if (salaire >= 5000) score += 20;
        else if (salaire >= 3000) score += 15;
        else score += 10;

        // ===============================
        // 3. HISTORIQUE (15 pts)
        // ===============================
        if (impayeNonRegle) score -= 10;
        if (impayeRegle) score += 5;

        if (retards >= 1 && retards <= 3) score -= 3;
        if (retards >= 4) score -= 5;
        if (retards > 0 && !impayeNonRegle) score += 3; // retards payés

        if (incidents == 0) score += 10; // aucun incident

        // ===============================
        // 4. RELATION CLIENT (10 pts)
        // ===============================
        if (!clientExistant) {
            // Nouveau client
            int age = java.time.Period.between(e.getDateDeNaissance(), java.time.LocalDate.now()).getYears();
            if (age >= 18 && age <= 25) score += 4;
            else if (age <= 35) score += 8;
            else if (age <= 55) score += 10;
            else score += 6;

            // Situation familiale
            if ("Marié".equalsIgnoreCase(e.getSituationFamiliale())) score += 3;
            else score += 2;

            // Enfants à charge
            if (e.getNombreEnfants() == 0) score += 2;
            else if (e.getNombreEnfants() <= 2) score += 1;
            // >2 enfants => 0 pt
        } else {
            // Client existant
            if (e.getAnciennete() > 36) score += 10;
            else if (e.getAnciennete() >= 12) score += 8;
            else score += 5;
        }

        // ===============================
        // 5. CRITÈRES COMPLÉMENTAIRES (10 pts)
        // ===============================
        if (e.getInvestissement() > 0 || e.getPlacement() > 0) {
            score += 10;
        }

        return Math.min(score, 100); // max 100
    }



    public boolean estEligible(double score, boolean estNouveau) {
        if (estNouveau) {
            return score >= 70;
        } else {
            return score >= 60;
        }
    }


    public double capaciteEmprunt(Employe e, boolean estNouveau, double score) {
        double salaire = e.getSalaire();

        if (estNouveau) {
            return 4 * salaire;
        } else {
            if (score > 80) {
                return 10 * salaire;
            } else if (score >= 60) {
                return 7 * salaire;
            } else {
                return 0;
            }
        }
    }

    public static double calculerScore(Employe e) {
        // valeurs par défaut : nouveau client, pas d’incidents, pas de retards
        return calculerScore(e, false, 0, 0, false, false);
    }

    public void prendreDecision(Credit credit, Employe e, boolean estNouveau) {
        double score = calculerScore(e);
        boolean eligible = estEligible(score, estNouveau);
        double maxEmprunt = capaciteEmprunt(e, estNouveau, score);

        if (!eligible) {
            credit.setDecision(DecisionCredit.REFUS_AUTOMATIQUE);
            credit.setMontantOctroye(0);
        } else if (credit.getMontantDemande() <= maxEmprunt && score > 80) {
            credit.setDecision(DecisionCredit.ACCORD_IMMEDIAT);
            credit.setMontantOctroye(credit.getMontantDemande());
        } else {
            credit.setDecision(DecisionCredit.ETUDE_MANUELLE);
            credit.setMontantOctroye(Math.min(credit.getMontantDemande(), maxEmprunt));
        }
    }
}

