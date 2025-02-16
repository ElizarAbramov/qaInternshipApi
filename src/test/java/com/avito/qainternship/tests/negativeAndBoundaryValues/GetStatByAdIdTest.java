package com.avito.qainternship.tests.negativeAndBoundaryValues;

import com.avito.qainternship.api.AdvertisementService;
import com.avito.qainternship.model.Advertisement;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertTrue;

public class GetStatByAdIdTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "00000000-0000-0000-0000-000000000000", // Несуществующий UUID Все нули
            "2281d393-a667-4bf2-bb39-4d90acd4618e1", // Длина + 1 знак
            "2281d393-a667-4bf2-bb39-4d90acd4618"  // Длина -1
    })
    public void testGetNonExistentAdvertisement(String id) {
        SoftAssertions softly = new SoftAssertions();

        // Шаг 2: Проверяем статус код и сообщение об ошибке
        try {
            Advertisement createdAdvertisement = AdvertisementService.getAdvertisementById(id);
            Assertions.fail("Ожидалось исключение при некорректном id");
        } catch (RuntimeException e) {
            // Проверяем, что исключение содержит статус код 400
            assertTrue(e.getMessage().contains("400")||e.getMessage().contains("404"));
        }
    }
}

