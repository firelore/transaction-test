package com.github.firelore.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.firelore.domain.Transaction;
import com.github.firelore.service.TransactionService;

@Controller
public class TransactionController {
	private TransactionService transactionService;
	
	@Inject
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public String getMethod(@RequestParam("transactionId") int transactionId) {
		Transaction trans = transactionService.findOne(transactionId);
		trans.setSubmittedDate(new Date());
		transactionService.save(trans);
		return "Success";
	}
}
