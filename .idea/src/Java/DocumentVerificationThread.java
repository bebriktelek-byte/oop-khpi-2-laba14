public class DocumentVerificationThread extends Thread {
    private final Document document;
    private final DocumentService service;
    private String logResult;

    public DocumentVerificationThread(Document document, DocumentService service, String threadName) {
        super(threadName);
        this.document = document;
        this.service = service;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Запущена проверка документа ID: " + document.getId());
        try {
            Thread.sleep(1200);
            boolean isValid = service.verifyContent(document);
            document.setVerified(isValid);
            Thread.sleep(300);
            logResult = "Документ '" + document.getTitle() + "' проверен. Статус верификации: " + isValid;
            System.out.println("[" + threadName + "] Завершено: " + logResult);
        } catch (InterruptedException e) {
            System.out.println("[" + threadName + "] Процесс проверки был прерван.");
            Thread.currentThread().interrupt();
        }
    }

    public String getLogResult() { return logResult; }
}