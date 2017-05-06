package net.brutus5000.bireus;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FileUtils;

import net.brutus5000.bireus.data.Repository;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPreparator {
    public static Path generateTemporaryClientRepositoryV1() throws IOException {
        Path sourcePath = Paths.get("src/test/resources/server_repo/v1").toAbsolutePath();
        Path destPath = Files.createTempDirectory("bireus_");

//        Files.copy(sourcePath, destPath);
        FileUtils.copyDirectory(sourcePath.toFile(), destPath.toFile());

        destPath.resolve(Repository.BIREUS_INTERAL_FOLDER).toFile().mkdir();

        ObjectMapper objectMapper = new ObjectMapper();
        Repository repository = objectMapper.readValue(sourcePath.resolveSibling(Repository.BIREUS_INFO_FILE).toFile(), Repository.class);
        repository.setCurrentVersion("v1");
        repository.setUrl(new URL("http://localhost/BiReUS"));
        objectMapper.writeValue(destPath.resolve(Repository.BIREUS_INTERAL_FOLDER).resolve(Repository.BIREUS_INFO_FILE).toFile(), repository);

        FileUtils.copyFile(
                sourcePath.resolveSibling(Repository.BIREUS_VERSIONS_FILE).toFile(),
                destPath.resolve(Repository.BIREUS_INTERAL_FOLDER).resolve(Repository.BIREUS_VERSIONS_FILE).toFile()
        );

        return destPath;
    }
}
