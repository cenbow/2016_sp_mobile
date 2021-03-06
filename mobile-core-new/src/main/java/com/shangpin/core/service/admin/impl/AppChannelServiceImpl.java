package com.shangpin.core.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.shangpin.core.dao.admin.AppChannelDao;
import com.shangpin.core.entity.admin.AppChannel;
import com.shangpin.core.service.admin.AppChannelService;
import com.shangpin.core.util.dwz.Page;
import com.shangpin.core.util.dwz.PageUtils;

@Service
@Transactional
public class AppChannelServiceImpl implements AppChannelService {

    @Autowired
    private AppChannelDao appChannelDao;

    @Override
    public void save(AppChannel appChannel) {
        this.appChannelDao.save(appChannel);
    }

    @Override
    public void update(AppChannel appChannel) {
        this.appChannelDao.save(appChannel);
    }

    @Override
    public void delete(Long id) {
        this.appChannelDao.delete(id);
    }

    @Override
    public AppChannel findById(Long id) {
        return this.appChannelDao.findOne(id);
    }

    @Override
    public Long findMaxNum() {
        Long maxNum = new Long(0);
        String maxStr = this.appChannelDao.findMaxNum();
        if (!StringUtils.isEmpty(maxStr)) {
            maxNum = Long.valueOf(maxStr);
        }
        return maxNum;
    }

    @Override
    public List<AppChannel> find(Page page, String name) {
        org.springframework.data.domain.Page<AppChannel> springDataPage = appChannelDao.findByNameContaining(name, PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }
    
    @Override
    public List<AppChannel> findByExample(Specification<AppChannel> specification, Page page) {
        org.springframework.data.domain.Page<AppChannel> springDataPage = appChannelDao.findAll(specification, PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<AppChannel> findAll(Page page) {
        org.springframework.data.domain.Page<AppChannel> springDataPage = appChannelDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

}
