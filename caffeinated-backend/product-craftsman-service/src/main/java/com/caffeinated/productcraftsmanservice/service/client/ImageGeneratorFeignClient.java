package com.caffeinated.productcraftsmanservice.service.client;

import com.caffeinated.productcraftsmanservice.config.ImageGeneratorConfig;
import com.caffeinated.productcraftsmanservice.dto.openapi.GenerateImageRequest;
import com.caffeinated.productcraftsmanservice.dto.openapi.GenerateImageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "imageGenerator", url = "${openai.image-generator.host}", configuration = ImageGeneratorConfig.class)
public interface ImageGeneratorFeignClient {

    @PostMapping(value = "${openai.image-generator.endpoint}")
    GenerateImageResponse generateImage(@RequestBody final GenerateImageRequest request);

}
