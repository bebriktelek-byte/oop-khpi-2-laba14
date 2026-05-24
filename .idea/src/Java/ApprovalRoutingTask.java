import java.util.List;

public class ApprovalRoutingTask implements Runnable {
    private final Document document;
    private final List<Employee> staff;
    private final DocumentService service;
    private List<ApprovalStep> generatedRoute;

    public ApprovalRoutingTask(Document document, List<Employee> staff, DocumentService service) {
        this.document = document;
        this.staff = staff;
        this.service = service;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Начало построения маршрута для документа: " + document.getTitle());
        try {
            Thread.sleep(800);
            generatedRoute = service.createRoute(document, staff);
            Thread.sleep(400);
            System.out.println("[" + threadName + "] ...Маршрут успешно построен.");
        } catch (InterruptedException e) {
            System.out.println("[" + threadName + "] Процесс маршрутизации прерван.");
            Thread.currentThread().interrupt();
        }
    }

    public List<ApprovalStep> getGeneratedRoute() { return generatedRoute; }
}