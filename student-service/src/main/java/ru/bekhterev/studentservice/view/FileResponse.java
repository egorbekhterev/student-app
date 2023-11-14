package ru.bekhterev.studentservice.view;

import lombok.Data;
import org.springframework.core.io.InputStreamResource;

import java.io.Serializable;

@Data
public class FileResponse implements Serializable {

    private String fileName;
    transient InputStreamResource inputStream;
}
