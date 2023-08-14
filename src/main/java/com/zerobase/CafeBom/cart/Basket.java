package com.zerobase.CafeBom.cart;


import com.springboot.Weather.domain.product.AddProductBasketForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Basket {

    @Id
    private Long customerId;

    private List<Product> products = new ArrayList<>();


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class Product{

        private Long id;
        private Long sellerId;
        private String name;
        private String description;
        private List<ProductItem>items = new ArrayList<>();

        public static Product from(AddProductBasketForm form)
        {
            return Product.builder()
                    .id(form.getProductId())
                    .sellerId(form.getSellerId())
                    .name(form.getName())
                    .description(form.getDescription())
                    .build();
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class ProductItem{

        private Long id;
        private String name;
        private Integer count;
        private Integer price;


    }

}
