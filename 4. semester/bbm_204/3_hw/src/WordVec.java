
public class WordVec {
	private float[] vec;
	private int index;
	
	public WordVec(int index, float[] vec){
		this.vec = new float[vec.length];
		for(int i = 0; i < vec.length; i++)
			this.vec[i] = vec[i];
		this.index = index;
		
	}

	public int getIndex(){
		return this.index;
	}
	
	public float[] getVec(){
		return this.getVec();
		
	}
	
	public void setIndex(int i){
		this.index = i;
	}
	
	public void display(){
		System.out.println("index: " + this.index);
		for(float f : this.vec){
			System.out.printf("%f ", f);
		}
		System.out.println();
	}

	private static float vecLen(float[] v){
		float len = 0;
		for(float f : v)
			len = f*f + len;
		len = (float) Math.sqrt(len);
		return len;
	}
	
	public float cosSim(WordVec v){
		float[] v1 = this.vec;
		float[] v2 = v.vec;
		float len1 = vecLen(this.vec);
		float len2 = vecLen(v.vec);
		float sum = 0;
		for(int i = 0; i < v1.length; i++){
			sum = v1[i] * v2[i] + sum;
			
		}
		return sum /(len1 * len2);
		
	}


}


