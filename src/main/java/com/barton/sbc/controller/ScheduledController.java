package com.barton.sbc.controller;

import cn.hutool.core.util.RandomUtil;
import com.barton.sbc.SbcApplication;
import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.domain.entity.ScheduleConfig;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.scheduled.MyScheduled2;
import com.barton.sbc.service.ScheduledService;
import com.barton.sbc.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author barton
 * 定时 Controller
 */
@RestController
@RequestMapping("/config/scheduled/")
public class ScheduledController {
    @Autowired
    private ScheduledService scheduledService;

    private static AuthUser currentUser;

    private static String BASEPACKAGE = "com.barton.sbc.scheduled";


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        currentUser = CurrentUserUtil.getAuthUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }
    /**
     * 保存定时任务配置信息
     * @param scheduleConfig
     * @return 操作结果
     */
    @PostMapping(value = "/save")
    @Transactional
    public ServerResponse save(ScheduleConfig scheduleConfig) {
        if(scheduledService.selectByClass(scheduleConfig.getScheduleClass()) != null){
            scheduleConfig.setId(RandomUtil.randomUUID());
            scheduleConfig.setGmtCreate(new Date());
            scheduleConfig.setUserCreate(currentUser.getId());
            scheduleConfig.setGmtModified(new Date());
            scheduleConfig.setUserModified(currentUser.getId());
            scheduledService.insert(scheduleConfig);
        }else{
            scheduleConfig.setGmtModified(new Date());
            scheduleConfig.setUserModified(currentUser.getId());
            scheduledService.update(scheduleConfig);
        }
        //修改 定时任务
        scheduledService.scheduleChange(scheduleConfig);
        return ServerResponse.createBySuccessMessage("保存成功！");
    }

    /**
     * 删除定时任务
     * @param id
     * @return ServerResponse
     */
    @GetMapping(value = "/delete")
    @Transactional
    public ServerResponse delete(String id) {
        ScheduleConfig  config = scheduledService.selectByClass(id);
        config.setScheduleStatus(ScheduleConfig.STOP);
        //修改 定时任务
        scheduledService.scheduleChange(config);
        scheduledService.deleteById(id);
        return ServerResponse.createBySuccessMessage("删除成功！");
    }

    /**
     * 查询
     * @return List<ScheduleConfig>
     */
    @GetMapping(value = "/selectAll")
    public List<ScheduleConfig> selectAll() {
        return scheduledService.selectAll();
    }

    /**
     * 查询
     * @return List<ScheduleConfig>
     */
    @GetMapping(value = "/searchClass")
    public List<String> searchClass() {
        //先把包名转换为路径,首先得到项目的classpath
        String classpath = SbcApplication.class.getResource("/").getPath();
        //然后把我们的包名basPach转换为路径名
        String basePack = BASEPACKAGE.replace(".", File.separator);
        //然后把classpath和basePack合并
        String searchPath = classpath + basePack;
        List<String> classPaths = new ArrayList<String>();
        doPath(new File(searchPath),classPaths);
        List<String> classes = new ArrayList<String>();
        //这个时候我们已经得到了指定包下所有的类的绝对路径了。我们现在利用这些绝对路径和java的反射机制得到他们的类对象
        for (String s : classPaths) {
            s = s.replaceFirst(classpath,"").replace(File.separator,".").replace(".class","");
            classes.add(s);
            //s = s.;
            //s = s.replace(".class","");
            /*Class cls = null;
            try {
                cls = Class.forName(s);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*/
        }
        return classes;
    }

    public static void main(String [] args){

    }

    /**
     * 收集类
     *
     * @param file
     * @param classPaths
     */
    private static void doPath(File file,List<String> classPaths) {
        //文件夹
        if (file.isDirectory()) {
            //文件夹我们就递归
            File[] files = file.listFiles();
            for (File f1 : files) {
                doPath(f1,classPaths);
            }
        } else {//标准文件
            //标准文件我们就判断是否是class文件
            if (file.getName().endsWith(".class")) {
                //如果是class文件我们就放入我们的集合中。
                classPaths.add(file.getPath());
            }
        }
    }
}
