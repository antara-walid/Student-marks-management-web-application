package com.gsnotes.web.controllers;

import com.gsnotes.services.IEtudiantService;
import com.gsnotes.utils.export.ExcelExporter;
import com.gsnotes.utils.export.ExcelHandler;
import com.gsnotes.utils.export.ExcelHandlerException;
import com.gsnotes.utils.export.ExcelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView importFromExcel(Model model){
        ModelAndView modelAndView = new ModelAndView();
        //1. importing excel data and validating it
        try {
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
            modelAndView.setViewName("cadreadmin/userHomePage");

            //2. check if students with type inscription are not in the database

            String InscriptionMsg = etudiantService.inscrireEtudiants(studentsList);
            modelAndView.addObject("InscriptionMsg",InscriptionMsg);




        }catch (ExcelHandlerException ex)
        {
            modelAndView.addObject("readingExcelMsg","excel reading failed");
            System.err.println("excel reading failed");
        };



        return modelAndView;
    }

}
