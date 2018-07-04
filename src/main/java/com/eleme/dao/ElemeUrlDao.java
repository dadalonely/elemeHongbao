package com.eleme.dao;

import com.eleme.entity.ElemeUrl;

import java.util.List;

public interface ElemeUrlDao {

    List<ElemeUrl> getAllEffectUrl();


    int insertElemeUrl(String url);

    int updateElemeUrlById (String id);
}
