package com.workintech.s17d2.model;

import com.workintech.s17d2.tax.Taxable;
import com.workintech.s17d2.utils.Constants;

public class DeveloperFactory {
    public static Developer createDeveloper(Developer developer, Taxable taxable) {
        Developer createdDeveloper = null;
        if (developer.getExperience().name().equalsIgnoreCase(Constants.JUNIOR)) {
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(),
                    (developer.getSalary() - (developer.getSalary() * taxable.getSimpleTaxRate())));
        } else if (developer.getExperience().name().equalsIgnoreCase(Constants.MID)) {
            createdDeveloper = new MidDeveloper(developer.getId(), developer.getName(), (developer.getSalary() - (developer.getSalary() * taxable.getMiddleTaxRate())));
        } else if (developer.getExperience().name().equalsIgnoreCase(Constants.SENIOR)) {
            createdDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(), (developer.getSalary() - (developer.getSalary() * taxable.getUpperTaxRate())));
        }

        return createdDeveloper;
    }
}
