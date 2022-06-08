package com.gsnotes.utils.export;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ExcelValidator {

    private static final ExcelValidator instance = new ExcelValidator();

    private ExcelValidator(){};

    // getting the singleton
    public static ExcelValidator getInstance(){
        return instance;
    }


    public Boolean validateExcel(List<ArrayList<Object>> l)
    {
        ListIterator<ArrayList<Object>> lit = l.listIterator(1); // start at index 1 to avoid the labels
        while(lit.hasNext())
        {

            ArrayList<Object> row = lit.next();
            if(row.size() != 6)
            {
                return false;
            }
            for(int i=0 ;i<6;i++)
            {
                if(row.get(i).toString().isBlank())
                {

                    return false;
                }
            }
            //System.out.println("is empty good");
            try{
                // checking if ids are numbers
                Double.parseDouble(row.get(0).toString());
                Double.parseDouble(row.get(4).toString());
            }catch (Exception e)
            {
                return false;
            }
           // System.out.println("parseDouble good");

            String type = row.get(5).toString().toUpperCase();
            if(!type.equals("INSCRIPTION") && !type.equals("REINSCRIPTION"))
            {
                return false;
            }


        }


        return true;
    }
}
