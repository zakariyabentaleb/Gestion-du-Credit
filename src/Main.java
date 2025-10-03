import dao.DatabaseConnection;
import dao.EmployeDAO;
import model.entities.Employe;
import model.enums.SecteurEmploi;
import view.MenuView;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        MenuView menuView = new MenuView();

        menuView.affichermenu();

    }}
