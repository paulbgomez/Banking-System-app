package com.ironhack.demobakingapp.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Time {

    public static Integer years(LocalDate date){
        Integer quantityYears = Period.between(date, LocalDate.now()).getYears();
        return quantityYears;
    }

    public static Integer months(LocalDate date){
        Integer quantityMonths = Period.between(date, LocalDate.now()).getMonths();
        return quantityMonths;
    }

}
