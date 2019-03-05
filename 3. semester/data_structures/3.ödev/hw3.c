#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>

typedef struct nodetext{

	char *str;
	struct nodetext *next;

}nodetext;


typedef struct pairnode{
	char *str;
	int count;
	struct nestednode *next;


	
}pairnode;

typedef struct nestednode{
	char *str;
	int count;
	struct pairnode *pairs;
	struct nestednode *next;


}nestednode;


//head of the filenodes will be initialized in main
typedef struct filenode{
	struct nestednode *words;
	char *file;//maybe string?
	struct filenode *next;

}filenode;


typedef struct linkedFiles{

	struct fileNode *next;

}linkedFiles;

int addToTextLinked(nodetext *textHead, char *str);
int readWords(char *fileName, nodetext *textHead);
int deleteLinkedText(nodetext *textHead);
int addToNestedNode(nodetext *current, nestednode *head);
int sameStr(char *str1, char *str2);//if false returns 0, else 1
int changeNested(nestednode *head,nestednode *new, nestednode *old);//sorted
nestednode *headOfNested(filenode *head, char *file);//returns the head of the files words
int pairs(nestednode head);//prints most common three pairs starting from head 
int deleteNode(nestednode *head, char *str);//deletes node whichs string is equal to str.dont forget to delete pairnodes and strings
int sim(nestednode *first, nestednode *second);
int makelist(nestednode *first, nestednode *second, int *l);//makes a list from 2 nested list.List will 40 elements in it. even numbers in first half will be first's first 10 words count. even numbers in second half will be second 10 words count. can create a node like structure for holding char?

int listToVector(int *l);//for similarity check




int main(){

	nodetext textHead1;
	nodetext *textHead = &textHead1;
	textHead->next = NULL;
	readWords("textTest",textHead);
	while(textHead != NULL){
		printf("%s\t",textHead->str);
		textHead = textHead->next;
	}


	return 0;
}





int addToTextLinked(nodetext *textHead, char *str){
	nodetext *new = (nodetext *)malloc(sizeof(nodetext));
	new->next = NULL;
	new->str = str;
	nodetext *current = textHead;
	if(textHead == NULL){
		textHead = new;
		return 1;	
		
	}
	
	for(;current->next;current = current->next);
	current->next = new;
	return 1;


}

int readWords(char *fileName, nodetext *textHead){

	FILE *file = fopen(fileName,"r");
	int count = 0,i;
	char c;
	char *s;
	while(c != EOF){
		count = 0;
		do{
			c = fgetc(file);
			count++;
			
		}
		while(isalnum(c));
		printf("count : %d\tc = %c\n",count,c);
		if(count > 1){
			s = (char *)malloc( count * sizeof(char));
			fseek(file, -(count),SEEK_CUR);
			for(i = 0; i < count - 1; i++){
					s[i] = fgetc(file);
			}
			s[count - 1] = '\0';
			addToTextLinked(textHead,s);
			
		}	

	return 1;


	}
}















