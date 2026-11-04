package com.casic.microservice.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.casic.microservice.contract.entity.Contract;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同 Mapper
 */
@Mapper
public interface ContractMapper extends BaseMapper<Contract> {
}
