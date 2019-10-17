package com.javalab.clothshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse extends BaseEntity {

    private int code;
    private String type;
    private String message;
}
