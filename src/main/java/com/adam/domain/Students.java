package com.adam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Students {
    private Long id;

    private String name;

    private Long registeredOn;

    private Integer status;
}
