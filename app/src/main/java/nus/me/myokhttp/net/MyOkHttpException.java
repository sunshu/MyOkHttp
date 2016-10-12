package nus.me.myokhttp.net;

public class MyOkHttpException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 异常码
	 */
	private int errorCode = ExceptionStatusCode.STATUS_UNKNOWN;
	
	public MyOkHttpException() {
		super();
	}

	public MyOkHttpException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public MyOkHttpException(int errorCode, String detailMessage) {
		super(detailMessage);
		this.errorCode = errorCode;
	}

	public MyOkHttpException(int errorCode, Throwable throwable) {
		super(throwable);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		String msg = super.toString();
		String codeInfo = "";
		switch(errorCode) {
		case ExceptionStatusCode.STATUS_PARSER_RESULT_ERROR:
			codeInfo = "解析结果失败";
			break;
		case ExceptionStatusCode.STATUS_REQUEST_FAILED:
			codeInfo = "请求失败";
			break;
		case ExceptionStatusCode.STATUS_REQUEST_IO_ERROR:
			codeInfo = "请求IO异常导致失败";
			break;
		case ExceptionStatusCode.STATUS_RESULT_EXIST_ERRORINFO:
			codeInfo = "请求结果中存在服务端错误信息";
			break;
		case ExceptionStatusCode.STATUS_RESULT_FORMAT_ERROR:
			codeInfo = "请求结果没有安装指定格式进行包装";
			break;
		default :
			break;
		}
		return "errorCode:" + codeInfo + "\n" + msg;
	}
	
}