public class ApprovalStep {
    private final Employee approver;
    private final String status;

    public ApprovalStep(Employee approver, String status) {
        this.approver = approver;
        this.status = status;
    }

    public Employee getApprover() { return approver; }
    public String getStatus() { return status; }
}