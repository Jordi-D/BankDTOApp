package com.loansDto.loans.controller;

import com.loansDto.loans.constants.LoansConstants;
import com.loansDto.loans.dto.LoansDto;
import com.loansDto.loans.dto.ResponseDto;
import com.loansDto.loans.service.ILoansService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoansControllerTests {

    @Mock
    private ILoansService loansService;

    @InjectMocks
    private LoansController loansController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateLoan_Success() {
        String mobileNumber = "1234567890";

        doNothing().when(loansService).createLoan(anyString());

        ResponseEntity<ResponseDto> response = loansController.createLoan(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(LoansConstants.STATUS_201, response.getBody().getStatusCode());
        assertEquals(LoansConstants.MESSAGE_201, response.getBody().getStatusMsg());

        verify(loansService, times(1)).createLoan(mobileNumber);
    }

    @Test
    public void testFetchLoanDetails_Success() {
        String mobileNumber = "1234567890";
        LoansDto loansDto = new LoansDto();
        loansDto.setMobileNumber(mobileNumber);

        when(loansService.fetchLoan(anyString())).thenReturn(loansDto);

        ResponseEntity<LoansDto> response = loansController.fetchLoanDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loansDto, response.getBody());

        verify(loansService, times(1)).fetchLoan(mobileNumber);
    }


    @Test
    public void testUpdateLoanDetails_Success() {
        LoansDto loansDto = new LoansDto();

        when(loansService.updateLoan(any(LoansDto.class))).thenReturn(true);

        ResponseEntity<ResponseDto> response = loansController.updateLoanDetails(loansDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LoansConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(LoansConstants.MESSAGE_200, response.getBody().getStatusMsg());

        verify(loansService, times(1)).updateLoan(loansDto);
    }

    @Test
    public void testDeleteLoanDetails_Success() {
        String mobileNumber = "1234567890";

        when(loansService.deleteLoan(anyString())).thenReturn(true);

        ResponseEntity<ResponseDto> response = loansController.deleteLoanDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LoansConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(LoansConstants.MESSAGE_200, response.getBody().getStatusMsg());

        verify(loansService, times(1)).deleteLoan(mobileNumber);
    }



    @Test
    public void testFetchLoanDetails_Unsuccessful() {
        String mobileNumber = "1234567890";

        when(loansService.fetchLoan(anyString())).thenReturn(null); // Simulating that no loan details were found

        ResponseEntity<LoansDto> response = loansController.fetchLoanDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody()); // Expecting null because no loan details were found

        verify(loansService, times(1)).fetchLoan(mobileNumber);
    }
    @Test
    public void testUpdateLoanDetails_Unsuccessful() {
        LoansDto loansDto = new LoansDto();

        when(loansService.updateLoan(any(LoansDto.class))).thenReturn(false);

        ResponseEntity<ResponseDto> response = loansController.updateLoanDetails(loansDto);

        assertNotNull(response);
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(LoansConstants.STATUS_417, response.getBody().getStatusCode());
        assertEquals(LoansConstants.MESSAGE_417_UPDATE, response.getBody().getStatusMsg());

        verify(loansService, times(1)).updateLoan(loansDto);
    }


    @Test
    public void testDeleteLoanDetails_Unsuccessful() {
        String mobileNumber = "1234567890";

        when(loansService.deleteLoan(anyString())).thenReturn(false);

        ResponseEntity<ResponseDto> response = loansController.deleteLoanDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(LoansConstants.STATUS_417, response.getBody().getStatusCode());
        assertEquals(LoansConstants.MESSAGE_417_DELETE, response.getBody().getStatusMsg());

        verify(loansService, times(1)).deleteLoan(mobileNumber);
    }



}
