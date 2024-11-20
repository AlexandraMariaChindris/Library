package repository.book;

import java.util.*;

//folosim clase generice
public class Cache<T> {

    public List<T> storage;

    public List<T> load(){
        return storage;
    }

    //cache-ul va fi incarcat cu totul, nu fiecare elem pe rand
    public void save(List<T> storage){
        this.storage = storage;
    }

    public boolean hasResult(){
        return storage != null;
    }

    public void invalidateCache()
    {
        storage = null;
    }
}
