#include<stdio.h>

int main(int argc, char *argv[]){
	FILE *myout = fopen("output.txt", "r");
	FILE *out;
	char x;
	char str[50] = "Samples_HW2/Sample1/Output.txt";
	char myc;
	char outc;
	x = argv[1][0];
	str[18] = x;
	out = fopen(str, "r");
	int count = 0;
	while(!feof(out))
		printf("%c", fgetc(out));
	printf("\n");
	printf("------------------------------------------------");
	printf("\n");
	while(!feof(myout))
		printf("%c", fgetc(myout));
	fseek(out,0,SEEK_SET);
	fseek(myout,0,SEEK_SET);	
	while(!feof(out)){
		myc = fgetc(myout);
		outc = fgetc(out);
		if(myc == '\0')
			myc = fgetc(myout);
		if(outc == '\n')
			outc = fgetc(out);
		if(myc != outc){
			printf("theese two text does not equal!!\n");
			printf("out: %c\tmyout: %c\tcount: %d\n",outc,myc,count);
			return 0;
		}
		count++;
	
	
	}
	
	printf("\nmy output is true, yay!!!!!\n");
	return 0;



}
