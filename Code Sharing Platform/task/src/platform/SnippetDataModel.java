package platform;

public class SnippetDataModel {
    public CodeSnippet codeSnippet;

    public SnippetDataModel(CodeSnippet codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public CodeSnippet getCodeSnippet() {
        return codeSnippet;
    }
}
