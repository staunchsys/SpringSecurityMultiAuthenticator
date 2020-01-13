package com.staunchsys.multiauthenticator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.staunchsys.multiauthenticator.dao.UserDao;
import com.staunchsys.multiauthenticator.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	public void saveUser(User user) {
		userDao.save(user);
	}

}
