package com.casic.microservice.contract.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casic.microservice.contract.entity.Contract;

/**
 * 合同 Service
 */
public interface ContractService {

    /** 根据 ID 查询 */
    Contract getById(Long id);

    /** 分页查询 */
    IPage<Contract> page(Page<Contract> page, String keyword, Integer status);

    /** 新增 */
    Contract create(Contract contract);

    /** 更新 */
    boolean update(Contract contract);

    /** 删除 */
    boolean delete(Long id);
}
