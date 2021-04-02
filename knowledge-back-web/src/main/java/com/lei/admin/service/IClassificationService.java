package com.lei.admin.service;

import com.lei.admin.entity.Classification;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.admin.vo.ClassificationNodeVO;
import com.lei.admin.vo.GraphDataVO;
import com.lei.admin.vo.GraphLinksVO;
import com.lei.error.SystemException;
import com.lei.system.vo.MenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-25
 */
public interface IClassificationService extends IService<Classification> {

    List<ClassificationNodeVO> tree();

    Classification add(ClassificationNodeVO classificationNodeVO);

    void delete(Integer id) throws SystemException;

    ClassificationNodeVO edit(Integer id) throws SystemException;

    void update(Integer id, ClassificationNodeVO classificationNodeVO) throws SystemException;

    List<GraphDataVO> getGraphData();

    List<GraphLinksVO> getGraphLinks();

    List<Classification> listAll();
}
