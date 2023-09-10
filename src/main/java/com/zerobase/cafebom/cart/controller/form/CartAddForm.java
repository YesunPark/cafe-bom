package com.zerobase.cafebom.cart.controller.form;

import com.zerobase.cafebom.cart.domain.entity.type.CartOrderStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartAddForm {

    private Integer productId;

    private Integer count;

    private List<Integer> optionIdList;

    private CartOrderStatus cartOrderStatus;
}
