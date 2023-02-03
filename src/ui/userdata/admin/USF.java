package ui.userdata.admin;

import java.time.LocalDate;
import java.util.regex.Pattern;

// User Param Formater class
public class USF {

    public final static int MAX_LENGTH = 250;

    // check if its empty

    /**
     * TODO: it has to tell you wether it contains weird characters
     * login properties are: 
     *  1 not begining with a number
     *  2 
     * @param login
     * @return
     */

    public static boolean isLoginFormat(String login) {
        String patternLogin = 
            "([a-z]*)([a-z0-9]*)";
        Pattern.matches(login, patternLogin);
        return !login.contains(" ") 
            && !(login.length() == 1 && login.charAt(0) == ' ');       
    }

    /**
     * @param email
     * @return
     */
    public static boolean isEmailFormat(String email) {
        String patternEmail = 
            "([a-z0-9]*)@([a-z]*).(com|org|cn|net|gov|eus)";

        return Pattern.matches(patternEmail, email) && !email.contains(" "); 
    }

    /**
     * TODO: add the system that checks the full char sequence
     * @param fullName
     * @return
     */

    public static boolean isFullNameFormat(String fullName) {
        return fullName.contains(" ") &&
        (fullName.charAt(0) != ' ' && fullName.charAt(fullName.length() - 1) != ' ');
    }

    /**
     * TODO: check if the date is (or not) possible
     * @param date
     * @return
     */
    
    public static boolean dateFormatIsFine(LocalDate date) {
        return true;
    }


}
