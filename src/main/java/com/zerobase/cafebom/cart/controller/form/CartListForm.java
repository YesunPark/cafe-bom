package com.zerobase.cafebom.cart.controller.form;

import com.zerobase.cafebom.option.domain.entity.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartListForm {

    @AllArgsConstructor
    @NotNull
    @Builder
    @Getter
    public static class Response {

        private Integer productId;

        private String productName;

        private String productPicture;

        private List<Option> productOptions;

        private Integer productCount;

    }
}
