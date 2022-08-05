package com.example.restController.configuration;
//
//import org.springframework.boot.actuate.trace.http.HttpTrace;
//import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
//import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicReference;
//
//@Repository
//public class CustomTraceRepository implements HttpTraceRepository {
//
//    AtomicReference<HttpTrace> lastTrace = new AtomicReference<>();
//    InMemoryHttpTraceRepository inMemoryHttpTraceRepository = new InMemoryHttpTraceRepository();
//
//    @Override
//    public List<HttpTrace> findAll() {
//        return inMemoryHttpTraceRepository.findAll();
//       // return Collections.singletonList(lastTrace.get());
//    }
//
//    @Override
//    public void add(HttpTrace trace) {
////        if ("GET".equals(trace.getRequest().getMethod())) {
////            lastTrace.set(trace);
////        }
//        inMemoryHttpTraceRepository.add(trace);
//    }
//
//}
