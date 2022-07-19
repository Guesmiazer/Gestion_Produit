/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author aissa
 * @param <T>
 */
public interface IService<T> {

    public void ajouter(T t);

    public List<T> afficher();

    public void modifier(String nom, double prix);
    
    public void modifParNom(T t);

    public void suprimer(T t );

    public Boolean exist(T t);

    public T getById(int id);
}
