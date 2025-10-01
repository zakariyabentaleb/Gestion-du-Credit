package dao;

import model.entities.Employe;
import model.enums.TypePersonne;

import java.sql.*;

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
    }


