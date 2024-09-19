package dl.pro.java;

public class Book {
    private int Id;
    private String name;
    private String description;
    private String author;


    public String toString() {
        return Id + " : " + name + "\n" +
                "decr : " + description +"\n" +
                "author : " + author;
    }

    public void setId(int id) {
        if (id < 1)
            throw new IllegalArgumentException("Id должно быть больше 0");
        Id = id;
    }

    public void setName(String name) {
        if(name == null)
            throw new IllegalArgumentException("name должно быть задано");
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        if(author.split(" ").length<2)
            throw new IllegalArgumentException("author должно быть больше одного слова");
        this.author = author;
    }

    public Book(int id, String name, String description, String author) {
        Id = id;
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public String getId() {
        return String.valueOf(Id);
    }

    public String getName() {
        return "Назваие : " + name;
    }

    public String getDescription() {
        return "Эта книга про :" + description;
    }

    public String getAuthor() {
        return "Автор книги : " + author;
    }
}
