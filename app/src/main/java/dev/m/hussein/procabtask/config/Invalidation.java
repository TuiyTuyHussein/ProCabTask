package dev.m.hussein.procabtask.config;

import android.util.Patterns;

/**
 * Created by Dev. M. Hussein on 11/18/2017.
 */

public class Invalidation {


    public static boolean isValidEmail(String email){
        if (email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


}
