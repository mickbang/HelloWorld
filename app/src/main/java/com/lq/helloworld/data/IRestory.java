package com.lq.helloworld.data;

import java.util.List;

public interface IRestory {

    Dog getDog(String name);

    List<Dog> getDogs();
}
