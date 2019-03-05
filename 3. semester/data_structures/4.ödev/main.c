#include<stdio.h>
#include<stdlib.h>



typedef struct treeNode{

	int data;
	struct treeNode *child;
	struct treeNode *sibling;

}treeNode;

typedef struct linkedNode{

	struct treeNode *cur;
	struct linkedNode *next;

}linkedNode;

int rowNumber(FILE *ptr);
int getFirstDigit(FILE *ptr);//applicable only for this hw
int getSecondDigit(FILE *ptr);//applicable only for this hw
void addChild(treeNode *parent, int x);
treeNode* initTreeNode(int x);//returns a treeNode which has the value of x and both child and sibling is NULL
linkedNode* initLinkedNode();//returns a linkedNode which both cur and next is NULL
int findLeaves(linkedNode *head, treeNode *root);//returns the number of leaves for testing purposes
void deleteLinked(linkedNode *head);//deletes and frees linked node
void printTree(treeNode *root);//prints tree from given node pointer, with preorder
void printLinked(linkedNode *head);//Testing Purposes
treeNode* createTree(FILE *ptr1, FILE *ptr2);//creates tree and returns its root
treeNode* findParent(treeNode *parent, int x);//finds the parent of x from given root. Returns NULL if x is not in the tree
void deleteRoot(treeNode **root);//deletes root
void deleteMidTreeNode(treeNode *root, int x);//deletes a node which is not the root. Does nothing if x is not in the tree
void deleteTreeNode(treeNode **root, int x);//deletes tree node whichs data is x. If x is not in the list, does nothing
treeNode* findTreeNode(treeNode *root, int x);//find tree node whichs data is x. If x is not in the tree returns NULL
void list(treeNode *root, int x);//prints every nodes data starting from x, with preorder
void operate(treeNode **root, FILE *ptr);//does operations delete and list with respect to given file 





int main(){
	/*int i;									//Testing add child, constructed a random tree,printTree
	treeNode *root = initTreeNode(1);
	printf("a\n");
	treeNode *new;
	printf("%p\n",root->child);
	for(i = 2; i < 6; i++){
		//new = initTreeNode(i);
		addChild(root, i);
		
	}
	treeNode *cur = root->child;
	for(;cur;cur = cur->sibling){
		//new = initTreeNode(i++);	
		addChild(cur, i++);	
	
	}
	cur = root->child;
	//new = initTreeNode(i++);
	addChild(cur, i++);		
	printf("%p\n",root->child);
	printTree(root);
	printf("\n");
	linkedNode *leaves = initLinkedNode(root);
	findLeaves(leaves, root);
	printf("asasfdf\n");
	printLinked(leaves);*/
	
	
	/*FILE *fptr = fopen("Samples_HW4/Sample2/I2.txt","r");
	char ch;
	int x = 0;
	printf("%d\n",rowNumber(fptr));
	for(;!feof(fptr);){
		printf("%c", (ch = fgetc(fptr)));
		if(ch == '\n')
			x++;
		if(ch < 20){
			printf("\n*****%d--->%d",x,ch);
			printf("\n");
		}
	}*/

/*	char input1[50], input2[50];
	scanf("%s",input1);
	FILE *ptr1 = fopen(input1,"r"), *ptr2 = fopen(input1,"r");	//Testing constructing tree
	treeNode *root = createTree(ptr1,ptr2);
	//if(root == NULL)
		//printf("root is NULL\n");
	printTree(root);						//Testing for findParent
	treeNode* parent = findParent(root, 19);
	treeNode* child = parent->child;
	printf("\n");
	printf("%d ---->\t",parent->data);
	printTree(parent);
	printf("\n");	
	list(root, 19);					//Testing list
	//deleteTreeNode(&root, 3);		//Testing	deleteTreeNode
	deleteTreeNode(&root, 2);	
	deleteTreeNode(&root, 75);		
	printTree(root);
	printf("\n");*/
	//char com, node;
	//char input1[60], input2[60];
	//scanf("%s",input1);
	//scanf("%s",input2);
	char input1[60] = "I1.txt";	
	char input2[60] = "I2.txt";
	printf("aaaaaaaaaaaaa\n");
	FILE *ptr1 = fopen(input1,"r"), *ptr2 = fopen(input1,"r");	
	FILE *ptr3 = fopen(input2, "r");
	treeNode *root = createTree(ptr1,ptr2);
	char ch = 'x';
	while(!feof(ptr3)){
		ch = fgetc(ptr3);
		if(ch < 20)
			printf("ch is : %d\n",ch);
	}
	fseek(ptr3, 0, SEEK_SET);
	operate(&root, ptr3);
	printf("b\n");
	fclose(ptr1); fclose(ptr2); fclose(ptr3);
	
	return 0;
}


int rowNumber(FILE *ptr){
	char c = fgetc(ptr);
	int rowNum = 0;
	while(c != EOF){
		if(c == '\n'){
			rowNum++;			
			c = fgetc(ptr);
			if(c < 20){
				break;
			}
		}
		else{
			c = fgetc(ptr);
			if(c == EOF)
				rowNum++;
		}
	
	}
	fseek(ptr,0,SEEK_SET);
	return rowNum;

}


int getFirstDigit(FILE *ptr){
	int x,y;
	fscanf(ptr,"%d %d\n",&x,&y);
	return x;
}

int getSecondDigit(FILE *ptr){
	int x,y;
	fscanf(ptr,"%d %d\n",&x,&y);
	return y;

}

void addChild(treeNode *parent,int x ){
	treeNode *child = initTreeNode(x);
	if(parent->child == NULL){
		parent->child = child;
		return;
	}
	treeNode *head = parent->child;
	for(;head->sibling;head = head->sibling);
	head->sibling = child;
	return;
	

}

treeNode* initTreeNode(int x){

	treeNode *new = (treeNode *)malloc(sizeof(treeNode));
	new->data = x;
	new->child = NULL;
	new->sibling = NULL;
	return new;


}

linkedNode* initLinkedNode(){
	linkedNode *head = (linkedNode *)malloc(sizeof(linkedNode));
	head->cur = NULL;
	head->next = NULL;
	return head; 


} 

int findLeaves(linkedNode *head, treeNode *root){
	int n = 0;
	if(root->child == NULL){
		if(head->cur == NULL)
			head->cur = root;
		else{
			for(;head->next;head = head->next);
			linkedNode *new = initLinkedNode();
			new->cur = root;
			head->next= new;
		}
		return 1;
	}
	
	root = root->child;
	for(;root;root = root->sibling){

		n = n + findLeaves(head, root);	
	
	
	}

	return n;

}


void deleteLinked(linkedNode *head){
	linkedNode *cur = head, *after;
	if(cur == NULL)
		return;
	if(cur->next == NULL){
		free(cur);
		return;
	
	}
	after = cur->next;
	while(cur->next){
		free(cur);
		cur = after;
		after = after->next;
	}
	return;
}



void printLinked(linkedNode *head){
	for(;head; head = head->next ){
		printf("%d ",head->cur->data);
	}
	printf("\n");
	return;
}


void printTree(treeNode *root){
	
	if (root == NULL){
		return;
	}
	printf("%d,",root->data);
	root = root->child;
	for(;root; root = root->sibling){
		printTree(root);
	}

	return;

}


treeNode* createTree(FILE *ptr1, FILE *ptr2){
	int rowNum = rowNumber(ptr1);
	int sum = 0,first, second,i;
	linkedNode *leaves = initLinkedNode(), *mostLeftLeaf;
	rowNum--;
	treeNode* root = initTreeNode(getFirstDigit(ptr1));
	while(sum < rowNum){
		deleteLinked(leaves);
		leaves = initLinkedNode();
		findLeaves(leaves, root);
		mostLeftLeaf = leaves;
		second = getSecondDigit(ptr2);
		sum += second;
		if(sum > rowNum){
			second = rowNum - sum + second;
		}
		for(i = 0; i < second; i++){
			first = getFirstDigit(ptr1);
			addChild(leaves->cur, first);
			if(leaves->next == NULL)
				leaves = mostLeftLeaf;
			else
				leaves = leaves->next;
		}
	
	}
	return root;
}

treeNode* findParent(treeNode *parent, int x){
	treeNode *child = parent->child;
	treeNode *childParent;//sounds a little offensive heh?
	if(child == NULL)
		return NULL;
	for(;child;child = child->sibling){
		if(child->data == x){
			return parent;
		}				
	}
	child = parent->child;
	for(;child; child = child->sibling){
		childParent = findParent(child , x);
		if(childParent != NULL)
			return childParent;
	
	}

	return NULL;




}

void deleteRoot(treeNode **root){
	treeNode *mostLeftChild = (*root)->child;
	treeNode *grandchild;
	if(mostLeftChild->child == NULL){
		mostLeftChild->child = mostLeftChild->sibling;
		free(*root);
		*root = mostLeftChild;
		return;
	}
	grandchild = mostLeftChild->child;
	for(;grandchild->sibling; grandchild = grandchild->sibling);
	grandchild->sibling = mostLeftChild->sibling;
	free(*root);
	*root = mostLeftChild;
	return;		
}


void deleteMidTreeNode(treeNode *root, int x){

	treeNode *parent = findParent(root , x);
	if(parent == NULL)
		return;
	treeNode *child,*grandchild,*sibling;
	if(parent->child->data == x){
		child = parent->child;
		if(child->child == NULL){
			parent->child = child->sibling;
			free(child);
			return;
		}
		grandchild = child->child;
		for(;grandchild->sibling; grandchild = grandchild->sibling);
		grandchild->sibling = child->sibling;
		grandchild = child->child;
		parent->child = grandchild;
		free(child);
		return;		
	}
	child = parent->child;
	sibling = child->sibling;
	while(sibling->data != x){
		child = child->sibling;
		sibling = sibling->sibling;
	}
	if(sibling->child == NULL){
		child->sibling = sibling->sibling;
		free(sibling);
		return;
	
	}
	grandchild = sibling->child;
	for(;grandchild->sibling; grandchild = grandchild->sibling);
	grandchild->sibling = sibling->sibling;
	grandchild = sibling->child;
	child->sibling = grandchild;
	free(sibling);
	return;

}




void deleteTreeNode(treeNode **root, int x){
	
	if((*root)->data == x){
		deleteRoot(root);
		return;
	}
	else
		deleteMidTreeNode(*root, x);
	return;



}

treeNode* findTreeNode(treeNode *root, int x){

	if(root == NULL)
		return NULL;
	
	if( (root->data) == x )
		return root;
	
	treeNode *child = root->child;
	treeNode *wanted;
	for(;child;child = child->sibling){
		wanted = findTreeNode(child, x);
		if (wanted != NULL)
			return wanted;	
	
	}
	return NULL;

}

void list(treeNode *root, int x){
	printTree(findTreeNode(root, x));
	printf("\b \n");
	return;


}

void operate(treeNode **root, FILE *ptr){
	int rowNum = rowNumber(ptr);
	printf("rowNum is : %d\n",rowNum);
	char op;
	int node, i;
	
	for(i = 0; i < rowNum; i++){
		printf("%d\n",i);
		fscanf(ptr,"%c %d\r\n", &op, &node);
		printf("scanned %c\t%d\n",op,node);
		if(op == 'd'){
			deleteTreeNode(root, node);
			printf("deleted\n");		
		}
		if(op == 'l'){
			list(*root, node);
			printf("listed\n");
		}
	}

	return;

}










