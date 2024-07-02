package com.cardsDto.cards.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

public class CardsControllerTests {

    @Mock
    private ICardsService iCardsService;

    @InjectMocks
    private CardsController cardsController;

    public CardsControllerTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCard_Success() {
        // Arrange
        String mobileNumber = "1234567890";

        // Mocking the service method
        doNothing().when(iCardsService).createCard(anyString());

        // Act
        ResponseEntity<ResponseDto> response = cardsController.createCard(mobileNumber);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_201, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_201, response.getBody().getStatusMsg());

        // Verify that the service method was called
        verify(iCardsService, times(1)).createCard(mobileNumber);
    }


    @Test
    public void testFetchCardDetails_Success() {
        // Arrange
        String mobileNumber = "1234567890";
        CardsDto cardsDto = new CardsDto();
        cardsDto.setMobileNumber(mobileNumber);

        // Mocking the service method
        when(iCardsService.fetchCard(anyString())).thenReturn(cardsDto);

        // Act
        ResponseEntity<CardsDto> response = cardsController.fetchAccountDetails(mobileNumber);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardsDto, response.getBody());

        // Verify that the service method was called
        verify(iCardsService, times(1)).fetchCard(mobileNumber);
    }
    @Test
    public void testUpdateCardDetails_Success() {
        // Arrange
        CardsDto cardsDto = new CardsDto();

        // Mocking the service method
        when(iCardsService.updateCard(any(CardsDto.class))).thenReturn(true);

        // Act
        ResponseEntity<ResponseDto> response = cardsController.updateCardDetails(cardsDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_200, response.getBody().getStatusMsg());

        // Verify that the service method was called
        verify(iCardsService, times(1)).updateCard(cardsDto);
    }
    @Test
    public void testDeleteCardDetails_Success() {
        // Arrange
        String mobileNumber = "1234567890";

        // Mocking the service method
        when(iCardsService.deleteCard(anyString())).thenReturn(true);

        // Act
        ResponseEntity<ResponseDto> response = cardsController.deleteCardDetails(mobileNumber);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_200, response.getBody().getStatusMsg());

        // Verify that the service method was called
        verify(iCardsService, times(1)).deleteCard(mobileNumber);
    }
    @Test
    public void testFetchCardDetails_Unsuccessful() {
        // Arrange
        String mobileNumber = "1234567890";

        // Mocking the service method to return null
        when(iCardsService.fetchCard(anyString())).thenReturn(null); // Simulating that no card details were found

        // Act
        ResponseEntity<CardsDto> response = cardsController.fetchAccountDetails(mobileNumber);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody()); // Expecting null because no card details were found

        // Verify that the service method was called
        verify(iCardsService, times(1)).fetchCard(mobileNumber);
    }
    @Test
    public void testUpdateCardDetails_Unsuccessful() {
        // Arrange
        CardsDto cardsDto = new CardsDto();

        // Mocking the service method to return false
        when(iCardsService.updateCard(any(CardsDto.class))).thenReturn(false);

        // Act
        ResponseEntity<ResponseDto> response = cardsController.updateCardDetails(cardsDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_417, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_417_UPDATE, response.getBody().getStatusMsg());

        // Verify that the service method was called
        verify(iCardsService, times(1)).updateCard(cardsDto);
    }

    @Test
    public void testDeleteCardDetails_Unsuccessful() {
        // Arrange
        String mobileNumber = "1234567890";

        // Mocking the service method to return false
        when(iCardsService.deleteCard(anyString())).thenReturn(false);

        // Act
        ResponseEntity<ResponseDto> response = cardsController.deleteCardDetails(mobileNumber);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_417, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_417_DELETE, response.getBody().getStatusMsg());

        // Verify that the service method was called
        verify(iCardsService, times(1)).deleteCard(mobileNumber);
    }
}