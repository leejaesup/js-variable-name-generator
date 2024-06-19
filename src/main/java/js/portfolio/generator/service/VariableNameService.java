package js.portfolio.generator.service;

import js.portfolio.generator.dto.ResponseData;

import java.io.IOException;

public interface VariableNameService {
    ResponseData convert(String messages) throws IOException;
}