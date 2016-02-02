package com.armorhero.controller.dealer;

import com.armorhero.controller.base.BaseController;
import com.armorhero.entity.system.SysStatic;
import com.armorhero.service.dealer.settlement.SettlementService;
import com.armorhero.util.Const;
import com.armorhero.util.NumberValidationUtils;
import com.armorhero.util.PageData;
import com.armorhero.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by chenwei on 2015/12/10 0010.
 * desc:结算管理
 */
@Controller
@RequestMapping(value = "/settlement")
public class SettlementController extends BaseController {

    @Resource(name = "settlementService")
    private SettlementService settlementService;

    /**
     * 结算设置页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toSettlementSetting")
    public ModelAndView toSettlementSetting() throws Exception {
        PageData pd = new PageData();
        ModelAndView mv = this.getModelAndView();
        pd = this.getPageData();

        //获取结算系数
        SysStatic sysStatic = new SysStatic();
        sysStatic.setSTATIC_KEY(Const.STATIC_KEY_SETTLEMENT_COEFFICIENT);
        SysStatic coefficientStatic = settlementService.getStaticByKey(sysStatic);
        if (null != coefficientStatic) {
            pd.put("coefficient", coefficientStatic.getSTATIC_VALUE());
        } else {//默认1
            pd.put("coefficient", "1");
            sysStatic.setDESCRIPTION("结算系数");
            sysStatic.setSTATIC_VALUE("1");
            settlementService.addStatic(sysStatic);
        }

        //获取结算单价
        sysStatic.setSTATIC_KEY(Const.STATIC_KEY_SETTLEMENT_PRICE);
        SysStatic priceStatic = settlementService.getStaticByKey(sysStatic);
        if (null != priceStatic) {
            pd.put("price", priceStatic.getSTATIC_VALUE());
        } else {//默认1
            sysStatic.setDESCRIPTION("结算单价");
            sysStatic.setSTATIC_VALUE("1");
            settlementService.addStatic(sysStatic);
            pd.put("price", "1");
        }
        mv.setViewName("dealer/settlementSetting");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 结算设置页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        PageData pd = new PageData();
        ModelAndView mv = this.getModelAndView();
        pd = this.getPageData();

        //修改结算系数
        String coefficient = (String)pd.get("coefficient");
        if(StringUtils.isNotEmpty(coefficient) && (NumberValidationUtils.isPositiveInteger(coefficient) || NumberValidationUtils.isPositiveDecimal(coefficient))){//结算系数必须为数字
            SysStatic sysStatic = new SysStatic();
            sysStatic.setSTATIC_KEY(Const.STATIC_KEY_SETTLEMENT_COEFFICIENT);
            sysStatic.setSTATIC_VALUE(coefficient);
            settlementService.updateByKey(sysStatic);
        }

        //修改结算单价
        String price = (String)pd.get("price");
        if(StringUtils.isNotEmpty(price) && (NumberValidationUtils.isPositiveInteger(price) || NumberValidationUtils.isPositiveDecimal(price))){//结算单价必须为数字
            SysStatic sysStatic = new SysStatic();
            sysStatic.setSTATIC_KEY(Const.STATIC_KEY_SETTLEMENT_PRICE);
            sysStatic.setSTATIC_VALUE(price);
            settlementService.updateByKey(sysStatic);
        }

        mv.setViewName("dealer/settlementSetting");
        mv.addObject("pd", pd);
        return mv;
    }
}
