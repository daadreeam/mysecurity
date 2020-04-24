package com.lsw.craw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JdPhone implements Serializable {
    private Long id;

    private Long skuId;

    private Long spuId;

    private String title;

    private BigDecimal price;

    private String color;

    private String promoWord;

    private String imgName;

    private String imgPath;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static JdPhoneBuilder builder() {
        return new JdPhoneBuilder();
    }
}