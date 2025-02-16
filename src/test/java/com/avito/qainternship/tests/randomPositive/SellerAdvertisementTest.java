package com.avito.qainternship.tests.randomPositive;

import com.avito.qainternship.api.AdvertisementService;
import com.avito.qainternship.model.Advertisement;
import com.avito.qainternship.utils.TestUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SellerAdvertisementTest {

    @Test
    public void testGetAdvertisementsBySellerId() {
        Advertisement advertisement = TestUtils.generateFakeAdvertisement();

        Response createResponse = AdvertisementService.createAdvertisement(advertisement);
        assertEquals(200, createResponse.getStatusCode(), "Ожидался успешный статус код при создании объявления");

        // Шаг 2: Получаем список объявлений для этого пользователя
        Response getResponse = AdvertisementService.getAdvertisementsBySellerId(advertisement.getSellerId());
        assertEquals(200, getResponse.getStatusCode(), "Ожидался успешный статус код при получении объявлений");

        List<Advertisement> advertisements = getResponse.jsonPath().getList("", Advertisement.class);

        // Шаг 3: Проверяем, что список не пустой
        assertFalse(advertisements.isEmpty(), "Список объявлений не должен быть пустым");

        // Дополнительно: Проверяем, что все объявления принадлежат указанному sellerId
        advertisements.forEach(ad -> assertEquals(advertisement.getSellerId(), ad.getSellerId(), "Все объявления должны принадлежать продавцу"));
    }
}

