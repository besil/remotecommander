package beans;

public class CommandResult {
	String command;
	String stdout, stderr, stacktrace;
	int exitStatus;
	
	public CommandResult withStdout(String stdout) {
		this.stdout = stdout;
		return this;
	}
	
	public CommandResult withStderr(String stderr) {
		this.stderr = stderr;
		return this;
	}
	
	public CommandResult withStackTrace(String stackTrace) {
		this.stacktrace = stackTrace;
		return this;
	}
	
	public CommandResult withExitStatus(int status) {
		this.exitStatus = status;
		return this;
	}
	
	public CommandResult withCommand(String cmd) {
		this.command = cmd;
		return this;
	}
	
	public int getExitStatus() {
		return exitStatus;
	}public String getStacktrace() {
		return stacktrace;
	}public String getStderr() {
		return stderr;
	}public String getStdout() {
		return stdout;
	}public String getCommand() {
		return command;
	}
}
