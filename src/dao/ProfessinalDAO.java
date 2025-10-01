package dao;

import model.entities.Professionnel;
import model.enums.SecteurActivite;
import model.enums.TypePersonne;

import java.sql.*;

public class ProfessinalDAO {
    private static Connection connection = DatabaseConnection.getInstance().getConnection();

    public ProfessinalDAO(Connection connection) {
        this.connection= connection;
    }

        public void ajouterProfessionnel(Professionnel p) throws SQLException  {
            String sqlPersonne = "INSERT INTO personnes " +
                    "(nom, prenom, date_naissance, ville, nombre_enfants, investissement, placement, " +
                    "situation_familiale, date_creation, score, type_personne) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::type_personne) RETURNING id_personne";

            String sqlProfessionnel = "INSERT INTO professionnels " +
                    "(id_personne, revenu, immatriculation_fiscale, secteur_activite, activite) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmtPersonne = connection.prepareStatement(sqlPersonne)) {
                // === Insert dans PERSONNES ===
                stmtPersonne.setString(1, p.getNom());
                stmtPersonne.setString(2, p.getPrenom());
                stmtPersonne.setDate(3, Date.valueOf(p.getDateDeNaissance()));
                stmtPersonne.setString(4, p.getVille());
                stmtPersonne.setInt(5, p.getNombreEnfants());
                stmtPersonne.setDouble(6, p.getInvestissement());
                stmtPersonne.setDouble(7, p.getPlacement());
                stmtPersonne.setString(8, p.getSituationFamiliale());
                stmtPersonne.setDate(9, Date.valueOf(p.getCreatedAt()));
                stmtPersonne.setDouble(10, p.getScore());
                stmtPersonne.setString(11, TypePersonne.PROFESSIONNEL.name());

                ResultSet rs = stmtPersonne.executeQuery();
                int idPersonne = -1;
                if (rs.next()) {
                    idPersonne = rs.getInt("id_personne");
                }

                // === Insert dans PROFESSIONNELS ===
                try (PreparedStatement stmtPro = connection.prepareStatement(sqlProfessionnel)) {
                    stmtPro.setInt(1, idPersonne);
                    stmtPro.setDouble(2, p.getRevenu());
                    stmtPro.setString(3, p.getImmatriculationFiscale());
                    stmtPro.setString(4, p.getSecteurActivite().name());
                    stmtPro.setString(5, p.getActivite());

                    stmtPro.executeUpdate();
                }

                System.out.println("✅ Professionnel ajouté avec succès (id=" + idPersonne + ")");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

}

