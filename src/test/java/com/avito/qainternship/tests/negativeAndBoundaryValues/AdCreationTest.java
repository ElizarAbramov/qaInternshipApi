package com.avito.qainternship.tests.negativeAndBoundaryValues;

import com.avito.qainternship.api.AdvertisementService;
import com.avito.qainternship.model.Advertisement;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AdCreationTest {

        @ParameterizedTest
        @CsvSource({
                // sellerId, name, price, expectedErrorMessage
                "0, 'Продам велосипед', 10000",// sellerID = 0
                "111110,'Привет,я новенький',99,",// нижняя граница-1
                "111112,'GooD',123",// нижняя граница +1
                "111111,'GooD123',123", // нижняя граница
                "999999,'GooD',123", // верхняя граница
                "999998,'GooD',123", // верхняя граница -1
                "1000000,'GooD',123", // верхняя граница +1
                "123856, 'Продам велосипед', -100", // Отрицательная цена


        })
        public void testCreateAdvertisementWithInvalidData(long sellerId, String name, int price)
        {
            SoftAssertions softly = new SoftAssertions();
            // Шаг 1: Создаём объявление с некорректными данными
            Advertisement advertisement = new Advertisement();
            Advertisement.Statistics statistics = new Advertisement.Statistics();

            advertisement.setSellerId(sellerId);
            advertisement.setName(name);
            advertisement.setPrice(price);


            // Шаг 2: Отправляем запрос
            Response response = AdvertisementService.createAdvertisement(advertisement);

            // Шаг 3: Проверяем статус код и сообщение об ошибке
            softly.assertThat(400).isEqualTo(response.getStatusCode());
            if (!softly.wasSuccess()) {
                System.out.println("Ошибка не совпадает \n" + "Expected: \n" + "400" + "\n" + "Actual: \n"+ response.getStatusCode());
            }
           softly.assertThat(response.jsonPath().getString("status")).contains("не передан объект - объявление");
            if (!softly.wasSuccess()) {
                System.out.println("Текст ошибки не совпадает \n" + "Expected: \n" + "не передан объект - объявление" + "\n" + "Actual: \n"+ response.jsonPath().getString("status"));
            }
        }
    }
