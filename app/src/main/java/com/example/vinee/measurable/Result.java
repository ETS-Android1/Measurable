package com.example.vinee.measurable;

/**
 * Created by Vineet Sridhar on 3/21/2018.
 */

public class Result {
    private Double measure;
    private String name;
    private boolean choice;

    //Constructor
    public Result(String name, double measure, boolean choice){
        this.name = name;
        this.measure = measure;
        this.choice = choice;
    }

    //Getters and Setters
    public double getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }

    public boolean isHeight() {
        return choice;
    }

    //If a 'Result' is printed, it prints the name of the result
    @Override
    public String toString(){
        return getName();
    }
}
