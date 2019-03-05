#include<stdio.h>
#include<stdlib.h>

typedef struct{
	int row;
	int col;
	int value;

}term;

void getLoc(char **maze, char c, int size, int *loc);
int getMaze(FILE *ptr, char ***maze, term *chars, int size);
int getmSize(FILE *ptr);
int searchPath(char *path, char c);
int canE(char **maze, int *loc, char *path);
int canW(char **maze, int *loc, char *path);
int canS(char **maze, int *loc, char *path);
int canN(char **maze, int *loc, char *path);
void resetMaze(char **maze, term *chars);
char getdir(int *sloc, int *eloc);
void moveback(char *path, int *loc);
int move(char **maze, term *mazeChars,char *path, int *loc, int *eloc );
void displayMaze(char **maze, term *chars);//get rid of that after finished


int main(int argc, char *argv[]){
	
	FILE *ptr_txt;
	ptr_txt = fopen(argv[1],"r");
	char **maze;
	char c;
	term mazeChars[54];
	if (ptr_txt != NULL){
		while( (c = fgetc(ptr_txt)) != EOF){
			printf("%c",c);
		}
	
	}
	else
		printf("couldnt open the file\n");
	fseek(ptr_txt, 0, SEEK_SET);	
	int size = getmSize(ptr_txt);
	fseek(ptr_txt, 0, SEEK_SET);	
	getMaze(ptr_txt, &maze, mazeChars, size);
	fclose(ptr_txt);
	int row =0;
	int col = 0;
	printf("\n");
	for (row = 0; row < size+2; row++){
	//printf("%d\n",row);
		for ( col = 0; col < size+2; col++)
			printf("%c",(maze)[row][col]);
		printf("\n");
		}
		printf("\n");
	displayMaze(maze, mazeChars);
	
	/**int *loc = (int *)malloc(sizeof(int) * 2);	//test getloc start
	getLoc(maze, 'S', size, loc);
	printf("%d\n",size);
	printf("loc : (%d , %d)\n", loc[0], loc[1]);
	getLoc(maze, 'E', size, loc);
	printf("loc : (%d , %d)\n", loc[0], loc[1]);
	getLoc(maze, 'S', size, loc);	//end true
	
	for(row = 1; row < size+1; row++)		//test restartMaze start
		for(col = 1; col < size+1; col++)
			if(maze[row][col] != '1')
				maze[row][col] = '2';
	displayMaze(maze, mazeChars);
	resetMaze(maze, mazeChars);
	displayMaze(maze, mazeChars);			//end true
	
	char *path = malloc(sizeof(char) * ( 10 * 10 )); //test path & moveback
	getLoc(maze, 'S', size, loc);
	*(int *)path = 7;
	int co;
	path[4] = '!';
	path[5] = 'W';
	path[6] = 'N';
	path[7] = 'A';
	path[8] = 'N';
	path[9] = 'a';
	path[10] = 'N';
	path[11] = 'W';
	path[12] = '\0';
	printf("pathlen: %d\n", *(int *)path);
	printf("loc : (%d , %d)\n", loc[0], loc[1]);
	printf("%s\n", path + 5);
	moveback(path, loc);
	printf("%s\n",  path + 5);
	moveback(path, loc);
	printf("%s\n",  path + 5);
	moveback(path, loc);
	printf("%s\n",  path + 5);
	moveback(path, loc);
	printf("%s\n",  path + 5);
	moveback(path, loc);
	printf("%s\n",  path + 5);
	printf("loc: (%d , %d)\n", loc[0],loc[1]); //end true**/
	char *path = (char *)malloc( (sizeof(char) * (size * size) ) + sizeof(int) + sizeof(char) );
	*(int *)path = 0;
	path[( sizeof(int) + sizeof(char) -1 )] = '!';
	printf("%c\n", path[4]);
	int *loc = (int *) malloc(sizeof(int) * 2);
	int *eloc = (int *) malloc(sizeof(int) * 2);
	int sLoc[2];
	getLoc(maze, 'S', size, sLoc); //i will show that to display the path to console for salih :)
	getLoc(maze, 'S', size, loc);	
	getLoc(maze, 'E', size, eloc);
	maze[loc[0]][loc[1]] = '0';
	move(maze, mazeChars, path, loc, eloc );
	printf("%s\n", path + 5);
	printf("pathlen: %d\n",*(int *)path);
	displayMaze(maze, mazeChars);
	printf("path is %s\n",path+5);
	ptr_txt = fopen("path.txt", "w");
	if(ptr_txt != NULL){
		fprintf(ptr_txt, "Start ");
		int pathlen = *(int *)path;
		printf("pathlen : %d\n", pathlen);
		int pathloc;
		for(pathloc = 0; pathloc<pathlen; pathloc++ ){
			fprintf(ptr_txt, "%c", path[ 5 + pathloc]);
		}
		fprintf(ptr_txt, " Exit\n");
	
	}
	
	else
		printf("couldnt open the path.txt for writing purpose\n");
	fclose(ptr_txt);

	resetMaze(maze, mazeChars);
	maze[sLoc[0]][sLoc[1]] = 'S';
	displayMaze(maze, mazeChars);
	int charloc = 5;
	char pathChar;
	while( ( pathChar = path[charloc] ) != '\0'){
		
		if (pathChar == 'E'){
			if (maze[sLoc[0]][sLoc[1] + 1] == '*')
				maze[sLoc[0]][++sLoc[1]] = '^';// past this place second time
			else
				maze[sLoc[0]][++sLoc[1]] = '*';
		}
		
		if (pathChar == 'W'){
			if (maze[sLoc[0]][sLoc[1] - 1] == '*')
				maze[sLoc[0]][--sLoc[1]] = '^';// past this place second time
			else
				maze[sLoc[0]][--sLoc[1]] = '*';
		}		
		
		if (pathChar == 'S'){
			if (maze[sLoc[0] + 1][sLoc[1]] == '*')
				maze[++sLoc[0]][sLoc[1]] = '^';// past this place second time
			else
				maze[++sLoc[0]][sLoc[1]] = '*';
		}		
		
		if (pathChar == 'N'){
			if (maze[sLoc[0] - 1][sLoc[1]] == '*')
				maze[--sLoc[0]][sLoc[1]] = '^';// past this place second time
			else
				maze[--sLoc[0]][sLoc[1]] = '*';
		}		
	charloc++;
	}
	
	maze[sLoc[0]][sLoc[1]] = 'E';
	
	displayMaze(maze, mazeChars);
	
	return 0;
}

void displayMaze(char **maze, term *chars){
	int size = chars[0].row;
	int v;
	int col;
	int row;
	printf("\n");
	printf("size of matrix : %d\n", size);
	for (row = 0; row < size+2; row++){
		for ( col = 0; col < size+2; col++){
			printf("%c",maze[row][col]);
			}
		
		printf("\n");

	}
	printf("row: %d col: %d value: %d\n",chars[0].row, chars[0].col, chars[0].value);
	for( v = 1; v <= chars[0].value; v++){
		printf("row: %d col: %d value: %c\n", chars[v].row, chars[v].col, chars[v].value);
	}
		
	printf("\n");



}

int getmSize(FILE *ptr){
	
	int size = -1;
	if(ptr != NULL){
		char c;
		size = 0;
		while((c = fgetc(ptr) != '\n'))
			size++;
			
	
	}
	else
		printf("no file found\n");
	
	return size;
}


int getMaze(FILE *ptr, char ***maze, term *chars,int size){
	char c;
	int row;
	int col;
	chars[0].row = size;
	chars[0].col = size;
	chars[0].value = 0;
	*maze = (char**) malloc(sizeof(char *) * ( size + 2)  );
	for( row = 0; row < size+2 ; row++){
		(*maze)[row] = (char *) malloc(sizeof(char) * (size +2));
		for (col = 0; col < size+2; col++){
			if ((col == 0) || (col == size+1))
				(*maze)[row][col] = '1';
			else if((row == 0) || (row == size+1))
				(*maze)[row][col] = '1';
			else if((c = fgetc(ptr)) != EOF){
					if (c == '\n')
						c = fgetc(ptr);
					(*maze)[row][col] = c;
					if(c > 64){
						if( (c !='S') && c != 'E'){
						chars[0].value++;
						chars[chars[0].value].value = (int)c;
						chars[chars[0].value].row = row;
						chars[chars[0].value].col = col;
						}
					}
				}
				else
					printf("EOF reached \n");
		}
		
	}		

	for (row = 0; row < size+2; row++){
		for ( col = 0; col < size+2; col++)
			printf("%c",(*maze)[row][col]);
		printf("\n");

	}
	return 0;
}


int searchPath(char *path, char c){

	int len = *(int *)path;
	int pos;
	for(pos = 5 ; pos < len+5; pos++){
		if(path[pos] == c )
			return 1;
	
	}
	return 0;

}


int canE(char **maze, int *loc, char *path){
	char east = maze[loc[0]][loc[1] + 1];
	if((east == '0') || ( (east > 96) && (east < 123) ) || (east == 'E'))
		return 1;
	else if( (east >= 'A') && (east <= 'Z') )
		return searchPath(path, east + 32);
	return 0;
	}

int canW(char **maze, int *loc, char *path){
	char west = maze[loc[0]][loc[1] - 1];
	if( (west == '0') || ( (west > 96) && (west < 123)) || (west == 'E'))
		return 1;
	else if((west >= 'A') && (west <= 'Z'))
		return searchPath(path, west + 32);
	else
		return 0;
	}

int canS(char **maze, int *loc, char *path){
	char south = maze[loc[0] + 1][loc[1]];
	if( (south == '0') || ((south > 96) && (south < 123)) || (south == 'E'))
		return 1;
	else if((south >= 'A') && (south <= 'Z') )
		return searchPath(path, south + 32);
	return 0;
	}

int canN(char **maze, int *loc, char *path){
	char north = maze[loc[0] - 1][loc[1]];
	if((north == '0') || ((north > 96) && (north < 123)) || (north == 'E'))
		return 1;
	else if((north >= 'A') && (north <= 'Z'))
		return searchPath(path, north + 32);
	return 0;
	}


void resetMaze(char **maze, term *letters){
	int size = letters[0].row;
	int row;
	int col;
	for(row = 1; row < size+1; row++){
		for(col = 1; col < size+1; col++){
			if(maze[row][col] == '2')
				maze[row][col] = '0';
		}
	}
	int len = letters[0].value;
	int termNum;
	for(termNum = 1; termNum <= len; termNum++){
		row = letters[termNum].row;
		col = letters[termNum].col;
		maze[row][col] = (char)letters[termNum].value;
	}
	
}

int abs(int x){
	if(x < 0)
		return -x;
	else
		return x;

}

char getdir(int *sloc, int *eloc){
	int dir[2];
	dir[0] = eloc[0] - sloc[0]; 
	dir[1] = eloc[1] - sloc[1];
	if(abs(dir[0]) > abs(dir[1])){
		if (dir[0] >= 0)
			return 'S';
		else
			return 'N';
	} 
	else if(dir[1] >= 0)
		return 'E';
	else
		return 'W';

}

void getLoc(char **maze, char c, int size, int *loc){

	int row;
	int col;
	for(row = 0; row < size + 2; row++){
		for(col = 0; col < size + 2; col++){
			if(maze[row][col] == c){
				loc[0] = row;
				loc[1] = col;
				return;
			}
		
		}
	
	}
}

void moveback(char *path, int *loc){
	int *pathlen = (int *)path; 
	printf("pathlen: %d\n",(*pathlen));
	char cloc =  path[(*pathlen) + sizeof(int) ];
	if( (cloc != 'E') && (cloc != 'W') && (cloc != 'N') && (cloc != 'S') && (cloc != '!'))
		(*pathlen)--;
	char cmove = path[(*pathlen) + sizeof(int) ];
	path[(*pathlen) + 4] = '\0';
	(*pathlen)--;
	if(cmove == 'E')
		loc[1]--;
	else if(cmove == 'W')
		loc[1]++;
	else if(cmove == 'S')
		loc[0]--;
	else if(cmove == 'N')
		loc[0]++;
}
//dont forget to set S to 0
int move(char **maze, term *mazeChars,char *path, int *loc, int *eloc ){
	char cloc = maze[loc[0]][loc[1]];
	char dir = getdir(loc, eloc);
	int *pathlen = (int *)path;
	if(cloc == 'E'){
		printf("maze solved!!!\n");
		return 1;
		}
	if((*pathlen) < 0)
		return 0;
	if( (cloc >= 'a') && (cloc <= 'z') ){
		if(!searchPath(path, cloc)){
			path[(*pathlen) + 5] = cloc;
			(*pathlen)++;
			path[(*pathlen) + 5] = '\0';
			resetMaze(maze, mazeChars);
			
		}
		else{
			path[(*pathlen) + 5] = cloc;
			(*pathlen)++;
			path[(*pathlen) + 5] = '\0';			
		
		}
	}
	if( (cloc >= 'A') && (cloc <= 'Z') ){
		path[(*pathlen) + 5] = cloc;
		(*pathlen)++;
		path[(*pathlen) + 5] = '\0';
	}

	maze[loc[0]][loc[1]] = '2';
	int count = 0;
	while(count < 4){
		if(dir == 'E'){
			if(canE(maze, loc, path)){
				loc[1]++;
				path[(*pathlen) + 5] = 'E';
				(*pathlen)++;
				path[(*pathlen) + 5] = '\0';
				return move(maze, mazeChars, path, loc, eloc );
				}
			else
				dir = 'S';
		}
		if(dir == 'S'){
			if(canS(maze, loc, path)){
				loc[0]++;
				path[(*pathlen) + 5] = 'S';
				(*pathlen)++;
				path[(*pathlen) + 5] = '\0';
				return move(maze, mazeChars, path, loc, eloc );
				}
			else
				dir = 'W';
		}	
		
		if(dir == 'W'){
			if(canW(maze, loc, path)){
				loc[1]--;
				path[(*pathlen) + 5] = 'W';
				(*pathlen)++;
				path[(*pathlen) + 5] = '\0';
				return move(maze, mazeChars, path, loc, eloc );
				}
			else
				dir = 'N';
		}	
		
		if(dir == 'N'){
			if(canN(maze, loc, path)){
				loc[0]--;
				path[(*pathlen) + 5] = 'N';
				(*pathlen)++;
				path[(*pathlen) + 5] = '\0';
				return move(maze, mazeChars, path, loc, eloc );
				}
			else
				dir = 'E';
		}
					
		count++;
	}

	printf("%s\n",path + 5);
	moveback(path, loc);
	printf("moveback!!\n");
	printf("loc: (%d , %d)\n",loc[0],loc[1]);
	
	return move(maze, mazeChars, path, loc, eloc );

}




















