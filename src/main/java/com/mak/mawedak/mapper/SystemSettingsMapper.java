package com.mak.mawedak.mapper;

import com.mak.mawedak.dto.*;
import com.mak.mawedak.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SystemSettingsMapper {
    public static Customer mapToCustomerEntity(Customer existingCustomer, SystemSettingsDTO systemSettingsDTO) {
        if (systemSettingsDTO == null || existingCustomer == null) {
            return null;
        }

        if (systemSettingsDTO.getCustomerPersonalInfo() != null) {
            if (existingCustomer.getCustomerPersonalInfo() == null) {
                existingCustomer.setCustomerPersonalInfo(new CustomerPersonalInfo());
            }
            existingCustomer.getCustomerPersonalInfo().setName(systemSettingsDTO.getCustomerPersonalInfo().getName());
            existingCustomer.getCustomerPersonalInfo().setNickName(systemSettingsDTO.getCustomerPersonalInfo().getNickName());
            existingCustomer.getCustomerPersonalInfo().setEmail(systemSettingsDTO.getCustomerPersonalInfo().getEmail());
            existingCustomer.getCustomerPersonalInfo().setPhoneNumber(systemSettingsDTO.getCustomerPersonalInfo().getPhoneNumber());
            existingCustomer.getCustomerPersonalInfo().setAddress(systemSettingsDTO.getCustomerPersonalInfo().getAddress());
            existingCustomer.getCustomerPersonalInfo().setTaxNumber(systemSettingsDTO.getCustomerPersonalInfo().getTaxNumber());
        }

        if (systemSettingsDTO.getPatientProfileSettings() != null) {
            if (existingCustomer.getPatientProfileSettings() == null) {
                existingCustomer.setPatientProfileSettings(new PatientProfileSettings());
            }
            // map from SubscriptionSettingsDTO to SubscriptionSettings entity
            SubscriptionSettingsDTO subscriptionSettingsDTO = systemSettingsDTO.getPatientProfileSettings().getDefaultSubscription();
            SubscriptionSettings subscriptionSettings = existingCustomer.getPatientProfileSettings().getDefaultSubscription();
            if (subscriptionSettings == null) {
                subscriptionSettings = new SubscriptionSettings();
                existingCustomer.getPatientProfileSettings().setDefaultSubscription(subscriptionSettings);
            }
            subscriptionSettings.setName(subscriptionSettingsDTO.getName());
            subscriptionSettings.setSubscriptionMethod(new SubscriptionMethod(subscriptionSettingsDTO.getSubscriptionMethodId()));
            if (subscriptionSettingsDTO.getInsuranceId() != null) {
                subscriptionSettings.setInsurance(new Insurance(subscriptionSettingsDTO.getInsuranceId()));
            }
            if (subscriptionSettingsDTO.getSubInsuranceId() != null) {
                subscriptionSettings.setSubInsurance(new SubInsurance(subscriptionSettingsDTO.getSubInsuranceId()));
            }
            subscriptionSettings.setSessionPrice(subscriptionSettingsDTO.getSessionPrice());
            subscriptionSettings.setPackagePrice(subscriptionSettingsDTO.getPackagePrice());
            subscriptionSettings.setCoveragePercentage(subscriptionSettingsDTO.getCoveragePercentage());
            subscriptionSettings.setExpiryDate(subscriptionSettingsDTO.getExpiryDate());
            existingCustomer.getPatientProfileSettings().setDefaultSubscription(subscriptionSettings);


            // map from IdNameDTO to TreatmentMethod entity
            List<TreatmentMethod> treatmentMethods = existingCustomer.getPatientProfileSettings().getTreatmentMethods();
            treatmentMethods.clear();
            treatmentMethods.addAll(
                    systemSettingsDTO.getPatientProfileSettings().getTreatmentMethods()
                            .stream()
                            .map(idNameDTO -> new TreatmentMethod(idNameDTO.getId(), idNameDTO.getName(), existingCustomer.getPatientProfileSettings()))
                            .collect(Collectors.toList())
            );
        }

        if (systemSettingsDTO.getCalendarSettings() != null) {
            if (existingCustomer.getCalendarSettings() == null) {
                existingCustomer.setCalendarSettings(new CalendarSettings());
            }
            existingCustomer.getCalendarSettings().setCalendarStartTime(systemSettingsDTO.getCalendarSettings().getCalendarStartTime());
            existingCustomer.getCalendarSettings().setCalendarEndTime(systemSettingsDTO.getCalendarSettings().getCalendarEndTime());
            existingCustomer.getCalendarSettings().setTimeSlotDurationInMinutes(systemSettingsDTO.getCalendarSettings().getTimeSlotDurationInMinutes());
            existingCustomer.getCalendarSettings().setActiveDays(systemSettingsDTO.getCalendarSettings().getActiveDays());
        }

        if (systemSettingsDTO.getNotificationSettings() != null) {
            if (existingCustomer.getNotificationSettings() == null) {
                existingCustomer.setNotificationSettings(new NotificationSettings());
            }
            existingCustomer.getNotificationSettings().setWhatsAppReminderMessageTemplate(systemSettingsDTO.getNotificationSettings().getWhatsAppReminderMessageTemplate());
            existingCustomer.getNotificationSettings().setWhatsAppAfterSessionFeedbackTemplate(systemSettingsDTO.getNotificationSettings().getWhatsAppAfterSessionFeedbackTemplate());
            existingCustomer.getNotificationSettings().setWhatsAppSessionNotAttendedTemplate(systemSettingsDTO.getNotificationSettings().getWhatsAppSessionNotAttendedTemplate());
        }

        if (systemSettingsDTO.getInsuranceSettings() != null) {
            if (existingCustomer.getInsurances() == null) {
                existingCustomer.setInsurances(new ArrayList<>());
            }
            existingCustomer.setInsurances(
                    systemSettingsDTO.getInsuranceSettings()
                            .stream()
                            .map(insuranceDTO -> new InsuranceMapper().toEntity(insuranceDTO, null))
                            .collect(Collectors.toList())
            );
        }

        return existingCustomer;
    }

    public static SystemSettingsDTO mapToSystemSettingsDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        SystemSettingsDTO systemSettingsDTO = new SystemSettingsDTO();

        if (customer.getCustomerPersonalInfo() != null) {
            systemSettingsDTO.setCustomerPersonalInfo(new CustomerPersonalInfoDTO(
                    customer.getCustomerPersonalInfo().getCustomerPersonalInfoId(),
                    customer.getCustomerPersonalInfo().getName(),
                    customer.getCustomerPersonalInfo().getNickName(),
                    customer.getCustomerPersonalInfo().getTaxNumber(),
                    customer.getCustomerPersonalInfo().getAddress(),
                    customer.getCustomerPersonalInfo().getPhoneNumber(),
                    customer.getCustomerPersonalInfo().getEmail()
            ));
        }

        if (customer.getPatientProfileSettings() != null) {
            systemSettingsDTO.setPatientProfileSettings(new PatientProfileSettingsDTO(
                    customer.getPatientProfileSettings().getPatientProfileSettingsId(),
                    customer.getPatientProfileSettings().getDefaultSubscription() != null ?
                            new SubscriptionSettingsDTO(
                                    customer.getPatientProfileSettings().getDefaultSubscription().getSubscriptionSettingsId(),
                                    customer.getPatientProfileSettings().getDefaultSubscription().getName(),
                                    customer.getPatientProfileSettings().getDefaultSubscription().getSubscriptionMethod() != null ?
                                            customer.getPatientProfileSettings().getDefaultSubscription().getSubscriptionMethod().getSubscriptionMethodId() : null,
                                    customer.getPatientProfileSettings().getDefaultSubscription().getInsurance() != null ?
                                            customer.getPatientProfileSettings().getDefaultSubscription().getInsurance().getInsuranceId() : null,
                                    customer.getPatientProfileSettings().getDefaultSubscription().getSubInsurance() != null ?
                                            customer.getPatientProfileSettings().getDefaultSubscription().getSubInsurance().getSubInsuranceId() : null,
                                    customer.getPatientProfileSettings().getDefaultSubscription().getSessionPrice(),
                                    customer.getPatientProfileSettings().getDefaultSubscription().getPackagePrice(),
                                    customer.getPatientProfileSettings().getDefaultSubscription().getCoveragePercentage(),
                                    customer.getPatientProfileSettings().getDefaultSubscription().getExpiryDate()
                            ) : null,
                    customer.getPatientProfileSettings().getTreatmentMethods()
                            .stream()
                            .map(tm -> new IdNameDTO(tm.getTreatmentMethodId(), tm.getName()))
                            .collect(Collectors.toList())
            ));
        }

        if (customer.getCalendarSettings() != null) {
            systemSettingsDTO.setCalendarSettings(new CalendarSettingsDTO(
                    customer.getCalendarSettings().getCalendarSettingsId(),
                    customer.getCalendarSettings().getCalendarStartTime(),
                    customer.getCalendarSettings().getCalendarEndTime(),
                    customer.getCalendarSettings().getTimeSlotDurationInMinutes(),
                    customer.getCalendarSettings().getActiveDays()
            ));
        }

        if (customer.getNotificationSettings() != null) {
            systemSettingsDTO.setNotificationSettings(new NotificationSettingsDTO(
                    customer.getNotificationSettings().getNotificationSettingsId(),
                    customer.getNotificationSettings().getWhatsAppReminderMessageTemplate(),
                    customer.getNotificationSettings().getWhatsAppAfterSessionFeedbackTemplate(),
                    customer.getNotificationSettings().getWhatsAppSessionNotAttendedTemplate()
            ));
        }

        if (customer.getInsurances() != null) {
            systemSettingsDTO.setInsuranceSettings(
                    customer.getInsurances()
                            .stream()
                            .map(insurance -> new InsuranceMapper().toDto(insurance))
                            .collect(Collectors.toList())
            );
        }
        return systemSettingsDTO;
    }
}
