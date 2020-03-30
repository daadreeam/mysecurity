package com.lsw.lswcommon.es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String name;
    private Integer age;
    private Float salary;
    private String address;
    private Date createTime;
    private String birthDate;
    private String remark;
}
