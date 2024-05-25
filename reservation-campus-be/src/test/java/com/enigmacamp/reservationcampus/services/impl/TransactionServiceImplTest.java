package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.*;
import com.enigmacamp.reservationcampus.model.entity.constant.*;
import com.enigmacamp.reservationcampus.model.request.TransactionRequest;
import com.enigmacamp.reservationcampus.model.response.TransactionDTO;
import com.enigmacamp.reservationcampus.model.response.TransactionDetailDTO;
import com.enigmacamp.reservationcampus.repository.*;
import com.enigmacamp.reservationcampus.repository.constant.AvailabilityRepository;
import com.enigmacamp.reservationcampus.repository.constant.StatusRepository;
import com.enigmacamp.reservationcampus.services.*;
import com.enigmacamp.reservationcampus.utils.constant.EPenalties;
import com.enigmacamp.reservationcampus.utils.constant.EStatusReservation;
import com.enigmacamp.reservationcampus.utils.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ProfileService profileService;

    @Mock
    private FacilityService facilityService;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private PenaltiesRepository penaltiesRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private TransactionDetailService transactionDetailService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionRequest transactionRequest;
    private Profile profile;
    private Facility facility;
    private StatusReservation status;
    private Penalties penalties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        profile = new Profile();
        profile.setId("3bf9ca19-360d-4488-9b20-9e5e14ec04be");
        profile.setFullName("John Doe");

        facility = new Facility();
        facility.setId("120c2fa2-5daa-4c63-a498-bb5cf907f4bb");
        facility.setQuantity(10);

        status = new StatusReservation();
        status.setStatus(EStatusReservation.STATUS_PROCESSED);

        penalties = new Penalties();
        penalties.setName(EPenalties.NOT_PENALTY);

        TransactionDetailDTO detailDTO = new TransactionDetailDTO();
        detailDTO.setId("detail-1");
        detailDTO.setIdFac(facility.getId());
        detailDTO.setQuantity(1);
        detailDTO.setPrice(100);

        List<TransactionDetailDTO> detailDTOList = new ArrayList<>();
        detailDTOList.add(detailDTO);

        transactionRequest = TransactionRequest.builder()
                .id_profile(profile.getId())
                .subject("minjam")
                .document("dokumen.doc")
                .dateReservation(Date.valueOf(LocalDate.now().plusDays(1)))
                .dateReturn(Date.valueOf(LocalDate.now().plusDays(2)))
                .transactionDetail(detailDTOList)
                .build();
    }

    @Test
    void saveTransaction_success() {
        when(profileService.getProfileById(anyString())).thenReturn(profile);
        when(statusRepository.findByStatus(any(EStatusReservation.class))).thenReturn(status);
        when(penaltiesRepository.findByName(any(EPenalties.class))).thenReturn(penalties);
        when(facilityService.getFacilityById(anyString())).thenReturn(facility);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction savedTransaction = transactionService.saveTransaction(transactionRequest);

        assertNotNull(savedTransaction);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void saveTransaction_facilityNotAvailable() {
        when(facilityService.getFacilityById(anyString())).thenReturn(facility);
        when(transactionRepository.findReservationsForFacilityWithinDates(anyString(), any(Date.class), any(Date.class)))
                .thenReturn(List.of(new Transaction()));  // Simulate facility is not available

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.saveTransaction(transactionRequest);
        });

        assertEquals("Facility is not available for the selected dates", exception.getMessage());
    }

    @Test
    void getTransactionById_success() {
        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setProfile(profile);  // Ensure profile is set

        // Ensure transactionDetail is set
        List<TransactionDetail> transactionDetails = new ArrayList<>();
        transaction.setTransactionDetail(transactionDetails);

        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(transaction));

        assertDoesNotThrow(() -> {
            TransactionDTO transactionDTO = transactionService.getTransactionById("1");
            assertNotNull(transactionDTO);
        });
    }

    @Test
    void getTransactionById_dataNotFound() {
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            transactionService.getTransactionById("1");
        });
    }

    @Test
    void deleteTransaction_success() {
        Transaction transaction = new Transaction();
        transaction.setId("1");
        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(transaction));

        assertDoesNotThrow(() -> {
            transactionService.deleteTransaction("1");
        });

        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    void deleteTransaction_dataNotFound() {
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            transactionService.deleteTransaction("1");
        });
    }
}
