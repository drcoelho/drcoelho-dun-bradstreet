package info.coelho.etl.service.extraction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonRootName(value = "activity")
public class ActivityJsonEntity {

    private String userName;
    private String websiteName;
    private String activityTypeDescription;

    private Date signedInTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getActivityTypeDescription() {
        return activityTypeDescription;
    }

    public void setActivityTypeDescription(String activityTypeDescription) {
        this.activityTypeDescription = activityTypeDescription;
    }

    public void setSignedInTime(Date signedInTime) {
        this.signedInTime = signedInTime;
    }

    @JsonIgnore
    public Object getSignedInTimeFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(signedInTime);
    }

}
