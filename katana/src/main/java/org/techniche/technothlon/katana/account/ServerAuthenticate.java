package org.techniche.technothlon.katana.account;

import android.content.Context;

/**
 * Created by kAd on 29/12/13.
 * Part of org.techniche.technothlon.katana.account
 */
public interface ServerAuthenticate {
    public String userSignIn( Context context, final String user, final String pass, String authType) throws Exception;
}
