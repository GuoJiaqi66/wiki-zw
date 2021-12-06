package com.zw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.domain.User;
import com.zw.domain.UserExample;
import com.zw.exception.BusinessException;
import com.zw.exception.BusinessExceptionCode;
import com.zw.mapper.UserMapper;
import com.zw.req.UserQueryReq;
import com.zw.req.UserQueryResp;
import com.zw.req.UserSaveReq;
import com.zw.resp.PageResp;
import com.zw.util.CopyUtil;
import com.zw.util.snowFlakeldGenerator.SnowFlakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    UserMapper userMapper;

    @Resource
    SnowFlakeIdGenerator snowFlakeIdGenerator;

    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);

        LOG.info("总行数：", pageInfo.getTotal());
        LOG.info("总页数：", pageInfo.getPages());


        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            User userDB = selectByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(userDB)) {

                user.setId(snowFlakeIdGenerator.nextId());
                userMapper.insert(user);
            } else {
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {

            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /*
     * 查询数据库是否存在相同登录名(主键是否唯一)
     * */
    public User selectByLoginName(String LoginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);

        List<User> userList = userMapper.selectByExample(userExample);

        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }
}
