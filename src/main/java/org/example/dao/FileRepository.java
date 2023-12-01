package org.example.dao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Component
public class FileRepository {
    @Value("${data.file.filename}")
    private String pathToFile;

    public void save(byte[] stream) throws IOException {
        OutputStream output = Files.newOutputStream(Path.of(pathToFile));
        output.write(stream);
    }
}
