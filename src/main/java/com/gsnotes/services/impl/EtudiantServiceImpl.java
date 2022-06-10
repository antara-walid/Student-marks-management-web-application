package com.gsnotes.services.impl;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.Filiere;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.Niveau;
import com.gsnotes.dao.IEtudiantDao;
import com.gsnotes.dao.IInscriptionAnnuelle;
import com.gsnotes.services.IEtudiantService;
import com.gsnotes.services.IFiliereService;
import com.gsnotes.services.IInscriptionAnnuelleService;
import com.gsnotes.services.INiveauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;


@Service
@Transactional
public class EtudiantServiceImpl implements IEtudiantService {


    @Autowired
    private IEtudiantDao etudiantDao;
    @Autowired
    private IInscriptionAnnuelleService inscriptionAnnuelleService;
    @Autowired
    private INiveauService iNiveauService;
    @Autowired
    private IFiliereService iFiliereService;

    public Etudiant getEtudiantByCne(String cin) {

        return etudiantDao.getEtudiantByCne(cin);

    }

    @Override
    public String inscrireEtudiants(List<ArrayList<Object>> students) {
        String message="";
        ListIterator<ArrayList<Object>> lit = students.listIterator(1);
        while(lit.hasNext())
        {
            ArrayList<Object> row = lit.next();
            String type = row.get(5).toString().toUpperCase();
            if(type.equals("INSCRIPTION")){
                Etudiant etudiant = getEtudiantByCne(row.get(1).toString());
                if(etudiant != null)
                {
                    message +="the student with cne :"+etudiant.getCne()+" is already registered\n";
                }else
                {
                    // adding the student to database
                    //1. creating the student object
                    etudiant = new Etudiant();
                    etudiant.setCne(row.get(1).toString());
                    etudiant.setNom(row.get(2).toString());
                    etudiant.setPrenom(row.get(3).toString());
                    //2. creating inscription annuel object
                    addEtudiant(etudiant);
                    Niveau niveau = new Niveau();
                    Long  idNiveau = Math.round(Double.parseDouble( row.get(4).toString() ) );
                    niveau.setIdNiveau(idNiveau);
                }
            }
        }
        return message;
    }

    @Override
    public void addEtudiant(Etudiant etudiant) {
        etudiantDao.save(etudiant);
    }
}
