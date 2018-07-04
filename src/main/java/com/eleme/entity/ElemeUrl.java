package com.eleme.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ElemeUrl extends BaseEntity{

    private String url;

    private String status;


}
