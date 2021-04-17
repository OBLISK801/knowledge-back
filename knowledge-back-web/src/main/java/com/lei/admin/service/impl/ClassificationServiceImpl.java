package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.converter.ClassificationConverter;
import com.lei.admin.entity.Classification;
import com.lei.admin.mapper.ClassificationMapper;
import com.lei.admin.service.IClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.admin.utils.ClassificationBuilder;
import com.lei.admin.vo.ClassVO;
import com.lei.admin.vo.ClassificationNodeVO;
import com.lei.admin.vo.GraphDataVO;
import com.lei.admin.vo.GraphLinksVO;
import com.lei.error.SystemCodeEnum;
import com.lei.error.SystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-25
 */
@Service
public class ClassificationServiceImpl extends ServiceImpl<ClassificationMapper, Classification> implements IClassificationService {

    @Autowired
    private ClassificationMapper classificationMapper;

    @Override
    public List<ClassificationNodeVO> tree() {
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        List<Classification> results = classificationMapper.selectList(wrapper);
        List<ClassificationNodeVO> classificationNodeVOS = ClassificationConverter.converterToALLClassificationNodeVO(results);
        return ClassificationBuilder.build(classificationNodeVOS);
    }

    @Override
    public Classification add(ClassificationNodeVO classificationNodeVO) {
        Classification classification = new Classification();
        BeanUtils.copyProperties(classificationNodeVO,classification);
        if (classificationNodeVO.getParentId() == 0) {
            classification.setLevel(1);
        } else {
            Classification result = classificationMapper.selectById(classificationNodeVO.getParentId());
            classification.setLevel(result.getLevel()+1);
        }
        classificationMapper.insert(classification);
        return classification;
    }

    @Override
    public void delete(Integer id) throws SystemException {
        Classification classification = classificationMapper.selectById(id);
        if (classification == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要删除的分类不存在");
        }
        Integer level = classification.getLevel();
        if (level == 3) {
            classificationMapper.deleteById(id);
        } else if (level == 2) {
            QueryWrapper<Classification> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id",id);
            classificationMapper.delete(wrapper);
            classificationMapper.deleteById(id);
        }
    }

    @Override
    public ClassificationNodeVO edit(Integer id) throws SystemException {
        Classification classification = classificationMapper.selectById(id);
        if (classification == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该编辑的分类不存在");
        }
        return ClassificationConverter.converterToClassificationNodeVO(classification);
    }

    @Override
    public void update(Integer id, ClassificationNodeVO classificationNodeVO) throws SystemException {
        Classification dbClassification = classificationMapper.selectById(id);
        if (dbClassification == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要更新的分类不存在");
        }
        Classification classification = new Classification();
        BeanUtils.copyProperties(classificationNodeVO,classification);
        classification.setId(id);
        UpdateWrapper<Classification> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        classificationMapper.update(classification,wrapper);
    }

    @Override
    public List<GraphDataVO> getGraphData() {
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        List<Classification> list = classificationMapper.selectList(wrapper);
        List<GraphDataVO> graphDataVOS = new ArrayList<>();
        for (Classification classification : list) {
            GraphDataVO graphDataVO = new GraphDataVO();
            graphDataVO.setName(classification.getClassificationName());
            graphDataVO.setLevel(classification.getLevel());
            graphDataVOS.add(graphDataVO);
        }
        return graphDataVOS;
    }

    @Override
    public List<GraphLinksVO> getGraphLinks() {
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        List<Classification> list = classificationMapper.selectList(wrapper);
        List<GraphLinksVO> graphLinksVOS = new ArrayList<>();
        for (Classification classification : list) {
            if (classification.getLevel() != 1) {
                GraphLinksVO graphLinksVO = new GraphLinksVO();
                graphLinksVO.setTarget(classification.getClassificationName());
                graphLinksVO.setSource( list.stream().filter(c ->
                        c.getId().equals(classification.getParentId())).collect(Collectors.toList()).get(0).getClassificationName() );
                graphLinksVOS.add(graphLinksVO);
            }
        }
        return graphLinksVOS;
    }

    @Override
    public List<Classification> listAll() {
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        return classificationMapper.selectList(wrapper);
    }

    @Override
    public List<Classification> listChildren() {
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        wrapper.eq("level",3);
        return classificationMapper.selectList(wrapper);
    }

    @Override
    public ClassVO listByNameLevel(String name, Integer level) {
        QueryWrapper<Classification> wrapper = new QueryWrapper<>();
        wrapper.eq("classification_name",name);
        Classification classification = classificationMapper.selectOne(wrapper);
        ClassVO classVO = new ClassVO();
        classVO.setClassificationName(classification.getClassificationName());
        classVO.setClassificationIntroduction(classification.getClassificationIntroduction());
        if (level == 1) {
            Integer num = classificationMapper.getNumOne();
            classVO.setNum(num);
        } else if (level == 2) {
            Integer num = classificationMapper.getNumTwo(classification.getId());
            classVO.setNum(num);
        } else {
            Integer num = classificationMapper.getNumThree(classification.getId());
            classVO.setNum(num);
        }
        return classVO;
    }
}
