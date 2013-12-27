package org.techniche.technothlon.katana.tcd;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techniche.technothlon.katana.R;
import org.techniche.technothlon.katana.db.TCDDatabase;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TCDContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<TCDQuestionMini> ITEMS = new ArrayList<TCDQuestionMini>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, TCDQuestion> ITEM_MAP = new HashMap<String, TCDQuestion>();
    private static String url = "http://localhost/technothlon/technocoupdoeil_app_gateway/android/?technocoupdoeil=fjalkfq2045rudacnavsofu0aswd988q29ra&lastFetchId=";

    private static int download(Context context) {

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long lastFetchID = sharedPref.getLong(context.getString(R.string.tcd_fetch_id), 0);

        Log.d("Pref - log", lastFetchID + " from shared pref");

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                JSONObject json = new JSONObject(downloadUrl(url + lastFetchID));
                if (json.getString("status").equals("success")) {
                    TCDDatabase db = new TCDDatabase(context);
                    JSONArray questions = json.getJSONArray("questions");
                    lastFetchID = json.getLong("lastFetchId");
                    int count = json.getInt("questions_count"), lastID;
                    for (int i = 0; i < count; i++) {
                        JSONObject q = questions.getJSONObject(i);
                        JSONObject links = q.getJSONObject("links");
                        lastID = q.getInt("uniqueId");
                        db.insert(
                                lastID,
                                q.getString("id"),
                                q.getString("color"),
                                q.getString("title"),
                                q.getString("question"),
                                links.getString("facebook"),
                                links.getString("google"),
                                links.getString("tumblr"),
                                links.getString("answer"),
                                q.getString("by"),
                                q.getString("time"),
                                q.getString("answer")
                        );
                        Log.d("Database - log", lastID + " loaded in database");
                    }
                    db.close();
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.putLong(context.getString(R.string.tcd_fetch_id), lastFetchID);
                    edit.commit();
                } else if (json.getString("status").equals("reset")) {
                    TCDDatabase db = new TCDDatabase(context);
                    db.reset();
                    db.close();
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.putLong(context.getString(R.string.tcd_fetch_id), 0);
                    edit.commit();
                    download(context);
                }
                final Context ct = context;
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(ct, "Sync Completed.", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }.start();
                return 0;
            } catch (JSONException e) {
                e.printStackTrace();
                final Context ct = context;
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(ct, "Sync Failed.", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }.start();
                return 3;
            } catch (IOException e) {
                e.printStackTrace();
                final Context ct = context;
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(ct, "Sync Failed.", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }.start();
                return 2;
            }
        } else {
            final Context ct = context;
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(ct, "No network connection available.", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();
            return 1;
        }
    }

    private static String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("TCD latest downloads", "The response is: " + response);
            int size = conn.getContentLength();
            Log.d("TCD latest downloads", "The content-length is: " + size);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            return readTextResponse(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static String readTextResponse(InputStream inputStream) throws IOException {
        Reader in = new InputStreamReader(inputStream);
        BufferedReader bufferedreader = new BufferedReader(in);
        StringBuilder stringBuilder = new StringBuilder();

        String stringReadLine;

        while ((stringReadLine = bufferedreader.readLine()) != null) {
            stringBuilder.append(stringReadLine);
        }

        return stringBuilder.toString();
    }

    public static void load(Context context) {
        boolean update = ITEMS.isEmpty() ? false : true;
        TCDDatabase helper = new TCDDatabase(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        assert db != null;
        Cursor c = db.rawQuery("SELECT * FROM " + TCDDatabase.Contracts.NAME + " ORDER BY " + TCDDatabase.Contracts.FIELD_TIME + " DESC, " + TCDDatabase.Contracts.FIELD_ID + " DESC", null);
        Log.d("DB", c.getCount() + " object in database");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            addItem(new TCDQuestion(
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_ID)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_DISPLAY_ID)),
                    c.getInt(c.getColumnIndex(TCDDatabase.Contracts.FIELD_COLOR)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_TITLE)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_QUESTION)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_FACEBOOK)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_GOOGLE)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_TUMBLR)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_ANSWER_URL)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_BY)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_ANSWER)),
                    c.getString(c.getColumnIndex(TCDDatabase.Contracts.FIELD_TIME))
            ), update);
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    private static void addItem(TCDQuestion item, boolean update) {
        if (!ITEM_MAP.containsKey(item.uniqueId)) {
            if (update) ITEMS.add(0, (new TCDQuestionMini(item.uniqueId)));
            else ITEMS.add((new TCDQuestionMini(item.uniqueId)));
            ITEM_MAP.put(item.uniqueId, item);
        }
    }

    public abstract static class TCDLoader extends AsyncTask<Object, Integer, Integer> {

        @Override
        protected Integer doInBackground(Object[] params) {
            int d = 4;
            try {
                d = download((Context) params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                load((Context) params[0]);
            }
            return d;
        }

        @Override
        protected void onPostExecute(Integer o) {
            finished(o);
        }

        public abstract void finished(int result);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TCDQuestion {
        public String id;
        public String question;
        public String facebook;
        public String google;
        public String tumblr;
        public String answer_url;
        public String by;
        public String answer;
        public String title;
        public java.util.Date date = null;
        public String dateString = "";
        public int color = R.drawable.tcd_background_1;
        public String uniqueId;
        private String status;
        private boolean ret = false;


        public TCDQuestion(String uniqueId, String id, int color, String title, String question, String facebook, String google, String tumblr,
                           String answer_url, String by, String answer, String status) {

            this.uniqueId = uniqueId;
            this.id = id;
            this.title = title;
            this.question = question;
            this.facebook = facebook;
            this.google = google;
            this.tumblr = tumblr;
            this.answer_url = answer_url;
            this.by = by;
            this.color = getBackground(color);
            this.answer = answer;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                this.date = sdf.parse(status);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            assert this.date != null;
            this.dateString = sdf.format(this.date);
            this.status = getStatus();
        }

        private int getBackground(int color) {
            switch (color) {
                case 10:
                    return R.drawable.tcd_background_2;
                case 20:
                    return R.drawable.tcd_background_3;
                case 30:
                    return R.drawable.tcd_background_4;
                case 40:
                    return R.drawable.tcd_background_5;
                case 50:
                    return R.drawable.tcd_background_6;
                default:
                    return R.drawable.tcd_background_1;
            }
        }

        public String getStatus() {
            if (ret) return status;
            long seconds = Math.abs(((new Date()).getTime() - date.getTime()) / 1000);
            if (seconds < 60) status = "about " + seconds + " seconds ago";
            else if (seconds < 3600) status = "about " + (seconds / 60) + " minutes ago";
            else if (seconds < 86400) status = "about " + (seconds / 3600) + " hours ago";
            else if (seconds < 172800) status = "yesterday";
            else if (seconds < 345600) status = (seconds / 86400) + " days ago";
            else {
                ret = true;
                status = dateString;
            }
            return status;
        }
    }

    public static class TCDHolder {
        public TextView id, title, question, status;
    }

    public static class TCDQuestionMini {
        public String id;

        public TCDQuestionMini(String id) {
            this.id = id;
        }
    }
}
