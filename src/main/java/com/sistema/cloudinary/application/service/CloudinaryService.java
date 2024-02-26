package com.sistema.cloudinary.application.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(@Value("${cloudinary.cloud-name}") String cloudName,
                             @Value("${cloudinary.api-key}") String apiKey,
                             @Value("${cloudinary.api-secret}") String apiSecret) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        this.cloudinary = new Cloudinary(config);
    }


    public String uploadFile(MultipartFile file) {
        try {
            File  uploadedFile = convertMultiPartToFile(file);

            Map<String, String> options = new HashMap<>();

            String nombreDeLaImagen=file.getOriginalFilename();
            assert nombreDeLaImagen != null;
            String nombreCortado= Objects.requireNonNull(nombreDeLaImagen).substring(0,nombreDeLaImagen.indexOf("."));

            UUID uuid = UUID.randomUUID();
            String nombreFinalEnCloudinary= uuid.toString().concat(nombreCortado);

            options.put("public_id", nombreFinalEnCloudinary);

            Map uploadResult = cloudinary.uploader().upload(uploadedFile, options);

            boolean deleteResult = uploadedFile.delete();
            if (!deleteResult) {
                // Manejar el caso en el que la eliminación del archivo falló
                System.err.println("fallo al eliminar el archivo: " + uploadedFile.getAbsolutePath());
            }

            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException("El archivo no pudo ser cargado!", e);
        }
    }


    public void delete(String id) throws IOException {
         cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    public String extractPublicIdFromImageUrl(String imageUrl) {
        String[] parts = imageUrl.split("/");
        String fileName = parts[parts.length - 1];
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
}

