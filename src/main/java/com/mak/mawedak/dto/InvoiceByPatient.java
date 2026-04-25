package com.mak.mawedak.dto;

import java.util.List;

public record InvoiceByPatient(List<Long> paymentIds, Long patientId, Integer quantity, Double pricePerSession,
                               Double total) {
}
