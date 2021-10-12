package io.test.accreditation.models;

public class UserAccreditationRequestPayloadDocument {
    private String mimeType;
    private String name;
    private String content;

    public UserAccreditationRequestPayloadDocument(String mimeType, String name, String content) {
        this.mimeType = mimeType;
        this.name = name;
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }
    public String getName() {
        return name;
    }

    public String getFileName(){
        return this.name.replaceAll("([0-9]+).*", "$1");
    }
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "UserAccreditationRequestPayloadDocument{" +
                "mimeType='" + mimeType + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
