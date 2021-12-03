package com.zw.controller;

import com.zw.req.EbookQueryReq;
import com.zw.req.EbookSaveReq;
import com.zw.resp.CommonResp;
import com.zw.resp.EbookQueryResp;
import com.zw.resp.PageResp;
import com.zw.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    // 分页查询 需要添加pom
    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> commonResp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        commonResp.setContent(list);
        return commonResp;
    }


    // 保存
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq ebookSaveReq) {
        CommonResp commonResp = new CommonResp();
        ebookService.save(ebookSaveReq);
        return commonResp;
    }


    // 删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp();
        ebookService.delete(id);
        return commonResp;
    }


}
