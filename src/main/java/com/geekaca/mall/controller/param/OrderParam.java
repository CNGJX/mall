package com.geekaca.mall.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class OrderParam {
    @NotNull
    private Integer addressId;
    @NotEmpty
    private Integer[] cartItemIds;
}
