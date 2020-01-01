package com.revature.bank;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.revature.bank.bo.*;
import com.revature.bank.boimpl.*;
import com.revature.bank.to.*;
import com.revature.exception.BusinessException;

public class BankApp {
	
	private static Logger log = Logger.getLogger(BankApp.class);

	/*
	 * The Bank app is a console-based application that simulates banking operations. 
	 * A customer can apply for an account, view their balance, and make withdrawals and deposits. 
	 * An employee can approve or deny accounts and view account balances for their customers.
	 * 
	 */

	private static boolean success;
	
	public static void main(String[] args) { // view or presentation layer
		Scanner scanner = new Scanner(System.in);

		log.info(
			  "\n******            ***           ****        **    **    **        *^**^*   " + 
			  "\n**   ***         ** **          ** **       **    **   **       {**O**O**} " + 
			  "\n**   ***        **   **         **   **     **    **  **        ****__**** " + 
			  "\n*******        *********        **    **    **    *****          ********  " + 
			  "\n********      ***********       **     **   **    *****           ******   " + 
			  "\n**    ***     **       **       **      **  **    **  **        ********** " + 
			  "\n**    ***    **         **      **       ** **    **   **         ******   " + 
			  "\n********    **           **     **        ****    **    **        **  **   " );
		log.info("___________________________________________________________\n");
		log.info("                   Welcome to the Robot Bank");
		log.info("___________________________________________________________\n");
		
		int choice = 0;
		do {
			log.info("Select you option");
			log.info("1) Login");
			log.info("2) Register New User");
			log.info("3) Exit");
			try {
				choice = Integer.parseInt(scanner.next());
			} catch (NumberFormatException e) {
				choice = 0;
			}
			UserBo bo = new UserBoImpl();
			CustomerBo cbo = new CustomerBoImpl();
			AccountTypeBo atbo = new AccountTypeBoImpl();
			AccountBo abo = new AccountBoImpl();
			
			switch (choice) {
			case 1:
				//////////////////////////////////////////////////////////////////////////////////////////////////////////
				//Customer Page 
				User userInfo = null;
				log.info("___________________________________________________________\n");
				log.info("                        Login Page");
				log.info("___________________________________________________________\n");
				String username, pass;
				log.info("Enter Username:  ");
				username = scanner.next();
				log.info("Enter Password:  ");
				pass = scanner.next();
				try {
					userInfo = bo.authenticateUser(username, pass);
				} catch (BusinessException e) {
					log.error(e.getMessage());
				}
				if(userInfo!=null) {// user has been login
					System.out.println("user has been login ");
					Customer cust = new Customer();
					Account account = new Account();
					if(userInfo.getRole().equals("c") ) {
						try {
							cust = cbo.getCustInfo(userInfo.getUserId());
						}catch(BusinessException e) {
							log.error(e.getMessage());
						}
						log.info("___________________________________________________________\n");
						log.info("Welcome " + cust.getLname().toUpperCase());
						log.info("___________________________________________________________\n");
						
						int custOption = 0;
						do {
							log.info("Select option below: ");
							log.info("1) Apply New Account ");
							log.info("2) Select Account ");
							log.info("3) Back ");
							try {
								custOption = Integer.parseInt(scanner.next());
							} catch (NumberFormatException e) {
								custOption = 0;
							}
							switch(custOption) {
							/////////////////////////////////////////////////////////////////////////////////////////////////////////
							case 1:
									account = new Account();
									account.setCustInfo(cust.getCID());
									boolean max = true;
									try {
										max = abo.checkAccount(cust.getCID());
									}catch (BusinessException e) {
										log.error(e.getMessage());
									}
									if(max == false) {
										log.info("Apply new account been denied. Max account has reach");
									}else {
										do {
											success = true;
											boolean syntax = false;
											do {
												try {//get all account Type List
													log.info("Apply which type of account? ");
													List<AccountType> accTypeList = atbo.getAccountTypeList(); 
													int i = 0;
													for(AccountType t:accTypeList) {//print account type list
														log.info(++i + ") " +t.getTypes());
													}
													log.info("3) Back");
													try {
														int select = Integer.parseInt(scanner.next());
														if(select == 3) {syntax = true;}
														else {
															select--;
															if(select < 0 || select >= accTypeList.size()) {
																throw new BusinessException("Invalid Selection..select between 1-"+accTypeList.size());
															}else {
																account.setaType(accTypeList.get(select));
																try {//creating account with info fill out 
																	account = abo.newAccount(account);
																	if(account.getAccountNumber() != null) {
																		log.info("\n-----------------------------------------------------------\n");
																		log.info("          Thanks you for applying robot bank account.");
																		log.info("        Allows 5-7 buiness day for account to process.");
																		log.info("\n-----------------------------------------------------------\n");
																		success = true;
																	}
																}catch (BusinessException e) {
																	log.error(e.getMessage());
																}
																syntax = true;
															}
														}
													}catch (NumberFormatException e) {
														custOption = 0;
													}
													
												}catch (BusinessException e) {
													log.error(e.getMessage());
												}
											}while (syntax != true);
											
										}while(success != true);
									}
								break;
								//////////////////////////////////////////////////////////////////////////////////////////////////////////
							case 2:
								account = new Account();
								boolean syntax = false;
								boolean found = false;
								do {
									try {
										List<Account> accList= abo.getAllAccount(cust.getCID());
										if(accList.size() == 0) {
											System.out.println("No account record. Please apply new account");
											syntax= true;
										}else {
											found =true;
											int i = 0;
											log.info("-----------------------------------------------------------\n");
											log.info("Select one of the account below for more option: \n");
											for(Account a:accList) {
												String status = null;
												if(a.getStatusId() == 1) {status = "Pending";}
												else if(a.getStatusId() == 2) {status = "Rejected";}
												else if(a.getStatusId() == 3) {status = "Active";}
												else{status = "Closed";}
												log.info(++i+") "+a.toString() + ", Status: " + status);
											}
											log.info("\n-----------------------------------------------------------\n");
											try {
												int select = Integer.parseInt(scanner.next());
												select--;
												if(select < 0 || select >= accList.size()) {
													throw new BusinessException("Invalid Selection..select between 1-"+accList.size());
												}else {
													account = accList.get(select);
													log.info("You had select your "+ account.getaType().getTypes()+" account with account#'s: " + account.getAccountNumber());
													syntax = true;
												}
											}catch (NumberFormatException e) {
												log.error("Number only selection");
											}
										}
					
									}catch(BusinessException e) {
										log.error(e.getMessage());
									}
								}while (syntax != true);
								//-------------------------------------------------------------------------------------
								//After selection account perform following action
								if(found == true) {
									if(account.getStatusId() == 3) {
										int action = 0;
										do {
											log.info("1) Deposit");
											log.info("2) Withdrawal");
											log.info("3) Transfer");
											log.info("4) Transaction Log");
											log.info("5) Back");
											
											try {
												action = Integer.parseInt(scanner.next());
											} catch (NumberFormatException e) {
												action = 0;
											}
											switch(action) {
											case 1:
												log.info("Enter the amount you want to deposit:  ");
												success = false;
												try {
													
													double amount = scanner.nextDouble();
													try {
														int i  = abo.deposit(account.getAccountNumber(),amount);
														
														if(i == 1) {
															log.info("\n-----------------------------------------------------------\n");
															log.info("The system has received the deposit  $" + amount + " into account#'s " + account.getAccountNumber());
															log.info("\n-----------------------------------------------------------\n");
														}
													}catch(BusinessException e) {
														log.error(e.getMessage());
													}
													
												}catch(InputMismatchException e) {
													log.info("Invalid input");
									
												}
												
												break;
											case 2:
												log.info("Enter the amount you want to withdrawal: ");
												try {
													double amount = scanner.nextDouble();
													try {
														int i = abo.withdrawal(account.getAccountNumber(), amount);
														if(i == 1) {
															log.info("\n-----------------------------------------------------------\n");
															log.info("The system has withdrawal  $" + amount + " from account#'s " + account.getAccountNumber());
															log.info("\n-----------------------------------------------------------\n");
														}
													}catch(BusinessException e) {
														log.error(e.getMessage());
													}
												}catch(InputMismatchException e) {
													log.info("Invalid input format");
												}
												break;
											case 3:
												log.info("Enter the amount you want to transfer: ");
						
												try {
													double amount = scanner.nextDouble();
													try {
														log.info("Enter recipient account number in format of BR follow by 6 acount digit");
														String target = scanner.next();
														int i = abo.transfer(account.getAccountNumber(), target, amount);
														if(i == 1) {
															log.info("\n-----------------------------------------------------------\n");
															log.info("The system has transfer money $"+amount+" to the recipient account");
															log.info("\n-----------------------------------------------------------\n");
														}
													}catch(BusinessException e) {
														log.error(e.getMessage());
													}
												}catch(InputMismatchException e) {
													log.info("Invalid input format");
												}
												
												break;
											case 4:
												log.info("Transaction Log");
												try {
													List<TransHistory> tList = abo.getAccountTransaction(account.getAccountNumber());
													int i = 0;
													log.info("\n-----------------------------------------------------------\n");
													for(TransHistory t:tList) {
														log.info(++i+") "+ t.toString());
													}
													log.info("-----------------------------------------------------------\n");
												}catch(BusinessException e) {
													log.error(e.getMessage());
												}
												break;
											case 5:
												break;
											default:
											}
										}while (action != 5);
									}else if(account.getStatusId() == 1) {
										log.info("-----------------------------------------------------------\n");
										log.info("Account still processing, allow 5-7 business day to process.");
										log.info("\n-----------------------------------------------------------\n");
									}else if(account.getStatusId() == 2) {
										log.info("-----------------------------------------------------------\n");
										log.info("Your account had been rejected please contact support to verify" );
										log.info("\n-----------------------------------------------------------\n");
									}else if(account.getStatusId() == 4) {
										log.info("-----------------------------------------------------------\n");
										log.info("Your account has been closed please contact support for details");
										log.info("\n-----------------------------------------------------------\n");
									}
								}
								
								break;
							case 3:
								break;
							default:
								log.info("Invalid Option......select 1-3");
							}
						
						}while (custOption !=3 );
						

////////////			//////////////////////////////////////////////////////////////////////////////////////////////
						// Employee Option
					}else if(userInfo.getRole().equals("e")){
						log.info("___________________________________________________________\n");
						log.info("                       Employee Page");
						log.info("___________________________________________________________\n");
						int selection = 0;
						EmployeeBo ebo = new EmployeeBoImpl();
						Account acc = new Account();
						Customer customer = new Customer();
						do {
							log.info("Select option");
							log.info("1. View Pending Account");
							log.info("2. View Account");
							log.info("3. Back");
							
							try {
								selection = Integer.parseInt(scanner.next());
							} catch (NumberFormatException e) {
								selection = 0;
							}
							
							switch(selection) {
							case 1 :
								acc = new Account();
								boolean syntax= false;
								boolean noAcountFound = false;
								do {//get all pending list  and select on account
									log.info("\n-----------------------------------------------------------");
									log.info("                       Pending Account");
									log.info("-----------------------------------------------------------\n");
									try {
										List<Account> pendingAcc = ebo.getAllPendingAccount();
										if(pendingAcc.size() == 0) {
											syntax = true;
											noAcountFound = true;
										}else {
											int i = 0;
											log.info("   "+"Account#    " + "Balance      "   + "Type    ");
											for(Account t:pendingAcc) {
									
												log.info(++i+ ") "+t.getAccountNumber()+"      " + t.getBalance()+"       "  + t.getaType().getTypes());
											}
											
											try {
												int select = Integer.parseInt(scanner.next());
												log.info("\n-----------------------------------------------------------\n");
												select--;
												if(select < 0 || select >= pendingAcc.size()) {
													throw new BusinessException("Invalid Selection..select between 1-"+pendingAcc.size());
												}else {
													acc = pendingAcc.get(select);
													syntax = true;
													customer = new Customer();
													try {
														customer = abo.getCustomer(acc.getCustInfo());
													}catch(BusinessException e) {
														log.error(e.getMessage());
													}
													log.info("Selecting infomation with account#'s: " + acc.getAccountNumber());
													log.info("\n-----------------------------------------------------------\n");
													log.info("                     Customer Infomation");
													log.info("\n-----------------------------------------------------------\n");
													log.info("Lastname: " + customer.getLname() + " Firstname: " + customer.getFname());
													log.info("Date of birth: " + customer.getDob()    + " Gender: " + customer.getGender().toUpperCase() );
													log.info("Applying for " + acc.getaType().getTypes() + " account");
													log.info("\n-----------------------------------------------------------\n");
													
												}
											}catch(NumberFormatException e) {
												log.error("Invalid format.......select between 1-" + pendingAcc.size());
											}
										}
										
										
									}catch (BusinessException e) {
										log.error(e.getMessage());
										syntax = true;
										noAcountFound = true;
									}
								}while (syntax != true);
								if(!noAcountFound) {
									do {
										syntax = false;
										log.info("1) Approved "); 
										log.info("2) Rejected ");
										log.info("3) Exit ");
										log.info("-----------------------------------------------------------\n");
										try {
											int select = Integer.parseInt(scanner.next());
											if(select == 3) {syntax = true;}
											if(select > 0 && select < 3) {
												//1 pending 2 reject 3 active
												if(select == 1) {
													acc.setStatusId(3);
												}else {
													acc.setStatusId(2);
												}
												try {
													syntax = ebo.proccessAccount(acc.getAccountNumber(),acc.getStatusId());
												}catch (BusinessException e) {
													log.error(e.getMessage());
												}
												log.info("You had proccess " + customer.getFname()+ " " + customer.getLname()  +
														" "+ acc.getaType().getTypes() + " account. ");
												log.info("-----------------------------------------------------------\n");
												syntax = true;
											}
										}catch(NumberFormatException e) {
											log.error("Invalid format.......select between 1-3");
										}
										
									}while (syntax != true);
								}
								
								
								
								break;
							case 2:
								log.info("\n-----------------------------------------------------------");
								log.info("                         View Account");
								log.info("-----------------------------------------------------------\n");
								acc = new Account();
								do {
									success = false;
									log.info("1) Provide the account number to be search");
									log.info("2) Last 4 digit of SSN and DOB");
									log.info("3) Back");

									int select = 0;
									try {
										select = Integer.parseInt(scanner.next());
									} catch (NumberFormatException e) {
										select = 0;
									}
									switch(select) {
									case 1:
										boolean getAccount = false;
										boolean quit = false;
										Customer c = new Customer();
										do {
											log.info("Please enter account number or type in q to go back: ");
											String aNum = scanner.next();
											if(aNum.equalsIgnoreCase("q")) {
												getAccount = true;
												quit = true;
											}else {
												try {
													acc = ebo.searchByAccountNumber(aNum);
													if(acc.getAccountNumber() != null) {
														getAccount = true;
													}
												}catch(BusinessException e) {
													log.error(e.getMessage());
												}
												if(getAccount) { // get customer information
													try {
														c = abo.getCustomer(acc.getCustInfo());
														log.info("\n-----------------------------------------------------------\n");
														log.info("                     Customer Infomation");
														log.info("\n-----------------------------------------------------------\n");
														log.info("Lastname: " + c.getLname() + " Firstname: " + c.getFname());
														log.info("Date of birth: " + c.getDob()    + " Gender: " + c.getGender().toUpperCase() );
														log.info("Applying for " + acc.getaType().getTypes() + " account.");
														log.info("\n-----------------------------------------------------------\n");
													}catch(BusinessException e ) {
														log.error(e.getMessage());
													}
												}
											}
											
										}while (getAccount != true);
										if(quit == true) {}
										else {
											boolean getTrans = false;
											do {
												log.info("1) View account transcation history");
												log.info("2) Exit");
												int transOption = 0;
												try {
													transOption = Integer.parseInt(scanner.next());
												} catch (NumberFormatException e) {
													transOption = 0;
												}
												switch (transOption) {
												case 1:
													log.info("View " + acc.getAccountNumber() + " transcation history.");
													log.info("\n-----------------------------------------------------------\n");
													List<TransHistory> transList = new ArrayList<>();
													try {
														transList = ebo.viewAllAccountTransaction(acc.getAccountNumber());
													}catch(BusinessException e) {
														log.error(e.getMessage());
													}
													if(transList.size() ==0) {
														log.info("There is no transaction record.");
													}else {
														for(TransHistory t:transList) {
															log.info(t.toString());
														}
													}
													
													log.info("\n-----------------------------------------------------------\n");
												
													break;
												case 2:	
													getTrans = true;
													break;
												default:
													log.info("Invalid option...select again.");
												}
											}while(getTrans != true);
											
										}
										
										break;
									case 2: 
										log.info("It will be update for next version.");
										break;
									case 3: 
										success = true;
										break;
									default: 
										log.info("Invalid selection");
										break;
									}
								} while(success != true);
								break;
							case 3:
								log.info("Logging off ");
								break;
							default: 
								log.info("Invalid Selection.....Selection between 1-3");
								break;
							}
						
						}while (selection != 3);
					}
				}
				break;
			case 2: 
				log.info("___________________________________________________________\n");
				log.info("                        Register New User");
				log.info("___________________________________________________________\n");
				User users = new User(); //new user object 
				Customer custInfo = new Customer(); // new customer object
				Account account = new Account();
				do {//new customer only 
					success = false;  // check if each step is accomplish
					log.info("Enter New Username:  "); //get username 
					users.setUsername(scanner.next());
					log.info("Enter New Password:  ");
					users.setPassword(scanner.next()); 
					
					try {
						 success = bo.createUser(users);
					} catch(BusinessException e) {
						log.error(e.getMessage());
					}
				}while(success != true);
				
				if(success == true ) {
					log.info("Success register new user");
					log.info("--------------------------------------------------");
					try {
						users = bo.authenticateUser(users.getUsername(), users.getPassword());
					} catch (BusinessException e) {
						log.error(e.getMessage());
					}
					if(users!=null) {// user has been login
						do{
							custInfo.setUserId(users.getUserId());
							String f,l,d,g;
							do{ success = false;
								log.info("Enter First Name");
							    f = scanner.next();
							    if(f.matches("^[a-zA-Z]*$")){success = true;}
							    else {log.error("Invalid input......first name should be alphabet only");}
							}while(success !=true);
							
							do{ success = false;
								log.info("Enter Last Name");
						    	l = scanner.next();
						    	if(l.matches("^[a-zA-Z]*$")){success = true;}
						    	else{log.error("Invalid input......last name should be alphabet only");}
							}while(success !=true);
							
							do {success = false;
								log.info("Enter your gender{m/f/o}");
								g = scanner.next();
								if(g.matches("^[a-zA-Z]*$") && g.equalsIgnoreCase("m")|| g.equalsIgnoreCase("f")||(g.equalsIgnoreCase("o"))) {
									success = true;
								}else {log.error("Invalid input......gender should be select from {m/f/o}");}
							}while(success !=true);
							
							do { success = false;
							log.info("Enter DOB(dd.MM.yyyy) ");
							d= scanner.next();
							if(d.matches("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}")) {success = true;}
							else {log.error("Invalid input......DOB should be in format (dd.MM.yyyy)");}
							} while(success != true);
							
							custInfo.setFname(f);
							custInfo.setLname(l);
							custInfo.setGender(g);
							try {
								custInfo.setDob(new SimpleDateFormat("dd.MM.yyyy").parse(d));
							}catch (ParseException e) {
								log.error(e);
							}
							
							try {
								custInfo = cbo.addCustInfo(custInfo);
							} catch (BusinessException e) {
								log.error(e.getMessage());
							}
							if(custInfo.getCID() != null) {
								success = true;
								//System.out.println(custInfo.getCID());
							}
						}while (success != true);
						
						do {
							success = false;
							log.info("You had Successful fill the info ");
							log.info("--------------------------------------------------");
							account.setCustInfo(custInfo.getCID()); // assign customer id to account 
							boolean syntax = false;
							do {
								try {//get all account Type List
									
									log.info("Apply which type of account? ");
									List<AccountType> accTypeList = atbo.getAccountTypeList(); 
								
									int i = 0;
									for(AccountType t:accTypeList) {
										log.info(++i + ") " +t.getTypes());
									}
									log.info("3) Back");
									try {
											int select = Integer.parseInt(scanner.next());
											if(select == 3) {
												syntax = true;
												success = true;
											}else {
												select--;
												if(select < 0 || select >= accTypeList.size()) {
													throw new BusinessException("Invalid Selection..select between 1-"+accTypeList.size());
												}else {
													account.setaType(accTypeList.get(select));
													try {//creating account with info fill out 
														account = abo.newAccount(account);
														if(account.getAccountNumber() != null) {
															log.info("\n-----------------------------------------------------------\n");
															log.info("          Thanks you for applying robot bank account.");
															log.info("        Allows 5-7 buiness day for account to process.");
															log.info("\n-----------------------------------------------------------\n");
															success = true;
															}
													} catch (BusinessException e) {
														log.error(e.getMessage());
													}
													syntax = true;
												}
											}
											
									}catch (NumberFormatException e) {
										log.error("Number only");
									}
								}catch (BusinessException e) {
									log.error(e.getMessage());
								}
							}while (syntax != true);
							
							
							
						}while (success != true);
					}
				}
				break;
			case 3:
				log.info("--------------------------------------------------");
				log.info("Thank You for using the robot bank application");
				log.info("--------------------------------------------------");
				break;
			default: 
				log.error("------------------------------------------------------");
				log.error("Invalid choice..........Select number between 1-3 only");
				log.error("______________________________________________________");
				break;
			}
			
		} while(choice != 3);
		scanner.close();
	}
	
	
	
	/*
	 * ## Requirements
	 *	1. Functionality should reflect the below user stories.
	 *	2. Data is stored in a database.
	 *	3. A custom stored procedure is called to perform some portion of the functionality.
	 *	4. Data Access is performed through the use of JDBC in a data layer consisting of Data Access Objects.
	 *	5. All input is received using the java.util.Scanner class.
	 *	6. Log4j is implemented to log events to a file.
	 *	7. A minimum of one (1) JUnit test is written to test some functionality.
	 *
	 * ## User Stories
	 * As a user, I can login.
	 * As a user, I can register for a customer account.
	 
	 * As a customer, I can apply for a new bank account with a starting balance.
	 * As a customer, I can view the balance of a specific account.
	 * As a customer, I can make a withdrawal or deposit to a specific account.

	 * 	Ex:
		* A withdrawal that would result in a negative balance.
		* A deposit or withdrawal of negative money.
	 * As a customer, I can post a money transfer to another account.
	 * As a customer, I can accept a money transfer from another account.

	 * As an employee, I can approve or reject an account.
	 * As an employee, I can view a customer's bank accounts.	
	 * A an employee, I can view a log of all transactions.
	 * 
	 * As the system, I reject invalid transactions.
		
	 */

}
