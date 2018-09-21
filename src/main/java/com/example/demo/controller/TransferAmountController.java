package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.BankRepo;
import com.example.demo.exception.BankTransactionException;
import com.example.demo.model.TransferModel;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;


@RestController
@RequestMapping("/transactions")
public class TransferAmountController 
{	
	private static final Logger logger = LoggerFactory.getLogger(TransferAmountController.class);

	@Autowired
	BankRepo repo1;
	
	@Autowired
	TransactionService tservice;
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/sendAmount", produces= "application/json")
	public ResponseEntity<String> processMoneyTransfer(HttpServletRequest request, @RequestBody TransferModel tmodel) throws BankTransactionException
	{
		logger.info("Entering into the method: processMoneyTransfer");
		boolean moneyTransferStatus = false;
		ResponseEntity response = new ResponseEntity("{\"Transaction Status\":\"Invalid input details.\"}", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		if(tmodel.getAmount() != null || tmodel.getAmount() != 0 ) 
		{
			try
			{
				moneyTransferStatus= tservice.sendMoney(tmodel.getFromAccount(), tmodel.getToAccount(), tmodel.getAmount());
				
			}catch(BankTransactionException e)
			{
				logger.info("Exception occured while funds transfer");
			}
			
			if(moneyTransferStatus == true) 
			{
				response = new ResponseEntity("{\"Transaction Status\":\"Amount transfer successful.\"}" , new HttpHeaders(), HttpStatus.OK);
			}
			else 
			{
				response = new ResponseEntity("{\"Transaction Status\":\"Error while transferring amount.\"}", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("Exiting the method: processMoneyTransfer");
		return response;
	}
}