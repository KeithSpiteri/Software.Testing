package persistant;

public class Bet {

	private int id;
	private String riskLevel;
	private float amount;
	private String userName;
	
	public void setId(int id) {
		this.id = id;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getRiskLevel()
	{
		return this.riskLevel;
	}
	
	public float getAmount()
	{
		return this.amount;
	}
	
	public String getUserName()
	{
		return this.userName;
	}

	
}
