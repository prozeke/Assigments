#include<stdio.h>
#include<math.h>
#include<stdlib.h>

double square(double x){

	return x*x;

}

int min(int x, int y){

	if(x<y)  
		return x ; 
	return y;


}

int max(int x, int y){
	if(x>y)  return x ; return y;

}


int swap(int *x, int *y ){
	int temp = *x;
	*x = *y;
	*y = temp;
	return 0;


}

int pairSort(int *l,int size){
	int i = 0;
	int j = 0;
	int first[2], second[2];
	size = size / 2;
	for(i = 0; i < size ; i++){
		for(j = size - 1; j > i; j--){
			first[0] = l[(j - 1) * 2]; first[1] = l[( (j - 1) * 2) + 1];
			second[0] = l[(j * 2)]; second[1] = l[(j * 2) + 1];
			if(min(second[1] , second[0]) > min(first[1] , first[0])){
				swap(l + (j - 1) * 2 , l + j * 2 );
				swap(l + (j - 1) * 2  + 1, l + j * 2 + 1);
			}
			if(min(second[1] , second[0]) == min(first[1] , first[0])){
				if(max(second[1] , second[0]) > max(first[1] , first[0])){
					swap(l + (j - 1) * 2 , l + j * 2 );
					swap(l + (j - 1) * 2  + 1, l + j * 2 + 1);
				}
			
			}
		
		}
	
	}
	


	return 0;
}



int main(int argc, char *argv[]){
	FILE *ptr = fopen(argv[1],"r");
	int *l = (int *)malloc(sizeof(int) * 40);
	int x;
	int count = 0;
	while(fscanf(ptr,"%d ",&x) != EOF){
		l[count++] = x;
		printf("%d ",x);
	}
	printf("\n");
	int i,j,sum;
	double len1,len2;
	//pairSort(l, 40);
	for(i = 0; i < 40; i++ )
		printf("%d ", l[i]);
	sum = 0;
	len1 = 0;
	len2 = 0;
	printf("\n");
	double res;
	int c = 0;
	for(i=0;i<20;i++){
		sum = sum + l[2*i]*l[2*i+1];
		len1 = len1 + square(l[2*i]);
		len2 = len2 + square(l[2*i+1]);
		printf("%d-%d\n", l[2*i],l[2*i + 1]);
		c++;;		
	}
	printf("\n\nc: %d\n\n",c);
	len1 =  sqrt(len1);
	len2 = sqrt(len2);
	res  = sum/(len1*len2);
	printf("len1 = %f, len2 = %f, sum = %d, res = %f\n",len1,len2,sum,res);
	return 0;
}
	
