package com.zw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.domain.Ebook;
import com.zw.domain.EbookExample;
import com.zw.mapper.EbookMapper;
import com.zw.req.EbookQueryReq;
import com.zw.req.EbookSaveReq;
import com.zw.resp.EbookQueryResp;
import com.zw.resp.PageResp;
import com.zw.util.CopyUtil;
import com.zw.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {

        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        /*if (!ObjectUtils.isEmpty(req.getCategory1Id())) {
            criteria.andCategory1IdEqualTo(req.getCategory1Id());
        }*/
        if (!ObjectUtils.isEmpty(req.getCategory2Id())) {
            // 根据category2id查找
            criteria.andCategory2IdEqualTo(req.getCategory2Id());
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // 列表复制
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;

    }

    public void save(EbookSaveReq ebookSaveReq) {
        Ebook ebook = CopyUtil.copy(ebookSaveReq, Ebook.class);
        if (ObjectUtils.isEmpty(ebook.getId())) {
            ebook.setId(snowFlake.nextId());
            ebook.setViewCount(0);
            ebook.setVoteCount(0);
            ebook.setDocCount(0);
            ebookMapper.insert(ebook);
        } else {
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
