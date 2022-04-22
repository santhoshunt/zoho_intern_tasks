/* Create a console application that can be used to hold the following info.
      1) Account information of various services such as google, yahoo, facebook, ... 
      Each service can have multiple accounts.
      2) Contacts info

      At any point of time, the user can do the following,
      1) Add / delete / view / update account. Service name, Service URL, Username, Password
       are the attributes for service and accounts.
      2) Add / delete / view / update contacts. Name, mobile numbers, email addresses, job title, age,
       Date of birth are the contact attributes.

      Analyze the requirement and implement it with appropriate data structure. */


#include <stdlib.h>
#include <stdio.h>
#include <dirent.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>


#define SIZE 30

//  ****************************** helper *********************************************



// ******************************* ACCOUNT *********************************************************

void insertName(char *name, char *url) {
	char userName[SIZE];
	char password[SIZE];

	printf("\nEnter user name: ");
	scanf("\n%[^\n]", userName);
	printf("\nEnter password: ");
	scanf("\n%[^\n]", password);

	DIR *d;
    struct dirent *dir;
    d = opendir("./data/");
    if (d) {
		while ((dir = readdir(d)) != NULL)
        {
            printf("%s\n", dir->d_name);
            if (strcmp(dir->d_name, name) == 0) {
            	goto createFile;
            }
        }
        goto createNewDir;
        closedir(d);
    }

	createNewDir: ;

	char res[40] = "./data/";
    strcat(res, name);
    mkdir(res, 0777);
    closedir(d);

	char credFile[SIZE] = "./data/";
	strcat(credFile, name);
    strcat(credFile, "/");
    strcat(credFile, "url.txt");
    printf("\n%s\n", credFile);
    FILE *fp1;
    fp1 = fopen(credFile, "w");
    fputs(name, fp1);
    fputs("\n", fp1);
    fputs(url, fp1);
    fclose(fp1);

    createFile: ;
    FILE *fp;
    char fileName[SIZE] = "./data/";
    strcat(fileName, name);
    strcat(fileName, "/");
    strcat(fileName, userName);
    strcat(fileName, ".txt");
    printf("\n%s\n", fileName);
    fp = fopen(fileName, "w");
    fputs(userName, fp);
    fputs("\n", fp);
    fputs(password, fp);
    fclose(fp);
	return;
}

void addAccount() {
	printf("\nEnter Service Name: ");
    char *name = malloc(sizeof(char) * SIZE);
	char *url = malloc(sizeof(char) * SIZE);
	scanf("\n%[^\n]", name);
	printf("Enter URL: ");
	scanf("\n%[^\n]", url);
    insertName(name, url);
}


void displayAccounts() {

	printf("\nPlease choose a service from the below to view the respective accounts\n");

	DIR *d;
    struct dirent *dir;
    d = opendir("./data/");
    int serviceDisplayed = 0;
    if (d)
    {
        while ((dir = readdir(d)) != NULL)
        {
        	if(strcmp(dir->d_name, ".") == 0 || strcmp(dir->d_name, "..") == 0) {
        		continue;
        	}
            printf("%s\n", dir->d_name);
            serviceDisplayed = 1;
        }
        closedir(d);
    }
    if (!serviceDisplayed) {
    	printf("no services to display!\nPlease create one");
    	return;
    }
    char displayService[SIZE];

    scanf("\n%[^\n]", displayService);
    char curdir[SIZE*2] = "./data/";
    strcat(curdir, displayService);
    int accountDisplayed = 0;
    d = opendir(curdir);
    if (d) {
		while ((dir = readdir(d)) != NULL)
        {
        	if(strcmp(dir->d_name, ".") == 0 || strcmp(dir->d_name, "..") == 0) {
        		continue;
        	}
            FILE *curFile;
            char fileName[SIZE*2];
            strcpy(fileName, curdir);
            strcat(fileName, "/");
            strcat(fileName, dir->d_name);
            curFile = fopen(fileName, "r");
            char temp[SIZE];
			printf("\nfile name %s\n", fileName);
            fgets(temp, SIZE, curFile);
            if (strcmp(dir->d_name, "url.txt") == 0) {
				printf("\nService Name: %s\n", temp);
            } else {
				printf("\nUser Name: %s", temp);
            }
            fgets(temp, SIZE, curFile);
            if (strcmp(dir->d_name, "url.txt") == 0) {
            	printf("\nURL: %s\n\n", temp);
            } else {
            	printf("\nPassword: %s\n\n", temp);
            }
            fclose(curFile);
            accountDisplayed = 1;
        }
        closedir(d);
    }
    if (!accountDisplayed) {
    	printf("No accounts to display in this service");
    }

    return;

	noneToDisplay: ;
	printf("No accounts to display!");
}

void deleteAccount() {
	printf("\nEnter the SERVICE name of the account you want to delete: ");
	char deleteService[SIZE];
	scanf("\n%[^\n]", deleteService);
	printf("\nEnter the USERNAME of the account you want to delete: ");
	char deleteName[SIZE];
	scanf("\n%[^\n]", deleteName);
	char fileName[SIZE*2] = "./data/";
	strcat(fileName, deleteService);
	strcat(fileName, "/");
	strcat(fileName, deleteName);
	strcat(fileName, ".txt");
	if (remove(fileName) == 0) {
		printf("\nDeletion successfull!\n");
	} else {
		printf("unable to delete file\nPLEASE CHECK THE SERVICE NAME AND USERNAME\n");
	}
}

void updateAccount() {
	printf("Do you want to change the service name and url? (press 1 if yes 0 if no): ");
	int choice = 0;
	scanf("%d", &choice);
	if (choice == 1) {
		char newName[SIZE];
		char newURL[SIZE];
		char oldName[SIZE];
		char oldURL[SIZE];
		printf("\nEnter old name: ");
		scanf("\n%[^\n]", oldName);
		printf("\nEnter oldURL: ");
		scanf("\n%[^\n]", oldURL);
		printf("\nEnter newName: ");
		scanf("\n%[^\n]", newName);
		printf("\nEnter newURL: ");
		scanf("\n%[^\n]", newURL);
		char file[SIZE] = "./data/";
		strcat(file, oldName);
		strcat(file, "/url.txt");
		FILE *f;
		f = fopen(file, "w+");
		if (f == NULL) {
			printf("\nUnable to open file, please check the spellings\n");
			return;
		}
		fputs(newName, f);
		fputs("\n", f);
		fputs(newURL, f);
		fclose(f);
		char newPath[SIZE] = "./data/";
		char oldPath[SIZE] = "./data/";
		strcat(newPath, newName);
		strcat(oldPath, oldName);
		if (rename(oldPath, newPath) != 0) {
			printf("\nUnable to change name\n");
			return;
		}
	}
	char oldServiceName[SIZE];
	printf("Enter the service name if you want to change credentials (no if none): ");
	scanf("\n%[^\n]", oldServiceName);
	if(strcmp(oldServiceName, "no") == 0) {
		return;
	}
	char serviceName[SIZE];
	char oldUserName[SIZE];
	char newUserName[SIZE];
	char newPassword[SIZE];
	printf("Enter the Service Name: ");
	scanf("\n%[^\n]", serviceName);
	char filePath[SIZE] = "./data/";
	strcat(filePath, serviceName);
	strcat(filePath, "/");
	printf("Enter the old User name: ");
	scanf("\n%[^\n]", oldUserName);
	printf("Enter the new user name: ");
	scanf("\n%[^\n]", newUserName);
	printf("Enter the new password: ");
	scanf("\n%[^\n]", newPassword);
	strcat(filePath, oldUserName);
	strcat(filePath, ".txt");
	FILE *fp;
	fp = fopen(filePath, "w");
	if (fp == NULL) {
		printf("\nPlease check the file name!");
		return;
	}
	fputs(newUserName, fp);
	fputs("\n", fp);
	fputs(newPassword, fp);
	fclose(fp);
	char curFilePath[SIZE] = "./data/";
	char newFileName[SIZE] = "./data/";
	strcat(curFilePath, oldUserName);
	strcat(newFileName, newUserName);
	strcat(curFilePath, ".txt");
	strcat(newFileName, ".txt");
	if (rename(curFilePath, newFileName) != 0) {
		printf("\nFile rename not successfull!\nPlease try again");
		return;
	}
	printf("\nUpdation successfull\n");
}	


//  ******************************* CONTACT **************************************************************

void addContact() {
	char name[SIZE] ;
	char email[SIZE];
	char mobileNumber[SIZE];
	char jobTitle[SIZE];
	char age[SIZE];
	char dob[SIZE];
	printf("\nEnter NAME: ");
	scanf("\n%[^\n]", name);
	printf("\nEnter E-Mail address: ");
	scanf("\n%[^\n]", email);
	printf("\nEnter mobileNumber: ");
	scanf("\n%s", mobileNumber);
	printf("\nEnter JOb-Title: ");
	scanf("\n%[^\n]", jobTitle);
	printf("\nEnter AGE: ");
	scanf("\n%s", age);
	printf("\nEnter Date of Birth: ");
	scanf("\n%[^\n]", dob);
	char file[SIZE] = "./con/";
	strcat(file, mobileNumber);
	FILE *fp;
	fp = fopen(file, "w+");
	fputs(name, fp);
	fputs("\n", fp);
	fputs(email, fp);
	fputs("\n", fp);
	fputs(mobileNumber, fp);
	fputs("\n", fp);
	fputs(jobTitle, fp);
	fputs("\n", fp);
	fputs(age, fp);
	fputs("\n", fp);
	fputs(dob, fp);
	fclose(fp);
}

void displayContacts() {
	DIR *d;
    struct dirent *dir;
    d = opendir("./con/");
    int serviceDisplayed = 0;
    if (d)
    {
        while ((dir = readdir(d)) != NULL)
        {
        	if(strcmp(dir->d_name, ".") == 0 || strcmp(dir->d_name, "..") == 0) {
        		continue;
        	}
        	char fileName[SIZE] = "./con/";
        	strcat(fileName, dir->d_name);
        	FILE *fp;
        	char temp[SIZE];
        	fp = fopen(fileName, "r");
        	fgets(temp, SIZE, fp);
        	printf("\nName: %s", temp);
        	fgets(temp, SIZE, fp);
        	printf("\nemail: %s", temp);
        	fgets(temp, SIZE, fp);
        	printf("\nphone number: %s", temp);
        	fgets(temp, SIZE, fp);
        	printf("\nJob title: %s", temp);
        	fgets(temp, SIZE, fp);
        	printf("\nage: %s", temp);
        	fgets(temp, SIZE, fp);
        	printf("\nDOB: %s\n\n", temp);
        	fclose(fp);
            serviceDisplayed = 1;
        }
        closedir(d);
    }
}

void deleteContact() {
	printf("\nEnter the mobile number of the contact you wanna delete: ");
	char deleteNumber[SIZE];
	scanf("\n%[^\n]", deleteNumber);
	char file[SIZE] = "./con";
	strcat(file, deleteNumber);
	if (remove(file) == 0) {
		printf("Deletion successfull!");
		addContact();
		return;
	}
	printf("Please check the mobile number");
}

void updateContact() {
	printf("\nEnter the mobile number of the contact you wanna Update: ");
	char upNumber[SIZE];
	scanf("\n%[^\n]", upNumber);
	char file[SIZE] = "./con";
	strcat(file, upNumber);
	if (remove(file) == 0) {
		printf("Contact found please enter the new details!");
		return;
	}
	printf("Please check the mobile number");
}


// ***************************** main function **************************************************

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
			printf("\nExited!!\n");
		} else {
			printf("please enter a valid choice");
			goto repeatUserChoice;
		}
	}while (userWantsToContinue);

	return 0;
}