package com.uit.librarymanagementapplication;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;


public class AppConfig {
    public String url;
    public String user;
    public String password;
    public String hashKey;

    private static AppConfig instance;

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                InputStream is = AppConfig.class.getClassLoader()
                        .getResourceAsStream("applicationSetting.json");

                instance = mapper.readValue(is, AppConfig.class);
            } catch (Exception e) {
                throw new RuntimeException("Không thể load file cấu hình", e);
            }
        }
        return instance;
    }
}
