package com.jatramaya.bibliotech.dto;

import com.jatramaya.bibliotech.utils.validator.ValidProfileContent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidProfileContent
public class AddProfileDTO {

    private String avatarUrl;
    private String bio;

}
