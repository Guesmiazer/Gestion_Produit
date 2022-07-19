/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entite.Panier;
import Utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author ASUS
 */
public class ServicePanier implements IService<Panier> {
    private Connection con = DataSource.getInstance().getConnection();
    String lienImage = null;

    @Override
    public void ajouter(Panier t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Panier> afficher() {
         List<Panier> list = new ArrayList<>();

        try {
//            System.out.println("************************Liste des panier************************");
            String requete = "SELECT * FROM panier";
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Panier(rs.getString(1), rs.getDouble(2)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public void modifier(String nom, double prix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifParNom(Panier t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void suprimer(Panier t) {
        try {
            Statement ste = con.createStatement();
            String requetedelete = "delete from panier where nom_produit='" + t.getNom_produit()+"'";
            ste.execute(requetedelete);
            System.out.println("Produit supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void truncate(){
        try {
            Statement ste = con.createStatement();
            String requete = "truncate table panier";
            ste.execute(requete);
            System.out.println("Panier vide !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public Boolean exist(Panier t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Panier getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
