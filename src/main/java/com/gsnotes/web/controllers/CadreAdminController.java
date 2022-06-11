package com.gsnotes.web.controllers;

import com.gsnotes.bo.CheckBox;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.services.IEtudiantService;
import com.gsnotes.utils.export.ExcelExporter;
import com.gsnotes.utils.export.ExcelHandler;
import com.gsnotes.utils.export.ExcelHandlerException;
import com.gsnotes.utils.export.ExcelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cadreadmin")
public class CadreAdminController {
    // services

    @Autowired
    IEtudiantService etudiantService;


    // excel tools that we will use
    ExcelHandler excelHandler = ExcelHandler.getInstance();
    ExcelValidator excelValidator = ExcelValidator.getInstance();

    @RequestMapping("/importExcel")
    public ModelAndView importFromExcel(Model model) throws ExcelHandlerException {
        ArrayList<Etudiant> etudiantToBeUpdated = new ArrayList<>();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("etudiantToBeUpdated",etudiantToBeUpdated);
        //1. importing excel data and validating it
        List<ArrayList<Object>> studentsList;
        studentsList = excelHandler.readFromExcel("C:\\Users\\walid\\Desktop\\data1.xlsx",0);
        System.out.println(studentsList);
        if(excelValidator.validateExcel(studentsList))
        {
            modelAndView.addObject("readingExcelMsg","excel reading succeeded");
        }else{
            studentsList =null;
            modelAndView.addObject("readingExcelMsg","excel reading failed excel format not valide");
        }



        modelAndView.addObject("studentsList",studentsList);


        //2. check if students with type inscription are not in the database

        String InscriptionMsg = etudiantService.inscrireEtudiants(studentsList);

        // 3. get the list of student that can be modified (new / initail)
        List<ArrayList<Etudiant>> listsReainscrire = etudiantService.getListsReainscrire();
        // if their is student that must be checked before reinscrire
        if(listsReainscrire.size() != 0)
        {
            System.out.println("********");
            System.out.println(listsReainscrire.get(0));
            System.out.println(listsReainscrire.get(1));
            modelAndView.addObject("listReainscrire",listsReainscrire.get(0));
            modelAndView.addObject("listOriginal",listsReainscrire.get(1));
            CheckBox checkBox = new CheckBox();
            modelAndView.addObject("checkBox",checkBox);
            modelAndView.setViewName("cadreadmin/choix");
        }
        else
        {
            modelAndView.setViewName("cadreadmin/userHomePage");
            modelAndView.addObject("InscriptionMsg",InscriptionMsg);
        }




        return modelAndView;
    }

    @RequestMapping("/updatesEtudiants")
    public String updateEtuiants(@ModelAttribute("checkBox") CheckBox checkBox,Model model )
    {

        System.out.println(checkBox);
         String arr[] = checkBox.getChecked();
         ArrayList<Etudiant> original =(ArrayList<Etudiant>) model.getAttribute("listOriginal");
         ArrayList<Etudiant> reisncrire =(ArrayList<Etudiant>) model.getAttribute("listReainscrire");
        for(String ele :arr)
        {
            System.out.println("-----------");
            System.out.println(Integer.parseInt(ele));
            Etudiant etudiant = original.get(Integer.parseInt(ele));
            Etudiant etudiant1 = reisncrire.get(Integer.parseInt(ele));
            etudiant.setNom(etudiant1.getNom());
            etudiant.setPrenom(etudiant1.getPrenom());
            etudiantService.updateEtudiant(etudiant);
        }
        return "cadreadmin/userHomePage";
    }

    @RequestMapping("/showListeEtudiant")
    public String showListeEtudiant(Model model)
    {
        List<Etudiant> listeEtudiant = etudiantService.getAllEtudiants();
        model.addAttribute("",listeEtudiant);
        return "cadreadmin/listEtudiant";
    }

}
