package ui.userdata.admin;

import java.util.regex.Pattern;

public class AUDW {

    public final static int MAX_LENGTH = 250;

    /**
     * TODO: it has to tell you wether it contains weird characters
     * @param login
     * @return
     */

    public static boolean isLoginFormat(String login) {
        if (login.contains(" "))
            return false;
        return true;
    }

    /**
     * TODO: recomendable make this method static from another class
     * @param email
     * @return
     */
    public static boolean isEmailFormat(String email) {
        String patternEmail = 
            "([a-z0-9]*)@([a-z]*).(com|org|cn|net|gov|eus)";

        return Pattern.matches(patternEmail, email) || email.contains(" "); 
    }

    /**
     * TODO: add the system that checks the full char sequence
     * @param fullName
     * @return
     */
    public static boolean isFullNameFormat(String fullName) {
        return fullName.contains(" ");
    }


}
