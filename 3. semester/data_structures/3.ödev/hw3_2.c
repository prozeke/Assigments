#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>
#include<math.h>

typedef struct nodeText{

	char *str;
	struct nodeText *next;

}nodeText;




typedef struct pairNode{
	char *str;
	int count;
	struct pairNode *next;


	
}pairNode;

typedef struct nestedNode{
	char *str;
	int count;
	struct pairNode *pairs;
	struct nestedNode *next;


}nestedNode;


//head of the filenodes will be initialized in main
typedef struct fileNode{
	struct nestedNode *words;
	struct nodeText *text;
	char *file;
	struct fileNode *next;

}fileNode;


typedef struct linkedFiles{

	struct fileNode *next;

}linkedFiles;


void readConsole(char *str);//reads console until \n an modifies given str. size of str must be higher than what is written to console. otherwise probably seg fault.
int convertText(char *fileName, nodeText **head);
int deleteText(nodeText **head);
int addToText(nodeText **head, char *str);
int printText(nodeText *head);//for testing purposes. Delete later
int sameStr(char *str1, char *str2);//returns 1 if true, else 0. two inputs must contain at least 1 letter. All inputs must end with \0.
int addToPairs(pairNode **head, char *pair);
void displayPairs(pairNode *head);//for Testimg purposes. Delete later.
int addPairNode(pairNode **head,pairNode *pair);
void deletePairs(pairNode **head);
int convertNested(nestedNode **head,nodeText *thead);
int addToNested(nestedNode **head,nodeText *word);
int addNestedNode(nestedNode **head,nestedNode *new);
int removeFromNested(nestedNode **head, char *str);
void  displayNested(nestedNode *head);
void showPairs(nestedNode *head);
void addToFiles(fileNode **head,char *fileName);
void displayFiles(fileNode *head);
fileNode* returnFileNode(fileNode *head, char *str);
void addWordToNested(fileNode *cur, char *word, int count );
void deleteNestedList(nestedNode *head);
void deleteFiles(fileNode *head);
int isInTenNode(nestedNode *head,char *str);
int nestedNodeCount(nestedNode *head, char *str);
double similarity(nestedNode *head1, nestedNode *head2);


/////MAIN STARTS !!!!!!!!!!!!!!!!!!!!/////////

int main(){

	/*nodeText *head = NULL;              //TEST NODETEXT
	convertText("textTest",&head);
	printText(head);
	deleteText(&head);*/
	
/*	pairNode *head = NULL;                 //TEST PAIRNODE
	char *c1,*c2,*c3;
	c1 = (char *)malloc(4);
	c2 = (char *)malloc(4);
	c3 = (char *)malloc(4);
	scanf("%s",c1);
	scanf("%s",c2);
	scanf("%s",c3);
	addToPairs(&head, c1);
	addToPairs(&head,c2);
	addToPairs(&head,c2);
	addToPairs(&head,c2);
	addToPairs(&head,c1);
	addToPairs(&head,c3);
	addToPairs(&head,c3);	
	addToPairs(&head,c3);		
	displayPairs(head);
	deletePairs(&head);*/
	
/*	nodeText *thead = NULL, *cur;                       //TEST NESTEDNODE
	convertText("d1.txt",&thead);
	printText(thead);
	nestedNode *head = NULL;
	cur = thead;
	printText(thead);
	for(;cur;cur = cur->next)
		addToNested(&head,cur);
	displayNested(head);
	//removeFromNested(&head, "on");
	printf("------------------------\n");
	nestedNode *new = (nestedNode *)malloc(sizeof(nestedNode));
	new->count = 8;
	new->str = NULL;
	new->pairs = NULL;
	new->next = NULL;
	//addNestedNode(&head,new);
	showPairs(head);*/
	






	char input[110],input1[10], input2[50], input3[50];
	int count;
	double sim;
	input1[0] ='!';
	input1[1] = '\0'; 
	fileNode *head = NULL;
	fileNode *cur,*sec;
	while(1){
		printf("enter your command:\n");
		readConsole(input);
		sscanf(input,"%s ",input1);
		if(sameStr(input1 , "-r")){
			sscanf(input,"%s %s",input1,input2);
			addToFiles(&head, input2);
		
		}
		else if(sameStr(input1 , "-a")){
			sscanf(input,"%s %s %d %s",input1,input3,&count,input2);
			cur = returnFileNode(head,input2);
			if(cur){
				addWordToNested(cur, input3, count );
			}
			else
				printf("no file named %s\n",input2);
			
		}
		else if(sameStr(input1 , "-d")){
			sscanf(input,"%s %s %s",input1,input3,input2);
			cur = returnFileNode(head,input2);
			if(cur){
				removeFromNested(&(cur->words),input3);
			}
			else
				printf("no file named %s\n",input2);
		}
		else if(sameStr(input1 , "-n2")){
			sscanf(input,"%s %s",input1,input2);
			cur = returnFileNode(head,input2);
			if(cur){
				printf("the first 3 pair of words of %s =\n",input2);
				showPairs(cur->words);
			}
			else
				printf("no file named %s\n",input2);
		
		}
		else if(sameStr(input1 , "-s")){
			printf("-s\n");
			sscanf(input,"%s %s %s",input1,input2,input3);
			cur = returnFileNode(head,input2);
			sec = returnFileNode(head,input3);
			sim = similarity(cur->words, sec->words);
			printf("Cosine Similarity of %s and %s = %f\n", input2, input3, sim);
			
			//displayNested(cur->words);
			//printf("is in nestedlist: %d\n",isInTenNode(cur->words,input3) );
			//printf("count = %d\n",nestedNodeCount(cur->words, input3));
			
		}
		else if(sameStr(input1 , "-q")){
			deleteFiles(head);
			break;
		
		}
		else
			printf("that is not a valid command\n");
	
	}
	
	
	
	return 0;
}

void readConsole(char *str){
	char c;
	scanf("%c",&c);
	int len = 0;
	while(1){
		if(c != '\n'){
			str[len] = c;
			len++;
			str[len] = '\0';
			scanf("%c",&c);
		}
		else
			return;
	
	}

}


int addToText(nodeText **head, char *str){
	nodeText *new = (nodeText *)malloc(sizeof(nodeText));
	new->next = NULL;
	new->str = str; 
	nodeText *current = *head;
	if(current == NULL){
		*head = new;
		return 1;
	}
	for(;current->next;current = current->next);
	current->next = new;
	return 2;



}

int convertText(char *fileName, nodeText **head){

	FILE *file = fopen(fileName, "r");
	int count = 0, i;
	char c=1,*str;
	
	while(c != EOF){
		count = 0;
		do{
		
			c = fgetc(file);
			count++;	
	
	
		}while(isalnum(c));
		if (count > 1){
			fseek(file,-count,SEEK_CUR);
			str = (char *)malloc(sizeof(char) * count);
			for(i = 0; i < count-1; i++)	
				str[i] = tolower( fgetc(file) );
			str[count - 1] = '\0';
			addToText(head, str);
		}

	}
	fclose(file);
	return 0;


}

int printText(nodeText *head){
	printf("LinkedText is:\n");
	nodeText *current = head;
	for(;current;current = current->next)
		printf("%s\t",current->str);
	printf("\n");
	return 0;
}

int deleteText(nodeText **head){
	nodeText* a= (*head) , *next;
	while(a!=NULL){
		next=a->next;
		free(a->str);
		free(a);
		a=next;
	}
	return 0;

}


int sameStr(char *str1, char *str2){

	int i = 0;
	while(str1[i] == str2[i]){
		if(str1[i] == '\0')
			return 1;
		i++;
	}

	return 0;
}


int addPairNode(pairNode **head,pairNode *pair){
	if(*head == NULL){
		printf("error in addPairNode func: head is NULL\n");
		return 0;
	
	}
	if(pair->count > (*head)->count){
	
		pair->next = *head;
		*head = pair;
		return 3;
	}
	
	pairNode *after = (*head)->next;
	pairNode *before = *head;
	while(after != NULL){
		if(pair->count > after->count){
			pair->next = after;
			before->next = pair;
			return 1;
		}
		
	before = after;
	after = after->next;
	}
	before->next = pair;
	pair->next = NULL;
	return 2;
}

int addToPairs(pairNode **head, char *pair){

	if(pair == NULL)
		return -1;
	if(*head == NULL){
		pairNode *new = (pairNode *)malloc(sizeof(pairNode));
		new->str = pair;
		new->next = NULL;
		new->count = 1;		
		*head = new;
		return 0;
	}
	if(sameStr((*head)->str, pair)){
		(*head)->count++;
		return 3;
	}
	
	pairNode *before = *head;
	pairNode *after = (*head)->next;
	for(;after;before = before->next, after = after->next){
		if(sameStr(after->str,pair)){
			after->count = after->count + 1;
			before->next = after->next;
			addPairNode(head,after);
			return 1;			
		
		}
			
	
	}
	pairNode *new = (pairNode *)malloc(sizeof(pairNode));
	new->str = pair;
	new->next = NULL;
	new->count = 1;
	before->next = new;
	return 2;
}



void displayPairs(pairNode *head){
	printf("pairs are:\n");
	pairNode *cur = head;
	for(;cur;cur = cur->next){
	
		printf("str: %s count: %d  -  ",cur->str, cur->count);
	}
	printf("\n");
}

void deletePairs(pairNode **head){
	pairNode *next,*cur;
	cur = *head;
	while(cur != NULL){
		next = cur->next;
		free(cur);
		cur = next;
		
	}
	return;

}


int addToNested(nestedNode **head,nodeText *word){
	if(*head == NULL){
		nestedNode *new = (nestedNode *)malloc(sizeof(nestedNode));
		new->next = NULL;
		new->count = 1;
		new->str = word->str;
		new->pairs = NULL;
		if(word->next){
			addToPairs(&(new->pairs),word->next->str);
		}
		*head = new;
		return 0;	
	}
	if(sameStr((*head)->str, word->str)){
		(*head)->count++;
		if(word->next)
			addToPairs(&((*head)->pairs),word->next->str);
		return 1;
	
	}
	
	nestedNode *cur = (*head)->next, *before = *head;
	for(;cur;before = before->next,cur = cur->next){
		if(sameStr(cur->str, word->str)){
			before->next = cur->next;
			cur->count++;
			if(word->next)
				addToPairs(&(cur->pairs),word->next->str);
			addNestedNode(head, cur);
			return 1;
		
		}
	}
		nestedNode *new = (nestedNode *)malloc(sizeof(nestedNode));
		new->next = NULL;
		new->str = word->str;
		new->count = 1;
		new->pairs = NULL;
		if(word->next)
			addToPairs(&(new->pairs),word->next->str);
		before->next = new;
		return 2;
		
		
	



}

int convertNested(nestedNode **head,nodeText *thead){
	nodeText *cur = thead;
	for(;cur;cur = cur->next)
		addToNested(head,cur);

	return 0;
}

int addNestedNode(nestedNode **head, nestedNode *new){
	if(*head == NULL){
		printf("error in addNestedNode func: head is NULL\n");
		return 0;
	
	}
	if(new->count > (*head)->count){
	
		new->next = *head;
		*head = new;
		return 3;
	}
	
	nestedNode *after = (*head)->next;
	nestedNode *before = *head;
	while(after != NULL){
		if(new->count > after->count){
			new->next = after;
			before->next = new;
			return 1;
		}
		
	before = after;
	after = after->next;
	}
	before->next = new;
	new->next = NULL;
	return 2;


}


void displayNested(nestedNode *head){
	nestedNode *cur = head;
	for(;cur;cur = cur->next){
		printf("word : %s  count : %d\n",cur->str,cur->count);
		displayPairs(cur->pairs);
		printf("\n\n\n");
	}
	printf("\n");

}

int removeFromNested(nestedNode **head, char *str){

	if(*head == NULL){
		printf("this nestedList is empty\n");
		return -1;
	
	
	}
	nestedNode *after = (*head)->next;
	nestedNode *before = *head;
	if(sameStr((*head)->str, str)){
		deletePairs( &( (*head)->pairs ));
		free(*head);
		*head = after;
		return 0;
	}

	for(;after;after = after->next,before = before->next){
		if(sameStr(after->str, str)){
			before->next = after->next;
			deletePairs( &( after->pairs ));
			free(after);
			return 1;
		}
	
	}
	printf("There is no string as %s in nestedList\n",str);
	return -2;

}


void showPairs(nestedNode *head){
	int i;
	pairNode *cur;
	nestedNode *max = NULL, *secMax = NULL, *thirdMax = NULL;
	nestedNode *node = head;
	if(!head){
		printf("Error in showPairs: nestedList is NULL\n");
		return;
	}
	max = (nestedNode *)malloc(sizeof(nestedNode));
	secMax = (nestedNode *)malloc(sizeof(nestedNode));
	thirdMax = (nestedNode *)malloc(sizeof(nestedNode));
	max->next = secMax;
	max->count = 0;
	max->str = NULL; 
	max->pairs = NULL;
	secMax->next = thirdMax;
	secMax->count = 0;
	secMax->str = NULL;
	secMax->pairs = NULL;
	thirdMax->next = NULL;
	thirdMax->count = 0;
	thirdMax->str = NULL;
	thirdMax->pairs = NULL;
	while(node){
		if(node->count < thirdMax->count)
			break;
		cur = node->pairs;
		for(i = 0; i < 3; i++){
			if(cur){
				if(cur->count >= thirdMax->count){
					thirdMax->str = node->str;
					thirdMax->count = cur->count;
					thirdMax->pairs = cur;
				
				}
				if(cur->count >= secMax->count){
					thirdMax->str = secMax->str;
					thirdMax->count = secMax->count;
					thirdMax->pairs = secMax->pairs;
					secMax->str = node->str;
					secMax->count = cur->count;
					secMax->pairs = cur;
				
				}
				if(cur->count >= max->count){
					secMax->str = max->str;
					secMax->count = max->count;
					secMax->pairs = max->pairs;
					max->str = node->str;
					max->count = cur->count;
					max->pairs = cur;
				
				}								
				
				cur = cur->next;
			
			}
			else
				i = 3;
		}
		
	node = node->next;
	
	}
	

	//printf("%s-%s, %s-%s, %s-%s\n",max->str,max->pairs->str,secMax->str,secMax->pairs->str,thirdMax->str,thirdMax->pairs->str);
	printf(" %s - %s (%d)\n",max->str,max->pairs->str,max->count);
	printf(" %s - %s (%d)\n",secMax->str,secMax->pairs->str,secMax->count);
	printf(" %s - %s (%d)\n",thirdMax->str,thirdMax->pairs->str,thirdMax->count);
	free(max);
	free(secMax);
	free(thirdMax);
}


void addToFiles(fileNode **head,char *fileName){

	fileNode *new = (fileNode *)malloc(sizeof(fileNode));
	nodeText *textHead = NULL;
	nestedNode *nestedHead = NULL;
	int i = 0,j = 0, c = 0;
	while(fileName[c] != '\0'){
		c++;
		i++;
		if(fileName[c] == '/'){
			j++;
			i = 0;
		}
	}
	c = 0;
	while(j){
		if(fileName[c] == '/')
			j--;
		c++;
	
	}
	char *file = (char *)malloc(sizeof(char) * (i + 1) );
	for(j = 0; j < i ; j++){
		file[j] = fileName[c+j];
	}
	file[j] = '\0';
	convertText(fileName, &textHead);
	convertNested(&nestedHead, textHead);
	new->next = NULL;
	new->file = file;
	new->words = nestedHead;
	new->text = textHead;
	
	if(*head == NULL){
		*head = new;
		return;
	}
	new->next = *head;
	*head = new;
	return;


}


void displayFiles(fileNode *head){
	fileNode *cur = head;
	for(;cur;cur = cur->next){
	
		displayNested(cur->words);
	
	}
	printf("\n");

}

fileNode* returnFileNode(fileNode *head, char *str){
	fileNode *cur = head;
	for(;cur;cur = cur->next){
		if(sameStr( (cur->file), str)){
			return cur;
		}
	
	
	}
	printf("no file named %s\n",str);
	return NULL;

}


void addWordToNested(fileNode *cur, char *word, int count ){
	int i = 0, c;
	while(word[i] != '\0')
		i++;
	i++;
	char *new = (char *)malloc(sizeof(char) * i);
	for(c = 0; c < i - 1; c++)
		new[c] = word[c];
	new[c] = '\0';
	nestedNode *nested = cur->words;
	for(;nested;nested = nested->next){
		if(sameStr(nested->str , new)){
			(nested->count) += count ;
			free(new);
			return;
		
		}
	}
	nestedNode *node = (nestedNode *)malloc(sizeof(nestedNode)); 
	node->count = count;
	node->str = new;
	node->pairs = NULL;
	node->next = NULL;
	addNestedNode(&(cur->words),node);
	addToText(&(cur->text), new);
	return;
}


void deleteNestedList(nestedNode *head){
	if(!head){
		return;
	}
	nestedNode *a = head, *next;
	while(a){
		next = a->next;
		deletePairs( &(a->pairs));
		free(a);
		a = next;
	}
	
	return;

}

void deleteFiles(fileNode *head){
	fileNode *a = head,*next;
	if(!a)
		return;
	while(a){
		next = a->next;
		deleteNestedList(a->words);
		deleteText( &(a->text) );
		free(a);
		a = next;
	
	}
	

	return;

}

int isInTenNode(nestedNode *head,char *str){
	nestedNode *cur = head;
	int i = 0;
	while(cur && i < 10){
		if (sameStr(str, cur->str)){
			return 1;
		}
		cur = cur->next;
		i++;
	
	}

	return 0;

}

int nestedNodeCount(nestedNode *head, char *str){
	nestedNode *cur = head;
	while(cur){
		if(sameStr(cur->str , str))
			return cur->count;
		cur = cur->next;
	
	}

	return 0;



}

double similarity(nestedNode *head1, nestedNode *head2){
	int i,count;
	nestedNode *cur1 = head1, *cur2 = head2;
	int vectors[20][2];
	for(i = 0; i<20; i++){
		vectors[i][0] = 0;
		vectors[i][1] = 0;
	}
	i = 0;
	while(cur1 && i < 10){
		count = nestedNodeCount(head2,cur1->str );
		vectors[i][0] = cur1->count;
		vectors[i][1] = count;
		cur1 = cur1->next;
		i++;
	
	}
	i = 0;
	while(cur2 && i < 10){
		if(!isInTenNode(head1,cur2->str)){
			count = nestedNodeCount(head1,cur2->str );
			vectors[i + 10][1] = cur2->count;
			vectors[i + 10][0] = count;
		}
		cur2 = cur2->next;
		i++;
	
	}
	//for(i = 0; i < 20; i++){
		//printf("*%d\t%d\n",vectors[i][0], vectors[i][1]);
	//}
	double dotsum = 0, lenproduct = 0;
	int len1 = 0,len2 = 0; 
	for( i = 0; i < 20; i++){
			dotsum = dotsum + (vectors[i][0] * vectors[i][1]);
			len1 = len1 + vectors[i][0] *  vectors[i][0];
			len2 = len2 + vectors[i][1] *  vectors[i][1];
	}
	
	//len1 = sqrt(len1);
	//len2 = sqrt(len2);
	lenproduct = sqrt(len1) * sqrt(len2);
	double result;
	result = dotsum/lenproduct;
	
	return result;	

}






















