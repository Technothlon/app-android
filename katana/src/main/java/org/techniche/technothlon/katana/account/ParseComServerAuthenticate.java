package org.techniche.technothlon.katana.account;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by kAd on 29/12/13.
 * Part of org.techniche.technothlon.katana.account
 */
public class ParseComServerAuthenticate implements ServerAuthenticate {
    private static final String KATANA_SECRET_KEY = ""; // TODO add secret key on production
    @Override
    public String userSignIn(Context context, String user, String pass, String authType) throws Exception {
        Log.d("katana", "userSignIn");
        String accessToken = "";
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String query = null;
            try {
                query = String.format("client_id=%s&client_secret=%s&username=%s&password=%s&scope=%s",
                        "katana", KATANA_SECRET_KEY,URLEncoder.encode(user, "UTF-8"), URLEncoder.encode(pass, "UTF-8"),
                        URLEncoder.encode("basic accesstechnopedia accessresults updateprofile", "URF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            URL url = new URL(AccountConstants.loginUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
            writer.write(query);
            writer.flush();

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Error signing-in [" + responseCode + "] - " + httpURLConnection.getResponseMessage());
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            String result = readTextResponse(inputStream);
            JSONObject jsonObject = new JSONObject(result);
            accessToken = jsonObject.getString("access_token");
        } else {
            Log.d("katana", "Network Not Available");
        }
        return accessToken;
    }

    private String readTextResponse(InputStream inputStream) throws IOException {
        Reader in = new InputStreamReader(inputStream);
        BufferedReader bufferedreader = new BufferedReader(in);
        StringBuilder stringBuilder = new StringBuilder();

        String stringReadLine;

        while ((stringReadLine = bufferedreader.readLine()) != null) {
            stringBuilder.append(stringReadLine);
        }

        return stringBuilder.toString();
    }
}
