
public class Computer extends Electronic {
	private String opSystem;
	private String cpuType;
	private String ram;
	private String hdd;
	public Computer(){}
	public Computer(double cost,String manufacturer,String brand,String maxVoltage,String maxPowConsumption,
			String opSystem,String cpuType,String ram, String hdd){
		super(cost, manufacturer, brand, maxVoltage, maxPowConsumption );
		this.opSystem = opSystem;
		this.cpuType = cpuType;
		this.ram = ram;
		this.hdd = hdd;
	}
	public void displayItem(){
		super.displayItem();
		String op = this.opSystem;
		String cpu= this.cpuType;
		String ram = this.ram;
		String hdd = this.hdd;
		System.out.printf("Operating System: %s\nCPU Type: %s\nRAM Capacity: %s GB.\nHDD Capacity: %s GB.\n", op,cpu,ram,hdd);
	
	}
	}
