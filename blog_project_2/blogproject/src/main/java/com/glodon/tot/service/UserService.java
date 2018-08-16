package com.glodon.tot.service;

import com.glodon.tot.mappers.UserMapper;
import com.glodon.tot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Crawn 用户相关服务
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户查询（用户名）
     * @param name 用户名
     * @return
     */
    public User check(String name){
        return userMapper.selectByName(name);
    }

    /**
     * 添加用户
     * @param user 用户实例
     * @return
     */
    public boolean insert(User user){
        if (userMapper.insert(user)==1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除用户
     * @param userid 用户id
     * @return 删除行数
     */
    public int delete(long  userid){
        return userMapper.deleteByPrimaryKey(userid);
    }

    /**
     * 更新用户
     * @param user 用户实例
     * @return
     */
    public int update(User user){
        return userMapper.updateByPrimaryKey(user);
    }

    /**
     * 使用关键字获取用户信息
     * @param userId 用户id
     * @return User 用户  不存在为空
     */
    public User select(Long userId){
        return userMapper.selectByPrimaryKey(userId);
    }
}
