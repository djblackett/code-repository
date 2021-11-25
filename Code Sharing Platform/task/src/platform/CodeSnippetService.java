package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeSnippetService {

    SnippetRepository snippetRepository;

    @Autowired
    public CodeSnippetService(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    public void newSnippet(CodeSnippet snippet) {
        snippetRepository.save(snippet);
    }

    public Optional<CodeSnippet> getCodeSnippetByUuid(String id) {
       return snippetRepository.getCodeSnippetByUuid(id);
    }

    public List<CodeSnippet> getLatestCodeSnippets() {
        return snippetRepository.getLatestCodeSnippets();
    }

    public void deleteSnippet(String uuid) {
        snippetRepository.deleteCodeSnippetByUuid(uuid);
    }

    public void updateTime(long time, String uuid) {
        snippetRepository.updateTimeColumn(time, uuid);
    }

    public void updateViewsColumn(long views, String uuid) {
        snippetRepository.updateViewsColumn(views, uuid);
    }

}
