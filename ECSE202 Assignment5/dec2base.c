#include <stdio.h>
#include <stdlib.h>

//Student Name: Ibrahim T Miraj
//Student ID: 260791021


void revStr(char *str, int l){                       //Function that reverses the string to be in the correct order
	char* reversedString = (char*)malloc(sizeof(char)*l);
	int j;
	for( j = 0 ; j < l ; j++)
		reversedString[j] = str[l-j-1];
	for(j = 0 ; j < l; j++)
		printf("%c",reversedString[j]);
}


void dec2base(int input,int base,char *str){   //Function for converting number to a given base

	int i = input;
	int k = 0;
	while(i > 0)
	{
		int r = i%base;
		i = i/base;

		char rChar;
		if(r < 10)
			rChar = '0'+r;
		else
			rChar = 'A'+(r-10);
		str[k] = rChar;
		k++;
	}
	revStr(str,k);
}


int main (int argc, char *argv[]) {    //Takes reading from the ConsoleProgram

	int a, b;                          //Initializes a, which is number and b, which is base

	if (argc == 3) {                   //Handles case with both number and base
		sscanf(argv[1], "%d", &a);
		sscanf(argv[2], "%d", &b);
	} else if (argc == 2) {            //Handles case with just the number
		sscanf(argv[1], "%d", &a);
		b = 2;
	} else {                           //All other cases will be invalid
        printf("You must only enter 2 values.");
        return 0;
	}
	
	if (a < 0 || a > 2147483647) {     //Makes sure number is within bounds
        printf("Error: number must be in the range of [0,214748367]");
        return 0;
	}
	
	if(b < 2 || b > 36) {              //Makes sure base is within bounds
        printf("Error: base must be in the range [2, 36]");
	return 0;
	}
	
	if(a == 0) {                       //Handles case when number is 0
	printf("The Base-%d form of 0 is: 0", b);
	return 0;
	}
	
 	int q = a;
 	int length = 0;
 	while(q > 0) {
 		int r = q%b;
 		q = q/b;
 		length++;
 	}
 	
 	char* str = (char*)(malloc(sizeof(char)*length));
 	
 	printf("The Base-%d form of %d is: ", b, a);
 	
 	dec2base(a,b,str);

 	return 0;
}




