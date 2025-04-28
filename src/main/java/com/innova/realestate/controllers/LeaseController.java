package com.innova.realestate.controllers;

import com.innova.realestate.models.Lease;
import com.innova.realestate.services.LeaseService;
import com.innova.realestate.services.PropertyService;
import com.innova.realestate.services.TenantService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.properties.UnitValue;
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
@RequestMapping("/leases")
public class LeaseController {
    private final LeaseService leaseService;
    private final TenantService tenantService;
    private final PropertyService propertyService;

    public LeaseController(LeaseService leaseService, TenantService tenantService, PropertyService propertyService) {
        this.leaseService = leaseService;
        this.tenantService = tenantService;
        this.propertyService = propertyService;
    }

    @GetMapping
    public String listLeases(Model model) {
        model.addAttribute("leases", leaseService.getAllLeases());
        return "leases";
    }

    @GetMapping("/new")
    public String showLeaseForm(Model model) {
        model.addAttribute("lease", new Lease());
        model.addAttribute("tenants", tenantService.getAllTenants());
        model.addAttribute("properties", propertyService.getAllProperties());
        return "add-lease";
    }

    @PostMapping
    public String saveLease(@ModelAttribute Lease lease) {
        leaseService.saveLease(lease);
        return "redirect:/leases";
    }
    @GetMapping("/leases/export/pdf")
    public void exportLeasesToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=leases.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Create a bold font using PdfFontFactory
        PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");

        // Create a Text element with bold font and apply font size
        Text titleText = new Text("Lease Report").setFont(boldFont).setFontSize(18);

        // Add title as a Paragraph
        document.add(new Paragraph(titleText));

        // Create the table and add data
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 3, 3}))
                .useAllAvailableWidth();

        table.addHeaderCell("Start Date");
        table.addHeaderCell("End Date");
        table.addHeaderCell("Tenant");
        table.addHeaderCell("Property");

        for (Lease lease : leaseService.getAllLeases()) {
            table.addCell(lease.getStartDate().toString());
            table.addCell(lease.getEndDate().toString());
            table.addCell(lease.getTenant().getName());
            table.addCell(lease.getProperty().getTitle());
        }

        document.add(table);
        document.close();
    }
    @GetMapping("/leases/chart")
    public String showLeaseChart(Model model) {
        List<Lease> leases = leaseService.getAllLeases();

        Map<String, Long> leaseCount = leases.stream()
                .collect(Collectors.groupingBy(
                        l -> l.getStartDate().getMonth().toString(),
                        Collectors.counting()
                ));

        model.addAttribute("months", leaseCount.keySet());
        model.addAttribute("counts", leaseCount.values());

        return "leases/chart";
    }

}
