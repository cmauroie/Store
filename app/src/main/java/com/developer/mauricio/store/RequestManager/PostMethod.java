/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store.RequestManager;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Mauricio on 26/10/15.
 */

public class PostMethod {

    public final static String urlbase= "https://itunes.apple.com";


    public PostMethod() {

    }

    public String GetFromServerURL(String complement,Map<String,String> params) throws IOException {

        URL mURL= new URL(urlbase+complement+createQueryStringForParameters(params));

        HttpURLConnection urlConnection = (HttpURLConnection) mURL.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");

        urlConnection.setRequestMethod("GET");

        Log.w("Data-OUT", urlConnection.getRequestProperties().toString());
        Log.w("Data-URL", urlbase+complement+createQueryStringForParameters(params));


        String jsonResultStr="";
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());


            BufferedReader reader = new BufferedReader(new InputStreamReader(in));


            StringBuilder total = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line);
            }

            jsonResultStr=total.toString();

            Log.w("Data-IN",jsonResultStr);


        }finally{
                urlConnection.disconnect();
        }


        return jsonResultStr;
    }


    /**
     * Method in use
     * @return
     * @throws IOException
     */
    public String PostToServerURL(String complement,String json,Map<String,String> params) throws IOException {

        URL mURL= new URL(urlbase+complement);

        HttpURLConnection urlConnection = (HttpURLConnection) mURL.openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
            if(params.size()>0){
            urlConnection.setRequestProperty("Token", params.get("Token"));

        }
        urlConnection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        urlConnection.setRequestMethod("POST");
        Log.w("Data-OUT", urlConnection.getRequestProperties().toString());
        Log.w("Data-URL", urlbase + complement);

        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);

        String jsonResultStr="";

        DataOutputStream wr = new DataOutputStream (
                urlConnection.getOutputStream ());
        wr.writeBytes (json);
        wr.flush ();
        wr.close ();


        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));


            StringBuilder total = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line);
            }

            jsonResultStr=total.toString();

            Log.w("Data-IN",jsonResultStr);


        }finally{
            urlConnection.disconnect();
        }


        return jsonResultStr;
    }



    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';
    public static String createQueryStringForParameters(Map<String, String> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for (String parameterName : parameters.keySet()) {
                if (!firstParameter) {
                    parametersAsQueryString.append(PARAMETER_DELIMITER);
                }

                parametersAsQueryString.append(parameterName)
                        .append(PARAMETER_EQUALS_CHAR)
                        .append(URLEncoder.encode(
                                parameters.get(parameterName)));

                firstParameter = false;
            }
        }
        return "?"+parametersAsQueryString.toString();
    }
}
