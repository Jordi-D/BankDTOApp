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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag(name = "CRUD REST APIs for Accounts in bank", description = "CRUD operations for accounts")
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
    @Operation(summary = "Create an account", description = "Endpoint to create an account for a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
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
    @Operation(summary = "Create an account unsuccessfully", description = "Endpoint to handle errors during account creation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
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
    @Operation(summary = "Fetch account details", description = "Endpoint to fetch account details for a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details fetched successfully", content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
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
    @Operation(summary = "Fetch account details unsuccessfully", description = "Endpoint to handle errors when fetching account details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details fetched successfully", content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
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
    @Operation(summary = "Update account details", description = "Endpoint to update account details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details updated successfully", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
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
    @Operation(summary = "Update account details unsuccessfully", description = "Endpoint to handle errors during account update.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
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
    @Operation(summary = "Delete account details", description = "Endpoint to delete account details for a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details deleted successfully", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "417", description = "Expectation Failed", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
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
    @Operation(summary = "Delete account details unsuccessfully", description = "Endpoint to handle errors when deleting account details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "417", description = "Expectation Failed", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
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
