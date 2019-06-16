package com.ruoyi.wx.wxmenu.service;

import com.ruoyi.wx.wxmenu.domain.WxMenu;
import java.util.List;

/**
 * 微信菜单 服务层
 * 
 * @author ruoyi
 * @date 2019-06-16
 */
public interface IWxMenuService 
{
	/**
     * 查询微信菜单信息
     * 
     * @param id 微信菜单ID
     * @return 微信菜单信息
     */
	public WxMenu selectWxMenuById(Integer id);
	
	/**
     * 查询微信菜单列表
     * 
     * @param wxMenu 微信菜单信息
     * @return 微信菜单集合
     */
	public List<WxMenu> selectWxMenuList(WxMenu wxMenu);
	
	/**
     * 新增微信菜单
     * 
     * @param wxMenu 微信菜单信息
     * @return 结果
     */
	public int insertWxMenu(WxMenu wxMenu);
	
	/**
     * 修改微信菜单
     * 
     * @param wxMenu 微信菜单信息
     * @return 结果
     */
	public int updateWxMenu(WxMenu wxMenu);
		
	/**
     * 删除微信菜单信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteWxMenuByIds(String ids);
	
}
