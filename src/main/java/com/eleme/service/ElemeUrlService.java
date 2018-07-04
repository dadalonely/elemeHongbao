package com.eleme.service;

import com.eleme.entity.ElemeUrl;

import java.util.List;

public interface ElemeUrlService {

    List<ElemeUrl> getAllEffectUrl();


    int insertElemeUrl(String url);

    int updateElemeUrlById (String id);
}
