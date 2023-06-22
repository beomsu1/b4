package org.zerock.b4.mappers;

import java.util.List;
import java.util.Map;

import org.zerock.b4.dto.PageRequestDTO;
import org.zerock.b4.dto.ProductListDTO;
import org.zerock.b4.dto.ProductRegisterDTO;

public interface ProductMapper {

    List<ProductListDTO> getList(PageRequestDTO pageRequestDTO);
    
    // 상품 등록
    int insertProduct(ProductRegisterDTO registerDTO);

    // 이미지 등록
    int insertImages(List<Map<String,String>> imageList);

}
