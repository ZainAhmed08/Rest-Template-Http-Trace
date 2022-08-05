package com.example.restController.controller;

public class Trace {

    private String timestamp;
    private Object principal;
    private Object session;
    private Request request;
    private Response response;
    private Integer timeTaken;
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public Object getPrincipal() {
        return principal;
    }
    public void setPrincipal(Object principal) {
        this.principal = principal;
    }
    public Object getSession() {
        return session;
    }
    public void setSession(Object session) {
        this.session = session;
    }
    public Request getRequest() {
        return request;
    }
    public void setRequest(Request request) {
        this.request = request;
    }
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    public Integer getTimeTaken() {
        return timeTaken;
    }
    public void setTimeTaken(Integer timeTaken) {
        this.timeTaken = timeTaken;
    }

}
