package com.example.demo.service;

import java.util.List;
//import java.util.ListIterator;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BankRepo;
import com.example.demo.model.BankModel;

@Service
public class UserService 
{	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	BankRepo bankRepo;
	
	public BankModel addUser(BankModel user) 
	{
		try 
		{
			bankRepo.save(user);
		}
		catch(Exception e) 
		{
			logger.error("Exception while adding a user: ", e);
			user = null;
		}
		return user;
	}

	public List<BankModel> getUserDetails() 
	{
		List<BankModel> list = null;
		try
		{
			list = bankRepo.findAll();
		}
		catch(Exception e)
		{
			logger.error("Exception while getting the users list: ", e);
		}
		return list;
	}
	
	public Optional<BankModel> getUser(Long acc_no) 
	{
		Optional<BankModel> user = null;
		try 
		{
			user = bankRepo.findById(acc_no);
		}
		catch(Exception e) 
		{
			logger.error("Exception while adding a user: ", e);
			user = null;
		}
		return user;
	}
	
	public boolean deleteUser(Long acc_no) 
	{
		boolean userDeleted = false;
		try 
		{
			if(true)
			{
				BankModel user = bankRepo.getOne(acc_no);
				bankRepo.delete(user);
				userDeleted = true;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception while deleting the user: ", e);
		}
		return userDeleted;
	}
}