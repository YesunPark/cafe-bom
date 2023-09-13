package com.zerobase.cafebom.cart.controller.form;

import com.zerobase.cafebom.type.CartOrderStatus;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartAddForm {

    @NotNull(message = "productId는 필수입니다.")
    private Integer productId;

    @NotNull(message = "count는 필수입니다.")
    private Integer count;

    private List<Integer> optionIdList;

    @NotNull(message = "cartOrderStatus는 필수입니다.")
    private CartOrderStatus cartOrderStatus;
}
