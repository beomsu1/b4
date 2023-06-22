package org.zerock.b4.dto;

import java.util.List;

import lombok.Data;

// 상품등록 DTO

@Data
public class ProductRegisterDTO {
    
    private Integer pno;
    private String pname;
    private int price;
    private boolean status;

    // 쉬운 방법
    private List<String> fileNames;
    
}
