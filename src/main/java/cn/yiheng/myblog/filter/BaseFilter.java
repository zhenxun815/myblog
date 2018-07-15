package cn.yiheng.myblog.filter;

import javax.persistence.Transient;
import java.util.List;

public abstract class BaseFilter {
	
   @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;
    
    /**   
     * @Fields defaultName : TODO(默认的filter 名称)   
     */  
    public String defaultName="filter";
    
    /**   
     * @Fields defaultFormId : TODO(默认的listForm ID)   
     */  
    public String defaultFormId="listForm";
    
    public Integer eq_delFlag=1;
    public List<String> in_id;
    
    public String orderbyField=" id desc ";
    
    public String key;
    

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public String getDefaultFormId() {
		return defaultFormId;
	}

	public void setDefaultFormId(String defaultFormId) {
		this.defaultFormId = defaultFormId;
	}

	public Integer getEq_delFlag() {
		return eq_delFlag;
	}

	public void setEq_delFlag(Integer eq_delFlag) {
		this.eq_delFlag = eq_delFlag;
	}

	public List<String> getIn_id() {
		return in_id;
	}

	public void setIn_id(List<String> in_id) {
		this.in_id = in_id;
	}

	public String getOrderbyField() {
		return orderbyField;
	}

	public void setOrderbyField(String orderbyField) {
		this.orderbyField = orderbyField;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
	

}
