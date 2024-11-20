package app.controller;

import app.dto.input.CategoryDto;
import app.dto.input.ProductDto;
import app.service.DataReceiverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/v1")
public class ParserController
{
    final DataReceiverService service;

    @PostMapping(path = "category", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getCategory(@RequestBody CategoryDto categoryDto)
    {
        service.sendCategoryInfoToReceiver(categoryDto.url());
        return "Success";
    }

    @PostMapping(path = "product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getProduct(@RequestBody ProductDto productDto)
    {
        service.sendProductInfoToReceiver(productDto.url());
        return "Success";
    }
}
