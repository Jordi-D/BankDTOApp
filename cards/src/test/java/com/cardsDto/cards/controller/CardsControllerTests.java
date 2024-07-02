package com.cardsDto.cards.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cardsDto.cards.constants.CardsConstants;
import com.cardsDto.cards.controller.CardsController;
import com.cardsDto.cards.dto.CardsDto;
import com.cardsDto.cards.dto.ResponseDto;
import com.cardsDto.cards.service.ICardsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

public class CardsControllerTests {

    @Mock
    private ICardsService iCardsService;

    @InjectMocks
    private CardsController cardsController;

    public CardsControllerTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Operation(summary = "Create a card", description = "Endpoint to create a card for a given mobile number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    public void testCreateCard_Success() {
        String mobileNumber = "1234567890";

        doNothing().when(iCardsService).createCard(anyString());

        ResponseEntity<ResponseDto> response = cardsController.createCard(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_201, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_201, response.getBody().getStatusMsg());

        verify(iCardsService, times(1)).createCard(mobileNumber);
    }


    @Test
    @Operation(summary = "Fetch card details", description = "Endpoint to fetch card details for a given mobile number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardsDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    public void testFetchCardDetails_Success() {
        String mobileNumber = "1234567890";
        CardsDto cardsDto = new CardsDto();
        cardsDto.setMobileNumber(mobileNumber);

        when(iCardsService.fetchCard(anyString())).thenReturn(cardsDto);

        ResponseEntity<CardsDto> response = cardsController.fetchAccountDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardsDto, response.getBody());

        verify(iCardsService, times(1)).fetchCard(mobileNumber);
    }

    @Test
    @Operation(summary = "Update card details", description = "Endpoint to update card details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    public void testUpdateCardDetails_Success() {
        CardsDto cardsDto = new CardsDto();

        when(iCardsService.updateCard(any(CardsDto.class))).thenReturn(true);

        ResponseEntity<ResponseDto> response = cardsController.updateCardDetails(cardsDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_200, response.getBody().getStatusMsg());

        verify(iCardsService, times(1)).updateCard(cardsDto);
    }

    @Test
    @Operation(summary = "Delete card details", description = "Endpoint to delete card details for a given mobile number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "417", description = "Expectation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    public void testDeleteCardDetails_Success() {
        String mobileNumber = "1234567890";

        when(iCardsService.deleteCard(anyString())).thenReturn(true);

        ResponseEntity<ResponseDto> response = cardsController.deleteCardDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_200, response.getBody().getStatusMsg());

        verify(iCardsService, times(1)).deleteCard(mobileNumber);
    }

    @Test
    @Operation(summary = "Fetch card details unsuccessfully", description = "Endpoint to fetch card details for a given mobile number when no card details are found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardsDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    public void testFetchCardDetails_Unsuccessful() {
        String mobileNumber = "1234567890";

        when(iCardsService.fetchCard(anyString())).thenReturn(null); // Simulating that no card details were found

        ResponseEntity<CardsDto> response = cardsController.fetchAccountDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody()); // Expecting null because no card details were found

        verify(iCardsService, times(1)).fetchCard(mobileNumber);
    }

    @Test
    @Operation(summary = "Update card details unsuccessfully", description = "Endpoint to update card details when the update operation fails.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "417", description = "Expectation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    public void testUpdateCardDetails_Unsuccessful() {
        CardsDto cardsDto = new CardsDto();

        when(iCardsService.updateCard(any(CardsDto.class))).thenReturn(false);

        ResponseEntity<ResponseDto> response = cardsController.updateCardDetails(cardsDto);

        assertNotNull(response);
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_417, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_417_UPDATE, response.getBody().getStatusMsg());

        verify(iCardsService, times(1)).updateCard(cardsDto);
    }

    @Test
    @Operation(summary = "Delete card details unsuccessfully", description = "Endpoint to delete card details for a given mobile number when the delete operation fails.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "417", description = "Expectation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    public void testDeleteCardDetails_Unsuccessful() {
        String mobileNumber = "1234567890";

        when(iCardsService.deleteCard(anyString())).thenReturn(false);

        ResponseEntity<ResponseDto> response = cardsController.deleteCardDetails(mobileNumber);

        assertNotNull(response);
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_417, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_417_DELETE, response.getBody().getStatusMsg());

        verify(iCardsService, times(1)).deleteCard(mobileNumber);
    }
}
