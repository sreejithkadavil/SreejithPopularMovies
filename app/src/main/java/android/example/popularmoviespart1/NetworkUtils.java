package android.example.popularmoviespart1;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

        private final static String TAG = NetworkUtils.class.getSimpleName();

        private final static String API_KEY_PARAM = "api_key";
        private final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie";
        private final static String API_KEY = "5480260e96f3999b66a42759f10f0e9d";

        static public URL buildUrl(String query) {
            Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                    .appendPath(query)
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();

            URL url = null;
            try {

                url = new URL(builtUri.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        }

        public static String getResponseFromHttpUrl(URL movieRequestUrl) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) movieRequestUrl.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }



}
