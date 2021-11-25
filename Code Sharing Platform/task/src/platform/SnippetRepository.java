package platform;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SnippetRepository extends CrudRepository<CodeSnippet, UUID> {

    @Query(value="SELECT * FROM code_snippets WHERE time = 0 AND views = 0 ORDER BY id DESC LIMIT 10", nativeQuery=true)
    List<CodeSnippet> getLatestCodeSnippets();

    @Query(value="SELECT MAX(id) FROM code_snippets", nativeQuery = true)
    long getLatestCodeSnippetId();

    @Query(value="SELECT * FROM code_snippets WHERE uuid = ?1", nativeQuery = true)
    Optional<CodeSnippet> getCodeSnippetByUuid(String uuid);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM code_snippets WHERE uuid = ?1", nativeQuery = true)
    void deleteCodeSnippetByUuid(String uuid);

    @Transactional
    @Modifying
    @Query(value="UPDATE code_snippets SET time = ?1 WHERE uuid = ?2", nativeQuery = true)
    void updateTimeColumn(long time, String uuid);

    @Transactional
    @Modifying
    @Query(value="UPDATE code_snippets SET views = ?1 WHERE uuid = ?2", nativeQuery = true)
    void updateViewsColumn(long views, String uuid);
}
