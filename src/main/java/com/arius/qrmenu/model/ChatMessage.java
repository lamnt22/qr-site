package com.arius.qrmenu.model;

import java.util.List;

import lombok.Data;

@Data
public class ChatMessage {


    private String title;

    private String content;

    private int tableId;
    
    private String tableName;
    
    private List<Long> orderIds;

}
