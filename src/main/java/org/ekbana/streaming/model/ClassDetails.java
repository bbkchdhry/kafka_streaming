package org.ekbana.streaming.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

public class ClassDetails {
    private String Id;
    private String CreatedBy;
    private String UpdatedBy;
    private long CreatedDate;
    private long UpdatedDate;
    private boolean IsDeleted;
    private String PolicyNo;
    private String DocumentNo;
    private String PolicyIssuanceId;
    private String ClassId;
    private String PortfolioId;
//    @JsonProperty
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ClassDetailJSON;
//    @JsonProperty
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String CalulationDetailJSON;
    private boolean HasMultipleAssets;
    private boolean IsIssued;
    private boolean IsDraft;
    private String BranchCode;

    public ClassDetails(String str_data){
        JSONObject data = new JSONObject(str_data);
        if(data.has("Id")) this.setId(data.getString("Id"));
        if(data.has("CreatedBy")) this.setCreatedBy(data.getString("CreatedBy"));
        if(data.has("UpdatedBy")) this.setUpdatedBy(data.getString("UpdatedBy"));
        if(data.has("CreatedDate")) this.setCreatedDate(data.getLong("CreatedDate"));
        if(data.has("UpdatedDate")) this.setUpdatedDate(data.getLong("UpdatedDate"));
        if(data.has("IsDeleted")) this.setDeleted(data.getBoolean("IsDeleted"));
        if(data.has("PolicyNo")) this.setPolicyNo(data.getString("PolicyNo"));
        if(data.has("DocumentNo")) this.setDocumentNo(data.getString("DocumentNo"));
        if(data.has("PolicyIssuanceId")) this.setPolicyIssuanceId(data.getString("PolicyIssuanceId"));
        if(data.has("ClassId")) this.setClassId(data.getString("ClassId"));
        if(data.has("PortfolioId")) this.setPortfolioId(data.getString("PortfolioId"));
        if(data.has("ClassDetailJSON")) this.setClassDetailJSON(data.get("ClassDetailJSON").toString());
        if(data.has("CalulationDetailJSON")) this.setCalulationDetailJSON(data.get("CalulationDetailJSON").toString());
        if(data.has("HasMultipleAssests")) this.setHasMultipleAssets(data.getBoolean("HasMultipleAssests"));
        if(data.has("IsIssued")) this.setIssued(data.getBoolean("IsIssued"));
        if(data.has("IsDraft")) this.setDraft(data.getBoolean("IsDraft"));
        if(data.has("BranchCode")) this.setBranchCode(data.getString("BranchCode"));
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public long getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(long createdDate) {
        CreatedDate = createdDate;
    }

    public long getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        UpdatedDate = updatedDate;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getPolicyNo() {
        return PolicyNo;
    }

    public void setPolicyNo(String policyNo) {
        PolicyNo = policyNo;
    }

    public String getDocumentNo() {
        return DocumentNo;
    }

    public void setDocumentNo(String documentNo) {
        DocumentNo = documentNo;
    }

    public String getPolicyIssuanceId() {
        return PolicyIssuanceId;
    }

    public void setPolicyIssuanceId(String policyIssuanceId) {
        PolicyIssuanceId = policyIssuanceId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getPortfolioId() {
        return PortfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        PortfolioId = portfolioId;
    }

//    @JsonIgnore
    public String getClassDetailJSON(){
        return ClassDetailJSON;
    }

    @JsonProperty("classDetailJSON")
//    @JsonProperty("filteredClassDetailJSON")
    public JSONObject getFilteredClassDetailJSON() {
        return new JSONObject(ClassDetailJSON);
    }

    public void setClassDetailJSON(String classDetailJSON) {
        ClassDetailJSON = classDetailJSON;
    }

    public JSONObject getCalulationDetailJSON() {
        return new JSONObject(CalulationDetailJSON);
    }

    public void setCalulationDetailJSON(String calulationDetailJSON) {
        CalulationDetailJSON = calulationDetailJSON;
    }

    public boolean isHasMultipleAssets() {
        return HasMultipleAssets;
    }

    public void setHasMultipleAssets(boolean hasMultipleAssets) {
        HasMultipleAssets = hasMultipleAssets;
    }

    public boolean isIssued() {
        return IsIssued;
    }

    public void setIssued(boolean issued) {
        IsIssued = issued;
    }

    public boolean isDraft() {
        return IsDraft;
    }

    public void setDraft(boolean draft) {
        IsDraft = draft;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    @JsonIgnore
    public JSONObject getAllData(){
        JSONObject data = new JSONObject();
        data.put("Id", Id);
        data.put("CreatedBy", CreatedBy);
        data.put("UpdatedBy", UpdatedBy);
        data.put("CreatedDate", CreatedDate);
        data.put("UpdatedDate", UpdatedDate);
        data.put("IsDeleted", IsDeleted);
        data.put("PolicyNo", PolicyNo);
        data.put("DocumentNo", DocumentNo);
        data.put("PolicyIssuanceId", PolicyIssuanceId);
        data.put("ClassId", ClassId);
        data.put("PortfolioId", PortfolioId);
        data.put("ClassDetailJSON", getFilteredClassDetailJSON());
        data.put("CalulationDetailJSON", getCalulationDetailJSON());
        data.put("HasMultipleAssets", HasMultipleAssets);
        data.put("IsIssued", IsIssued);
        data.put("IsDraft", IsDraft);
        data.put("BranchCode", BranchCode);
        return data;
    }
}