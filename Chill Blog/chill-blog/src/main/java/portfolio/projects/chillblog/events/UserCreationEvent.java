package portfolio.projects.chillblog.events;

import portfolio.projects.chillblog.model.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class UserCreationEvent {
        private final User user;
}
