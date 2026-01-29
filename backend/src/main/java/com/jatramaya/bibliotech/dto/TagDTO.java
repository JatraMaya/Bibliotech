package com.jatramaya.bibliotech.dto;

import com.jatramaya.bibliotech.entity.book.TagEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {

    private Long id;
    private String name;

    public TagDTO(TagEntity tag) {
        name = tag.getName();
        id = tag.getId();
    }

}
