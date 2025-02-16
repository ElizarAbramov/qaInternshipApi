package com.avito.qainternship.model;

import lombok.Data;

@Data
public class Advertisement {
    private String createdAt;
    private String id;
    private String name;
    private int price;
    private long sellerId;
    private Statistics statistics;


    @Data
    public static class Statistics {
        private int contacts;
        private int likes;
        private int viewCount;
    }

}