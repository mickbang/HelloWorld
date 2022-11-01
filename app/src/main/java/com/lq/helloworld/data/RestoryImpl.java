package com.lq.helloworld.data;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RestoryImpl implements IRestory {

    @Inject
    public RestoryImpl() {
    }

    @Override
    public Dog getDog(String name) {
        return new Dog(name);
    }

    @Override
    public List<Dog> getDogs() {
        List<Dog> result = new ArrayList<>();
        result.add(new Dog("1"));
        result.add(new Dog("2"));
        result.add(new Dog("3"));
        result.add(new Dog("4"));
        result.add(new Dog("5"));
        result.add(new Dog("6"));
        return result;
    }
}
