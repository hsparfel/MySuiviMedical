package com.pouillos.mysuivimedical.enumeration;

public enum TypePhoto {
    //Objets directement construits
    Analyse("Resultat Analyse"),
    Examen("Resultat Examen"),
    Ordonnance("Ordonnance");

    private String name = "";

    //Constructeur
    TypePhoto(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

}
