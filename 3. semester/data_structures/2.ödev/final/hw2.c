#include<stdio.h>
#include<stdlib.h>

typedef struct{

	int top;
	char *element;
	int maxSize;

}stack;

//when initializing a queue I have to increment the maxsize for once. Circular 	queue
typedef struct{

	int front;
	int rear;
	char *element;
	int maxSize;

}queue;

typedef struct{

	stack interrupt;
	queue process;
	stack history;

}computer;



int push(stack *s, char c);
char pop(stack *s);
int isEmptyStack(stack s);
int isFullStack(stack s);
int enqueue(queue *q, char c);
char dequeue(queue *q);
int isEmptyQueue(queue q);
int isFullQueue(queue q);
int addComputer(computer *c, int maxProcess, int maxInterrupt, int commentnum);
int addProcess(computer *c, char process);
int addInterrupt(computer *c, char interrupt);
int sendToServer(computer *c, computer *server);
int operate(computer *server);
int stackToString(stack s, char *c);



int main(int argc, char *argv[]){
	FILE *input1 = fopen(argv[1], "r");
	FILE *input2 = fopen(argv[2], "r");
	FILE *output = fopen(argv[3], "w");
	int computernum;
	int commentnum;
	int count;
	int count2;
	fscanf(input1,"%d\n", &computernum);
	fscanf(input2,"%d\n", &commentnum);
	computer *computers = (computer *)malloc(sizeof(computer) * computernum);
	char *maxHistory = (char *)malloc(sizeof(char) * commentnum);
	int maxProcess;
	int maxInterrupt;
	for(count = 0; count < computernum; count++){
		fscanf(input1,"%d %d\n", &maxProcess, &maxInterrupt);
		addComputer(computers + count, maxProcess, maxInterrupt, commentnum);

	
	}
	
	char one;
	int two;
	char three;
	for(count = 0; count < commentnum; count++){
		if(fscanf(input2,"%c %d %c\n", &one, &two, &three) != 3){
			operate(computers + computernum - 1);
			while(fgetc(input2) != '\n');
		}
		else if( one == 'A'){
			addProcess(computers + two - 1, three);
		}
		else if(one == 'I'){
			addInterrupt(computers + two - 1, three);
		
		}
		else if(one == 'S'){
			sendToServer(computers + two  -1, computers + computernum - 1);
		}
	}
	
	for(count = 0; count < computernum; count++){
		stackToString(computers[count].history, maxHistory);
		push( &(computers[count].history) , '\0');
		for(count2 = 0; count2 < computers[count].history.top; count2++){
			fprintf(output,"%c",  computers[count].history.element[count2] );fprintf(output," ");
			if(count2 == computers[count].history.top - 1){
				fseek(output, -1, SEEK_CUR);
				
			}
		}
		fprintf(output,"\n");
	
	}	
	fclose(output);
	fclose(input1);
	fclose(input2);
	
	return 0;
}





//you have to check whether queue/stack is empty/full before each pop/push operation
int push(stack *s, char c){
	s->top++;
	(s->element)[s->top] = c;
	return 1;


}


char pop(stack *s){
	char c = (s->element)[s->top];
	s->top--;
	return c;
}


int isEmptyStack(stack s){

	if(s.top == -1)
		return 1;
	else
		return 0;	

}

int isFullStack(stack s){
	if(s.top == s.maxSize - 1)
		return 1;
	else
		return 0;


}


int enqueue(queue *q, char c){
	q->rear = (++ (q->rear) ) % q->maxSize;
	(q->element)[q->rear] = c;
	return 1;

}


char dequeue(queue *q){

	q->front = (++ (q->front) ) % q->maxSize;
	char c = (q->element)[q->front];
	return c;

}



int isEmptyQueue(queue q){
	
	if( q.rear == q.front)
		return 1;
	else
		return 0;	
}


int isFullQueue(queue q){

	if( (q.rear + 1) % q.maxSize == q.front )
		return 1;
	else
		return 0;
}

int addComputer(computer *c, int maxProcess, int maxInterrupt, int commentnum){
	c->interrupt.top = -1;
	c->interrupt.maxSize = maxInterrupt;
	c->interrupt.element = (char *)malloc(sizeof(char) * maxInterrupt);
	c->process.front = 0;
	c->process.rear = 0;
	c->process.maxSize = maxProcess + 1;
	c->process.element = (char *)malloc(sizeof(char) * (maxProcess + 1));
	c->history.element = (char *)malloc(sizeof(char) * commentnum);
	c->history.top = -1;
	return 1;
}

int addProcess(computer *c, char cprocess){

	if(isFullQueue(c->process)){
		push( &(c->history), '1' );
		return 0;
	}
	
	else
		enqueue( &(c->process), cprocess );
	return 1;
}

int addInterrupt(computer *c, char cinterrupt){

	if( isFullStack(c->interrupt) ){
		push( &(c->history), '2' );
		return 0;
	}
	
	else
		push( &(c->interrupt), cinterrupt );
	return 1;


}

int sendToServer(computer *c, computer *server){
	char send;
	if( !isEmptyStack(c->interrupt)){
		send = pop( &(c->interrupt) );
		if(isFullQueue(server->process)){
			push( &(server->history), '1' );
			push( &(c->history), send);
			return 0;
		}
		push( &(c->history), send);
		enqueue( &(server->process), send);
		return 1;
	
	}else if(!isEmptyQueue(c->process)){
		send = dequeue( &(c->process) );
		if(isFullQueue(server->process)){
			push( &(server->history), '1' );
			push( &(c->history), send);
			return -1;
		}
		push( &(c->history), send);
		enqueue( &(server->process), send);
		return 2;	
	
	
	}else
		push(&(c->history), '3');
	return 3;
		
	

}

int stackToString(stack s, char *c){
	int count = 0;
	while(!isEmptyStack(s)){
		c[count] = pop(&s);
		count++;	
	
	}
		



}


int operate(computer *server){
	char operation;
	if(!isEmptyStack(server->interrupt)){
		operation = pop(&(server->interrupt));
		push(&(server->history),operation);
		return 1;
	}else if(!isEmptyQueue(server->process)){
		operation = dequeue(&(server->process));
		push(&(server->history),operation);
		return 2;		
	
	}else
		push(&(server->history),'3');
	return 0;


}














