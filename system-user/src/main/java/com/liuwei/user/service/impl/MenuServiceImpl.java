package com.liuwei.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.po.Menu;
import com.liuwei.user.dao.MenuDao;
import com.liuwei.user.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:35:18
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuDao,Menu> implements MenuService {

}
