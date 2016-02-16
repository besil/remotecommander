package beans;

public class MessageResult extends CommandResult {
	private final String message;
	
	public MessageResult(String mex) {
		this.message = mex;
	}
	
	public String getMessage() {
		return message;
	}
}
