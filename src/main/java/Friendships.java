import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Friendships {
    
    @VisibleForTesting
    Map<String, List<String>> friendships = new HashMap<>();
    
    public void makeFriends(String person1, String person2) {
        if ( person1 == null || person2 == null || StringUtils.isBlank(person1) || StringUtils.isBlank(person2) ) {
            throw new IllegalArgumentException();
        }
        addFriend(person1, person2);
        addFriend(person2, person1);
    }
    
    public List<String> getFriendsList(String person) {
        if ( person == null || StringUtils.isBlank(person) ) {
            throw new IllegalArgumentException();
        }
        return friendships.containsKey(person) ? friendships.get(person) : new ArrayList<>();
    }
    
    public boolean areFriends(String person1, String person2) {
        return friendships.get(person1)
                .contains(person2);
    }
    
    private void addFriend(String person, String friend) {
        if ( friendships.containsKey(person) ) {
            List<String> friends = friendships.get(person);
            friends.add(friend);
            friendships.put(person, friends);
        } else {
            List<String> friends = Collections.singletonList(friend);
            friendships.put(person, friends);
        }
    }
    
}