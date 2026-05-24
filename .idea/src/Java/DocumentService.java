import java.util.ArrayList;
import java.util.List;

public class DocumentService {
    public boolean verifyContent(Document doc) {
        if (doc == null || doc.getContent() == null) {
            return false;
        }
        return !doc.getContent().trim().isEmpty() && doc.getContent().length() > 10;
    }

    public List<ApprovalStep> createRoute(Document doc, List<Employee> staff) {
        List<ApprovalStep> route = new ArrayList<>();
        if (doc == null || staff == null || !doc.isVerified()) {
            return route;
        }
        for (Employee employee : staff) {
            if (doc.getTitle().contains("Финансовый") && employee.getRole().equals("Бухгалтер")) {
                route.add(new ApprovalStep(employee, "Ожидает подписи"));
            } else if (employee.getRole().equals("Директор")) {
                route.add(new ApprovalStep(employee, "Ожидает финального утверждения"));
            }
        }
        return route;
    }
}