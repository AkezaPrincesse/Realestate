package com.innova.realestate.controllers;

import com.innova.realestate.models.Payment;
import com.innova.realestate.services.LeaseService;
import com.innova.realestate.services.PaymentService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final LeaseService leaseService;

    public PaymentController(PaymentService paymentService, LeaseService leaseService) {
        this.paymentService = paymentService;
        this.leaseService = leaseService;
    }

    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "payments";
    }

    @GetMapping("/new")
    public String showPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("leases", leaseService.getAllLeases());
        return "add-payment";
    }

    @PostMapping
    public String savePayment(@ModelAttribute Payment payment) {
        paymentService.savePayment(payment);
        return "redirect:/payments";
    }

    @GetMapping("/payments/chart")
    public String showPaymentsChart(Model model) {
        List<Payment> payments = paymentService.getAllPayments();

        Map<String, Double> totalPerMonth = payments.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getPaymentDate().getMonth().toString(),
                        Collectors.summingDouble(Payment::getAmount)
                ));

        model.addAttribute("months", totalPerMonth.keySet());
        model.addAttribute("totals", totalPerMonth.values());
        return "payments/chart";
    }

    @GetMapping("/payments/export/pdf")
    public void exportPaymentsToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=payments.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Create a bold font using PdfFontFactory
        PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");

        // Create a Text element with bold font and apply font size
        Paragraph title = new Paragraph("Payment Report")
                .setFont(boldFont) // Set bold font
                .setFontSize(18);  // Set font size

        document.add(title);

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 3, 3}))
                .useAllAvailableWidth();

        table.addHeaderCell("Date");
        table.addHeaderCell("Amount");
        table.addHeaderCell("Tenant");
        table.addHeaderCell("Lease");

        for (Payment p : paymentService.getAllPayments()) {
            table.addCell(p.getPaymentDate().toString());
            table.addCell(String.valueOf(p.getAmount()));
            table.addCell(p.getLease().getTenant().getName());
            table.addCell("Lease #" + p.getLease().getId());
        }

        document.add(table);
        document.close();
    }
}
