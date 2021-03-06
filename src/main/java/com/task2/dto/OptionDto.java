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
@ToString
@EqualsAndHashCode

public class OptionDto implements Serializable {
    private Integer id;
    private String name;
    private BigDecimal price;
    private BigDecimal connectionCost;
    private Boolean deleted;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> requiredOptions= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OptionDto> exclusionOptions= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Integer> requirementsId= new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Integer> exclusionsId= new HashSet<>();




}