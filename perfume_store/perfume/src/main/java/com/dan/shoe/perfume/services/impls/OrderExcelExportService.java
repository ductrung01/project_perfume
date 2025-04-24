package com.dan.shoe.perfume.services.impls;


import com.dan.shoe.perfume.models.Order;
import com.dan.shoe.perfume.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderExcelExportService {
@Autowired
  private  OrderRepository orderRepository;

  public ByteArrayInputStream exportOrdersToExcel() throws IOException {
    List<Order> orders = orderRepository.findAll();

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Orders");

      // Header
      Row headerRow = sheet.createRow(0);
      String[] headers = {"ID", "User", "Staff", "Order Type", "Payment Type", "Address", "Order Time", "Total Price", "Status", "Paid"};
      for (int col = 0; col < headers.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(headers[col]);
      }

      // Data
      int rowIdx = 1;
      for (Order order : orders) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(order.getId());
        row.createCell(1).setCellValue(order.getUser() != null ? order.getUser().getUsername() : "");
        row.createCell(2).setCellValue(order.getStaff() != null ? order.getStaff().getUsername() : "");
        row.createCell(3).setCellValue(order.getOrderType().name());
        row.createCell(4).setCellValue(order.getPaymentType().name());
        row.createCell(5).setCellValue(order.getAddress());
        row.createCell(6).setCellValue(order.getOrderTime() != null ? order.getOrderTime().toString() : "");
        row.createCell(7).setCellValue(order.getTotalPrice());
        row.createCell(8).setCellValue(order.getStatus().name());
        row.createCell(9).setCellValue(order.isPaid() ? "Yes" : "No");
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    }
  }
}
