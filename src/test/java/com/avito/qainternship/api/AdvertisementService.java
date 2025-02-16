package com.avito.qainternship.api;

import com.avito.qainternship.model.Advertisement;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;


public class AdvertisementService {
    private static final String BASE_URL = "https://qa-internship.avito.com";

    public static Response createAdvertisement(Advertisement advertisement) {
        return given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(advertisement)
                .post("api/1/item");

    }

    public static Advertisement getAdvertisementById(String id) {
        Response response = given()

                .baseUri(BASE_URL)
                .pathParam("id", id)
                .get("/api/1/item/{id}");
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Ошибка при создании объявления: " + response.getStatusCode() + " " + response.getBody().asString());
        }



        List<Advertisement> advertisements = response.jsonPath().getList("", Advertisement.class);
        if (advertisements.isEmpty()) {
            throw new RuntimeException("Объявление с ID " + id + " не найдено");
        }
        return advertisements.get(0);
    }

    public static Response getAdvertisementsBySellerId(long sellerId) {
        return given()
                .baseUri(BASE_URL)
                .pathParam("sellerID", sellerId)
                .get("/api/1/{sellerID}/item");
    }

    public static Advertisement.Statistics getAdvertisementStats(String id) {
        Response response = given()
                .baseUri(BASE_URL)
                .pathParam("id", id)
                .get("/api/1/statistic/{id}");

        List<Advertisement.Statistics> advertisements = response.jsonPath().getList("", Advertisement.Statistics.class);
        if (advertisements.isEmpty()) {
            throw new RuntimeException("Объявление с ID " + id + " не найдено");
        }
        return advertisements.get(0);
    }
}
