package com.casic.microservice.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.casic.microservice.common.core.exception.BusinessException;
import com.casic.microservice.common.core.result.ResultCode;
import com.casic.microservice.contract.entity.Contract;
import com.casic.microservice.contract.mapper.ContractMapper;
import com.casic.microservice.contract.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合同 Service 实现
 */
@Slf4j
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {

    @Override
    public Contract getById(Long id) {
        Contract contract = super.getById(id);
        if (contract == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND, "合同不存在: " + id);
        }
        return contract;
    }

    @Override
    public IPage<Contract> page(Page<Contract> page, String keyword, Integer status) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(w -> w
                    .like(Contract::getContractNo, keyword)
                    .or().like(Contract::getContractName, keyword)
                    .or().like(Contract::getPartyA, keyword)
                    .or().like(Contract::getPartyB, keyword)
            );
        }
        if (status != null) {
            wrapper.eq(Contract::getStatus, status);
        }
        wrapper.orderByDesc(Contract::getCreateTime);
        return super.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Contract create(Contract contract) {
        // 校验合同编号唯一性
        Long count = super.count(new LambdaQueryWrapper<Contract>()
                .eq(Contract::getContractNo, contract.getContractNo()));
        if (count > 0) {
            throw new BusinessException(ResultCode.DATA_DUPLICATE, "合同编号已存在: " + contract.getContractNo());
        }
        super.save(contract);
        log.info("创建合同成功: {}", contract.getContractNo());
        return contract;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Contract contract) {
        Contract exist = super.getById(contract.getId());
        if (exist == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND, "合同不存在: " + contract.getId());
        }
        return super.updateById(contract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        Contract exist = super.getById(id);
        if (exist == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND, "合同不存在: " + id);
        }
        return super.removeById(id);
    }
}
