package dao;

import model.entities.Professionnel;
import model.enums.SecteurActivite;
import model.enums.TypePersonne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public void modifierProfessionnel(Professionnel p) throws SQLException {
        String sqlPersonne = "UPDATE personnes SET nom=?, prenom=?, date_naissance=?, ville=?, " +
                "nombre_enfants=?, investissement=?, placement=?, situation_familiale=?, " +
                "date_creation=?, score=? WHERE id_personne=?";
        String sqlProfessionnel = "UPDATE professionnels SET revenu=?, immatriculation_fiscale=?, " +
                "secteur_activite=?, activite=? WHERE id_personne=?";

        try (PreparedStatement stmtPersonne = connection.prepareStatement(sqlPersonne);
             PreparedStatement stmtPro = connection.prepareStatement(sqlProfessionnel)) {

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
            stmtPersonne.setInt(11, p.getIdPersonne());
            stmtPersonne.executeUpdate();

            stmtPro.setDouble(1, p.getRevenu());
            stmtPro.setString(2, p.getImmatriculationFiscale());
            stmtPro.setString(3, p.getSecteurActivite().name());
            stmtPro.setString(4, p.getActivite());
            stmtPro.setInt(5, p.getIdPersonne());
            stmtPro.executeUpdate();

            System.out.println("✅ Professionnel modifié avec succès (id=" + p.getIdPersonne() + ")");
        }}

        public void supprimerProfessionel(int idPersonne) throws SQLException {
            String sql = "DELETE FROM personnes WHERE id_personne = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, idPersonne);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("✅ Professionel supprimé avec succès (id=" + idPersonne + ")");
                } else {
                    System.out.println("⚠ Aucun Professionnel trouvé avec l'id=" + idPersonne);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
        public List<Professionnel> listerProfessionnels() throws SQLException {
            List<Professionnel> pros = new ArrayList<>();
            String sql = "SELECT p.id_personne, p.nom, p.prenom, p.date_naissance, p.ville, " +
                    "p.nombre_enfants, p.investissement, p.placement, p.situation_familiale, " +
                    "p.date_creation, p.score, pr.revenu, pr.immatriculation_fiscale, pr.secteur_activite, pr.activite " +
                    "FROM personnes p " +
                    "JOIN professionnels pr ON p.id_personne = pr.id_personne " +
                    "WHERE p.type_personne='PROFESSIONNEL'";

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Professionnel p1 = new Professionnel(
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
                            rs.getDouble("revenu"),
                            rs.getString("immatriculation_fiscale"),
                            SecteurActivite.valueOf(rs.getString("secteur_activite")),
                            rs.getString("activite")
                    );
                    p1.setIdPersonne(rs.getInt("id_personne"));
                    pros.add(p1);
                }
            }
            return pros;
        }
    }

