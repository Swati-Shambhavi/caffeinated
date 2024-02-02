package com.caffeinated.productcraftsmanservice.service.impl;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageCompressionService {

	public byte[] compressImage(MultipartFile originalImage, float compressionQuality) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		Thumbnails.of(originalImage.getInputStream()).size(100, 100).outputQuality(compressionQuality)
				.toOutputStream(outputStream);

		return outputStream.toByteArray();
	}
}
