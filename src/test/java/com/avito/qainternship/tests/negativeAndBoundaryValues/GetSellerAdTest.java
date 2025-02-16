package com.avito.qainternship.tests.negativeAndBoundaryValues;
import com.avito.qainternship.api.AdvertisementService;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class GetSellerAdTest {
    @ParameterizedTest
    @MethodSource("provideInvalidSellerIds")
    public void testGetAdvertisementsByInvalidSellerId(long sellerId, int expectedStatusCode, String expectedErrorMessage) {

        Response response = AdvertisementService.getAdvertisementsBySellerId(sellerId);


        SoftAssertions softly = new SoftAssertions();


        softly.assertThat(response.getStatusCode())
                .as("Статус код для sellerId=%d", sellerId)
                .isEqualTo(expectedStatusCode);
        if (!softly.wasSuccess()) {
            System.out.println("Код ошибки не совпадает " + "Expexted: " + expectedStatusCode + "Actual: " + response.getStatusCode());
        }


        softly.assertThat(response.getBody().asString())
                .as("Сообщение об ошибке для sellerId=%d", sellerId)
                .contains(expectedErrorMessage);
        if (!softly.wasSuccess()) {
            System.out.println("Сообщение об ошибке не совпадает " + "Expexted: " + expectedErrorMessage + "Actual: " +
                    response.getBody().asString());
        }

    }


    private static Stream<Arguments> provideInvalidSellerIds() {
        return Stream.of(
                // sellerId, expectedStatusCode, expectedErrorMessage
                Arguments.of(0, 400, "передан некорректный идентификатор продавца"), // sellerId = 0
                Arguments.of(-123, 400, "передан некорректный идентификатор продавца"), // Отрицательный sellerId
                Arguments.of(1000000, 400, "передан некорректный идентификатор продавца"), // Верхняя граница +1
                Arguments.of(111110, 400, "передан некорректный идентификатор продавца") //нижняя граница -1



        );
    }
}

