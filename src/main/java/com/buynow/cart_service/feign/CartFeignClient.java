package com.buynow.cart_service.feign;

import com.buynow.cart_service.dto.response.ProductResponse;
import com.buynow.cart_service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface CartFeignClient {

    @GetMapping("${api.prefix}/products/{productId}")
    ApiResponse<ProductResponse> getProductById(@PathVariable Long productId);

}
