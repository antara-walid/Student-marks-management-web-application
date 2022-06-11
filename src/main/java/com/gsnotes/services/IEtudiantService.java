package com.gsnotes.services;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public interface IEtudiantService {

    public void addEtudiant(Etudiant etudiant);


    public Etudiant getEtudiantByCne(String cin);

    public String inscrireEtudiants(List<ArrayList<Object>> students);
    public String inscrireEtudiant(Etudiant etudiant,Long  idNiveau);
    public String reinscrireEtudiant(Etudiant etudiant,Long  idNiveau);

    public boolean doesNotContradictPastYear(Etudiant etudiant,Long  idNiveau);
    public boolean sameDataForStudent(Etudiant etudiant);
    public List<ArrayList<Etudiant>>  getListsReainscrire();
    public void updateEtudiant(Etudiant etudiant);

    public List<Etudiant> getAllEtudiants();

}
