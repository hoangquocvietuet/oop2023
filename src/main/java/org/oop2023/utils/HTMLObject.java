package org.oop2023.utils;

public class HTMLObject {
    private String content;
    private String tag;
    private int liLevel;

    /**
     * Default constructor.
     */
    public HTMLObject() {
        content = "";
        tag = "";
        liLevel = 0;
    }

    /**
     * Construct the object content.
     * @param content The content
     */
    public HTMLObject(String content) {
        content = modifyNonHTMLTags(content);
        content = "<html>" + content + "</html>";
        this.tag = "";
        this.liLevel = 0;
        tag = content.substring(0, content.indexOf('>') + 1);
        String tagRaw = removeAttribute(tag);
        this.content = content.substring(tag.length(), content.length() - tagRaw.length() - 1);
        this.tag = removeAttribute(tag);
    }

    /**
     * Construct the object content and the level of li's.
     * @param content The content
     * @param liLevel The liLevel
     */
    private HTMLObject(String content, int liLevel) {
        content = modifyNonHTMLTags(content);
        this.content = content;
        this.liLevel = liLevel;
        tag = content.substring(0, content.indexOf('>') + 1);
        String tagRaw = removeAttribute(tag);
        this.content = content.substring(tag.length(), content.length() - tagRaw.length() - 1);
        this.tag = removeAttribute(tag);
    }

    /**
     * Get the content of the object.
     * @return The content
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        if (tag.equals("<h1>") || tag.equals("<h2>") || tag.equals("<h3>") || tag.equals("<li>")) ret.append("\n");
//        System.out.println(content);
        int nextLiLevel = liLevel;
        if (tag.equals("<h1>")) ret.append("@\t");
        else if (tag.equals("<h2>"))
            ret.append("*\t");
        else if (tag.equals("<h3>")) ret.append("#\t");
        else if (tag.equals("<ul>") || tag.equals("<ol>"))
            nextLiLevel++;
        else if (tag.equals("<li>")) {
            if (liLevel == 1)
                ret.append("\t-\t");
            else ret.append("\t\t+\t");
        }
        int curPos = 0;
        while (curPos < content.length() && content.charAt(curPos) != '<') {
            ret.append(content.charAt(curPos));
            curPos++;
        }
        for (int i = curPos; i + 1 < content.length(); i++) {
            if (content.charAt(i) != '<' || content.charAt(i + 1) == '/') continue;
            String nextTag = content.substring(i, content.indexOf('>', i) + 1);
            nextTag = removeAttribute(nextTag);
            String endNextTag = getCloseTag(nextTag);
            int level = 0;
            for (int j = i + 1; j < content.length(); j ++) {
                if (content.charAt(j) == '<' && content.charAt(j + 1) != '/') {
                    String nextTag2 = content.substring(j, content.indexOf('>', j) + 1);
                    nextTag2 = removeAttribute(nextTag2);
                    String endNextTag2 = getCloseTag(nextTag2);
                    if (nextTag2.equals(nextTag)) {
                        level++;
                        j = content.indexOf(endNextTag2, j) - 1;
                    }
                }
                else if (content.charAt(j) == '<' && content.charAt(j + 1) == '/') {
                    String nextTag2 = content.substring(j, content.indexOf('>', j) + 1);
                    if (nextTag2.equals(endNextTag)) {
                        if (level == 0) {
                            ret.append(new HTMLObject(
                                    content.substring(i,
                                            content.indexOf(endNextTag, j) + endNextTag.length()),
                                    nextLiLevel));
                            i = content.indexOf(endNextTag, j) + endNextTag.length() - 1;
                            break;
                        }
                        else {
                            level--;
                            j = content.indexOf(endNextTag, j) + endNextTag.length() - 1;
                        }
                    }
                }
            }
        }
        return ret.toString();
    }

    private String modifyNonHTMLTags(String content) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '<') {
                if (content.substring(i).startsWith("<h1")
                || content.substring(i).startsWith("<h2")
                || content.substring(i).startsWith("<h3")
                || content.substring(i).startsWith("<ul")
                || content.substring(i).startsWith("<ol")
                || content.substring(i).startsWith("<li")
                || content.substring(i).startsWith("<i")
                || content.substring(i).startsWith("</")) {
                    ret.append(content.charAt(i));
                    continue;
                }
                if (content.substring(i).startsWith("<br")) {
                    ret.append("\n");
                    i += 3;
                    continue;
                }
                ret.append('[');
                for (int j = i + 1; j < content.length(); j++) {
                    if (content.charAt(j) == '>') {
                        ret.append(']');
                        i = j;
                        break;
                    }
                    if (content.charAt(j) == '<') {
                        i = j - 1;
                        break;
                    }
                    ret.append(content.charAt(j));
                }
                continue;
            }
            ret.append(content.charAt(i));
        }
        return ret.toString();
    }

    /**
     * Remove the attribute of an HTML tag.
     * @return The tag without attributes
     */
    private String removeAttribute(String tag) {
        int i = 0;
        while (i < tag.length() && tag.charAt(i) != ' ') i++;
        if (i < tag.length()) {
            tag = tag.substring(0, i);
            tag += ">";
        }

        return tag;
    }

    /**
     * Get the close tag of an HTML tag.
     * @param tag The tag
     * @return The close tag
     */
    private String getCloseTag(String tag) {
        return tag.replace("<", "</");
    }

    /**
     * Test client.
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        HTMLObject htmlObject = new HTMLObject("<h1>slay</h1><h3><i>/slei/</i></h3><h2>ngoại động từ slew; slain</h2><ul><li>(thơ ca); (văn học);(đùa cợt) giết</li></ul>");
        System.out.println(htmlObject.toString());
    }
}