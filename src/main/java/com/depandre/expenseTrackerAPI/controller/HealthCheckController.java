package com.depandre.expenseTrackerAPI.controller;

import com.depandre.expenseTrackerAPI.entity.VersionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<VersionModel> version(){
        VersionModel vm = new VersionModel();
        vm.setVersion("1.0");
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }
}
