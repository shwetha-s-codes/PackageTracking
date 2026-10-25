package com.Sclera.PackageService.DTO.External;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PincodeApiResponse {
    private String Status;
    private List<PostOffice> PostOffice;

    public String getStatus() { return Status; }
    public void setStatus(String status) { this.Status = status; }
    public List<PostOffice> getPostOffice() { return PostOffice; }
    public void setPostOffice(List<PostOffice> postOffice) { this.PostOffice = postOffice; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PostOffice {
        private String Name;
        private String District;
        private String State;

        public String getName() { return Name; }
        public void setName(String name) { this.Name = name; }
        public String getDistrict() { return District; }
        public void setDistrict(String district) { this.District = district; }
        public String getState() { return State; }
        public void setState(String state) { this.State = state; }
    }
}