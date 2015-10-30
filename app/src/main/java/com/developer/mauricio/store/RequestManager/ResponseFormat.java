/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store.RequestManager;

/**
 * Created by Mauricio on 26/10/15.
 */
public class ResponseFormat {
    private boolean isErrorLocal=false;
    private String ErrorMsn="";
    private boolean isErrorServer=false;
    private String Data="";

    public boolean isErrorLocal() {
        return isErrorLocal;
    }

    public void setIsErrorLocal(boolean isErrorLocal) {
        this.isErrorLocal = isErrorLocal;
    }

    public String getErrorMsn() {
        return ErrorMsn;
    }

    public void setErrorMsn(String errorMsn) {
        ErrorMsn = errorMsn;
    }

    public boolean isErrorServer() {
        return isErrorServer;
    }

    public void setIsErrorServer(boolean isErrorServer) {
        this.isErrorServer = isErrorServer;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
