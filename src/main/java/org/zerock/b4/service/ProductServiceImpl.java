package org.zerock.b4.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.b4.dto.PageRequestDTO;
import org.zerock.b4.dto.PageResponseDTO;
import org.zerock.b4.dto.ProductListDTO;
import org.zerock.b4.dto.ProductRegisterDTO;
import org.zerock.b4.mappers.ProductMapper;

import groovyjarjarantlr4.v4.codegen.model.chunk.RetValueRef;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    // 의존성 주입
    private final ProductMapper productMapper;

    @Override
    public Integer register(ProductRegisterDTO dto){

    List<String> fileNames = dto.getFileNames();

    int count = productMapper.insertProduct(dto);

    log.info("insert product count: " + count);

    int pno = dto.getPno();

    log.info("-----------------------------" + pno);

    AtomicInteger index = new AtomicInteger();

    List<Map<String,String>> list = fileNames.stream().map(str -> {
      String uuid = str.substring(0, 36);
      String fileName = str.substring(37);

      return Map.of("uuid", uuid, "fileName", fileName,"pno", ""+pno, "ord", "" + index.getAndIncrement());

    }).collect(Collectors.toList());

    log.info(list);

    int countImages = productMapper.insertImages(list);

    log.info("=====================" + countImages);

        return pno;


    }


    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO){

        List<ProductListDTO> result= productMapper.getList(pageRequestDTO);

        Long total = 123L;

         return PageResponseDTO.<ProductListDTO>withAll()
         .list(result)
         .total(total)
         .build();
    }

    


}
