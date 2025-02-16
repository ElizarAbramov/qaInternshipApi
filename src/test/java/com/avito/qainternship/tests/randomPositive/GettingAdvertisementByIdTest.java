package com.avito.qainternship.tests.randomPositive;

import com.avito.qainternship.api.AdvertisementService;
import com.avito.qainternship.model.Advertisement;
import com.avito.qainternship.utils.TestUtils;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GettingAdvertisementByIdTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testGetAdvertisementById() {
        SoftAssertions softly = new SoftAssertions();
        Advertisement expectedAdvertisement = TestUtils.generateFakeAdvertisement();
        Response createResponse = AdvertisementService.createAdvertisement(expectedAdvertisement);
        String responseMessage = createResponse.jsonPath().getString("status");
        assertEquals(200, createResponse.getStatusCode(), "Ожидался успешный статус код при создании объявления");
        String advertisementId = responseMessage.substring(23);
        Advertisement actualAdvertisement = AdvertisementService.getAdvertisementById(advertisementId);
        String localTime = LocalDate.now().toString();

        // Сравниваем все поля
        assertEquals(advertisementId, actualAdvertisement.getId(), "id не совпадает");
        assertEquals(expectedAdvertisement.getSellerId(), actualAdvertisement.getSellerId(), "sellerId не совпадает");

        softly.assertThat(expectedAdvertisement.getName()).isEqualTo(actualAdvertisement.getName());
        if (!softly.wasSuccess()) {
            System.out.println("Имя в ответе не совпадает с созданным ранее по " + advertisementId);
        }
        assertEquals(expectedAdvertisement.getPrice(), actualAdvertisement.getPrice(), "Цена объявления не совпадает");
        assertTrue((actualAdvertisement.getCreatedAt().contains(localTime)), "Время создания объявления не совпадает");

        // Сравниваем статистику
        assertEquals(expectedAdvertisement.getStatistics().getLikes(), actualAdvertisement.getStatistics().getLikes(), "Лайки не совпадают");
        assertEquals(expectedAdvertisement.getStatistics().getViewCount(), actualAdvertisement.getStatistics().getViewCount(), "Просмотры не совпадают");
        assertEquals(expectedAdvertisement.getStatistics().getContacts(), actualAdvertisement.getStatistics().getContacts(), "Контакты не совпадают");

    }
}

