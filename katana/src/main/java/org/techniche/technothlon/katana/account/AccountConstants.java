package org.techniche.technothlon.katana.account;

/**
 * Created by kAd on 29/12/13.
 * Part of org.techniche.technothlon.katana.account
 */
public class AccountConstants {
    public static  final String loginUrl = "http://www.technothlon.techniche.org/oauth2/login";
    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "technothlon.techniche.org";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "Technothlon";

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Udinic account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Udinic account";

    public static final ServerAuthenticate sServerAuthenticate = new ParseComServerAuthenticate();
}
