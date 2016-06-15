package com.shangpin.biz.service;

import java.util.List;

import com.shangpin.biz.bo.Entrance;


public interface ASPBizEntranceService {
   
    /**
     *首页入口运营位
     *
     * @param   type 1为m站，2为app
     * @param   pageIndex   页码 
     * @param   pageSize    每页数量   
     * @return
     * @author wangfeng
     */
    public List<Entrance> queryEntranceList(String type,String pageIndex,String pageSize);

    
	

}
