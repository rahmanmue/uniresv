package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.*;
import com.enigmacamp.reservationcampus.model.entity.constant.*;
import com.enigmacamp.reservationcampus.model.request.TransactionRequest;
import com.enigmacamp.reservationcampus.model.request.UpdateStatusRequest;
import com.enigmacamp.reservationcampus.model.response.TransactionDTO;
import com.enigmacamp.reservationcampus.model.response.TransactionDetailDTO;
import com.enigmacamp.reservationcampus.repository.*;
import com.enigmacamp.reservationcampus.repository.constant.AvailabilityRepository;
import com.enigmacamp.reservationcampus.repository.constant.StatusRepository;
import com.enigmacamp.reservationcampus.services.*;
import com.enigmacamp.reservationcampus.utils.constant.*;
import com.enigmacamp.reservationcampus.utils.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final ProfileService profileService;
    private final FacilityService facilityService;
    private final StatusRepository statusRepository;
    private final PenaltiesRepository penaltiesRepository;
    private final AvailabilityRepository availabilityRepository;

    @Override
    @Transactional
    public Transaction saveTransaction(TransactionRequest transactionRequest) {
        // Validate reservation date
        LocalDate today = LocalDate.now();
        LocalDate maxReservationDate = today.plusDays(7);
        if (transactionRequest.getDateReservation().toLocalDate().isAfter(maxReservationDate)) {
            throw new RuntimeException("Reservation can only be made up to 7 days in advance");
        }

        String profileId = transactionRequest.getId_profile();
        Profile profile = profileService.getProfileById(profileId);
        StatusReservation status = statusRepository.findByStatus(EStatusReservation.STATUS_PROCESSED);
        Penalties penalties = penaltiesRepository.findByName(EPenalties.NOT_PENALTY);

        // Check availability of each facility in the request
        for (TransactionDetailDTO detailDTO : transactionRequest.getTransactionDetail()) {
            if (!isFacilityAvailable(detailDTO.getId(), transactionRequest.getDateReservation(), transactionRequest.getDateReturn())) {
                throw new RuntimeException("Facility is not available for the selected dates");
            }
        }

        Transaction transaction = new Transaction();
        transaction.setSubject(transactionRequest.getSubject());
        transaction.setDocument(transactionRequest.getDocument());
        transaction.setProfile(profile);
        transaction.setDateSubmission(Date.valueOf(today));
        transaction.setDateReservation(transactionRequest.getDateReservation());
        transaction.setDateReturn(transactionRequest.getDateReturn());
        transaction.setStatus(status);
        transaction.setPenalties(penalties);

        Transaction savedTransaction = transactionRepository.save(transaction);

        List<TransactionDetail> transactionDetailList = new ArrayList<>();
        for (TransactionDetailDTO detailDTO : transactionRequest.getTransactionDetail()) {
            TransactionDetail detail = new TransactionDetail();
            detail.setTransaction(savedTransaction);
            detail.setId(detailDTO.getId());
            detail.setPrice(detailDTO.getPrice());
            detail.setQuantity(detailDTO.getQuantity());
            Facility facility = facilityService.getFacilityById(detailDTO.getIdFac());
            detail.setFacility(facility);

            // Reduce facility stock
            facility.setQuantity(facility.getQuantity() - detailDTO.getQuantity());
            facilityService.updateFacility(facility);

            transactionDetailList.add(transactionDetailService.saveTransactionDetail(detail));
        }

        savedTransaction.setTransactionDetail(transactionDetailList);
        return savedTransaction;
    }

    private boolean isFacilityAvailable(String facilityId, Date startDate, Date endDate) {
        List<Transaction> reservations = transactionRepository.findReservationsForFacilityWithinDates(facilityId, startDate, endDate);
        return reservations.isEmpty();
    }

    @Override
    public TransactionDTO getTransactionById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data not found"));
        return convertToDTO(transaction);
    }

    @Override
    public Page<TransactionDTO> getAllTransaction(Pageable pageable){
        return transactionRepository.findAll(pageable).map(this::convertToDTO);
    }

//    @Override
//    public Page<TransactionDTO> findTransactionsbySubject(String subject, Pageable pageable){
//        return transactionRepository.findBySubject(subject, pageable).map(this::convertToDTO);
//    }

    public Page<TransactionDTO> findTransactionsByProfileName(String name, Pageable pageable) {
        return transactionRepository.findByProfileFullNameIgnoreCase(name, pageable).map(this::convertToDTO);
    }

//    @Override
//    public List<TransactionDTO> getAllTransaction() {
//        List<Transaction> transactions = transactionRepository.findAll();
//        List<TransactionDTO> transactionDTOList = new ArrayList<>();
//        for (Transaction transaction : transactions) {
//            transactionDTOList.add(convertToDTO(transaction));
//        }
//        return transactionDTOList;
//    }
//
//    @Override
//    public List<TransactionDTO> findTransactionsbySubject(String subject) {
//        List<Transaction> transactions = transactionRepository.findBySubject(subject);
//        List<TransactionDTO> transactionDTOList = new ArrayList<>();
//        for (Transaction transaction : transactions) {
//            transactionDTOList.add(convertToDTO(transaction));
//        }
//        return transactionDTOList;
//    }

    @Override
    @Transactional
    public Transaction updateTransaction(Transaction transaction) {
        Transaction existingTransaction = transactionRepository.findById(transaction.getId())
                .orElseThrow(() -> new DataNotFoundException("Data not found"));
        existingTransaction.setSubject(transaction.getSubject());
        existingTransaction.setDocument(transaction.getDocument());
        existingTransaction.setProfile(transaction.getProfile());
        existingTransaction.setDateReservation(transaction.getDateReservation());
        existingTransaction.setDateSubmission(transaction.getDateSubmission());
        existingTransaction.setDateReturn(transaction.getDateReturn());
        existingTransaction.setStatus(transaction.getStatus());
        existingTransaction.setPenalties(transaction.getPenalties());

        StatusReservation status = statusRepository.findById(transaction.getStatus().getId())
                .orElseThrow(() -> new DataNotFoundException("Status not found"));
        if (status.getStatus() == EStatusReservation.STATUS_REJECTED ||
                status.getStatus() == EStatusReservation.STATUS_CANCELED ||
                status.getStatus() == EStatusReservation.STATUS_COMPLETED) {

            for (TransactionDetail transactionDetail : transaction.getTransactionDetail()) {
                Facility facility = facilityService.getFacilityById(transactionDetail.getFacility().getId());
                facility.setQuantity(facility.getQuantity() + transactionDetail.getQuantity());
                facilityService.updateFacility(facility);
            }
        }

        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data not found"));
        transactionRepository.delete(transaction);
    }

    @Override
    public void cancelTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data not found"));
        StatusReservation status = statusRepository.findByStatus(EStatusReservation.STATUS_CANCELED);
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }

    @Override
    public void updateTransactionStatus(String id, UpdateStatusRequest request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Data not found"));
        StatusReservation status = statusRepository.findByStatus(request.getStatus());
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }

    public Page<TransactionDTO> findTransactionsByUserId(String userId, Pageable pageable) {
        return transactionRepository.findByUserId(userId, pageable).map(this::convertToDTO);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        Profile profile = profileService.getProfileById(transaction.getProfile().getId());

        List<TransactionDetailDTO> transactionDetailDTOList = new ArrayList<>();
        for (TransactionDetail detail : transaction.getTransactionDetail()) {
            transactionDetailDTOList.add(TransactionDetailDTO.fromEntity(detail));
        }

        int totalItem = transactionDetailDTOList.stream().mapToInt(TransactionDetailDTO::getQuantity).sum();
        int grandTotal = transactionDetailDTOList.stream().mapToInt(dto -> dto.getPrice() * dto.getQuantity()).sum();

        return TransactionDTO.builder()
                .id(transaction.getId())
                .name(profile.getFullName())
                .subject(transaction.getSubject())
                .document(transaction.getDocument())
                .dateSubmission(transaction.getDateSubmission())
                .dateReservation(transaction.getDateReservation())
                .dateReturn(transaction.getDateReturn())
                .status(transaction.getStatus().getStatus().name())
                .penalties(transaction.getPenalties().getName().name())
                .totalItem(totalItem)
                .grandTotal(grandTotal)
                .transactionDetailDTO(transactionDetailDTOList)
                .build();
    }
}
