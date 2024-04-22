package com.liuwei.api;

import com.liuwei.framework.domain.po.Menu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("system-admin")
public interface SecurityClient {
    @GetMapping("/roleMenu/menu/{roleId}")
    public List<Menu> getMenuByRoleId(@PathVariable(value = "roleId") Long roleId);
}
