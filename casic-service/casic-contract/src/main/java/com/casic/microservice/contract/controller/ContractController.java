package com.casic.microservice.contract.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casic.microservice.common.core.result.R;
import com.casic.microservice.contract.entity.Contract;
import com.casic.microservice.contract.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 合同控制器
 */
@Slf4j
@RestController
@RequestMapping("/contract")
@Validated
public class ContractController {

    @Resource
    private ContractService contractService;

    /**
     * 根据 ID 查询合同
     */
    @GetMapping("/{id}")
    public R<Contract> getById(@NotNull(message = "ID不能为空") @PathVariable Long id) {
        return R.ok(contractService.getById(id));
    }

    /**
     * 分页查询合同
     */
    @GetMapping("/page")
    public R<IPage<Contract>> page(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "20") long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {
        Page<Contract> page = new Page<>(current, size);
        return R.ok(contractService.page(page, keyword, status));
    }

    /**
     * 新增合同
     */
    @PostMapping
    public R<Contract> create(@Valid @RequestBody CreateContractDTO dto) {
        log.info("新增合同: {}", dto.getContractNo());
        Contract contract = new Contract();
        contract.setContractNo(dto.getContractNo());
        contract.setContractName(dto.getContractName());
        contract.setContractType(dto.getContractType());
        contract.setAmount(dto.getAmount());
        contract.setSignDate(dto.getSignDate());
        contract.setEffectiveDate(dto.getEffectiveDate());
        contract.setExpireDate(dto.getExpireDate());
        contract.setStatus(0); // 草稿
        contract.setPartyA(dto.getPartyA());
        contract.setPartyB(dto.getPartyB());
        contract.setRemark(dto.getRemark());
        return R.ok("创建成功", contractService.create(contract));
    }

    /**
     * 更新合同
     */
    @PutMapping("/{id}")
    public R<Boolean> update(@PathVariable Long id, @RequestBody UpdateContractDTO dto) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setContractName(dto.getContractName());
        contract.setAmount(dto.getAmount());
        contract.setStatus(dto.getStatus());
        contract.setRemark(dto.getRemark());
        return R.ok(contractService.update(contract));
    }

    /**
     * 删除合同
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.ok(contractService.delete(id));
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public R<String> health() {
        return R.ok("casic-contract is running!");
    }

    // ================ DTO 内部类 ================

    @lombok.Data
    public static class CreateContractDTO {
        @NotNull(message = "合同编号不能为空")
        private String contractNo;
        @NotNull(message = "合同名称不能为空")
        private String contractName;
        private Integer contractType;
        private BigDecimal amount;
        private java.time.LocalDate signDate;
        private java.time.LocalDate effectiveDate;
        private java.time.LocalDate expireDate;
        private String partyA;
        private String partyB;
        private String remark;
    }

    @lombok.Data
    public static class UpdateContractDTO {
        private String contractName;
        private BigDecimal amount;
        private Integer status;
        private String remark;
    }
}
