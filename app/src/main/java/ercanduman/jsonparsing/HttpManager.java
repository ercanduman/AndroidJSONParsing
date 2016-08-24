package ercanduman.jsonparsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    private static BufferedReader reader = null;
    private static URL url;
    private static HttpURLConnection connection;
    private static StringBuilder sb;

    public static String getDataFromURL(String uri) {
        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
