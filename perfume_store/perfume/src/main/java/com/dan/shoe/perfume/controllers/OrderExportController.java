package com.dan.shoe.perfume.controllers;


import com.dan.shoe.perfume.services.impls.OrderExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderExportController {
    @Autowired
  private  OrderExcelExportService excelExportService;

  @GetMapping("/export/excel")
  public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
    ByteArrayInputStream in = excelExportService.exportOrdersToExcel();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=orders.xlsx");

    return ResponseEntity
        .ok()
        .headers(headers)
        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(new InputStreamResource(in));
  }
}