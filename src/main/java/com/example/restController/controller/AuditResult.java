package com.example.restController.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class AuditResult {

    List<Audit> auditList = new ArrayList<>();

    public void addTrace(Trace trace){
        if(trace.getRequest().getUri().contains("actuator"))
        {
            return;
        }
        Audit audit=this.getAuditFromURL(trace.getRequest().getUri());
        int status=trace.getResponse().getStatus();
        boolean isStatusSuccess=status>=200 && status<300;
        if(audit==null)
        {
            audit=new Audit();
            audit.setEndPoint(trace.getRequest().getUri());
            audit.setSuccess(isStatusSuccess?1:0);
            audit.setFailure(!isStatusSuccess?1:0);
            auditList.add(audit);

        }
        else if(isStatusSuccess)
        {
            audit.setSuccess(audit.getSuccess()+1);
        }
        else
        {
            audit.setFailure(audit.getFailure()+1);
        }
    }

    public Audit getAuditFromURL(String url)
    {
        Optional<Audit> audit=this.auditList.stream().filter(x->x.getEndPoint().toLowerCase(Locale.ROOT).equals(url)).findFirst();
        if(audit.isPresent())
            return audit.get();
        return null;
    }
}

