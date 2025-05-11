package ru.golovkov.fintracker.util;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.fintracker.dto.AccountDto;
import ru.golovkov.fintracker.dto.ClientDto;
import ru.golovkov.fintracker.dto.parsed.ParsedEntitiesDto;

import static ru.golovkov.fintracker.util.FinparserClient.MultipartSupportConfig;

@FeignClient(name = "finparser", url = "${finparser.url}", configuration = MultipartSupportConfig.class)
public interface FinparserClient {

    @PostMapping(value = "/parsing/rows/{rowNum}/cells/{cellNum}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String getCell(
            @RequestPart("file") MultipartFile sheetFile,
            @PathVariable("rowNum") Integer rowNum,
            @PathVariable("cellNum") Integer cellNum
    );

    @PostMapping(value = "/parsing/client", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ClientDto getClient(@RequestPart("file") MultipartFile sheetFile);

    @PostMapping(value = "/parsing/account", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    AccountDto getAccount(@RequestPart("file") MultipartFile sheetFile);

    @PostMapping(value = "/parsing/parsed-entities", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ParsedEntitiesDto getParsedEntities(@RequestPart("file") MultipartFile sheetFile);

    @Configuration
    class MultipartSupportConfig {

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }
}
