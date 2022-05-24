package helpers;

import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    public static User readJsonData(String fileName) {
        String jsonPath = "src/main/resources/" + fileName + ".json";
        try {
            return new ObjectMapper().readValue(new File(jsonPath), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
