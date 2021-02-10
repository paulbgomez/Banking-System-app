package com.ironhack.demobakingapp.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Years {

    public static Integer years(LocalDate date){
        Integer quantityYears = Period.between(date, LocalDate.now()).getYears();
        return quantityYears;
    }

}
