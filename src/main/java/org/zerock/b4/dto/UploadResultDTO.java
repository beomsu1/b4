package org.zerock.b4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    
    private String uuid;
    private String fileName;
    private boolean img;

    // Default 이미지가 들어가게 설정 Json데이터의 link프로퍼티가 추가된다.
    public String getLink(){

        if(img){
            return "s_"+uuid+"_"+fileName;
        }else{
            return "default.jpg";
        }
    }

}
