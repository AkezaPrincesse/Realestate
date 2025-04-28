package com.innova.realestate.controllers;

import com.innova.realestate.models.Tenant;
import com.innova.realestate.services.TenantService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tenants") // Changed from /api/tenants to match your HTML
public class TenantController {

    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    // MVC Endpoints for Thymeleaf views
    @GetMapping
    public String listTenants(Model model) {
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "tenants"; // matches your HTML template name
    }

    @GetMapping("/new")
    public String showTenantForm(Model model) {
        model.addAttribute("tenant", new Tenant());
        return "add-tenant"; // should match your form template
    }

    @PostMapping
    public String saveTenant(@ModelAttribute Tenant tenant) {
        tenantService.saveTenant(tenant);
        return "redirect:/tenants"; // redirects back to list view
    }

    // PDF Export Endpoint
    @GetMapping("/export/pdf")
    public void exportTenantsToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=tenants.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");
        Paragraph title = new Paragraph("Tenant Report")
                .setFont(boldFont)
                .setFontSize(18);

        document.add(title);

        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 4, 4}))
                .useAllAvailableWidth();

        table.addHeaderCell("Name");
        table.addHeaderCell("Email");
        table.addHeaderCell("Phone");

        for (Tenant tenant : tenantService.getAllTenants()) {
            table.addCell(tenant.getName());
            table.addCell(tenant.getEmail());
            table.addCell(tenant.getPhone());
        }

        document.add(table);
        document.close();
    }

    // Chart Endpoint
    @GetMapping("/chart")
    public String showTenantChart(Model model) {
        List<Tenant> tenants = tenantService.getAllTenants();
        Map<String, Long> grouped = tenants.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getName().substring(0, 1).toUpperCase(),
                        Collectors.counting()
                ));

        model.addAttribute("letters", grouped.keySet());
        model.addAttribute("counts", grouped.values());
        return "/tenantChart"; // should match your chart template path
    }
}