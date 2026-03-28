package com.mak.mawedak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionReviewDTO {
    private Long reviewId;
    private Long sessionId;
    private Integer subjectiveReportRate;
    private String comments;
    private Integer painScale;
    private Integer romProgressRate;
    private Integer strengthProgressRate;
    private Integer functionProgressRate;
    private Integer complianceRate;
}
