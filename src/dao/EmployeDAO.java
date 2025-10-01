package dao;

import model.entities.Employe;
import model.enums.SecteurEmploi;
import model.enums.TypePersonne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAO {
    private static Connection connection = DatabaseConnection.getInstance().getConnection();

    public EmployeDAO(Connection connection) {
        this.connection= connection;
    }

    public void ajouterEmploye(Employe e) throws SQLException {

            String sqlPersonne = "INSERT INTO personnes " +
                    "(nom, prenom, date_naissance, ville, nombre_enfants, investissement, placement, " +
                    "situation_familiale, date_creation, score, type_personne) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::type_personne) RETURNING id_personne";

            String sqlEmploye = "INSERT INTO employes " +
                    "(id_personne, salaire, anciennete_mois, poste, type_contrat, secteur) " +
                    "VALUES (?, ?, ?, ?, ?, ?::secteur_emploi)";

            try (
                    PreparedStatement stmtPersonne = connection.prepareStatement(sqlPersonne);
            ) {

                stmtPersonne.setString(1, e.getNom());
                stmtPersonne.setString(2, e.getPrenom());
                stmtPersonne.setDate(3, Date.valueOf(e.getDateDeNaissance()));
                stmtPersonne.setString(4, e.getVille());
                stmtPersonne.setInt(5, e.getNombreEnfants());
                stmtPersonne.setDouble(6, e.getInvestissement());
                stmtPersonne.setDouble(7, e.getPlacement());
                stmtPersonne.setString(8, e.getSituationFamiliale());
                stmtPersonne.setDate(9, Date.valueOf(e.getCreatedAt()));
                stmtPersonne.setDouble(10, e.getScore());
                stmtPersonne.setString(11, TypePersonne.EMPLOYE.name());

                ResultSet rs = stmtPersonne.executeQuery();
                int idPersonne = -1;
                if (rs.next()) {
                    idPersonne = rs.getInt("id_personne");
                }

                try (PreparedStatement stmtEmploye = connection.prepareStatement(sqlEmploye)) {
                    stmtEmploye.setInt(1, idPersonne);
                    stmtEmploye.setDouble(2, e.getSalaire());
                    stmtEmploye.setInt(3, e.getAnciennete());
                    stmtEmploye.setString(4, e.getPoste());
                    stmtEmploye.setString(5, e.getTypeContrat());
                    stmtEmploye.setString(6, e.getSecteur().name());

                    stmtEmploye.executeUpdate();
                }

                System.out.println("✅ Employé ajouté avec succès (id=" + idPersonne + ")");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    public void supprimerEmploye(int idPersonne) throws SQLException {
        String sql = "DELETE FROM personnes WHERE id_personne = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPersonne);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Employé supprimé avec succès (id=" + idPersonne + ")");
            } else {
                System.out.println("⚠ Aucun employé trouvé avec l'id=" + idPersonne);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    public void modifierEmploye(Employe e) throws SQLException {

        String sqlPersonne = "UPDATE personnes SET " +
                "nom = ?, prenom = ?, date_naissance = ?, ville = ?, nombre_enfants = ?, " +
                "investissement = ?, placement = ?, situation_familiale = ?, date_creation = ?, " +
                "score = ? WHERE id_personne = ?";


        String sqlEmploye = "UPDATE employes SET " +
                "salaire = ?, anciennete_mois = ?, poste = ?, type_contrat = ?, secteur = ?::secteur_emploi " +
                "WHERE id_personne = ?";

        try (
                PreparedStatement stmtPersonne = connection.prepareStatement(sqlPersonne);
                PreparedStatement stmtEmploye = connection.prepareStatement(sqlEmploye)
        ) {
            // === Table personnes ===
            stmtPersonne.setString(1, e.getNom());
            stmtPersonne.setString(2, e.getPrenom());
            stmtPersonne.setDate(3, Date.valueOf(e.getDateDeNaissance()));
            stmtPersonne.setString(4, e.getVille());
            stmtPersonne.setInt(5, e.getNombreEnfants());
            stmtPersonne.setDouble(6, e.getInvestissement());
            stmtPersonne.setDouble(7, e.getPlacement());
            stmtPersonne.setString(8, e.getSituationFamiliale());
            stmtPersonne.setDate(9, Date.valueOf(e.getCreatedAt()));
            stmtPersonne.setDouble(10, e.getScore());
            stmtPersonne.setInt(11, e.getIdPersonne());
            stmtPersonne.executeUpdate();


            stmtEmploye.setDouble(1, e.getSalaire());
            stmtEmploye.setInt(2, e.getAnciennete());
            stmtEmploye.setString(3, e.getPoste());
            stmtEmploye.setString(4, e.getTypeContrat());
            stmtEmploye.setString(5, e.getSecteur().name());
            stmtEmploye.setInt(6, e.getIdPersonne()); // ID pour WHERE
            stmtEmploye.executeUpdate();

            System.out.println("✅ Employé modifié avec succès (id=" + e.getIdPersonne() + ")");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    public List<Employe> listerEmployes() throws SQLException {
        List<Employe> employes = new ArrayList<>();

        String sql = "SELECT p.id_personne, p.nom, p.prenom, p.date_naissance, p.ville, " +
                "p.nombre_enfants, p.investissement, p.placement, p.situation_familiale, " +
                "p.date_creation, p.score, e.salaire, e.anciennete_mois, e.poste, e.type_contrat, e.secteur " +
                "FROM personnes p " +
                "JOIN employes e ON p.id_personne = e.id_personne " +
                "WHERE p.type_personne = 'EMPLOYE'";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employe e = new Employe(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance").toLocalDate(),
                        rs.getString("ville"),
                        rs.getInt("nombre_enfants"),
                        rs.getDouble("investissement"),
                        rs.getDouble("placement"),
                        rs.getString("situation_familiale"),
                        rs.getDate("date_creation").toLocalDate(),
                        rs.getDouble("score"),
                        rs.getDouble("salaire"),
                        rs.getInt("anciennete_mois"),
                        rs.getString("poste"),
                        rs.getString("type_contrat"),
                        SecteurEmploi.valueOf(rs.getString("secteur"))
                );
                e.setIdPersonne(rs.getInt("id_personne"));
                employes.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

        return employes;
    }


}


