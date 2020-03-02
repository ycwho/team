package client;

public interface Protocol {
	
	String DISCONNECTION = "END";
	
	String CLIENT_WANNA_LOGIN = "client wants to login";
	
	int CLIENT_WANNA_SIGNUP = 2;
	
	int CLIENT_LOGIN_FAILED = 3;
	
	int CLIENT_LOGIN_SUCCESS = 4;
	
	int CLIENT_SIGNUP_FAILED = 5;
}
