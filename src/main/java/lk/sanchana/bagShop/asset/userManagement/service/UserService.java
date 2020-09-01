package lk.sanchana.bagShop.asset.userManagement.service;

<<<<<<< HEAD
=======

import lk.sanchana.bagShop.asset.employee.entity.Employee;
import lk.sanchana.bagShop.asset.userManagement.dao.UserDao;
import lk.sanchana.bagShop.asset.userManagement.entity.User;
import lk.sanchana.bagShop.util.interfaces.AbstractService;
>>>>>>> 56d8cb4e848d36271016629645d45166b942a42b
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig( cacheNames = {"user"} ) // tells Spring where to store cache for this class
public class UserService implements AbstractService< User, Integer> {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Cacheable
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Cacheable
    @Transactional
    public User findById(Integer id) {
        return userDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "user", allEntries = true )},
            put = {@CachePut( value = "user", key = "#user.id" )} )
    @Transactional
    public User persist(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        if ( user.getPassword() != null ) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDao.getOne(user.getId()).getPassword());
        }
        return userDao.save(user);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Integer id) {
        //according to this project can not be deleted user
        userDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<User> search(User user) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< User > userExample = Example.of(user, matcher);
        return userDao.findAll(userExample);
    }

    @Cacheable
    public Integer findByUserIdByUserName(String userName) {
        return userDao.findUserIdByUserName(userName);
    }

    @Cacheable
    @Transactional( readOnly = true )
    public User findByUserName(String name) {
        return userDao.findByUsername(name);
    }

    @Cacheable
    public User findUserByEmployee(Employee employee) {
        return userDao.findByEmployee(employee);
    }

    @Cacheable
    public boolean findByEmployee(Employee employee) {
        return userDao.findByEmployee(employee) == null;
    }


}