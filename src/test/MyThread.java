import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class MyThread extends Thread{
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        EsealActivateDetailInfo esealActivateDetailInfo =new EsealActivateDetailInfo();
        esealActivateDetailInfo.setEnterpriseName("12345");
        esealActivateDetailInfo.setActivateDate("2022-11-29 10:42:25");
        esealActivateDetailInfo.setValidityPeriod("2023-11-29 10:42:25");
        esealActivateDetailInfo.setActivateUserId("12345");
        esealActivateDetailInfo.setSealCode("12345");
        esealActivateDetailInfo.setActiveType(0);
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("session_token",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1OTkyMzg2NjQyNTMzNDE2OTYiLCJ1c2VySWQiOiI1OTkyMzg2NjQyNTMzNDE2OTYiLCJpc3MiOiJzaWduLXN5cyIsImlhdCI6MTY2OTc4OTYzMywiZXhwIjoxNjY5ODc2MDMzfQ.JLgF9owB3F7Yy9TEkGNwRWvQpj2B_RcOH7SuUslbMcyJkmcTzLFsecLBKakQwzCC0YTFJPEozoB6MLBpRs7aLg");
        //HttpEntity
        HttpEntity<EsealActivateDetailInfo> requestEntity = new HttpEntity<EsealActivateDetailInfo>(esealActivateDetailInfo, requestHeaders);
        //post
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.postForEntity("http://localhost:18002/activate/saveEsealActivateInfo", requestEntity, String.class));
    }
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        thread1.start();
        thread2.start();
    }

    @Data
    private static class EsealActivateDetailInfo {

        private static final long serialVersionUID = 2907001850703083876L;

        private String enterpriseName;

        private String sealCode;

        private String activateDate;

        private String validityPeriod;

        private String activateUserId;

        private Integer activeType;

    }
}
