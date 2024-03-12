package com.nick24.testhonorm;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class CrptApi {
    private int requestLimit;
    private TimeUnit timeUnit;
    private AtomicLong timeLimitInMillis;
    private static final String POST_URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String client = "theOnlyOneClient";

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
    }

    public static void main(String[] args) {
        Product product = new Product("string", "string", "string", "string", "string", "string", "string", "string", "string");
        Product[] products = {product};
        Description description = new Description("stringExample");
        Document document = new Document(description, "string", "string", "string", true, "string", "string", "string", "string", "string", products, "string", "string");
        TimeUnit testTimeUnit = TimeUnit.MINUTES;
        int testRequestLimit = 10;
        CrptApi crptApi = new CrptApi(testTimeUnit, testRequestLimit);
        try {
            System.out.println((crptApi.createDoc(document, "signExample")).get());
        } catch (Exception e) {

        }
    }

    public Optional<String> createDoc(Object document, String sign) throws IOException {
        String jsonDocument = mapper.writeValueAsString(document);
        URL url = new URL(POST_URL);
        timeLimitInMillis = new AtomicLong(TimeUnit.MILLISECONDS.convert(1, timeUnit));
        RateLimiter rateLimiter = new RateLimiter(requestLimit, timeLimitInMillis.get());
        if (rateLimiter.isAllowed(client)) {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] jsonDocumentBytes = jsonDocument.getBytes("utf-8");
                os.write(jsonDocumentBytes, 0, jsonDocumentBytes.length);
            }
            return Optional.of("String:\n" + jsonDocument + "\n was sent");
        } else {
            return Optional.of("The number of the requests has exceeded the limit, please wait");
        }
    }

    public static class Document {
        private Description description;
        private String docId;
        private String docStatus;
        private String docType;
        private boolean importRequest;
        private String ownerInn;
        private String participantInn;
        private String producerInn;
        private String productionDate;
        private String productionType;
        private Product[] products;
        private String regDate;
        private String regNumber;

        public Document(Description description, String docId, String docStatus, String docType, boolean importRequest, String ownerInn, String participantInn, String producerInn, String productionDate, String productionType, Product[] products, String regDate, String regNumber) {
            this.description = description;
            this.docId = docId;
            this.docStatus = docStatus;
            this.docType = docType;
            this.importRequest = importRequest;
            this.ownerInn = ownerInn;
            this.participantInn = participantInn;
            this.producerInn = producerInn;
            this.productionDate = productionDate;
            this.productionType = productionType;
            this.products = products;
            this.regDate = regDate;
            this.regNumber = regNumber;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        @JsonGetter("doc_id")
        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        @JsonGetter("doc_status")
        public String getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        @JsonGetter("doc_type")
        public String getDocType() {
            return docType;
        }

        public void setDoc_Type(String docType) {
            this.docType = docType;
        }

        @JsonGetter("importRequest")
        public boolean isImportRequest() {
            return importRequest;
        }

        public void setImportRequest(boolean importRequest) {
            this.importRequest = importRequest;
        }

        @JsonGetter("owner_inn")
        public String getOwnerInn() {
            return ownerInn;
        }

        public void setOwnerInn(String ownerInn) {
            this.ownerInn = ownerInn;
        }

        @JsonGetter("participant_inn")
        public String getParticipantInn() {
            return participantInn;
        }

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }

        @JsonGetter("producer_inn")
        public String getProducerInn() {
            return producerInn;
        }

        public void setProducerInn(String producerInn) {
            this.producerInn = producerInn;
        }

        @JsonGetter("production_date")
        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        @JsonGetter("production_type")
        public String getProductionType() {
            return productionType;
        }

        public void setProductionType(String productionType) {
            this.productionType = productionType;
        }

        @JsonGetter("products")
        public Product[] getProducts() {
            return products;
        }

        public void setProducts(Product[] products) {
            this.products = products;
        }

        @JsonGetter("reg_date")
        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        @JsonGetter("reg_number")
        public String getRegNumber() {
            return regNumber;
        }

        public void setRegNumber(String regNumber) {
            this.regNumber = regNumber;
        }
    }

    public static class Description {
        private String participantInn;

        public Description(String participantInn) {
            this.participantInn = participantInn;
        }

        @JsonGetter("participant_inn")
        public String getParticipantInn() {
            return participantInn;
        }

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }
    }

    public static class Product {
        private String certificateDocument;
        private String certificateDocumentDate;
        private String certificateDocumentNumber;
        private String ownerInn;
        private String producerInn;
        private String productionDate;
        private String tnvedCode;
        private String uitCode;
        private String uituCode;

        public Product(String certificateDocument, String certificateDocumentDate, String certificateDocumentNumber, String ownerInn, String producerInn, String productionDate, String tnvedCode, String uitCode, String uituCode) {
            this.certificateDocument = certificateDocument;
            this.certificateDocumentDate = certificateDocumentDate;
            this.certificateDocumentNumber = certificateDocumentNumber;
            this.ownerInn = ownerInn;
            this.producerInn = producerInn;
            this.productionDate = productionDate;
            this.tnvedCode = tnvedCode;
            this.uitCode = uitCode;
            this.uituCode = uituCode;
        }

        @JsonGetter("certificate_document")
        public String getCertificateDocument() {
            return certificateDocument;
        }

        public void setCertificateDocument(String certificateDocument) {
            this.certificateDocument = certificateDocument;
        }

        @JsonGetter("certificate_document_date")
        public String getCertificateDocumentDate() {
            return certificateDocumentDate;
        }

        public void setCertificateDocumentDate(String certificateDocumentDate) {
            this.certificateDocumentDate = certificateDocumentDate;
        }

        @JsonGetter("certificate_document_number")
        public String getCertificateDocumentNumber() {
            return certificateDocumentNumber;
        }

        public void setCertificateDocumentNumber(String certificateDocumentNumber) {
            this.certificateDocumentNumber = certificateDocumentNumber;
        }

        @JsonGetter("owner_inn")
        public String getOwnerInn() {
            return ownerInn;
        }

        public void setOwnerInn(String ownerInn) {
            this.ownerInn = ownerInn;
        }

        @JsonGetter("producer_inn")
        public String getProducerInn() {
            return producerInn;
        }

        public void setProducerInn(String producerInn) {
            this.producerInn = producerInn;
        }

        @JsonGetter("production_date")
        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        @JsonGetter("tnved_code")
        public String getTnvedCode() {
            return tnvedCode;
        }

        public void setTnvedCode(String tnvedCode) {
            this.tnvedCode = tnvedCode;
        }

        @JsonGetter("uit_code")
        public String getUitCode() {
            return uitCode;
        }

        public void setUitCode(String uitCode) {
            this.uitCode = uitCode;
        }

        @JsonGetter("uitu_code")
        public String getUituCode() {
            return uituCode;
        }

        public void setUituCode(String uituCode) {
            this.uituCode = uituCode;
        }
    }

    public static class RateLimiter {
        private final int maxRequestPerWindow;
        private final long windowSizeInMillis;
        private final Map<String, Window> store = new HashMap<>();

        public RateLimiter(int maxRequestPerWindow, long windowSizeInMillis) {
            this.maxRequestPerWindow = maxRequestPerWindow;
            this.windowSizeInMillis = windowSizeInMillis;
        }

        public synchronized boolean isAllowed(String clientId) {
            long currentTimeMillis = System.currentTimeMillis();
            Window window = store.get(clientId);

            if (window == null || window.getStartTime() < currentTimeMillis - windowSizeInMillis) {
                window = new Window(currentTimeMillis, 0);
            }

            if (window.getRequestCount() >= maxRequestPerWindow) {
                return false;
            }

            window.setRequestCount(window.getRequestCount() + 1);
            store.put(clientId, window);
            return true;
        }

        private static class Window {
            private final long startTime;
            private int requestCount;

            public Window(long startTime, int requestCount) {
                this.startTime = startTime;
                this.requestCount = requestCount;
            }

            public long getStartTime() {
                return startTime;
            }

            public int getRequestCount() {
                return requestCount;
            }

            public void setRequestCount(int requestCount) {
                this.requestCount = requestCount;
            }
        }
    }
}
