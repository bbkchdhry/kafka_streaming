package org.ekbana.streaming.model;

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
    private String ClassDetailJSON;
    private String CalulationDetailJSON;
    private boolean HasMultipleAssets;
    private boolean IsIssued;
    private boolean IsDraft;
    private String BranchCode;

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

    public JSONObject getClassDetailJSON() {
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
        data.put("ClassDetailJSON", getClassDetailJSON());
        data.put("CalulationDetailJSON", getCalulationDetailJSON());
        data.put("HasMultipleAssets", HasMultipleAssets);
        data.put("IsIssued", IsIssued);
        data.put("IsDraft", IsDraft);
        data.put("BranchCode", BranchCode);
        return data;
    }
}