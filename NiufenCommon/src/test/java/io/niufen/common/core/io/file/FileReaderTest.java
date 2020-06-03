package io.niufen.common.core.io.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class FileReaderTest {
    String filePath = "/Users/niufen/Desktop/项目组接入信息";
    FileReader fileReader = FileReader.create(filePath);

    @Test
    public void create() {
        String filePath = "/Users/niufen/Desktop/项目组接入信息";
        FileReader fileReader = FileReader.create(filePath);
    }

    @Test
    public void readBytes() {
        byte[] bytes = fileReader.readBytes();

    }

    @Test
    public void readString() {
        String s = fileReader.readString();
        log.debug(s);
    }

    @Test
    public void readLines() {
        List<String> strings = fileReader.readLines();
        for (String string : strings) {
            log.debug(string);
        }
    }

    @Test
    public void readLinesReaderHandler() {
        List<String> strings = fileReader.readLines();
        for (String string : strings) {
            log.debug(string);
        }
    }

    @Test
    public void testReadLines1() {
    }

    @Test
    public void read() {
    }

    @Test
    public void getReader() {
    }

    @Test
    public void getInputStream() {
    }

    @Test
    public void writeToStream() {
    }
}
