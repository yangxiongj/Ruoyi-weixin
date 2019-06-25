package com.ruoyi.spider.factory;

import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.spider.baiduyun.domain.SpiFile;
import com.ruoyi.spider.baiduyun.service.ISpiFileService;

import java.util.List;
import java.util.TimerTask;

public class AsyncSpiderFactory extends AsyncFactory {
    /**异步存储获取到的Url**/
    public static TimerTask saveFiles(List<SpiFile> files, ISpiFileService spiFileService) {
        return new TimerTask() {
            @Override
            public void run() {
                try{
                    spiFileService.insertSpiFileList(files);
                }catch (Exception e){
                    //失败重试
                    reboot(files,spiFileService);
                    e.printStackTrace();
                }
            }
        };
    }
    public static void reboot(List<SpiFile> files, ISpiFileService spiFileService){
        int stop = 0;
        double size;
        for (int i=0;files.size()!=stop;i+=size) {
             size = Math.ceil((float)files.size()/2f);
             stop =  (int)(i + size);
            if(stop>files.size()){
                stop = files.size();
            }
            if(i>stop){
                i=stop;
            }
            List<SpiFile> saveFiles= files.subList(i,stop);
            try{
                if(saveFiles.size()>0){
                spiFileService.insertSpiFileList(saveFiles);
                }
            }catch (Exception e2){
                e2.printStackTrace();
                if(saveFiles.size()!=1){
                    reboot(saveFiles,spiFileService);
                }
            }
        }
    }

}
