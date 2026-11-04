package com.casic.microservice.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.casic.microservice.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 合同实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_contract")
public class Contract extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 合同编号 */
    private String contractNo;

    /** 合同名称 */
    private String contractName;

    /** 合同类型 (1-采购合同 2-销售合同 3-框架协议) */
    private Integer contractType;

    /** 合同金额 */
    private BigDecimal amount;

    /** 签订日期 */
    private LocalDate signDate;

    /** 生效日期 */
    private LocalDate effectiveDate;

    /** 到期日期 */
    private LocalDate expireDate;

    /** 合同状态 (0-草稿 1-待审批 2-已生效 3-已终止) */
    private Integer status;

    /** 甲方名称 */
    private String partyA;

    /** 乙方名称 */
    private String partyB;

    /** 备注 */
    private String remark;

    /** 合同类型名称（非数据库字段） */
    @TableField(exist = false)
    private String contractTypeName;
}
