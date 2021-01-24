package scraper;

import org.bson.Document;

public class Senator {
    private String name;
    private String state;
    private String party;
    private String classNum;

    public Senator(String name, String state, String party, String classNum) {
        this.name = name;
        this.state = state;
        this.party = party;
        this.classNum = classNum;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getParty() {
        return party;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String toString() {
        return this.name;
    }

    public Document toDocument() {
        Document doc = new Document("name", this.name)
            .append("state", this.state)
            .append("party", this.party)
            .append("classNum", this.classNum);
        return doc;
    }

    public static Senator getFromDocument(Document doc) {
        String name = doc.getString("name");
        String state = doc.getString("state");
        String party = doc.getString("party");
        String classNum = doc.getString("classNum");
        return new Senator(name, state, party, classNum);
    }

}