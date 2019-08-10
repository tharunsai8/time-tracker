package com.yurwar.trainingcourse.util.validator;

import java.util.ArrayList;
import java.util.List;

public class CompositeResult implements Result {
    private List<Result> resultList = new ArrayList<>();

    @Override
    public boolean isValid() {
        boolean valid = true;
        for (Result result : resultList) {
            if (!result.isValid()) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Result result : resultList) {
            if (!result.isValid()) {
                stringBuilder.append(result.getMessage()).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public void addResult(Result result) {
        resultList.add(result);
    }

    public void removeResult(Result result) {
        resultList.remove(result);
    }
}
