package com.ruoyi.spider.baiduyun.mapper;

import com.ruoyi.spider.baiduyun.domain.SpiFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 百度云盘 数据层
 * 
 * @author ruoyi
 * @date 2019-06-22
 */
public interface SpiFileMapper 
{
	/**
     * 查询百度云盘信息
     * 
     * @param id 百度云盘ID
     * @return 百度云盘信息
     */
	public SpiFile selectSpiFileById(Integer id);
	
	/**
     * 查询百度云盘列表
     * 
     * @param spiFile 百度云盘信息
     * @return 百度云盘集合
     */
	public List<SpiFile> selectSpiFileList(SpiFile spiFile);
	
	/**
     * 新增百度云盘
     * 
     * @param spiFile 百度云盘信息
     * @return 结果
     */
	public int insertSpiFile(SpiFile spiFile);

	/**
	 * 新增多条百度云盘
	 *
	 * @param spiFiles 百度云盘信息
	 * @return 结果
	 */
	int insertSpiFileList(@Param("spiFiles") List<SpiFile> spiFiles);
	/**
     * 修改百度云盘
     * 
     * @param spiFile 百度云盘信息
     * @return 结果
     */
	public int updateSpiFile(SpiFile spiFile);
	
	/**
     * 删除百度云盘
     * 
     * @param id 百度云盘ID
     * @return 结果
     */
	public int deleteSpiFileById(Integer id);
	
	/**
     * 批量删除百度云盘
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSpiFileByIds(String[] ids);


}