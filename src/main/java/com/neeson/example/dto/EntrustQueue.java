package com.neeson.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/21
 * Time: 15:46
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrustQueue  {

    @ApiModelProperty(value = "ID", example = "1", allowableValues = "range[0, 12]")
    private Long id;

    @ApiModelProperty(value = "交易帐号ID", example = "10001", required = true)
    @NotNull(message = "accountId:不能为空")
    private Long accountId;

    @ApiModelProperty(value = "股票代码", example = "600100", required = true)
    @NotNull(message = "stockCode:不能为空")
    private String stockCode;

    @ApiModelProperty(value = "委托数量", example = "100", required = true)
    @NotNull(message = "volume:不能为空")
    private Integer volume;

    @ApiModelProperty(value = "委托价格", example = "10.00", required = true)
    @NotNull(message = "price:不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "委托类型", example = "1", allowableValues = "1, 2", required = true)
    @Range(min = 1, max = 2, message = "entrustType:数据范围只能取1或2")
    private Integer entrustType;

    private Long createAt;
    private Long updateAt;

}