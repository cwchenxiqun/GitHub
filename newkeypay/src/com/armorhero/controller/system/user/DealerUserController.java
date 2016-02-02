package com.armorhero.controller.system.user;

import com.armorhero.controller.base.BaseController;
import com.armorhero.entity.Page;
import com.armorhero.entity.system.Role;
import com.armorhero.entity.system.User;
import com.armorhero.service.system.menu.MenuService;
import com.armorhero.service.system.role.RoleService;
import com.armorhero.service.system.user.UserService;
import com.armorhero.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类名称：UserController
 * 经销商账号管理
 *
 * @author chenwei
 *         创建时间：2014年6月28日
 */
@Controller
@RequestMapping(value = "/dealerUser")
public class DealerUserController extends BaseController {

    String menuUrl = "dealerUser/listUsers.do"; //菜单地址(权限用)
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "roleService")
    private RoleService roleService;
    @Resource(name = "menuService")
    private MenuService menuService;

    private List<Role> roleList;

    /**
     * 保存用户
     */
    @RequestMapping(value = "/saveU")
    public ModelAndView saveU(PrintWriter out) throws Exception {
        PageData pd = new PageData();
        ModelAndView mv = this.getModelAndView();

        pd = this.getPageData();
        pd.put("USER_ID", this.get32UUID());    //ID
        pd.put("RIGHTS", "");                    //权限
        pd.put("LAST_LOGIN", "");                //最后登录时间
        pd.put("IP", "");                        //IP
        pd.put("STATUS", "0");                    //状态
        pd.put("SKIN", "default");                //默认皮肤
        pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());

        if (null == userService.findByUId(pd)) {
            if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
                userService.saveU(pd);
            } //判断新增权限
            mv.addObject("msg", "success");
        } else {
            mv.addObject("msg", "failed");
        }
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 判断用户名是否存在
     */
    @RequestMapping(value = "/hasU")
    @ResponseBody
    public Object hasU() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (userService.findByUId(pd) != null) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 判断邮箱是否存在
     */
    @RequestMapping(value = "/hasE")
    @ResponseBody
    public Object hasE() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (userService.findByUE(pd) != null) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/editU")
    public ModelAndView editU() throws Exception {
        PageData pd = new PageData();
        ModelAndView mv = this.getModelAndView();
        pd = this.getPageData();
        if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
            pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
        }

        if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            userService.editU(pd);
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改用户页面
     */
    @RequestMapping(value = "/goEditU")
    public ModelAndView goEditU() throws Exception {
        PageData pd = new PageData();
        ModelAndView mv = this.getModelAndView();
        pd = this.getPageData();

        //顶部修改个人资料
        String fx = pd.getString("fx");
        if ("head".equals(fx)) {
            mv.addObject("fx", "head");
        } else {
            mv.addObject("fx", "user");
        }

        pd = userService.findByUiId(pd);//根据ID读取

        mv.setViewName("system/user/dealer_user_edit");
        mv.addObject("msg", "editU");
        mv.addObject("pd", pd);
        mv.addObject("roleList", listAllERRoles());

        return mv;
    }

    /**
     * 去新增用户页面
     */
    @RequestMapping(value = "/goAddU")
    public ModelAndView goAddU() throws Exception {
        PageData pd = new PageData();
        ModelAndView mv = this.getModelAndView();
        pd = this.getPageData();

        mv.setViewName("system/user/dealer_user_edit");
        mv.addObject("msg", "saveU");
        mv.addObject("pd", pd);
        mv.addObject("roleList", listAllERRoles());

        return mv;
    }

    /**
     * 去用户个人信息页面
     */
    @RequestMapping(value = "/goViewU")
    public ModelAndView goViewU() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User loginUser = (User)session.getAttribute(Const.SESSION_USER);
        pd.put("USER_ID", loginUser.getUSER_ID());
        pd = userService.findByUiId(pd);//根据ID读取

        mv.setViewName("system/user/dealer_user_view");
        mv.addObject("msg", "viewU");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 去修改登陆账号信息界面
     */
    @RequestMapping(value = "/goEditUByOneself")
    public ModelAndView goEditUByOneself() throws Exception {
        PageData pd = new PageData();
        ModelAndView mv = this.getModelAndView();
        pd = this.getPageData();

        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User loginUser = (User)session.getAttribute(Const.SESSION_USER);
        pd.put("USER_ID", loginUser.getUSER_ID());
        pd = userService.findByUiId(pd);//根据ID读取

        //顶部修改个人资料
        String fx = pd.getString("fx");
        if ("head".equals(fx)) {
            mv.addObject("fx", "head");
        } else {
            mv.addObject("fx", "user");
        }
        mv.setViewName("system/user/dealer_user_edit_oneself");
        mv.addObject("msg", "editUByOneself");
        mv.addObject("pd", pd);
        mv.addObject("roleList", listAllERRoles());
        return mv;
    }

    /**
     * 修改登陆账号信息
     */
    @RequestMapping(value = "/editUByOneself")
    public ModelAndView editUByOneself() throws Exception {
        PageData pd = new PageData();
        ModelAndView mv = this.getModelAndView();
        pd = this.getPageData();
        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User loginUser = (User)session.getAttribute(Const.SESSION_USER);
        pd.put("USER_ID", loginUser.getUSER_ID());
        pd.put("DEALER_CODE",null);//防止用户伪造篡改DEALER_CODE
        if (StringUtils.isNotEmpty(pd.getString("PASSWORD"))) {
            pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
        }

        userService.editU(pd);

        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 显示经销商用户列表
     */
    @RequestMapping(value = "/listUsers")
    public ModelAndView listUsers(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        String USERNAME = pd.getString("USERNAME");

        if (null != USERNAME && !"".equals(USERNAME)) {
            USERNAME = USERNAME.trim();
            pd.put("USERNAME", USERNAME);
        }

        String lastLoginStart = pd.getString("lastLoginStart");
        String lastLoginEnd = pd.getString("lastLoginEnd");

        if (lastLoginStart != null && !"".equals(lastLoginStart)) {
            lastLoginStart = lastLoginStart + " 00:00:00";
            pd.put("lastLoginStart", lastLoginStart);
        }
        if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
            lastLoginEnd = lastLoginEnd + " 00:00:00";
            pd.put("lastLoginEnd", lastLoginEnd);
        }

        pd.put("ROOT_ROLE_ID", Const.DEALER_ROLE_ID);//不显示经销商用户

        page.setPd(pd);
        List<PageData> userList = userService.listPdPageUser(page);            //列出用户列表

        mv.setViewName("system/user/dealer_user_list");
        mv.addObject("userList", userList);
        mv.addObject("roleList", listAllERRoles());
        mv.addObject("pd", pd);
        mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        return mv;
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/deleteU")
    public void deleteU(PrintWriter out) {
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
                userService.deleteU(pd);
            }
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAllU")
    @ResponseBody
    public Object deleteAllU() {
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String USER_IDS = pd.getString("USER_IDS");

            if (null != USER_IDS && !"".equals(USER_IDS)) {
                String ArrayUSER_IDS[] = USER_IDS.split(",");
                if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
                    userService.deleteAllU(ArrayUSER_IDS);
                }
                pd.put("msg", "ok");
            } else {
                pd.put("msg", "no");
            }

            pdList.add(pd);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
    }
    //===================================================================================================

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }

    /**
     * 获取所有所有系统管理二级角色
     *
     * @return
     * @throws Exception
     */
    private List<Role> listAllERRoles() throws Exception {
        Role role = new Role();
        role.setPARENT_ID(Const.DEALER_ROLE_ID);
        return roleService.listAllERRoles(role);//列出所有系统管理二级角色
    }

    /* ===============================权限================================== */
    private Map<String, String> getHC() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */
}
