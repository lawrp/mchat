package mchat;

public abstract class User {
	protected IChatMediator mediator;
	protected String name;
	
	public User(IChatMediator med, String name){
		this.mediator=med;
		this.name=name;
	}
	
	public abstract void send(String msg);
	
	public abstract void receive(String msg);
}
