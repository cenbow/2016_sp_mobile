/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, shangpin.com
 * Filename:		com.shangpin.manager.service.impl.UserServiceImpl.java
 * Class:			UserServiceImpl
 * Date:			2012-8-7
 * Author:			<a href="mailto:sundful@gmail.com">sundful</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.shangpin.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shangpin.core.dao.UserDAO;
import com.shangpin.core.entity.main.User;
import com.shangpin.core.exception.ExistedException;
import com.shangpin.core.exception.ServiceException;
import com.shangpin.core.service.UserService;
import com.shangpin.core.shiro.ShiroDbRealm;
import com.shangpin.core.shiro.ShiroDbRealm.HashPassword;
import com.shangpin.core.util.dwz.Page;
import com.shangpin.core.util.dwz.PageUtils;

/** 
 * 	
 * @author 	<a href="mailto:sundful@gmail.com">sundful</a>
 * Version  1.1.0
 * @since   2012-8-7 下午3:14:29 
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDAO userDAO;
	
	@Autowired
	private ShiroDbRealm shiroRealm;
	
	/**  
	 * 构造函数
	 * @param jpaRepository  
	 */ 
	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Override
	public User get(Long id) {
		return userDAO.findOne(id);
	}

	@Override
	public List<User> findAll(Page page) {
		org.springframework.data.domain.Page<User> springDataPage = userDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**
	 * 
	 * @param user
	 * @throws ExistedException  
	 * @see com.shangpin.core.service.UserService#save(com.shangpin.core.entity.main.User)
	 */
	public void save(User user) throws ExistedException {		
		if (userDAO.findByUsername(user.getUsername()) != null) {
			throw new ExistedException("用户添加失败，登录名：" + user.getUsername() + "已存在。");
		}
		
		if (userDAO.findByRealname(user.getRealname()) != null) {
			throw new ExistedException("用户添加失败，真实名：" + user.getRealname() + "已存在。");
		}
		
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		
		userDAO.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/**   
	 * @param user  
	 * @see com.shangpin.core.service.UserService#update(com.shangpin.core.entity.main.User)  
	 */
	public void update(User user) {
		userDAO.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}
	
	/**
	 * 
	 * @param user
	 * @param newPwd
	 * @throws ServiceException  
	 * @see com.shangpin.core.service.UserService#updatePwd(com.shangpin.core.entity.main.User, java.lang.String)
	 */
	@Override
	public void updatePwd(User user, String newPwd) throws ServiceException {
		//if (isSupervisor(user.getId())) {
		//	logger.warn("操作员{},尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
		//	throw new ServiceException("不能修改超级管理员用户");
		//}
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		boolean isMatch = ShiroDbRealm.validatePassword(user.getPlainPassword(), user.getPassword(), user.getSalt());
		if (isMatch) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
			
			userDAO.save(user);
			shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
			
			return; 
		}
		
		throw new ServiceException("用户密码错误！");
	}

	/**   
	 * @param id  
	 * @see com.shangpin.core.service.UserService#delete(java.lang.Long)  
	 */
	public void delete(Long id) throws ServiceException {
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除超级管理员用户", SecurityUtils.getSubject()
					.getPrincipal() + "。");
			throw new ServiceException("不能删除超级管理员用户。");
		}
		User user = userDAO.findOne(id);
		userDAO.delete(user.getId());
		
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/**   
	 * @param username
	 * @return  
	 * @see com.shangpin.core.service.UserService#get(java.lang.String)  
	 */
	public User get(String username) {
		return userDAO.findByUsername(username);
	}

	/**   
	 * @param page
	 * @param name
	 * @return  
	 * @see com.shangpin.core.service.UserService#find(com.shangpin.core.util.dwz.Page, java.lang.String)  
	 */
	public List<User> find(Page page, String name) {
		org.springframework.data.domain.Page<User> springDataPage = 
				userDAO.findByUsernameContaining(name, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.shangpin.manager.service.UserService#resetPwd(com.shangpin.manager.entity.main.User, java.lang.String)
	 */
	@Override
	public void resetPwd(User user, String newPwd) {
		if (newPwd == null) {
			newPwd = "123456";
		}
		
		HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
		user.setSalt(hashPassword.salt);
		user.setPassword(hashPassword.password);
		
		userDAO.save(user);
	}
	
	/* (non-Javadoc)
	 * @see com.shangpin.manager.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.shangpin.manager.util.dwz.Page)
	 */
	@Override
	public List<User> findByExample(Specification<User> specification, Page page) {
		org.springframework.data.domain.Page<User> springDataPage = userDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}
}
