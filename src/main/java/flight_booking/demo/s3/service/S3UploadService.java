package flight_booking.demo.s3.service;

import flight_booking.demo.domain.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final S3Client s3Client;
    private final String bucketName;

    public void upload(Payment payment, String json) {
        String fileName = "payment_meta_data/" + payment.getUid() + "_" +
                payment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+".json";

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType("application/json")
                .build();

        s3Client.putObject(request, RequestBody.fromString(json));
    }
}