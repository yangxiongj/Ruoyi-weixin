package com.ruoyi.wx.wxmenu.service.impl;

import java.util.List;

import com.ruoyi.wx.wxmenu.domain.WxMenu;
import com.ruoyi.wx.wxmenu.mapper.WxMenuMapper;
import com.ruoyi.wx.wxmenu.service.IWxMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;

/**
 * 微信菜单 服务层实现
 * 
 * @author ruoyi
 * @date 2019-06-16
 */
@Service
public class WxMenuServiceImpl implements IWxMenuService
{
	@Autowired
	private WxMenuMapper wxMenuMapper;

	/**
     * 查询微信菜单信息
     * 
     * @param id 微信菜单ID
     * @return 微信菜单信息
     */
    @Override
	public WxMenu selectWxMenuById(Integer id)
	{
	    return wxMenuMapper.selectWxMenuById(id);
	}
	
	/**
     * 查询微信菜单列表
     * 
     * @param wxMenu 微信菜单信息
     * @return 微信菜单集合
     */
	@Override
	public List<WxMenu> selectWxMenuList(WxMenu wxMenu)
	{
	    return wxMenuMapper.selectWxMenuList(wxMenu);
	}
	
    /**
     * 新增微信菜单
     * 
     * @param wxMenu 微信菜单信息
     * @return 结果
     */
	@Override
	public int insertWxMenu(WxMenu wxMenu)
	{
	    return wxMenuMapper.insertWxMenu(wxMenu);
	}
	
	/**
     * 修改微信菜单
     * 
     * @param wxMenu 微信菜单信息
     * @return 结果
     */
	@Override
	public int updateWxMenu(WxMenu wxMenu)
	{
	    return wxMenuMapper.updateWxMenu(wxMenu);
	}

	/**
     * 删除微信菜单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteWxMenuByIds(String ids)
	{
		return wxMenuMapper.deleteWxMenuByIds(Convert.toStrArray(ids));
	}
	
}
