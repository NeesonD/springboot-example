package com.neeson.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/27
 * Time: 15:25
 * Description:
 */
@Data
public class QiniuDTO {

    @ApiModelProperty(value = "直播流",required = true)
    @NotNull
    private String streamKey;

    @ApiModelProperty(value = "开始时间",required = true)
    @NotNull
    private Integer start;

    @ApiModelProperty(value = "结束时间",required = true)
    @NotNull
    private Integer end;

}
