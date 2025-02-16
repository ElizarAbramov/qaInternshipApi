package com.avito.qainternship.utils;

import com.avito.qainternship.model.Advertisement;
import com.github.javafaker.Faker;

public class TestUtils {

    static Faker faker = new Faker();

    public static Advertisement generateFakeAdvertisement() {
        Advertisement advertisement = new Advertisement();
        advertisement.setSellerId(faker.number().numberBetween(111111, 999999)); // sellerID в диапазоне 111111-999999
        advertisement.setName(faker.commerce().productName()); // Случайное название продукта
        advertisement.setPrice(faker.number().numberBetween(1, 10000)); // Цена от 1 до 10000

        // Генерация статистики
        Advertisement.Statistics statistics = new Advertisement.Statistics();
        statistics.setContacts(faker.number().numberBetween(0, 1000)); // Контакты от 0 до 1000
        statistics.setLikes(faker.number().numberBetween(0, 10000)); // Лайки от 0 до 10000
        statistics.setViewCount(faker.number().numberBetween(0, 100000)); // Просмотры от 0 до 100000

        advertisement.setStatistics(statistics);

        return advertisement;
    }
}

