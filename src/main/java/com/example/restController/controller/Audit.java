package com.example.restController.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Audit {

    private String endPoint;
    private Integer failure;
    private Integer success;

}
