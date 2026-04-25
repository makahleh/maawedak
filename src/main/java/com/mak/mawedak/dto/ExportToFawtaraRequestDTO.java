package com.mak.mawedak.dto;

import java.util.List;

public record ExportToFawtaraRequestDTO(List<InvoiceByPatient> patientInvoices) {
}
