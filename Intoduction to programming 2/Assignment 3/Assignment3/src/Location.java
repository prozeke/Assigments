
public class Location {
	double latitude;
	double longitude;

	Location(double latitude,double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude(){
		return this.latitude;		
	}

	public double getLongitude(){
		return this.longitude;
	}

}
