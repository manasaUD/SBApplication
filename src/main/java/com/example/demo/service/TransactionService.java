package com.example.demo.service;

import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.BankRepo;
import com.example.demo.exception.BankTransactionException;
import com.example.demo.model.BankModel;

@Service
public class TransactionService {

	@Autowired
	BankRepo repo1;
		
	@Autowired
	EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
		
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
    public boolean sendMoney(Long fromAccount, Long toAccount, Long amount) throws BankTransactionException 
	{
		boolean moneySent = false;
		try 
		{
			if(true)
			{
				addAmount(fromAccount, -amount);
				addAmount(toAccount, amount);
				moneySent = true;
			}
		}
		catch(Exception e)
		{
			logger.error("Exception while transferring money: ", e);
			throw e;
		}
		return moneySent;
    }
	
	@Transactional(propagation = Propagation.MANDATORY )
    public void addAmount(Long acc_no, Long amount) throws BankTransactionException 
	{	
        BankModel account = this.findById(acc_no);
        if (account == null) 
        {
            throw new BankTransactionException("Account not found " + acc_no);
        }
        Long newBalance = account.getAcc_balance() + amount;
        if (newBalance < 0) 
        {
            throw new BankTransactionException("The money in the account '" + acc_no + "' is not enough (" + account.getAcc_balance() + ")");
        }
        account.setAcc_balance(newBalance);
    }

	public BankModel findById(Long acc_no) 
	{ 
        return this.em.find(BankModel.class, acc_no);
    }
	
}