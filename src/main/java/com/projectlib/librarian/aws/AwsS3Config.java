package com.projectlib.librarian.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    @Value("${aws.s3.accessKey}")
    private String awsAccessKey;

    @Value("${aws.s3.secretKey}")
    private String awsSecretKey;

    @Value("${aws.s3.region}")
    private String awsRegion;

    @Value("${aws.s3.endpoint}")
    private String awsS3Endpoint;

    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsS3Endpoint, awsRegion))
                .build();
    }

    @Bean
    public AwsS3Service awsS3Service() {
        return new AwsS3Service();
    }

    @Value("${aws.s3.bucket.name}")
    private String awsS3BucketName;

    @Value("${aws.s3.endpoint}")
    private String awsS3BucketUrl;

    @Bean
    public String awsS3BucketName() {
        return awsS3BucketName;
    }

    @Bean
    public String awsS3BucketUrl() {
        return awsS3BucketUrl;
    }
}
