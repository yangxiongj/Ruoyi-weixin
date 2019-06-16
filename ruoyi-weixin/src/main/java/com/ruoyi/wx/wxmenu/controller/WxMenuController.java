package com.ruoyi.wx.wxmenu.controller;

import java.util.List;

import com.ruoyi.wx.wxmenu.domain.WxMenu;
import com.ruoyi.wx.wxmenu.service.IWxMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 微信菜单 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-06-16
 */
@Controller
@RequestMapping("/wx/wxmenu")
public class WxMenuController extends BaseController
{
    private String prefix = "wx/wxmenu";
	
	@Autowired
	private IWxMenuService wxMenuService;
	
	@RequiresPermissions("wx:wxmenu:view")
	@GetMapping()
	public String wxMenu()
	{
	    return prefix+"/wxMenu";
	}
	
	/**
	 * 查询微信菜单列表
	 */
	@RequiresPermissions("wx:wxmenu:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(WxMenu wxMenu)
	{
		startPage();
        List<WxMenu> list = wxMenuService.selectWxMenuList(wxMenu);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出微信菜单列表
	 */
	@RequiresPermissions("wx:wxmenu:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WxMenu wxMenu)
    {
    	List<WxMenu> list = wxMenuService.selectWxMenuList(wxMenu);
        ExcelUtil<WxMenu> util = new ExcelUtil<WxMenu>(WxMenu.class);
        return util.exportExcel(list, "templates/wx/wxmenu/wxMenu");
    }
	
	/**
	 * 新增微信菜单
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存微信菜单
	 */
	@RequiresPermissions("wx:wxmenu:add")
	@Log(title = "微信菜单", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(WxMenu wxMenu)
	{		
		return toAjax(wxMenuService.insertWxMenu(wxMenu));
	}

	/**
	 * 修改微信菜单
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		WxMenu wxMenu = wxMenuService.selectWxMenuById(id);
		mmap.put("templates/wx/wxmenu", wxMenu);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存微信菜单
	 */
	@RequiresPermissions("wx:wxmenu:edit")
	@Log(title = "微信菜单", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(WxMenu wxMenu)
	{		
		return toAjax(wxMenuService.updateWxMenu(wxMenu));
	}
	
	/**
	 * 删除微信菜单
	 */
	@RequiresPermissions("wx:wxmenu:remove")
	@Log(title = "微信菜单", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(wxMenuService.deleteWxMenuByIds(ids));
	}
	
}
