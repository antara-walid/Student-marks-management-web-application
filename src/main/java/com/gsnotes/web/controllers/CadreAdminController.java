package com.gsnotes.web.controllers;

import com.gsnotes.utils.export.ExcelExporter;
import com.gsnotes.utils.export.ExcelHandler;
import com.gsnotes.utils.export.ExcelHandlerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cadreadmin")
public class CadreAdminController {
    ExcelHandler excelHandler = ExcelHandler.getInstance();

    @RequestMapping("/importExcel")
    public Model importFromExcel(Model model){
        try {
            List<ArrayList<Object>> studentsList;
            studentsList = excelHandler.readFromExcel("C:\\Users\\walid\\Desktop\\data1.xlsx",0);
            System.out.println(studentsList);
            model.addAttribute("listOfStudents,studentsList");
        }catch (ExcelHandlerException ex)
        {
            System.err.println("excel reading failed");
        };
        return model;
    }

}
