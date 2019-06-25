package com.ruoyi.spider.baiduyun.service.impl;

import java.util.List;

import com.ruoyi.spider.baiduyun.domain.SpiFile;
import com.ruoyi.spider.baiduyun.mapper.SpiFileMapper;
import com.ruoyi.spider.baiduyun.service.ISpiFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;

/**
 * 百度云盘 服务层实现
 * 
 * @author ruoyi
 * @date 2019-06-22
 */
@Service
public class SpiFileServiceImpl implements ISpiFileService
{
	@Autowired
	private SpiFileMapper spiFileMapper;

	/**
     * 查询百度云盘信息
     * 
     * @param id 百度云盘ID
     * @return 百度云盘信息
     */
    @Override
	public SpiFile selectSpiFileById(Integer id)
	{
	    return spiFileMapper.selectSpiFileById(id);
	}
	
	/**
     * 查询百度云盘列表
     * 
     * @param spiFile 百度云盘信息
     * @return 百度云盘集合
     */
	@Override
	public List<SpiFile> selectSpiFileList(SpiFile spiFile)
	{
	    return spiFileMapper.selectSpiFileList(spiFile);
	}
	
    /**
     * 新增百度云盘
     * 
     * @param spiFile 百度云盘信息
     * @return 结果
     */
	@Override
	public int insertSpiFile(SpiFile spiFile)
	{
	    return spiFileMapper.insertSpiFile(spiFile);
	}
	/**
	 * 新增百度云盘
	 *
	 * @param spiFile 百度云盘信息
	 * @return 结果
	 */
	@Override
	public int insertSpiFileList(List<SpiFile> spiFile)
	{
		return spiFileMapper.insertSpiFileList(spiFile);
	}

	/**
     * 修改百度云盘
     * 
     * @param spiFile 百度云盘信息
     * @return 结果
     */
	@Override
	public int updateSpiFile(SpiFile spiFile)
	{
	    return spiFileMapper.updateSpiFile(spiFile);
	}

	/**
     * 删除百度云盘对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSpiFileByIds(String ids)
	{
		return spiFileMapper.deleteSpiFileByIds(Convert.toStrArray(ids));
	}
	
}
