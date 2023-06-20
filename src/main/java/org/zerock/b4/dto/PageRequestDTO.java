package org.zerock.b4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    public void setPage(Integer page){
        if(page <= 0){
            this.page = 1;
        } else {
            this.page = page;
        }
    }    

    public void setSize(Integer size){
        if(size <= 0 || size > 100){
            this.size = 10;
        } else {
            this.size = size;
        }
    }    

    public int getSkip(){
        return (this.page - 1) * this.size;
    }

    public int getEnd(){
        return this.page * this.size;
    }

    public int getCountEnd(){
        int temp = (int) (Math.ceil(this.page/10.0)) * (10*size);

        return temp + 1;
    }
}
