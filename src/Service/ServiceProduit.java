/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entite.Panier;
import Entite.Produit;
import java.sql.*;
import Utils.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class ServiceProduit implements IService<Produit> {

    private Connection con = DataSource.getInstance().getConnection();
    String lienImage = null;

    @Override
    public void ajouter(Produit t) {
        
            try {
                String req="insert into produit(nom,prix) values"      
            +"('"+t.getNom()+"','"+t.getPrix()+"')";
            Statement st=con.createStatement();
            st.executeUpdate(req);
            System.out.println("Ajout Produit avec succes");

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        
    }

    @Override
    public List<Produit> afficher() {
         List<Produit> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM produit";
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Produit(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
    
    @Override
    public void modifier(String nom, double prix) {
        try {
            String req="update produit set  prix= ?  where nom=? ";
            PreparedStatement ps=con.prepareStatement(req);
            
            ps.setDouble(1, prix);
            ps.setString(2, nom);
            ps.executeUpdate();
            System.out.println("Modification produit avec succes");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void suprimer(Produit t) {
        //if (exist(getById(id))) {
        try {
            Statement ste = con.createStatement();
            String requetedelete = "delete from produit where id=" + t.getId();
            ste.execute(requetedelete);
            System.out.println("Produit supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        /*} else {
            System.out.println("Produit n'existe pas !");
        }*/
    }
    public void somme(){
    }

    
public ArrayList<Produit> RechercheNom(String nom) {

        ArrayList<Produit> produits = new ArrayList<>();
        String requete = "select * from produit where nom ='" + nom + "'";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                produits.add(new Produit(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors d'extraction des données \n" + ex.getMessage());
        }
        return produits;
    }
/*
    public ArrayList<Produit> triNom() {
        ArrayList<Produit> produits = new ArrayList<>();
        String requete = "select * from produit ORDER BY nom DESC";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                produits.add(new Produit(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }

    public ArrayList<Produit> triQuqntite() {
        ArrayList<Produit> produits = new ArrayList<>();
        String requete = "select * from produit ORDER BY quantite DESC";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                produits.add(new Produit(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }

    public ArrayList<Produit> triPrix() {
        ArrayList<Produit> produits = new ArrayList<>();
        String requete = "select * from produit ORDER BY prix DESC";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                produits.add(new Produit(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }

    public int nombre() {

        int i = 0;
        String requete = "SELECT COUNT(*) as nbr FROM produit";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {

                i = rs.getInt("nbr");

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return i;
    }
     */
    @Override
    public Produit getById(int id) {
        try {
            String query = "select * from produit where id=" + id;
            PreparedStatement pst;

            pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produit a = new Produit(rs.getInt(1), rs.getString(2), rs.getDouble(3));
                return a;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public Boolean exist(Produit t) {
        return getById(t.getId()) != null;
    }

    public Produit getByNom(String nom) {
        try {
            String query = "select * from produit where nom='" + nom + "'";
            PreparedStatement pst;

            pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produit a = new Produit(rs.getInt(1), rs.getString(2),  rs.getDouble(3));
                return a;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public void modifier1(int id, String nom, String categorie, double prix, String imgproduit) {
        try {
            String requete = "UPDATE produit SET nom='" + nom + "',prix=" + prix +  "' WHERE id=" + id;
            PreparedStatement pst = con.prepareStatement(requete);
            pst.executeUpdate();
            System.out.println("Produit modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public Boolean produitExist(String nom) {
        
        if(RechercheNom(nom).isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public void modifParNom(Produit t) {
        
        try {
            String requete = "UPDATE produit SET prix=" + t.getPrix() +  "' WHERE nom=" + t.getNom();
            PreparedStatement pst = con.prepareStatement(requete);
            pst.executeUpdate();
            System.out.println("Produit modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public List<String> GetNameProduct() {
         List<String> list = new ArrayList<>();

        try {
            String requete = "SELECT nom FROM produit";
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return list;
    }
    public void addtocart(Produit p) throws SQLException{
        String req="insert into panier(nom_produit,prix_produit) values"      
            +"('"+p.getNom()+"','"+p.getPrix()+"')";
            Statement st=con.createStatement();
            st.executeUpdate(req);
            System.out.println("Ajout Produit dans panier avec succes");
    }
}