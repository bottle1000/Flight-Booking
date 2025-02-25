package flight_booking.demo.s3;

import flight_booking.demo.BaseTest;
import flight_booking.demo.domain.payment.entity.Payment;
import flight_booking.demo.s3.service.S3UploadService;
import mockuser.WithMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.mockito.Mockito.*;

@WithMockUser
@SpringBootTest
@Transactional
public class S3UploadTest extends BaseTest {
    @Autowired
    private S3UploadService service;

    @MockitoBean
//    @Autowired
    private S3Client s3Client;

    @Captor
    private ArgumentCaptor<PutObjectRequest> putObjectCaptor;
    private Payment mockPayment;
    private UUID paymentUid;

    @BeforeEach
    void setup() {
        paymentUid = UUID.randomUUID();
        mockPayment = Mockito.mock(Payment.class);
        when(mockPayment.getUid()).thenReturn(paymentUid.toString());
        when(mockPayment.getCreatedAt()).thenReturn(LocalDateTime.of(2024, 2, 25, 15, 30, 12));
    }

    @Test
    public void s3DummyUpload() {
        // Given
        String jsonData = "{\"message\": \"Test invoice upload\"}";

        // When
        service.upload(mockPayment, jsonData);

        // Then
        verify(s3Client, times(1)).putObject(putObjectCaptor.capture(), any(RequestBody.class));

        PutObjectRequest capturedRequest = putObjectCaptor.getValue();

        String expectedFileName = "payment_meta_data/"
                + paymentUid.toString() + "_"
                + mockPayment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ".json";

        System.out.println("Captured S3 Key: " + capturedRequest.key());

        assert capturedRequest.bucket().equals("flightbookings3");
        assert capturedRequest.key().equals(expectedFileName);
        assert capturedRequest.contentType().equals("application/json");
    }
}
