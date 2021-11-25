package platform;

import java.util.List;

public class DataModel {
    public List<CodeSnippet> codeSnippets;

    public DataModel(List<CodeSnippet> codeSnippets) {
        this.codeSnippets = codeSnippets;
    }

    public List<CodeSnippet> getCodeSnippets() {
        return codeSnippets;
    }
}
