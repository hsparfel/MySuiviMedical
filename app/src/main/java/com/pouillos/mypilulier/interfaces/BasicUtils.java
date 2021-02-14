package com.pouillos.mypilulier.interfaces;

public interface BasicUtils {

    public static boolean isInteger(float f) {

        boolean isValid = true;
        String string = ""+f;
        String intString = string.substring(0,string.indexOf("."));
        int i = Integer.parseInt(intString);
        if (i/f !=1) {
            isValid = false;
        }
        return isValid;
    }
}
