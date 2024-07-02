package com.accountsDto.accounts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.accountsDto.accounts.constants.AccountsConstants;
import com.accountsDto.accounts.dto.AccountsContactInfoDto;
import com.accountsDto.accounts.dto.CustomerDto;
import com.accountsDto.accounts.dto.ResponseDto;
import com.accountsDto.accounts.service.IAccountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AccountsControllerTest {

    @Mock
    private IAccountsService iAccountsService;

    @Mock
    private AccountsContactInfoDto accountsContactInfoDto;

    @InjectMocks
    private AccountsController accountsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {
        CustomerDto customerDto = new CustomerDto();
        doNothing().when(iAccountsService).createAccount(any(CustomerDto.class));

        ResponseEntity<ResponseDto> response = accountsController.createAccount(customerDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_201, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_201, response.getBody().getStatusMsg());

        verify(iAccountsService, times(1)).createAccount(customerDto);
    }

    @Test
    public void testCreateAccount_Unsuccessful() {
        CustomerDto customerDto = new CustomerDto();
        doThrow(new RuntimeException("Service error")).when(iAccountsService).createAccount(any(CustomerDto.class));

        ResponseEntity<ResponseDto> response = null;
        try {
            response = accountsController.createAccount(customerDto);
        } catch (RuntimeException e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, e.getMessage()));
        }

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_500, response.getBody().getStatusCode());
        assertEquals("Service error", response.getBody().getStatusMsg());

        verify(iAccountsService, times(1)).createAccount(customerDto);
    }

    @Test
    public void testFetchAccountDetails_Success() {
        String mobileNumber = "1234567890";
        CustomerDto customerDto = new CustomerDto();
        when(iAccountsService.fetchAccount(anyString())).thenReturn(customerDto);

        ResponseEntity<CustomerDto> response = accountsController.fetchAccountDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDto, response.getBody());

        verify(iAccountsService, times(1)).fetchAccount(mobileNumber);
    }

    @Test
    public void testFetchAccountDetails_Unsuccessful() {
        String mobileNumber = "1234567890";
        when(iAccountsService.fetchAccount(anyString())).thenReturn(null);

        ResponseEntity<CustomerDto> response = accountsController.fetchAccountDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        verify(iAccountsService, times(1)).fetchAccount(mobileNumber);
    }

    @Test
    public void testUpdateAccountDetails_Success() {
        CustomerDto customerDto = new CustomerDto();
        when(iAccountsService.updateAccount(any(CustomerDto.class))).thenReturn(true);

        ResponseEntity<ResponseDto> response = accountsController.updateAccountDetails(customerDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_200, response.getBody().getStatusMsg());

        verify(iAccountsService, times(1)).updateAccount(customerDto);
    }

    @Test
    public void testUpdateAccountDetails_Unsuccessful() {
        CustomerDto customerDto = new CustomerDto();
        when(iAccountsService.updateAccount(any(CustomerDto.class))).thenReturn(false);

        ResponseEntity<ResponseDto> response = accountsController.updateAccountDetails(customerDto);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_500, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_500, response.getBody().getStatusMsg());

        verify(iAccountsService, times(1)).updateAccount(customerDto);
    }

    @Test
    public void testDeleteAccountDetails_Success() {
        String mobileNumber = "1234567890";
        when(iAccountsService.deleteAccount(anyString())).thenReturn(true);

        ResponseEntity<ResponseDto> response = accountsController.deleteAccountDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_200, response.getBody().getStatusMsg());

        verify(iAccountsService, times(1)).deleteAccount(mobileNumber);
    }

    @Test
    public void testDeleteAccountDetails_Unsuccessful() {
        String mobileNumber = "1234567890";
        when(iAccountsService.deleteAccount(anyString())).thenReturn(false);

        ResponseEntity<ResponseDto> response = accountsController.deleteAccountDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_417, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_417_DELETE, response.getBody().getStatusMsg());

        verify(iAccountsService, times(1)).deleteAccount(mobileNumber);
    }
}
