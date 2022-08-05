package com.example.restController.controller;

public class Request {

    private String method;
    private String uri;
    private Object remoteAddress;
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public Object getRemoteAddress() {
        return remoteAddress;
    }
    public void setRemoteAddress(Object remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
