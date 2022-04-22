/* Create a console application that can be used to hold the following info.
      1) Account information of various services such as google, yahoo, facebook, ... 
      Each service can have multiple accounts.
      2) Contacts info

      At any point of time, the user can do the following,
      1) Add / delete / view / update account. 
      Service name, Service URL, Username, Password
       are the attributes for service and accounts.
      2) Add / delete / view / update contacts. 
      Name, mobile numbers, email addresses, job title, age,
       Date of birth are the contact attributes.

      Analyze the requirement and implement it with appropriate data structure. */

#include <stdio.h>
#include <stdlib.h>

#define stringSize 20

struct contact {
	char name[stringSize] ;
	char email[stringSize];
	int mobileNumber;
	char jobTitle[stringSize];
	int age;
	char dob[stringSize];
	struct contact *next;
};

struct account {
	char serviceName[stringSize];
	char serviceURL[stringSize];
	char userName[stringSize];
	char password[stringSize];
	struct account *next;
};

struct account *start = NULL;
struct contact *startCon = NULL;

void viewContact(struct contact newUser) {
	printf("Name: %s\ne-mail: %s\nMobile Number: %d\nJob-title: %s\nAge: %d\nDOB: %s",
	 newUser.name, newUser.email, newUser.mobileNumber, newUser.jobTitle, newUser.age, newUser.dob);
}

void displayAccounts() {
	if (start == NULL) {
		printf("\nNo accounts to display, Please add one!\n");
		return;
	}
	struct account *ptr = start;
	int accountNumber = 1;
	while (ptr != NULL) {
		printf("\nAccount Number: %d\nserviceName: %s\nserviceURL: %s\nuserName: %s\npassword: %s\n",
		 accountNumber++, ptr->serviceName, ptr->serviceURL, ptr->userName, ptr->password);
		printf("\n***********\n");
		ptr = ptr->next;
	}
}

void addAccount() {
	struct account *curAccount, *ptr = start;
	curAccount = (struct account *) malloc(sizeof(struct account));
	printf("\nEnter Service Name:\n");
	scanf("\n%[^\n]", curAccount->serviceName);
	printf("\nEnter Service URL:\n");
	scanf("\n%[^\n]", curAccount->serviceURL);
	printf("\nEnter User Name:\n");
	scanf("\n%[^\n]", curAccount->userName);
	printf("\nEnter password:\n");
	scanf("\n%[^\n]", curAccount->password);
	if (start == NULL) {
		start = curAccount;
	} else {
		while (ptr->next != NULL) {
			ptr = ptr->next;
		}
		ptr->next = curAccount;
	}
}

void deleteAccount() {
	displayAccounts();
	enterValidAccountNumber: ;
	printf("Please enter the account's number you wanna delete: ");
	int accountNumberToBeDeleted;
	scanf("%d", &accountNumberToBeDeleted);
	if (accountNumberToBeDeleted <= 0) {
		printf("Please Enter a valid Account number\n");
		goto enterValidAccountNumber;
	}
	int curNumber = 1;
	struct account *curAccount = start;
	if (start == NULL) {
		printf("No accounts Detected please add some to delete!\n");
		return;
	}
	if (accountNumberToBeDeleted == 1) {
		start = start->next;
		return;
	}
	int deleted = 0;
	while (curAccount != NULL) {
		if (curNumber == accountNumberToBeDeleted -1) {
			curAccount->next = curAccount->next->next;
			deleted = 1;
		}
		curAccount = curAccount->next;
		curNumber ++;
	}
	if (!deleted) {
		printf("please Enter a number less than %d\n", curNumber);
		goto enterValidAccountNumber;
	}
}

void updateAccount() {
	enterValidAccountNumber2: ;
	printf("Please enter the account's number you wanna update: ");
	int accountToBeUpdated;
	scanf("%d", &accountToBeUpdated);
	if (accountToBeUpdated <= 0) {
		printf("Please Enter a valid Account number\n");
		goto enterValidAccountNumber2;
	}
	int curNumber = 1;
	int accUpdated = 0;
	struct account *curAcc = start;
	if (start == NULL) {
		printf("No accounts Detected please add some to Update!\n");
		return;
	}
	struct account *curAccount = start;
	while (curAccount != NULL) {
		if (curNumber == accountToBeUpdated) {
			printf("\nEnter Service Name:\n");
			scanf("\n%[^\n]", curAccount->serviceName);
			printf("\nEnter Service URL:\n");
			scanf("\n%[^\n]", curAccount->serviceURL);
			printf("\nEnter User Name:\n");
			scanf("\n%[^\n]", curAccount->userName);
			printf("\nEnter password:\n");
			scanf("\n%[^\n]", curAccount->password);
			printf("\nAccount updated successfully!\n");
			return;
		}
		curAccount = curAccount->next;
		curNumber ++;
	}
	if (!accUpdated) {
		printf("Please enter a valid account number to update\n");
		goto enterValidAccountNumber2;
	}
}

// Name, mobile numbers, email addresses, job title, age, Date of birth
void displayContacts() {
	if (startCon == NULL) {
		printf("\nNo contact to display, Please add one!\n");
		return;
	}
	struct contact *ptr = startCon;
	int accountNumber = 1;
	while (ptr != NULL) {
		printf("\nContact Number: %d\nName: %s\nmobile Number: %d\nemail address: %s\nJob-title: %s\nage: %d\nDate-Of-Birth: %s\n",
		 accountNumber++, ptr->name, ptr->mobileNumber, ptr->email, ptr->jobTitle, ptr->age, ptr->dob);
		printf("\n***********\n");
		ptr = ptr->next;
	}
}

void addContact() {
	struct contact *curAccount, *ptr = startCon;
	curAccount = (struct contact *) malloc(sizeof(struct contact));
	printf("\nEnter NAME: ");
	scanf("\n%[^\n]", curAccount->name);
	printf("\nEnter E-Mail address: ");
	scanf("\n%[^\n]", curAccount->email);
	printf("\nEnter mobileNumber: ");
	scanf("\n%d", &curAccount->mobileNumber);
	printf("\nEnter JOb-Title: ");
	scanf("\n%[^\n]", curAccount->jobTitle);
	printf("\nEnter AGE: ");
	scanf("\n%d", &curAccount->age);
	printf("\nEnter Date of Birth: ");
	scanf("\n%[^\n]", curAccount->dob);
	if (startCon == NULL) {
		startCon = curAccount;
	} else {
		while (ptr->next != NULL) {
			ptr = ptr->next;
		}
		ptr->next = curAccount;
	}
}

void deleteContact() {
	if (start == NULL) {
		printf("No accounts Detected please add some to delete!\n");
		return;
	}

	enterValidContactNumber: ;
	printf("Please enter the contact's number you wanna delete: ");
	int accountNumberToBeDeleted;
	scanf("%d", &accountNumberToBeDeleted);
	if (accountNumberToBeDeleted <= 0) {
		printf("Please Enter a valid Account number\n");
		goto enterValidContactNumber;
	}
	int curNumber = 1;
	struct contact *curAccount = startCon;
	
	if (accountNumberToBeDeleted == 1) {
		startCon = startCon->next;
		return;
	}
	int deleted = 0;
	while (curAccount != NULL) {
		if (curNumber == accountNumberToBeDeleted -1) {
			curAccount->next = curAccount->next->next;
			deleted = 1;
		}
		curAccount = curAccount->next;
		curNumber ++;
	}
	if (!deleted) {
		printf("please Enter a number less than %d\n", curNumber);
		goto enterValidContactNumber;
	}
}

void updateContact() {
	if (start == NULL) {
		printf("No accounts Detected please add some to Update!\n");
		return;
	}

	enterValidContactNumber2: ;
	printf("Please enter the account's number you wanna update: ");
	int accountToBeUpdated;
	scanf("%d", &accountToBeUpdated);
	if (accountToBeUpdated <= 0) {
		printf("Please Enter a valid Contact number\n");
		goto enterValidContactNumber2;
	}
	int curNumber = 1;
	int accUpdated = 0;
	struct contact *curAccount = startCon;
	while (curAccount != NULL) {
		if (curNumber == accountToBeUpdated) {
			printf("\nEnter NAME: ");
			scanf("%[^\n]", curAccount->name);
			printf("\nEnter E-Mail address: ");
			scanf("\n%[^\n]", curAccount->email);
			printf("\nEnter mobileNumber: ");
			scanf("\n%d", &curAccount->mobileNumber);
			printf("\nEnter JOb-Title: ");
			scanf("\n%[^\n]", curAccount->jobTitle);
			printf("\nEnter AGE: ");
			scanf("\n%d", &curAccount->age);
			printf("\nEnter Date of Birth: ");
			scanf("\n%[^\n]", curAccount->dob);
	
			printf("\nContact updated successfully!\n");
			return;
		}
		curAccount = curAccount->next;
		curNumber ++;
	}
	if (!accUpdated) {
		printf("Please enter a valid Contact number to update\n");
		goto enterValidContactNumber2;
	}
}

// ********************************** insert new function here *************************************

int main() {
	printf("\nWelcome to Digital vault!\n");
	int userWantsToContinue = 1;
	do {
		repeatUserChoice: ;
		printf("\nEnter 1 to Add / delete / view / update ACCOUNT\nEnter 2 to Add / delete / view / update CONTACTS\nEnter 0 to exit application\n");
		scanf("%d", &userWantsToContinue);
		if (userWantsToContinue == 1) {
			// Add / delete / view / update ACCOUNT
			repeatAccount: ;
			printf("\n1.Add Accounts  2.delete account  3.view Account  4.Update Account\n");
			int accountChoice;
			scanf("%d", &accountChoice);
			if (accountChoice == 1) {
				addAccount();
			} else if (accountChoice == 2) {
				deleteAccount();
			} else if (accountChoice == 3) {
				displayAccounts();
			} else if (accountChoice == 4) {
				updateAccount();
			} else {
				printf("\nPlease enter a valid choice.\nThe choices are:\t1, 2, 3, 4");
				goto repeatAccount;
			}
		} else if (userWantsToContinue == 2) {
			// Add / delete / view / update CONTACT
			repeatContact: ;
			printf("\n1.Add Contact  2.delete Contact  3.view Contacts  4.Update Contact\n");
			int accountChoice;
			scanf("%d", &accountChoice);
			if (accountChoice == 1) {
				addContact();
			} else if (accountChoice == 2) {
				deleteContact();
			} else if (accountChoice == 3) {
				displayContacts();
			} else if (accountChoice == 4) {
				updateContact();
			} else {
				printf("\nPlease enter a valid choice.\nThe choices are:\t1, 2, 3, 4");
				goto repeatContact;
			}
		} else if (userWantsToContinue == 0) {
			printf("\nExiting application\n");
		} else {
			printf("please enter a valid choice");
			goto repeatUserChoice;
		}
	}while (userWantsToContinue);

	return 0;
}
