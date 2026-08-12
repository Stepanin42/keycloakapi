package com.ipr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credential {

    private String type;

    private String value;

    private boolean temporary;
}
