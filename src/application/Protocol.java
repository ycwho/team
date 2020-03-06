package application;

public interface Protocol {
	
	String DISCONNECTION = "END";
	
	String CLIENT_NEED_RESENT_COMMAND = "illeagl command, please resent command";
	

	
	String CLIENT_SIGNUP = "sign";
	
	String[] CLIENT_SIGNUP_REPLY = {"sign up success" , "sign up failed because the username already exists", "sign up because reason 2"};
	
	//log username password
	String CLIENT_LOGIN = "login";
	
	String[] CLIENT_LOGIN_REPLY = {"login success" , "log in failed because wrong username/password", "log in failed because reason 2"};
	
	String SERVER_NOTICE_OTHER_LOGIN = "otheruser_is_login ";
	
	String SERVER_NOTICE_OTHER_LOGOUT = "otheruser_is_logout";
	
	
	String CLIENT_CHECK_ONLINE_USER = "check";
	
	
	String CLIENT_CREATE_GAME = "create";
	
	String[] CLIENT_CREATE_REPLY = {"create game successfully" , "already has the game", "create game for unknown reason"};
	
	String GAME_NOTICE_CREATE = "new_game_created";
	
	String GAME_NOTICE_END = "game_is_end";
	
	String CLIENT_JOIN_GAME = "join";
	
	String[] CLIENT_JOIN_REPLY = {"join game successfully" , "join game failed no game founded", "failed for unknown reason"};
	
	String CLIENT_QUIT_GAME = "quit"; 
	
	String[] CLIENT_QUIT_REPLY = {"quit game successfully" , "you are not in the game"}; 
	
	String CLIENT_UPLOAD_SHIP_POSITIONS = "upload";
	
	String[] CLIENT_UPLOAD_REPLY = {"upload successfully", "game is started" };
	
	String CLIENT_ATTACK = "attack";
	
	String[] CLIENT_ATTACK_REPLY = {"attack successfully", "it is not your turn", "attack illegal"};
	
	

}
