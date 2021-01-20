package eu.daxiongmao.wordpress.db.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * Utility class to validate DB data before persistence.
 * @author Guillaume Diaz
 * @version 1.0
 * @Since 2020/12
 */
public class DbDataValidationUtils {

    private DbDataValidationUtils() {
        // private factory
    }


    /**
     * @param email email to check. It must not be blank, and it must be a valid email according to current RFCs.
     * @return true if the email is valid
     * @throws IllegalArgumentException if the email is NOT valid
     */
    public static boolean checkEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("You must provide an author email");
        }
        if (! EmailValidator.getInstance().isValid(email.trim())) {
            throw new IllegalArgumentException("You must provide a valid email, compliant with RFCs");
        }
        return true;
    }

    /**
     * @param ip ip to check. You must provide a not blank, valid IPv4 or IPv6.
     * @return true if the IP address is valid
     * @throws IllegalArgumentException if the IP address is NOT valid
     */
    public static boolean checkIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            throw new IllegalArgumentException("You must provide an IP");
        }
        if (!InetAddressValidator.getInstance().isValid(ip.trim())) {
            throw new IllegalArgumentException("You must provide a valid IPv4 or IPv6");
        }
        return true;
    }
}
