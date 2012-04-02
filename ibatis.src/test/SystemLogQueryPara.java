package test;

import java.util.List;

public class SystemLogQueryPara {
	
 
    private static final long serialVersionUID = 1L;
    private String userID;
	private String userName;
	private String operTime;
	private String theModule;
	private String operObject;
	private String operObjectName;
	private String operation;
	private String operResult;
	private String userIP;
	private String detailInfo;
	private String operDevIp;
	private String beginTime;
	private String endTime;
	private List<String> userGroupList;



	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getTheModule() {
		return theModule;
	}

	public void setTheModule(String theModule) {
		this.theModule = theModule;
	}

	public String getOperObject() {
		return operObject;
	}

	public void setOperObject(String operObject) {
		this.operObject = operObject;
	}

	public String getOperObjectName() {
		return operObjectName;
	}

	public void setOperObjectName(String operObjectName) {
		this.operObjectName = operObjectName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperResult() {
		return operResult;
	}

	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getOperDevIp() {
		return operDevIp;
	}

	public void setOperDevIp(String operDevIp) {
		this.operDevIp = operDevIp;
	}

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<String> userGroupList) {
        this.userGroupList = userGroupList;
    }
    


    //开始行
    private int startRowNum;
    
    //结束行
    private int endRowNum;
    
    //取行数
    private int rowCount;
    
    //总的记录条数
    private int totalNum;

    public int getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public int getEndRowNum() {
        return endRowNum;
    }

    public void setEndRowNum(int endRowNum) {
        this.endRowNum = endRowNum;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

}
