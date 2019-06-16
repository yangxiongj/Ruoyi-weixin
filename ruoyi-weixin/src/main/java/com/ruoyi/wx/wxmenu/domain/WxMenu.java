package com.ruoyi.wx.wxmenu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信菜单表 wx_menu
 * 
 * @author ruoyi
 * @date 2019-06-16
 */
public class WxMenu extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 菜单ID */
	private Integer id;
	/** 父菜单ID */
	private Integer pId;
	/** 菜单类型 */
	private String type;
	/** 子菜单标题 */
	private String name;
	/** 网页链接,用户点击菜单可打开链接 */
	private String url;
	/** 菜单key值,用于消息推送接口 */
	private String menuKey;
	/** 返回的类型 */
	private Integer responseType;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setPId(Integer pId) 
	{
		this.pId = pId;
	}

	public Integer getPId() 
	{
		return pId;
	}
	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String getUrl() 
	{
		return url;
	}
	public void setMenuKey(String menuKey) 
	{
		this.menuKey = menuKey;
	}

	public String getMenuKey() 
	{
		return menuKey;
	}
	public void setResponseType(Integer responseType) 
	{
		this.responseType = responseType;
	}

	public Integer getResponseType() 
	{
		return responseType;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pId", getPId())
            .append("type", getType())
            .append("name", getName())
            .append("url", getUrl())
            .append("menuKey", getMenuKey())
            .append("responseType", getResponseType())
            .toString();
    }
}
