package com.ruoyi.spider.baiduyun.controller;

import java.util.List;

import com.ruoyi.spider.baiduyun.domain.SpiFile;
import com.ruoyi.spider.baiduyun.service.ISpiFileService;
import com.ruoyi.spider.baiduyun.service.SpiderService;
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
 * 百度云盘 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-06-22
 */
@Controller
@RequestMapping("/baiduyun/spifile")
public class SpiFileController extends BaseController
{
    private String prefix = "baiduyun/spifile";
	
	@Autowired
	private ISpiFileService spiFileService;
	@Autowired
	private SpiderService spiderService;
	@RequiresPermissions("baiduyun:spifile:view")
	@GetMapping()
	public String spiFile()
	{
	    return prefix + "/spifile";
	}
	
	/**
	 * 查询百度云盘列表
	 */
	@RequiresPermissions("baiduyun:spifile:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SpiFile spiFile)
	{
		startPage();
        List<SpiFile> list = spiFileService.selectSpiFileList(spiFile);
		List<SpiFile> spiFiles = spiderService.getFiles(10);
		TableDataInfo dataInfo = getDataTable(list);
		List listRow = dataInfo.getRows();
		listRow.addAll(list);
		listRow.addAll(spiFiles);
		return dataInfo;
	}
	
	
	/**
	 * 导出百度云盘列表
	 */
	@RequiresPermissions("baiduyun:spifile:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SpiFile spiFile)
    {
    	List<SpiFile> list = spiFileService.selectSpiFileList(spiFile);
        ExcelUtil<SpiFile> util = new ExcelUtil<SpiFile>(SpiFile.class);
        return util.exportExcel(list, "spifile");
    }
	
	/**
	 * 新增百度云盘
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存百度云盘
	 */
	@RequiresPermissions("baiduyun:spifile:add")
	@Log(title = "百度云盘", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SpiFile spiFile)
	{		
		return toAjax(spiFileService.insertSpiFile(spiFile));
	}

	/**
	 * 修改百度云盘
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		SpiFile spiFile = spiFileService.selectSpiFileById(id);
		mmap.put("spifile", spiFile);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存百度云盘
	 */
	@RequiresPermissions("baiduyun:spifile:edit")
	@Log(title = "百度云盘", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SpiFile spiFile)
	{		
		return toAjax(spiFileService.updateSpiFile(spiFile));
	}
	
	/**
	 * 删除百度云盘
	 */
	@RequiresPermissions("baiduyun:spifile:remove")
	@Log(title = "百度云盘", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(spiFileService.deleteSpiFileByIds(ids));
	}
	
}
