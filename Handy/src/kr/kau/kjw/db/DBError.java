package kr.kau.kjw.db;

public abstract class DBError {
	
	private int	errorCode;
	
	public DBError( int errorCode ) {
	
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
	
		return errorCode;
	}
	
	public abstract String getErrorMessage();
}
