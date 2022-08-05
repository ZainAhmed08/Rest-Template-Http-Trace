package com.example.restController.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
//import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@Endpoint(id = "audit")
//public class AuditEndpoint {
//
//    @Autowired
//    CustomTraceRepository customTraceRepository;
//
//    List<EndPointHitRate> resultentList = new ArrayList<>();
//
//    @ReadOperation
//    public List<EndPointHitRate> getHealth(){
//
//       List<String> list =  customTraceRepository.findAll().stream().map(ep-> ep.getRequest().getUri().toString()).distinct().collect(Collectors.toList());
//
//        resultentList.removeAll(resultentList);
//
//        for (String list1 : list) {
//            EndPointHitRate endPointHitRate = new EndPointHitRate();
//            endPointHitRate.setEndPoint(list1);
//            long successRate = customTraceRepository.findAll().stream().filter(ep-> ep.getRequest().getUri().toString().equals(list1)).filter(e->e.getResponse().getStatus()==200).count();
//            long failureRate = customTraceRepository.findAll().stream().filter(ep-> ep.getRequest().getUri().toString().equals(list1)).filter(e->e.getResponse().getStatus()!=200).count();
//            endPointHitRate.setSuccess((int) successRate);
//            endPointHitRate.setFailure((int) failureRate);
//            resultentList.add(endPointHitRate);
//            endPointHitRate = null;
//        }
//      return resultentList;
//    }
//
//}
