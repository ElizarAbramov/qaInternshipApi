package com.avito.qainternship.tests.randomPositive;

import com.avito.qainternship.api.AdvertisementService;
import com.avito.qainternship.model.Advertisement;
import com.avito.qainternship.utils.TestUtils;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetStatByAdIdTest {
    @Test
    public void testGetStatisticsById() {
        SoftAssertions softly = new SoftAssertions();
        Advertisement expectedAdvertisement = TestUtils.generateFakeAdvertisement();
        Response createResponse = AdvertisementService.createAdvertisement(expectedAdvertisement);
        String responseMessage = createResponse.jsonPath().getString("status");
        assertEquals(200, createResponse.getStatusCode(), "Ожидался успешный статус код при создании объявления");
        String advertisementId = responseMessage.substring(23);
        Advertisement.Statistics statsResponse = AdvertisementService.getAdvertisementStats(advertisementId);


        // Сравниваем статистику
        int expectedLikeCount = expectedAdvertisement.getStatistics().getLikes();
        int actualLikeCount = statsResponse.getLikes();
        int expectedViewCount = expectedAdvertisement.getStatistics().getViewCount();
        int actualViewCount = statsResponse.getViewCount();
        assertEquals(expectedAdvertisement.getStatistics().getContacts(), statsResponse.getContacts(), "Контакты не совпадают");
        softly.assertThat(expectedLikeCount).isEqualTo(actualLikeCount);
        if (!softly.wasSuccess()) {
            System.out.println("Лайки не совпадают  \n  Expected: " + expectedLikeCount + '\n' + "Actual: " + actualLikeCount);
        }

        softly.assertThat(expectedViewCount).isEqualTo(actualViewCount);
        if (!softly.wasSuccess()) {
            System.out.println("Просмотры не совпадают  \n Expected: " + expectedViewCount + '\n' + "Actual: " + actualViewCount);
        }
    }
}
