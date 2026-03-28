package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.SessionReviewDTO;
import com.mak.mawedak.entity.SessionReview;

public class SessionReviewMapper {

    public static SessionReviewDTO toDTO(SessionReview entity) {
        if (entity == null)
            return null;
        SessionReviewDTO dto = new SessionReviewDTO();
        dto.setReviewId(entity.getReviewId());
        dto.setSessionId(entity.getSession() != null ? entity.getSession().getSessionId() : null);
        dto.setSubjectiveReportRate(entity.getSubjectiveReportRate());
        dto.setComments(entity.getComments());
        dto.setPainScale(entity.getPainScale());
        dto.setRomProgressRate(entity.getRomProgressRate());
        dto.setStrengthProgressRate(entity.getStrengthProgressRate());
        dto.setFunctionProgressRate(entity.getFunctionProgressRate());
        dto.setComplianceRate(entity.getComplianceRate());
        return dto;
    }

    public static SessionReview toEntity(SessionReviewDTO dto) {
        if (dto == null)
            return null;
        SessionReview entity = new SessionReview();
        entity.setReviewId(dto.getReviewId());
        entity.setSubjectiveReportRate(dto.getSubjectiveReportRate());
        entity.setComments(dto.getComments());
        entity.setPainScale(dto.getPainScale());
        entity.setRomProgressRate(dto.getRomProgressRate());
        entity.setStrengthProgressRate(dto.getStrengthProgressRate());
        entity.setFunctionProgressRate(dto.getFunctionProgressRate());
        entity.setComplianceRate(dto.getComplianceRate());
        return entity;
    }


}
