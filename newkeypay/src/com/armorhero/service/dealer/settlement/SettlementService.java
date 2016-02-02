package com.armorhero.service.dealer.settlement;

import com.armorhero.dao.DaoSupport;
import com.armorhero.entity.system.SysStatic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by chenwei on 2015/12/10 0010.
 */
@Service("settlementService")
public class SettlementService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
    * 新增静态配置
	*/
    public void addStatic(SysStatic sysStatic) throws Exception {
        dao.findForObject("StaticMapper.addStatic", sysStatic);
    }

    /*
    * 获取所有静态参数
	*/
    public SysStatic getAllStatic() throws Exception {
        return (SysStatic) dao.findForObject("StaticMapper.getAllStatic", null);
    }

    /*
   * 通过KEY获取静态参数
   */
    public SysStatic getStaticByKey(SysStatic sysStatic) throws Exception {
        return (SysStatic) dao.findForObject("StaticMapper.getStaticByKey", sysStatic);
    }

    /*
   * 通过KEY修改静态参数
   */
    public void updateByKey(SysStatic sysStatic) throws Exception {
        dao.update("StaticMapper.updateByKey", sysStatic);
    }
}
