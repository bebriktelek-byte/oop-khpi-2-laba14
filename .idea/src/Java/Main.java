import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ГЛАВНЫЙ ПОТОК [" + Thread.currentThread().getName() + "] НАЧАЛ РАБОТУ ===");
        
        DocumentService documentService = new DocumentService();

        Document financialDoc = new Document("DOC-991", "Финансовый отчет за Q1", "Валидный текст документа объемом более десяти символов.");
        Document summaryDoc = new Document("DOC-992", "Сводка подразделений", "Краткие итоги.");

        List<Employee> corporateStaff = Arrays.asList(
                new Employee("Иван Иванов", "Бухгалтер"),
                new Employee("Петр Петров", "Директор"),
                new Employee("Анна Сидорова", "Секретарь")
        );

        DocumentVerificationThread verifyThread1 = new DocumentVerificationThread(financialDoc, documentService, "VerificationThread-1");
        DocumentVerificationThread verifyThread2 = new DocumentVerificationThread(summaryDoc, documentService, "VerificationThread-2");

        verifyThread1.start();
        verifyThread2.start();

        try {
            verifyThread1.join();
            verifyThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Верификация завершена. Запуск параллельной маршрутизации ---");

        ApprovalRoutingTask routingTask = new ApprovalRoutingTask(financialDoc, corporateStaff, documentService);
        Thread routingThread = new Thread(routingTask, "RoutingTaskThread");

        routingThread.start();

        try {
            routingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== ГЛАВНЫЙ ПОТОК ПОЛУЧИЛ ВСЕ ДАННЫЕ И ЗАВЕРШАЕТ СЦЕНАРИЙ ===");
        System.out.println("Лог проверки 1: " + verifyThread1.getLogResult());
        System.out.println("Лог проверки 2: " + verifyThread2.getLogResult());

        if (routingTask.getGeneratedRoute() != null && !routingTask.getGeneratedRoute().isEmpty()) {
            System.out.println("Сгенерированный маршрут согласования для '" + financialDoc.getTitle() + "':");
            for (ApprovalStep step : routingTask.getGeneratedRoute()) {
                System.out.println(" - " + step.getApprover().getName() + " (" + step.getApprover().getRole() + "): " + step.getStatus());
            }
        } else {
            System.out.println("Маршрут не был построен (документ не прошел верификацию).");
        }
    }
}