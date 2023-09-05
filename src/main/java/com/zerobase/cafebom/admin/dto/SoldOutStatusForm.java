package com.zerobase.cafebom.admin.dto;

import com.zerobase.cafebom.type.SoldOutStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class SoldOutStatusForm {

    @Schema(description = "품절여부", example = "SOLD_OUT")
    @NotNull(message = "품절여부는 필수로 입력해야 합니다.")
    private SoldOutStatus soldOutStatus;

}
