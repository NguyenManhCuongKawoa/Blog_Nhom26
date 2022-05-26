package nhom26.util;

import nhom26.model.User;

import org.springframework.data.domain.Page;

public class PagerUser {

    private final Page<User> users;

    public PagerUser(Page<User> users) {
        this.users = users;
    }

    public int getPageIndex() {
        return users.getNumber() + 1;
    }

    public int getPageSize() {
        return users.getSize();
    }

    public boolean hasNext() {
        return users.hasNext();
    }

    public boolean hasPrevious() {
        return users.hasPrevious();
    }

    public int getTotalPages() {
        return users.getTotalPages();
    }

    public long getTotalElements() {
        return users.getTotalElements();
    }

    public Page<User> getUsers() {
        return users;
    }

    public boolean indexOutOfBounds() {
        return getPageIndex() < 0 || getPageIndex() > getTotalElements();
    }

}
