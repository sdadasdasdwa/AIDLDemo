package com.example.server;

import android.os.MemoryFile;

import java.io.FileDescriptor;
import java.io.IOException;

public class MemoryFileUtils {
    /**
     * 创建一个 MemoryFile 实例。
     *
     * @param name   文件名
     * @param length 文件长度
     * @return MemoryFile 实例
     */
    public static MemoryFile createMemoryFile(String name, int length) {
        try {
            return new MemoryFile(name, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 MemoryFile 的文件描述符。
     *
     * @param memoryFile MemoryFile 实例
     * @return FileDescriptor 对象
     */
    public static FileDescriptor getFileDescriptor(MemoryFile memoryFile) {
        return (FileDescriptor) ReflectUtils.invoke(
                "android.os.MemoryFile",
                memoryFile,
                "getFileDescriptor"
        );
    }
}