package portfolio.projects.chillblog.events;

import portfolio.projects.chillblog.model.Post;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class PostCreationEvent {
        private final Post post;
}
