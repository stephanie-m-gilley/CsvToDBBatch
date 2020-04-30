package com.springbatch.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CsvDTO {

    @NotBlank
    @Size(min=1, max=30)
    private String surName;

    @NotBlank
    @Size(min=1, max=30)
    private String firstName;

    @Size(min=1, max=1)
    private String initial;

    @NotBlank
    @Pattern(regexp = "[A-za-z0-9 .]{1,30}")
    private String addrL1;

    @Pattern(regexp = "$|[A-za-z0-9 .]{1,30}")
    private String addrL2;

    @NotBlank
    @Pattern(regexp = "[A-za-z0-9 .]{1,30}")
    private String city;

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}", message="Province must be alpha characters of length 2. Ex: ON or NY" )
    private String province;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}", message="Country must be alpha characters of length 3. Ex: USA or CAN" )
    private String country;

    @NotBlank
    @Pattern(regexp = "[0-9]{5,9}", message="Zip code must be alphanumeric characters of length 5 to 9." )
    private String zip;

}
