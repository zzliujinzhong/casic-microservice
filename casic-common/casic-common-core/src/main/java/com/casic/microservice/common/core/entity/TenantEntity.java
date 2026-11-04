package com.casic.microservice.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户基础实体 - 多租户场景使用
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 租户 ID */
    @TableField("tenant_id")
    private Long tenantId;
}
