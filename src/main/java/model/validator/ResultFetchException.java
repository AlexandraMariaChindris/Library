package model.validator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResultFetchException extends RuntimeException{

    private final List<String> errors;

    public ResultFetchException(List<String> errors) {
        super("Failed to fetch result as operation encountered errors!");
        this.errors = errors;
    }

    @Override
    public String toString(){
        //pe fiecare obiect din lista il mapam la toString-ul lui
        return errors.stream().map(Objects::toString).collect(Collectors.joining(",")) + super.toString();
    }
}
