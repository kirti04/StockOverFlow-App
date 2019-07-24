package com.example.stackproject.di.module;
import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity";
    }
}
