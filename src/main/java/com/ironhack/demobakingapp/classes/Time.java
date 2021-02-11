package com.ironhack.demobakingapp.classes;

import java.time.LocalDate;
import java.time.Period;

public class Time {

    /** Calculate total years between the specified date and now **/
    public static Integer years(LocalDate date){
        Integer quantityYears = Period.between(date, LocalDate.now()).getYears();
        return quantityYears;
    }

    /** Calculate total months between the specified date and now **/
    public static Integer months(LocalDate date){
        Integer quantityMonths = Period.between(date, LocalDate.now()).getMonths();
        return quantityMonths;
    }

    /** Calculate total months between the specified date and now **/
    public static Integer days(LocalDate date){
        Integer quantityDays = Period.between(date, LocalDate.now()).getDays();
        return quantityDays;
    }

}
