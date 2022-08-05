package com.example.restController.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuditList {
    List<Audit> auditList = new ArrayList<>();


    public List<Audit> addTraces(HttpTrace httpTrace) {

        auditList.removeAll(auditList);
        System.out.println(httpTrace);
        List<String> uniqueUrl = httpTrace.getTraces().stream().map(x -> x.getRequest().getUri()).distinct().collect(Collectors.toList());
        System.out.println(uniqueUrl);
        for (String url : uniqueUrl) {
            Audit audit = new Audit();
            audit.setEndPoint(url);
            long successRate = httpTrace.getTraces().stream().filter(ep -> ep.getRequest().getUri().equals(url)).filter(e -> e.getResponse().getStatus() == 200).count();
            long failureRate = httpTrace.getTraces().stream().filter(ep -> ep.getRequest().getUri().equals(url)).filter(e -> e.getResponse().getStatus() != 200).count();
            audit.setSuccess((int) successRate);
            audit.setFailure((int) failureRate);
            auditList.add(audit);

        }
            return auditList;
    }
}
