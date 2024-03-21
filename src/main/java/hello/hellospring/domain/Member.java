package hello.hellospring.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Member {
    private AtomicLong id;
    private String name;

    public AtomicLong getId() {
        return id;
    }

    public void setId(AtomicLong id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
