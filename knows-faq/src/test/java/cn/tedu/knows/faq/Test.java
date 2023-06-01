package cn.tedu.knows.faq;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        BatchCreateByDownloadUrlRequestDTO requestDTO = new BatchCreateByDownloadUrlRequestDTO();
        requestDTO.productLineCode = "SINO_CLICK";
        requestDTO.workCollects = new ArrayList<>();
        requestDTO.checkBatchCreateByDownloadUrlParams(requestDTO);
        System.out.println(requestDTO.getWorkCollects());
        System.out.println(requestDTO.getWorkCollects());

    }
}

class BatchCreateByDownloadUrlRequestDTO{
    String productLineCode;

    public String getProductLineCode() {
        return productLineCode;
    }

    public List<CreateByDownloadUrlDTO> getWorkCollects() {
        return workCollects;
    }

    List<CreateByDownloadUrlDTO> workCollects;

    public void checkBatchCreateByDownloadUrlParams(BatchCreateByDownloadUrlRequestDTO requestDTO) {
        Preconditions.checkNotNull(requestDTO.getProductLineCode(), "productLineCode is null");
        Preconditions.checkNotNull(requestDTO.getWorkCollects(), "workCollects is null");
    }

}
