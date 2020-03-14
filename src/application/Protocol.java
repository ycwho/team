package application;

public interface Protocol {

	//todo check all protocols are used and called by both server and client
	//todo - what happens when trying to join a game that already exists

	// Misc Protocols
	String DISCONNECTION = "END";
	String CLIENT_NEED_RESENT_COMMAND = "illegal command, please resent command";

	// login and registration
	String CLIENT_SIGNUP = "register"; // "register [username] [password]"
	String[] CLIENT_SIGNUP_REPLY = {"register success" , "sign up failed because the username already exists", "sign up because reason 2"};
	//log username password
	String CLIENT_LOGIN = "log"; // "log [username] [password]"
	String CLIENT_LOGOUT = "logout";
	String[] CLIENT_LOGIN_REPLY = {"login success" , "log in failed because wrong username/password", "log in failed because reason 2"};
	String CLIENT_MISSING_LOGIN_INFORMATION = "Missing Username or Password";
	String USER_NAME_MISSING = "Username does not exist";
	String PASSWORD_INCORRECT = "Incorrect Password";

	// Notification Protocols
	String SERVER_NOTICE_OTHER_LOGIN = "otheruser_has_logged_in ";
	String SERVER_NOTICE_OTHER_LOGOUT = "otheruser_has_logged_out ";

	// Main Menu Protocols
	String CLIENT_CHECK_ONLINE_USER = "check"; // just Protocol
	String CLIENT_CHECK_ONLINE_USER_RESPONSE = "Other online users: ";
	String CLIENT_CHECK_GAME = "gamesList"; // just Protocol
	String CLIENT_CHECK_GAME_RESPONSE = "Games: ";
	String CLIENT_CREATE_GAME = "create"; // "create [playernumber] [gamename]"
	String[] CLIENT_CREATE_REPLY = {"create game successfully" , "game already exists", "create game failed for unknown reason"};
	String GAME_NOTICE_CREATE = "new_game_created";
	String CLIENT_JOIN_GAME = "join"; // "join [gamename]"
	String[] CLIENT_JOIN_REPLY = {"join game successfully" , "join game failed no game founded", "failed for unknown reason"};
	String LOAD_SETUP = "Load Setup"; // "Load Setup [Username] [SetupName]"
	String CLIENT_UPLOAD_SHIP_POSITIONS = "UploadShipPositions"; // "UploadShipPositions,[playerName],[setupName],s1p1 s1p2 s1p3 s1p4 s1p5-s2p1 s2p2 s2p3 s2p4-s3p1 s3p2 s3p3-s4p1 s4p2 s4p3-s5p1 s5p2"
	String[] CLIENT_UPLOAD_REPLY = {"upload successfully", "game is started" };

	// Ending game
	String GAME_NOTICE_END = "game_is_end";
	String CLIENT_QUIT_GAME = "quit";
	String[] CLIENT_QUIT_REPLY = {"quit game successfully" , "you are not in the game"}; 

	// Game initialise protocols
	String CLIENT_ATTACK = "attack";
	String[] CLIENT_ATTACK_REPLY = {"attack successfully", "it is not your turn", "attack illegal"};
	String PLAYER_NAME_REQUEST = "Request Player Names";
	String PLAYER_NAMES = "Player Names:";

	// Game Command Protocols
	String GAME_OVER = "Game over";
	String TURN = "Turn:";
	String GAME_START = "Game Start";
	String PLAYER_DEAD = "Player Dead";
	String SHIP_SUNK = "Sunk";
	String HIT = "Hit:";

	int[] SHIPS_LENGTH = {5, 4, 3, 3, 2};
	String [] SHIPS_NAME = {"A", "B" , "C", "D", "E"};
}
