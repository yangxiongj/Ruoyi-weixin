package com.ruoyi.spider.baiduyun.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 百度云盘表 spi_file
 * 
 * @author ruoyi
 * @date 2019-06-22
 */
public class SpiFile extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;
	/** 目录 */
	private String menu;
	/** 分享用户 */
	private String userName;
	/** 文件格式 */
	private String fileType;
	/** 文件大小 */
	private String fileSize;
	/** 浏览次数 */
	private String lookCount;
	/** 下载次数 */
	private String downCounr;
	/** 分享时间 */
	private LocalDateTime createData;
	/** 地址 */
	private String url;
    /**信息**/
    private String info;
	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setMenu(String menu) 
	{
		this.menu = menu;
	}

	public String getMenu() 
	{
		return menu;
	}
	public void setFileType(String fileType) 
	{
		this.fileType = fileType;
	}

	public String getFileType() 
	{
		return fileType;
	}
	public void setFileSize(String fileSize) 
	{
		this.fileSize = fileSize;
	}

	public String getFileSize() 
	{
		return fileSize;
	}
	public void setLookCount(String lookCount) 
	{
		this.lookCount = lookCount;
	}

	public String getLookCount() 
	{
		return lookCount;
	}
	public void setDownCounr(String downCounr) 
	{
		this.downCounr = downCounr;
	}

	public String getDownCounr() 
	{
		return downCounr;
	}
	public void setCreateData(LocalDateTime createData)
	{
		this.createData = createData;
	}

	public LocalDateTime getCreateData()
	{
		return createData;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String getUrl() 
	{
		return url;
	}

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("menu", getMenu())
            .append("userId", getUserName())
            .append("fileType", getFileType())
            .append("fileSize", getFileSize())
            .append("lookCount", getLookCount())
            .append("downCounr", getDownCounr())
            .append("createData", getCreateData())
            .append("url", getUrl())
            .toString();
    }
}
