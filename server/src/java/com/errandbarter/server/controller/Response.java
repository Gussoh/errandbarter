/*
 * Response.java
 *
 * Created on March 18, 2009, 11:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.errandbarter.server.controller;

/**
 *
 * @author Zach
 */
public class Response {
    
    private String message;
    private String status;

    public static final int STATUS_ERROR = 1, STATUS_OK = 0;
    
    /** Creates a new instance of Response */
    public Response() {
    }
    
    public Response(int statusI, String message) {
        this.message = message;
        switch (statusI) {
            case STATUS_OK:
                status = "OK";
                break;
            case STATUS_ERROR:
                status = "ERROR";
                break;
        }
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
}
