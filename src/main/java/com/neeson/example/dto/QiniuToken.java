package com.neeson.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MyPC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QiniuToken {

    private String token;
    private String originalKey;
    private String thumbnailsKey;

}
