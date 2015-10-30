/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store.RequestManager;

import android.os.AsyncTask;
import android.support.annotation.IntDef;
import android.util.Log;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Mauricio on 26/10/15.
 */

public class HttpManager {
    private Map<String, String> mParams = new HashMap<>();
    private Map<String, String> mHeaders = new HashMap<>();
    private int mMethod;
    private ServicesConsumer consumer;
    private String mRaw;

    @IntDef({POST, GET, DELETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethods {

    }
    public static final int POST =1;
    public static final int GET =2;
    public static final int DELETE =3;

    public HttpManager setmHeaders(Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
        return this;
    }

    public HttpManager setmParams(String tag, String value){
        mParams.put(tag,value);
        return this;
    }

    public HttpManager setmParams(Map<String, String> mParams) {
        this.mParams = mParams;
        return this;
    }

    public HttpManager setmMethod(@HttpMethods int mMethod) {
        this.mMethod = mMethod;
        return this;
    }

    public HttpManager setConsumer(ServicesConsumer consumer) {
        this.consumer = consumer;
        return this;
    }

    public HttpManager setmRaw(String mRaw) {
        this.mRaw = mRaw;
        return this;
    }

    public void excute(String urlComplement){
        ManagerAsycTask asyntasks= new ManagerAsycTask();
        asyntasks.execute(urlComplement);


    }

    private class ManagerAsycTask extends AsyncTask<String,Void,ResponseFormat>{

        @Override
        protected ResponseFormat doInBackground(String... complement) {
            ResponseFormat result= new ResponseFormat();
            PostMethod server = new PostMethod();

            if(mMethod==GET){
                try {//Get Here
                    result.setData(server.GetFromServerURL(complement[0], mParams)); //
                    if (result.getData().equals("")) {
                        result.setIsErrorLocal(true);
                        result.setErrorMsn("Error connection or empty response");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    result.setIsErrorLocal(true);
                    result.setErrorMsn(e.getMessage());
                }
            }else if (mMethod==POST){
                try{
                result.setData(server.PostToServerURL(complement[0],mRaw,mParams)); //
                if(result.getData().equals("")){
                    result.setIsErrorLocal(true);
                    result.setErrorMsn("Error connection or empty response");
                }
            } catch (IOException e) {
                e.printStackTrace();
                result.setIsErrorLocal(true);
                result.setErrorMsn(e.getMessage());
            }
            }
            return result;
        }

        @Override
        protected void onPostExecute(ResponseFormat responseFormat) {

            Log.i("From: ", "onPostManagerAsyn");

            consumer.processResponse(responseFormat);
            super.onPostExecute(responseFormat);
        }
    }

}
