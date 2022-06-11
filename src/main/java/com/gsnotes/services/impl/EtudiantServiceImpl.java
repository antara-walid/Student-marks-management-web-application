package com.gsnotes.services.impl;

import com.gsnotes.bo.*;
import com.gsnotes.bo.Module;
import com.gsnotes.dao.IEtudiantDao;
import com.gsnotes.dao.IInscriptionAnnuelle;
import com.gsnotes.services.IEtudiantService;
import com.gsnotes.services.IFiliereService;
import com.gsnotes.services.IInscriptionAnnuelleService;
import com.gsnotes.services.INiveauService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.*;


@Service
@Transactional
public class EtudiantServiceImpl implements IEtudiantService {


    @Autowired
    private IEtudiantDao etudiantDao;
    @Autowired
    private IInscriptionAnnuelleService inscriptionAnnuelleService;
    @Autowired
    private INiveauService iNiveauService;

    private ArrayList<Etudiant> listReainscrire;
    public Etudiant getEtudiantByCne(String cin) {

        return etudiantDao.getEtudiantByCne(cin);

    }

    @Override
    public String inscrireEtudiants(List<ArrayList<Object>> students) {
        String message="";
        listReainscrire = new ArrayList<>();
        ListIterator<ArrayList<Object>> lit = students.listIterator(1);
        while(lit.hasNext()) {
            ArrayList<Object> row = lit.next();
            String type = row.get(5).toString().toUpperCase();
            Etudiant etudiant = getEtudiantByCne(row.get(1).toString());
            Long  idNiveau = Math.round(Double.parseDouble( row.get(4).toString() ) );
            //1. creating the student object

            if (type.equals("INSCRIPTION")) {
                // check if student is in the database
                if(etudiant != null)
                {
                    message +="the student with cne :"+etudiant.getCne()+" is already registered\n";
                    return message;
                }

                else
                {
                    etudiant = new Etudiant();
                    etudiant.setCne(row.get(1).toString());
                    etudiant.setNom(row.get(2).toString());
                    etudiant.setPrenom(row.get(3).toString());
                    // add student to database inscription


                    message = inscrireEtudiant(etudiant,idNiveau);
                }

            }
            else {
                if(etudiant == null)
                {
                    message +=" Reinscription : the student with cne : "+row.get(1).toString()+" is not registered \n";
                    return message;
                }

                else
                {
                    etudiant = new Etudiant();
                    etudiant.setCne(row.get(1).toString());
                    etudiant.setNom(row.get(2).toString());
                    etudiant.setPrenom(row.get(3).toString());
                    message = reinscrireEtudiant(etudiant,idNiveau);
                    if( !message.isEmpty())
                        return message;
                }
            }

        }

        return message;
    }

    @Override
    public void addEtudiant(Etudiant etudiant) {
        etudiantDao.save(etudiant);
    }


    @Override
    public String inscrireEtudiant(Etudiant etudiant,Long  idNiveau)
    {
        String message ="";

            // e- check if niveau is in the database
            if(! iNiveauService.existsById(idNiveau)) {
                message += "l id de niveau " +idNiveau.toString() +" n existe pas\n";
                return message;
            }

            // if niveau is valide
            Niveau niveau = iNiveauService.getNiveau(idNiveau);
            InscriptionAnnuelle inscriptionAnnuelle = new InscriptionAnnuelle();
            inscriptionAnnuelle.setAnnee(2023);

            inscriptionAnnuelle.setEtudiant(etudiant);
            inscriptionAnnuelle.setNiveau(niveau);

            // because here we have inscription we need to create the list of inscription annuell for student
            List<InscriptionAnnuelle> inscriptionAnnuelles = new ArrayList<InscriptionAnnuelle>();

            Set<InscriptionModule> inscriptionModules = new HashSet<>();

            // we get the list of modules in niveau and for every module we create inscriptionModule
            // the we add it to inscriptionModules Set which will be added to inscription annuelle
            // and inscriptionAnnuelle will be adde to the List inscriptionAnnuelles
            List<Module> modules = niveau.getModules();
            for(Module module : modules)
            {
                InscriptionModule inscriptionModule = new InscriptionModule();
                inscriptionModule.setModule(module);
                inscriptionModules.add(inscriptionModule);
            }

            inscriptionAnnuelle.setInscriptionModules(inscriptionModules);
            inscriptionAnnuelles.add(inscriptionAnnuelle);

            etudiant.setInscriptions(inscriptionAnnuelles);
            addEtudiant(etudiant);
            message+= "l etudiant avec cne :"+etudiant.getCne()+"a ete ajouter /t";

        return message;
    }

    @Override
    public String reinscrireEtudiant(Etudiant etudiant, Long idNiveau) {

        String message ="";

        // e- check if niveau is in the database
        if(!iNiveauService.existsById(idNiveau)) {
            message += "l id de niveau " +idNiveau.toString() +" n existe pas\n";
            return message;
        }
        // check if data does not contradict past year



        if(!doesNotContradictPastYear(etudiant,idNiveau))
        {
            message += "niveau indique est contradictoire avec l annee passee\n";
            return message;
        }

        // student data is different from database
        if(!sameDataForStudent(etudiant))
        {
           listReainscrire.add(etudiant);
        }
        return "";
    }

    // d-check if student is valid
    @Override
    public boolean doesNotContradictPastYear(Etudiant etudiant,Long  idNiveau) {
        // first get list of inscriptions annuelle of etudiant
        Etudiant etudiantInDatabase = getEtudiantByCne(etudiant.getCne());
        List<InscriptionAnnuelle> inscriptionAnnuelles = etudiantInDatabase.getInscriptions();
        // now we get the last inscriptionAnnuelle in the list
        // you should work with a student that was created later
        InscriptionAnnuelle previousInscriptionAnnuelle = inscriptionAnnuelles.get(inscriptionAnnuelles.size() -1);
        // check if niveau is valid
        if(previousInscriptionAnnuelle.getNiveau().getIdNiveau() == 2 &&idNiveau == 12  || previousInscriptionAnnuelle.getNiveau().getIdNiveau() == idNiveau-1 )
        {
            // check if he validated previous year
            if(previousInscriptionAnnuelle.getValidation().equals("v"))
            {
                return true;
            }

        }

        return false;
    }

    @Override
    public boolean sameDataForStudent(Etudiant etudiant) {
        Etudiant etudiantInDataBase = getEtudiantByCne(etudiant.getCne());
        if(etudiantInDataBase.getNom().equals(etudiant.getNom()) && etudiantInDataBase.getPrenom().equals(etudiant.getPrenom()))
        {
            return true;
        }

        return false;
    }

    @Override
    public List<ArrayList<Etudiant>>  getListsReainscrire() {
        ArrayList<Etudiant> listOriginal = new ArrayList<>();
        for(Etudiant etd : listReainscrire)
        {
            Etudiant etudiant = getEtudiantByCne(etd.getCne());
            listOriginal.add(etudiant);
        }
        List<ArrayList<Etudiant>> listeTotal = new ArrayList<ArrayList<Etudiant>>();
        listeTotal.add(listReainscrire);
        listeTotal.add(listOriginal);
        return listeTotal;
    }

    @Override
    public void updateEtudiant(Etudiant etudiant) {
        etudiantDao.save(etudiant);
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantDao.findAll();
    }
}

