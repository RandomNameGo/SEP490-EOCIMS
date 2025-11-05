package com.example.sep490_eocims.services;

import com.example.sep490_eocims.dto.response.RoomImportResponse;
import org.springframework.web.multipart.MultipartFile;

public interface RoomService {
    RoomImportResponse importRoomsFromExcel(MultipartFile file);
}
