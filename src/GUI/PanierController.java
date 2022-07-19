/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entite.Panier;
import Service.ServicePanier;
import Utils.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PanierController implements Initializable {

    @FXML
    private ListView<Panier> ListPanier;
     private final ObservableList<Panier> listP = FXCollections.observableArrayList();
    ServicePanier sp = new ServicePanier();
    @FXML
    private Button back;
    @FXML
    private Button vider;
    @FXML
    private Button pdfP;
    private Connection con = DataSource.getInstance().getConnection();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         remplirListPanier();
        ListPanier.setItems(listP);
        // TODO
    }
    
    
    
    private void remplirListPanier() {
        listP.addAll(sp.afficher());
        ListPanier.setCellFactory((ListView<Panier> lv) -> new Cell());
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
       
        if(event.getSource()==back){
            stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("Produit.fxml"));
            root = FXMLLoader.load(getClass().getResource("Produit.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ProduitController lc = loader.getController();
        }
    }

    @FXML
    private void supptable(ActionEvent event) {
        sp.truncate();
        JOptionPane.showMessageDialog(null, "Panier vide!");
            try {
                Stage stageclose=(Stage) ((Node)event.getSource()).getScene().getWindow();

                stageclose.close();
                Parent root=FXMLLoader.load(getClass().getResource("Produit.fxml"));
                Stage stage =new Stage();

                Scene scene = new Scene(root);


                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(testfx.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void imprimer(ActionEvent event) throws IOException, Exception {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\ASUS\\Documents\\Panier.pdf"));
            doc.open();

          Image img = Image.getInstance("C:\\Users\\ASUS\\Documents\\Zoom\\GestionProduitFinalOLD\\src\\IMG\\IMG_9493.jpg");
            img.scaleAbsoluteHeight(91);
            img.scaleAbsoluteWidth(120);
            img.setAlignment(Image.ALIGN_RIGHT);
            doc.open();
            doc.add(img);

            doc.add(new Paragraph(" "));
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.UNDERLINE, BaseColor.BLACK);
            Paragraph p = new Paragraph("Panier ", font);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));

            PdfPTable tabpdf = new PdfPTable(2);
            tabpdf.setWidthPercentage(100);

            PdfPCell cell;
            cell = new PdfPCell(new Phrase("Produit"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);
            

            cell = new PdfPCell(new Phrase("Prix"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);
            
            String requete = "SELECT `nom_produit`, `prix_produit` FROM `panier`";
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cell = new PdfPCell(new Phrase(rs.getString(1)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(rs.getDouble(2))));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);
            }
            cell = new PdfPCell(new Phrase("Somme"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);
            String req = "SELECT SUM(prix_produit) FROM panier";
            PreparedStatement pt = con.prepareStatement(req);
            ResultSet rss = pt.executeQuery(); 
            while (rss.next()) {
                cell = new PdfPCell(new Phrase(String.valueOf(rss.getDouble(1))));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);
            
            }
            
            
            doc.add(tabpdf);
            
            JOptionPane.showMessageDialog(null, "Success !!");

            /**/
            doc.close();

            Desktop.getDesktop().open(new File("C:\\Users\\ASUS\\Documents\\Panier.pdf"));
        } catch (DocumentException | HeadlessException | IOException e) {
            System.out.println("ERROR PDF");
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }
    
    public class Cell extends ListCell<Panier> {

        Button supprimer = new Button("🚮");
        
        Label prix = new Label("");
        
        Label nom = new Label("");
        GridPane pane = new GridPane();
        AnchorPane aPane = new AnchorPane();

        public Cell() {
            
            nom.setStyle("-fx-font-weight: bold; -fx-font-size: 1.5em;");
            GridPane.setConstraints(nom, 1,0,2,3);
            
            GridPane.setConstraints(prix, 2,0,2,3);

            supprimer.setStyle("-fx-text-fill : black;-fx-background-color : none;");
            GridPane.setConstraints(supprimer,4, 0, 4, 3);
            
            
            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true));
            pane.setHgap(1);
            pane.setVgap(1);
            pane.getChildren().setAll(nom,prix,supprimer);
            AnchorPane.setTopAnchor(pane, 0d);
            AnchorPane.setLeftAnchor(pane, 0d);
            AnchorPane.setBottomAnchor(pane, 0d);
            AnchorPane.setRightAnchor(pane, 0d);
            aPane.getChildren().add(pane);
        }
        
        @Override
        protected void updateItem(Panier item, boolean empty) {
            super.updateItem(item, empty);
//            System.out.println(item);
            setGraphic(null);
            setText(null);
            setContentDisplay(ContentDisplay.LEFT);
            if (!empty && item != null) {
                
                nom.setText(item.getNom_produit());
                prix.setText(String.valueOf(item.getPrix_produit()));
                supprimer.setOnMouseEntered( (event) ->

                        supprimer.setStyle("-fx-text-fill : white; -fx-background-color : #870505;")
                       );
                      
                        supprimer.setOnMouseExited( (event) ->

                        supprimer.setStyle("-fx-text-fill : black;-fx-background-color : none;")
                       );
                supprimer.setOnAction(event -> {
                            
                            Panier f = getListView().getItems().get(getIndex());
                            
                            System.out.print(f);
                            sp.suprimer(f);
                            JOptionPane.showMessageDialog(null, "Produit supprime !");
                            listP.clear();
                            remplirListPanier();
                        }
                        );
               
                setGraphic(aPane);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }   
}
