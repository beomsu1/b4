package org.zerock.b4.mappers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b4.dto.PageRequestDTO;
import org.zerock.b4.dto.ProductListDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductMapperTests {
    
    // 의존성 주입
    @Autowired(required = false) // 다른 에디터에서 에러가 나지 않게 설정
    private ProductMapper productMapper;

    @Test
    public void testGetList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        
        // 리스트로 뽑아내기 위해 
        List<ProductListDTO> result = productMapper.getList(pageRequestDTO);

        log.info(result);
        
    }    
}
