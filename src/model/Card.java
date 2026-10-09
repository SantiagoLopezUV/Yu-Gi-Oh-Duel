package model;

public class Card {
    private long id;
    private String name;
    private String type;
    private int atk;
    private int def;
    private String imageUrl;
    private boolean used; // Indica si ya se jugó en una ronda anterior

    public Card() {
        this.used = false;
    }

    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getAtk() { return atk; }
    public void setAtk(int atk) { this.atk = atk; }

    public int getDef() { return def; }
    public void setDef(int def) { this.def = def; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}