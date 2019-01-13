package com.cg.app.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cg.app.account.SavingsAccount;
import com.cg.app.account.service.SavingsAccountService;
import com.moneymoney.exception.AccountNotFoundException;

@Controller
public class SpringController {
	@Autowired
	SavingsAccountService savingsAccountService;
	/*
	 * @Autowired SavingsAccountService savingsAccountService;
	 */

	@RequestMapping("/")
	public String controller() {
		return "index";
	}

	@RequestMapping("/getAll")
	public String getAllSavingsAccount(Model model) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> account = savingsAccountService.getAllSavingsAccount();
		model.addAttribute("account", account);

		return "accountform";

	}

	@RequestMapping("/AddNewAccount")
	public String addingAccount() { 
		//model.addAttribute("account", new SavingsAccount());
		return "addNewAccountForm";
	}

	@RequestMapping("/createAccount")
	public String addNewAccountDetails(@RequestParam("name") String account_hn, @RequestParam("amount") int account_bal,

			@RequestParam("rdSalaried") boolean salary) throws ClassNotFoundException, SQLException {
		savingsAccountService.createNewAccount(account_hn, account_bal, salary);

		return "redirect:index";
	}

	@RequestMapping("/deleteAccount") public String closingAccount() {
		return"closeAccount";
		  
	}
	  
	  // System.out.println("closing account"); }
	  
	  @RequestMapping("/Accountdelete")
	  public String deteleAccount(@RequestParam("accountNumber") int account_id) throws ClassNotFoundException, SQLException {
	  savingsAccountService.deleteAccount(account_id); return "redirect:index"; }

	@RequestMapping("/searchAccount")
	public String searchAccount() {
		return "searchAccount";
	}

	@RequestMapping("/searchForm")
	public void searchForm(@RequestParam("accountNumber") int account_id, Model model)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount account = savingsAccountService.getAccountById(account_id);
		model.addAttribute("account", account);
	}

	@RequestMapping("/deposit")
	public String depositMoney() {
		return "deposit";
	}

	@RequestMapping("/depositamount")
	public String depositamount(@RequestParam("accountNumber") int account_id, @RequestParam("amount") double amount)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount account = savingsAccountService.getAccountById(account_id);
		savingsAccountService.deposit(account, amount);
		return "redirect:index";
	}

	@RequestMapping("/withdraw")
	public String withdrawMoney() {
		return "withdrawform";
	}

	@RequestMapping("/withdrawAmount")
	public String withdrawamount(@RequestParam("accountNumber") int account_id, @RequestParam("amount") double amount)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount account = savingsAccountService.getAccountById(account_id);
		savingsAccountService.withdraw(account, amount);
		return "redirect:index";
	}

	@RequestMapping("/update")
	public String updateAccount() {
		return "redirect:index";
	}

	@RequestMapping("/updateAccount")
	public String updateAccountDetails(@RequestParam("accountNumber") int account_id)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount account = savingsAccountService.getAccountById(account_id);
		savingsAccountService.updateAccount(account);
		return "redirect:index";
	}

	@RequestMapping("/checkCurrentBalance")
	public String checkAllcurrentBalance() {
		return "checkCurrentbalance";
	}

	@RequestMapping("/checkBalance")
	public String checkCurrentAccountBalance(@RequestParam("accountNumber") int account_id)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		// SavingsAccount account=savingsAccountService.getAccountById(account_id);
		double account = savingsAccountService.checkCurrentBalance(account_id);
		System.out.println(account);
		return "redirect:index";
	}

	@RequestMapping("/fundTransfer")
	public String fundTransfer() {
		return "transferMoney";
	}
	@RequestMapping("/amountTransfer")
	public String transfermoney(@RequestParam("senderAccountNumber") int senderAccountNumber,@RequestParam("receiverAccountNumber") int receiverAccountNumber,
			@RequestParam("amount") int amount,Model model) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount senderAccount = savingsAccountService.getAccountById(senderAccountNumber);
		SavingsAccount receiverAccount = savingsAccountService.getAccountById(receiverAccountNumber);
		savingsAccountService.fundTransfer(senderAccount, receiverAccount, amount);

		// SavingsAccount account=savingsAccountService.getAccountById(account_id);
		//double account = savingsAccountService.checkCurrentBalance(account_id);
		//System.out.println(account);
		return "redirect:index";
	}

}
