package com.zerobase.cafebom.cart.service.dto;

import com.zerobase.cafebom.option.domain.Option;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@NotNull
@Builder
@Getter
public class CartListDto {

    private Integer productId;

    private String productName;

    private String productPicture;

    private List<Option> productOptions;

    private Integer productCount;
}
