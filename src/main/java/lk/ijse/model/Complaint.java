package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Complaint {

    private String complaintId;
    private String userId;
    private String title;
    private String description;
    private String status;
    private String remarks;
    private String createdAt;
}
