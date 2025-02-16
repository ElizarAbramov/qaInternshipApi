package com.avito.qainternship.tests.randomPositive;

import com.avito.qainternship.api.AdvertisementService;
import com.avito.qainternship.model.Advertisement;
import com.avito.qainternship.utils.TestUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvertisementCreationTest {
    @Test
    public void testCreateAdvertisements() {

        Advertisement advertisement = TestUtils.generateFakeAdvertisement();
        Response createResponse = AdvertisementService.createAdvertisement(advertisement);
        String responseMessage = createResponse.jsonPath().getString("status");
        assertEquals(200, createResponse.getStatusCode(), "Ожидался успешный статус код при создании объявления");
        System.out.println(responseMessage.substring(23));

    }
}
