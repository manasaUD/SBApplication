package com.example.demo.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.BankRepo;
import com.example.demo.model.BankModel;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;



@RestController
@RequestMapping("/userDetails")
public class UserDetailsController 
{	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsController.class);

	@Autowired
	TransactionService tservice;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BankRepo bankRepo;
	
	@GetMapping(value = "/bankUsers", produces= "application/json")
	public ResponseEntity<List<BankModel>> getPncUsers() throws Exception
	{
		logger.info("Entering into the method getPncUsers");
		ResponseEntity response = new ResponseEntity("{\"Users Retrieval Status\":\"Unable to display the users.\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		List<BankModel> list = null;
		list = userService.getUserDetails();
		
		if(!(list.isEmpty()))
		{
			response = new ResponseEntity(list, new HttpHeaders(), HttpStatus.OK);
		}
		else
		{
			response = new ResponseEntity("{\"Users Retrieval Status\":\"Error while displaying users from the database.\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("Exiting the method getPncUsers");
		return response;
	}
	
	@GetMapping(value = "/getUser", produces= "application/json")
	public ResponseEntity<Optional<BankModel>> getPncUser(HttpServletRequest request, @RequestParam(value = "acc_no", required = true) Long acc_no) throws Exception
	{
		logger.info("Entering into the method getPncUser");
		ResponseEntity response = new ResponseEntity("{\"User Retrieval Status\":\"Invalid input details.\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		if(acc_no != null)
		{
			if(userService.getUser(acc_no) != null)
			{
				response = new ResponseEntity( userService.getUser(acc_no), new HttpHeaders(), HttpStatus.OK);
			}
			else
			{
				response = new ResponseEntity("{\"User Retrieval Status\":\"Unable to retrieve user.\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Exiting the method getPncUser");
		return response;
	}
	
	@PostMapping(value = "/addBankUser", produces= "application/json")
	public ResponseEntity<BankModel> addPncUser(HttpServletRequest request, @RequestBody BankModel user) throws Exception
	{
		logger.info("Entering into the method addPncUser");
		ResponseEntity response = new ResponseEntity("{\"Adding User Status\":\"Invalid input details.\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		if((user.getAcc_no() != null) && (user.getAcc_balance() != null) && (user.getFname() != null)) 
		{
			if(!(bankRepo.existsById(user.getAcc_no())))
			{
				if(userService.addUser(user) != null) 
				{
					response = new ResponseEntity(user, new HttpHeaders(), HttpStatus.CREATED);
				}
				else 
				{
					response = new ResponseEntity("{\"Adding User Status\":\"Error while adding user to the database.\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else
			{
				response = new ResponseEntity("{\"Adding User Status\":\"User already exists.\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Exiting out of the method addPncUser");
		return response;
	}
	
	
	@PutMapping(value = "/updateBankUser", produces= "application/json")
	public ResponseEntity<BankModel> updatePncUser(HttpServletRequest request, @RequestBody BankModel user) throws Exception
	{
		logger.info("Entering into the method updatePncUser");
		ResponseEntity response = new ResponseEntity("{\"Updating User Status\":\"Invalid input details.\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		if((user.getAcc_balance() != null) || (user.getFname() != null)) {
			
			if(userService.addUser(user) != null) 
			{
				response = new ResponseEntity(user, new HttpHeaders(), HttpStatus.OK);
			}
			else 
			{
				response = new ResponseEntity("{\"Updating User Status\":\"Error while update user in the database.\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		logger.info("Exiting the method updatePncUser");
		return response;
	}
	

	@DeleteMapping(value = "/deleteUser", produces= "application/json")
	public ResponseEntity<String> deletePncUser(HttpServletRequest request, @RequestParam(value = "acc_no", required = true) Long acc_no) throws Exception
	{
		logger.info("Entering into the method deletePncUser");
		boolean userDeleteStatus = false;
		ResponseEntity response = new ResponseEntity("{\"User Removal Status\":\"Invalid input details.\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		
		if(acc_no != null)
		{
			userDeleteStatus = userService.deleteUser(acc_no);
			if(userDeleteStatus == true)
			{
				response = new ResponseEntity("{\"User Removal Status\":\"User removed successfully.\"}", new HttpHeaders(), HttpStatus.OK);
			}
			else
			{
				response = new ResponseEntity("{\"User Removal Status\":\"Error while deleting the user from the database.\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Exiting the method deletePncUser");
		return response;
	}

}