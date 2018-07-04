package com.eleme.service.impl;

import com.eleme.dao.ElemeUrlDao;
import com.eleme.entity.ElemeUrl;
import com.eleme.service.ElemeUrlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ElemeUrlServiceImpl implements ElemeUrlService {
    @Resource
    private ElemeUrlDao elemeUrlDao;


    @Override
    public List<ElemeUrl> getAllEffectUrl() {
        return elemeUrlDao.getAllEffectUrl();
    }

    @Override
    public int insertElemeUrl(String url) {
        return elemeUrlDao.insertElemeUrl(url);
    }

    @Override
    public int updateElemeUrlById(String id ) {
        return elemeUrlDao.updateElemeUrlById(id);
    }
}
