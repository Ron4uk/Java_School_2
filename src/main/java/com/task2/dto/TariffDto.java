package com.task2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TariffDto implements Serializable {
    private Integer id;
    private String tariff;
    private BigDecimal price;
    private Boolean deleted;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> options = new HashSet<>();


}