package org.zerock.b4.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.b4.dto.RequestFileRemoveDTO;
import org.zerock.b4.dto.UploadResultDTO;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@Log4j2
public class UpDownController {

    @Value("${org.zerock.upload.path}") // import 시에 springframework으로 시작하는 Value
    private String uploadPath;

    @PostMapping("/upload")
    public List<UploadResultDTO> upload(MultipartFile[] files) {

        // file 업로드 안하면
        if (files == null || files.length == 0) {
            return null;
        }

        // 여러파일들을 업로드 하기 위해서 List로 선언
        List<UploadResultDTO> resultList = new ArrayList<>();

        for (MultipartFile file : files) {
            
            UploadResultDTO result = null;
            String fileName = file.getOriginalFilename();

            log.info("fileName: " + fileName);
            long size = file.getSize();
            log.info("size: " + size);

            // uuid 생성 => 중복될일이 천문학적
            String uuidStr = UUID.randomUUID().toString();
            // uuid + fileName
            String saveFileName = uuidStr + "_" + fileName;
            // 지정한 경로에 파일 저장 inFile
            File saveFile = new File(uploadPath, saveFileName);

            // JVM이랑 파일서버랑 통신할때 exception
            try {
                // inputStream => outPutStream
                FileCopyUtils.copy(file.getBytes(), saveFile);
                // 파일 확장자 체크

                result = UploadResultDTO.builder()
                        .uuid(uuidStr)
                        .fileName(fileName)
                        .build();

                // 이미지 파일 여부 확인
                String mimeType = Files.probeContentType(saveFile.toPath());

                log.info("mimeType: " + mimeType);

                if (mimeType.startsWith("image")) {
                    // 업로드성공 섬네일
                    // 덮어써서 S를 붙임
                    File thumFile = new File(uploadPath, "s_" + saveFileName);

                    Thumbnailator.createThumbnail(saveFile, thumFile, 100, 100);
                    result.setImg(true);
                } // end if

                resultList.add(result);

            } catch (IOException e) {

                e.printStackTrace();

            }
        } // end for
          // 결과값은 JSON 데이터 getSkip하면 skip프로퍼티가 들어간다?
        return resultList;

    }

    // Nginx에 이미지가 있어서 사실상 필요가 없음
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    // 파일삭제
    @DeleteMapping("removeFile/{fileName}")
    public Map<String,String> removeFile(

        @PathVariable("fileName") String fileName

        // JSON을 DTO객체로 바꾸는 어노테이션
        // @RequestBody RequestFileRemoveDTO dto
        )
        {

            // String fileName = dto.getFileName();

            log.info("DELETE | /removeFile");
            log.info(fileName);

            File originFile = new File(uploadPath, fileName);

            // JVM외부랑 연결되는 소스는 exception 처리
            try {
                String mimeType = Files.probeContentType(originFile.toPath());

                if(mimeType.startsWith("image")){
                    File thumbFile = new File(uploadPath, "s_"+fileName);
                    thumbFile.delete();
                }

                originFile.delete();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return Map.of("result", "success");
        }
}
