package org.zerock.b4.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.b4.dto.PageRequestDTO;
import org.zerock.b4.dto.PageResponseDTO;
import org.zerock.b4.dto.ProductListDTO;
import org.zerock.b4.dto.ProductRegisterDTO;

@Transactional
public interface ProductService {
    
    // register
    Integer register(ProductRegisterDTO registerDTO);

    // list
    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

    //modify
    // void modify(// DTO);
    // 상품 데이터 수정
    // 기존 첨부파일 데이터베이스 내에서 삭제
    // DTO에 있는 첨부파일 데이터베이스에 등록
    
    

}