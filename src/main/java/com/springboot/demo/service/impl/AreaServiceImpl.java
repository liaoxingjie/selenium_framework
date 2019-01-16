package com.springboot.demo.service.impl;

import com.springboot.demo.dao.AreaDao;
import com.springboot.demo.entity.Area;
import com.springboot.demo.service.AreaService;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }

    @Override
    public Area getAreaById(int areaId) {
        return areaDao.queryAreaById(areaId);
    }

    @Transactional
    @Override
    public boolean addArea(Area area) {
        if(area.getAreaName() != null && !"".equals(area.getAreaName())){
            area.setCreateTime(new Date());
            area.setUpdateTime(new Date());
            try {
                int effectedNum = areaDao.insertArea(area);
                if (effectedNum > 0){
                    return true;
                }else {
                    throw new RuntimeException("插入信息失败!");
                }
            } catch (ExecutorException e){
                throw new RuntimeException("插入信息失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("不能为空！");
        }
    }

    @Override
    public boolean modifyArea(Area area) {
        if(area.getAreaId() != null && area.getAreaId() > 0){
            area.setUpdateTime(new Date());
            try {
                int effectedNum = areaDao.updateArea(area);
                if (effectedNum > 0){
                    return true;
                }else {
                    throw new RuntimeException("更新信息失败!");
                }
            } catch (ExecutorException e){
                throw new RuntimeException("更新信息失败：" + e.toString());
            }
        } else {
            throw new RuntimeException("不能为空！");
        }
    }

    @Override
    public boolean deleteArea(int areaId) {
        if(areaId > 0){
            try {
                int effectedNum = areaDao.deleteArea(areaId);
                if (effectedNum > 0){
                    return true;
                }else {
                    throw new RuntimeException("删除信息失败!");
                }
            } catch (ExecutorException e){
                throw new RuntimeException("删除信息失败：" + e.toString());
            }
        } else {
            throw new RuntimeException("ID不能为空！");
        }
    }
}
